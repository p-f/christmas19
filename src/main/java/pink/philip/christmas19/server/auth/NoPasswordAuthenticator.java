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
