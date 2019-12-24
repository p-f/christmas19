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
