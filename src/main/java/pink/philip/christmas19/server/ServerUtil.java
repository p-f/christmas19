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
package pink.philip.christmas19.server;

import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import pink.philip.christmas19.server.auth.NoPasswordAuthenticator;
import pink.philip.christmas19.server.command.AuxiliaryCommandFactory;
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
        server.setCommandFactory(new AuxiliaryCommandFactory());
        server.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(
                config.getHostKeyPath()));
        return server;
    }
}
