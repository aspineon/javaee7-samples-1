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
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
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
package org.glassfish.httpsession;

import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.websocket.Extension;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.DefaultServerConfiguration;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfiguration;

/**
 * @author Arun Gupta
 */
// "implements ServerEndpointConfiguration" needs to be here,
// most likely Servlet issue; see http://java.net/jira/browse/GLASSFISH-19551
// (should be reopened, it is currently discussed internally)

// Also providing an implementation of three methods until
// http://java.net/jira/browse/WEBSOCKET_SPEC-128 is fixed.
public class MyServerConfiguration extends DefaultServerConfiguration implements ServerEndpointConfiguration {

    public MyServerConfiguration() {
        super(MyEndpoint.class, "websocket");
    }

    @Override
    public String getNegotiatedSubprotocol(List<String> requestedSubprotocols) {
        return null;
    }

    @Override
    public List<Extension> getNegotiatedExtensions(List<Extension> requestedExtensions) {
        return Collections.emptyList();
    }

    @Override
    public boolean checkOrigin(String originHeaderValue) {
        return true;
    }

    @Override
    public void modifyHandshake(HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession)request.getSession();
        System.out.println(httpSession);
        System.out.println("Invoked from: " + httpSession.getServletContext().getContextPath());
        super.modifyHandshake(request, response);
    }
}