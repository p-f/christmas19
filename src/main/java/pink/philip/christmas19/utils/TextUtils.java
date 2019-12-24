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
