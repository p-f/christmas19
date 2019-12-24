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
package pink.philip.christmas19.snow;

import org.jline.terminal.Terminal;

import java.util.Objects;

/**
 * Renders snow flakes on a screen.
 */
public abstract class SnowRenderingEngine {

    /**
     * The engine calculating and updating flakes.
     */
    protected final SnowEngine source;

    /**
     * The terminal used as the output.
     */
    protected final Terminal output;

    /**
     * Create an instance of this engine.
     *
     * @param source The snow calculating engine.
     * @param output The output terminal.
     */
    protected SnowRenderingEngine(SnowEngine source, Terminal output) {
        this.source = Objects.requireNonNull(source);
        this.output = Objects.requireNonNull(output);
    }

    /**
     * Call a step of the {@link SnowEngine} and output the results on a
     * {@link Terminal}.
     */
    public abstract void stepAndRender();
}
