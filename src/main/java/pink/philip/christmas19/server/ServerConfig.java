package pink.philip.christmas19.server;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A configuration for server instances.
 */
public class ServerConfig {

    /**
     * Server port.
     */
    private int port = 22;

    /**
     * Server host.
     */
    private String host = "localhost";

    /**
     * The host key.
     */
    private Path hostKeyPath = Paths.get(System.getProperty("user.dir"),
            "christmasHostKey");

    /**
     * Server port.
     */
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Server host.
     */
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    /**
     * The host key.
     */
    public Path getHostKeyPath() {
        return hostKeyPath;
    }

    public void setHostKeyPath(Path hostKeyPath) {
        this.hostKeyPath = hostKeyPath;
    }
}
