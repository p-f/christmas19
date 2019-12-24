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
