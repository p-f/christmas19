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
import org.jline.utils.InfoCmp.Capability;
import pink.philip.christmas19.data.SnowFlake;

import java.io.PrintWriter;

/**
 * A basic rendering engine drawing all flakes in some order.
 */
public class BasicRenderingEngine extends SnowRenderingEngine {

    /**
     * Create an instance of this engine.
     *
     * @param source The snow calculating engine.
     * @param output The output terminal.
     */
    public BasicRenderingEngine(SnowEngine source, Terminal output) {
        super(source, output);
    }

    @Override
    public void stepAndRender() {
        source.preStep();
        source.step();
        final PrintWriter writer = output.writer();
        for (SnowFlake flake : source.getFlakes()) {
            if (flake.getPosRow() < 0) continue;
            output.puts(Capability.cursor_address, flake.getPosRow(),
                    flake.getPosCol());
            writer.append("*");
        }
        final int rows = source.getSize().getRows();
        for (int col = 0; col < source.getSize().getColumns(); col++) {
            final int fallenFlakes = source.getFallenFlakesPerCol(col);
            for (int row = 0; row < fallenFlakes; row++) {
                output.puts(Capability.cursor_address, rows - row,
                        col);
                writer.append("*");
            }
        }
        output.flush();
    }
}
