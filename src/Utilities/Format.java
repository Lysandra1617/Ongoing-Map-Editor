package Utilities;

import java.awt.*;
import java.util.ArrayList;

public interface Format {

    //Gets width/height of string
    static int stringWidth(String string, Graphics2D g) {
        return g.getFontMetrics().stringWidth(string);
    }
    static int stringHeight(Graphics2D g) {
        return g.getFontMetrics().getHeight();
    }

    //Not sure if this actually works, probably doesn't not going to lie
    default boolean overfill(int containerWidth, String string, Graphics2D g) {
        return stringWidth(string, g) > containerWidth;
    }
    default ArrayList<String> reformatString(String string, int containerWidth, int containerHeight, Graphics2D g) {
        ArrayList<String> strings = new ArrayList<>();

        StringBuilder substring = new StringBuilder();
        int stringIndex = 0;
        int stringLength = string.length();

        while (stringIndex < stringLength) {

            substring.append(string.charAt(stringIndex));
            stringIndex++;

            if (overfill(containerWidth, String.valueOf(substring), g)) {
                substring.deleteCharAt(stringIndex);
                strings.add(String.valueOf(substring));
                substring = new StringBuilder("");
                stringIndex--;

                if (strings.size() * stringHeight(g) > containerHeight) {
                    strings.remove(strings.size() - 1);
                    break;
                }
            }
        }

        return strings;
    }

    //If you want to center something horizontally or vertically, these functions are used
    static int getCenteredX(int containerX, int containerWidth, int objectWidth) {
        return containerX + containerWidth / 2 - objectWidth / 2;
    }
    static int getCenteredY(int containerY, int containerHeight, int objectHeight) {
        return containerY + containerHeight / 2 + objectHeight / 2;
    }
}
