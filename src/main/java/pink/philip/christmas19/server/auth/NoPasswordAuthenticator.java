/*
 * Copyright © 2019 Philip Fritzsche (p-f@users.noreply.github.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pink.philip.christmas19.server.auth;

import org.apache.sshd.server.auth.AsyncAuthException;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.auth.password.PasswordChangeRequiredException;
import org.apache.sshd.server.session.ServerSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A password authenticator accepting only empty passwords.
 */
public class NoPasswordAuthenticator implements PasswordAuthenticator {

    /**
     * Logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NoPasswordAuthenticator.class);

    @Override
    public boolean authenticate(String username, String password,
                                ServerSession serverSession) throws PasswordChangeRequiredException, AsyncAuthException {
        LOGGER.info("Auth from {} as {}({}), Pass={} ({})",
                serverSession.getClientAddress(),
                username, username.length(), password, password.length());
        return password.isEmpty();
    }
}
