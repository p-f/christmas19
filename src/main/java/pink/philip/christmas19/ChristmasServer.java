package pink.philip.christmas19;

import org.apache.sshd.server.SshServer;
import pink.philip.christmas19.server.ServerConfig;
import pink.philip.christmas19.server.ServerUtil;

import java.io.IOException;

/**
 * The main class launching the server.
 */
public class ChristmasServer {

    /**
     * Launch the server.
     *
     * @param args The CLI arguments (unused).
     */
    public static void main(String[] args) throws IOException {
        ServerConfig config = new ServerConfig();
        config.setPort(2019);
        System.setProperty("line.separator", "\r\n");
        final SshServer server = ServerUtil.createServer(config);
        server.start();
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            server.stop();
        }
    }
}
