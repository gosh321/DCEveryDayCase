package com.wairesd.dceverydaycase.tools;

import org.bukkit.ChatColor;
import java.util.regex.Pattern;
import java.lang.reflect.Method;

/**
 * Support colors
 */

public class ColorSupport {
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    private static final Method CHAT_COLOR_OF;

    static {
        Method method = null;
        try {
            method = ChatColor.class.getMethod("of", String.class);
        } catch (NoSuchMethodException ignored) { }
        CHAT_COLOR_OF = method;
    }

    public static String translate(String text) {
        if (text == null) return null;
        var matcher = HEX_PATTERN.matcher(text);
        var buffer = new StringBuffer();
        while (matcher.find()) {
            String replacement = "";
            if (CHAT_COLOR_OF != null) {
                try {
                    replacement = CHAT_COLOR_OF.invoke(null, "#" + matcher.group(1)).toString();
                } catch (Exception ignored) { }
            }
            matcher.appendReplacement(buffer, replacement);
        }
        matcher.appendTail(buffer);
        return ChatColor.translateAlternateColorCodes('&', buffer.toString());
    }
}
