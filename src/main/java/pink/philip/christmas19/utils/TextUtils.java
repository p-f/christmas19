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
package pink.philip.christmas19.utils;

public class TextUtils {

    /**
     * Draw a box around a string.
     *
     * @param msg The string.
     * @param boxChar The box character.
     * @param newLine The new line character.
     * @return The formatted string.
     */
    public static String boxed(String msg, char boxChar, String newLine) {
        final int length = msg.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < (length + 4); i++) {
            sb.append(boxChar);
        }
        sb.append(newLine).append(boxChar);
        for (int i = 0; i < (length + 2); i++) {
            sb.append(' ');
        }
        sb.append(boxChar).append(newLine).append(boxChar).append(' ')
                .append(msg).append(' ').append(boxChar).append(newLine)
                .append(boxChar);
        for (int i = 0; i < (length + 2); i++) {
            sb.append(' ');
        }
        sb.append(boxChar).append(newLine);
        for (int i = 0; i < (length + 4); i++) {
            sb.append(boxChar);
        }
        return sb.toString();
    }
}
