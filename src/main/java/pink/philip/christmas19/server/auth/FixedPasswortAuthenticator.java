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

import java.util.Objects;

/**
 * A password authenticator accepting only one specific passwords.
 */
public class FixedPasswortAuthenticator implements PasswordAuthenticator {

    /**
     * Logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FixedPasswortAuthenticator.class);

    /**
     * The expected password.
     */
    private final String expectedPassword;

    /**
     * Create a new instance of this authenticator using an empty password.
     */
    public FixedPasswortAuthenticator() {
        this("");
    }

    /**
     * Create a new instance of this authenticator using a specific password.
     *
     * @param expectedPassword The expected password.
     */
    public FixedPasswortAuthenticator(String expectedPassword) {
        this.expectedPassword = Objects.requireNonNull(expectedPassword);
    }

    @Override
    public boolean authenticate(String username, String password,
                                ServerSession serverSession) throws PasswordChangeRequiredException, AsyncAuthException {
        LOGGER.info("Auth from {} as {}({}), Pass={} ({})",
                serverSession.getClientAddress(),
                username, username.length(), password, password.length());
        return expectedPassword.equals(password);
    }
}
