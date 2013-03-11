/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package org.glassfish.jersey.tests.e2e.server.validation.validateexecutable;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateExecutable;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author Michal Gajdos (michal.gajdos at oracle.com)
 */
public class ValidateExecutableBasicTest extends ValidateExecutableAbstractTest {

    /**
     * On METHOD.
     */

    @Path("on-method")
    public static class ValidateExecutableOnMethodsResource {

        @POST
        @Path("validateExecutableDefault")
        @ValidateExecutable
        @Min(0)
        public Integer validateExecutableDefault(@Max(10) final Integer value) {
            return value;
        }

        @POST
        @Path("validateExecutableMatch")
        @ValidateExecutable(ExecutableType.NON_GETTER_METHODS)
        @Min(0)
        public Integer validateExecutableMatch(@Max(10) final Integer value) {
            return value;
        }

        @POST
        @Path("validateExecutableMiss")
        @ValidateExecutable(ExecutableType.CONSTRUCTORS)
        @Min(0)
        public Integer validateExecutableMiss(@Max(10) final Integer value) {
            return value;
        }

        @POST
        @Path("validateExecutableNone")
        @ValidateExecutable(ExecutableType.NONE)
        @Min(0)
        public Integer validateExecutableNone(@Max(10) final Integer value) {
            return value;
        }
    }

    /**
     * On TYPE.
     */

    public static abstract class ValidateExecutableOnType {

        @POST
        @Min(0)
        public Integer validateExecutable(@Max(10) final Integer value) {
            return value;
        }
    }

    @Path("on-type-default")
    @ValidateExecutable
    public static class ValidateExecutableOnTypeDefault extends ValidateExecutableOnType {
    }

    @Path("on-type-match")
    @ValidateExecutable(ExecutableType.NON_GETTER_METHODS)
    public static class ValidateExecutableOnTypeMatch extends ValidateExecutableOnType {
    }

    @Path("on-type-miss")
    @ValidateExecutable(ExecutableType.CONSTRUCTORS)
    public static class ValidateExecutableOnTypeMiss extends ValidateExecutableOnType {
    }

    @Path("on-type-none")
    @ValidateExecutable(ExecutableType.NONE)
    public static class ValidateExecutableOnTypeNone extends ValidateExecutableOnType {
    }

    /**
     * MIXED.
     */

    @Path("mixed-default")
    @ValidateExecutable(ExecutableType.NONE)
    public static class ValidateExecutableMixedDefault {

        @POST
        @Min(0)
        @ValidateExecutable
        public Integer validateExecutable(@Max(10) final Integer value) {
            return value;
        }
    }

    @Path("mixed-none")
    @ValidateExecutable
    public static class ValidateExecutableMixedNone {

        @POST
        @Min(0)
        @ValidateExecutable(ExecutableType.NONE)
        public Integer validateExecutable(@Max(10) final Integer value) {
            return value;
        }
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(ValidateExecutableOnMethodsResource.class,
                ValidateExecutableOnTypeDefault.class,
                ValidateExecutableOnTypeMatch.class,
                ValidateExecutableOnTypeMiss.class,
                ValidateExecutableOnTypeNone.class,
                ValidateExecutableMixedDefault.class,
                ValidateExecutableMixedNone.class);
    }

}
