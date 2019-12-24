package pink.philip.christmas19.server;

import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import pink.philip.christmas19.server.auth.NoPasswordAuthenticator;
import pink.philip.christmas19.server.shell.ChristmasShellFactory;

/**
 * A utility class creating a server instance.
 */
public class ServerUtil {
    /**
     * Create a server instance.
     *
     * @param config The server config.
     * @return A new server instance.
     */
    public static SshServer createServer(ServerConfig config) {
        SshServer server = SshServer.setUpDefaultServer();
        server.setHost(config.getHost());
        server.setPort(config.getPort());
        server.setPasswordAuthenticator(new NoPasswordAuthenticator());
        server.setShellFactory(new ChristmasShellFactory());
        server.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(
                config.getHostKeyPath()));
        return server;
    }
}
