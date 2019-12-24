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
package pink.philip.christmas19.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Provides common resources.
 */
public class Resources {

    /**
     * The name of the config resource file.
     */
    public static final String CONFIG_FILE = "christmas19.properties";

    /**
     * The instance of this object.
     */
    private static Resources instance;

    /**
     * The parsed configuration.
     */
    private Properties properties;

    /**
     * The message.
     */
    private String message;

    /**
     * Initialize this class.
     */
    private Resources() {
        Properties properties = new Properties();
        InputStream config = getClass().getResourceAsStream(CONFIG_FILE);
        if (config == null) {
            System.err.println("Failed to load config.");
            return;
        }
        try {
            properties.load(config);
            config.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read config: ", e);
        }
    }

    /**
     *
     */
    public String getMessage() {
        //return properties.getProperty("christmas19.message", "DEFAULT");
        return "Frohe Weihnachten!";
    }

    /**
     * Get a percentage of screen space to be filled.
     *
     * @return The space in percent ({@code [0, 100]}).
     */
    public byte getScreenFillPercent() {
        return 6;
    }

    /**
     * Get the instance of this class.
     *
     * @return The instance.
     */
    public static synchronized Resources getInstance() {
        if (instance == null) {
            instance = new Resources();
        }
        return instance;
    }
}
