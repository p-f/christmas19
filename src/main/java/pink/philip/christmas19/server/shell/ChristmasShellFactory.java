package pink.philip.christmas19.server.shell;

import org.apache.sshd.server.channel.ChannelSession;
import org.apache.sshd.server.command.Command;
import org.apache.sshd.server.shell.ShellFactory;

import java.io.IOException;

/**
 * A factory creating new christmas shell instances.
 */
public class ChristmasShellFactory implements ShellFactory {

    @Override
    public Command createShell(ChannelSession channelSession) throws IOException {
        return new TerminalCommandWrapper("Christmas19", null);
    }
}
