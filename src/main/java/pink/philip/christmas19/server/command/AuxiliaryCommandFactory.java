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
package pink.philip.christmas19.server.command;

import org.apache.sshd.server.channel.ChannelSession;
import org.apache.sshd.server.command.Command;
import org.apache.sshd.server.command.CommandFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pink.philip.christmas19.utils.TextUtils;

/**
 * Provides some additional shell commands.
 */
public class AuxiliaryCommandFactory implements CommandFactory {

    /**
     * Logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AuxiliaryCommandFactory.class);

    @Override
    public Command createCommand(ChannelSession channel, String command) {
        if (command.length() > 20) {
            LOGGER.info("Unrecognized command " + command + " for user " +
                    channel.getSession().getUsername());
            return new PrintAndExit("command-too-long", null,
                    getError("Unrecognized command."));
        }
        switch (command.toLowerCase()) {
            case "about":
                return new PrintAndExit("about", null, getAbout());
            case "senf":
                return new PrintAndExit("egg", null, "Geil");
            default:
        }
        LOGGER.info("Unrecognized command " + command + " for user " +
                channel.getSession().getUsername());
        return new PrintAndExit("unrecognized", null,
                getError("Unrecognized command."));
    }

    /**
     * Get a formatted error message.
     *
     * @return The error message.
     */
    private static String getError(String message) {
        return TextUtils.boxed("ChristmasServer 2019 Error", '!', "\r\n") +
                "\r\n" + message;
    }

    /**
     * Get an about message.
     *
     * @return The formatted about message.
     */
    private static String getAbout() {
        return TextUtils.boxed("ChristmasServer 2019", '*', "\r\n") + "\r\n" +
                "This is ChristmasServer 2019.\r\n" +
                "Copyright(c) 2019 Philip Fritzsche\r\n" +
                "This is free software, release under the Apache License 2.0" +
                "\r\nsee https://github.com/p-f/christmas19" +
                " for sources and license information.";
    }
}
