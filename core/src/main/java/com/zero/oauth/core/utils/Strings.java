package com.zero.oauth.core.utils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import com.zero.oauth.core.LoggerFactory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Strings Utilities.
 *
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Strings {

    /**
     * To String.
     *
     * @param object the object cast to string.
     * @return {@code blank} if null, else {@link Object#toString()} with {@code trim}
     */
    public static String toString(Object object) {
        return object == null ? "" : object.toString().trim();
    }

    /**
     * Check given text is blank or not.
     *
     * @param text the text to check for blank
     * @return {@code True} if blank, else otherwise
     */
    public static boolean isBlank(String text) {
        return text == null || "".equals(text.trim());
    }

    /**
     * Check given text is not blank or not. The reversion of {@link #isBlank(String)}
     *
     * @param text the text to check for blank
     * @return {@code True} if not blank, else otherwise
     */
    public static boolean isNotBlank(String text) {
        return !isBlank(text);
    }

    /**
     * Checks that the specified string reference is not {@code blank}. This method is designed primarily for doing
     * parameter validation in methods and constructors, as demonstrated below:
     * <blockquote><pre>
     * public Foo(Bar bar) {
     *     this.bar = Strings.requireNotBlank(bar);
     * }
     * </pre></blockquote>
     *
     * @param text the text to check for blank
     * @return Trimmed {@code text} if not {@code blank}
     * @throws IllegalArgumentException if {@code obj} is {@code blank}
     */
    public static String requireNotBlank(String text) {
        return requireNotBlank(text, "Given input cannot be empty");
    }

    /**
     * Checks that the specified string reference is not {@code blank}. This method is designed primarily for doing
     * parameter validation in methods and constructors, as demonstrated below:
     * <blockquote><pre>
     * public Foo(Bar bar) {
     *     this.bar = Strings.requireNotBlank(bar, "String cannot blank");
     * }
     * </pre></blockquote>
     *
     * @param text    the text to check for blank
     * @param message the error message will be included in exception
     * @return Trimmed {@code text} if not {@code blank}
     * @throws IllegalArgumentException if {@code obj} is {@code blank}
     */
    public static String requireNotBlank(String text, String message) {
        if (isBlank(text)) {
            throw new IllegalArgumentException(message);
        }
        return text.trim();
    }

    /**
     * Checks that the specified string reference is not {@code blank} then remove all space characters.
     *
     * @param text Given input
     * @return Optimization text
     * @throws IllegalArgumentException if {@code text} is {@code blank}
     */
    public static String optimizeNoSpace(String text) {
        String t = requireNotBlank(text);
        return t.replaceAll("\\s+", "");
    }

    /**
     * Checks that the specified string reference is not {@code blank} and its length greater than given input.
     *
     * @param text      Given input
     * @param minLength Min length
     * @return this text if conforms condition
     * @throws IllegalArgumentException if {@code text} or {@code optimized text} is {@code blank}
     */
    public static String requiredMinLength(String text, int minLength) {
        String t = requireNotBlank(text);
        if (t.length() < minLength) {
            throw new IllegalArgumentException("Text " + text + " length must be greater than " + minLength);
        }
        return t;
    }

    /**
     * Convert {@code string} to {@code int}.
     *
     * @param text     Text to convert
     * @param fallback Default value to fallback
     * @return Int value that corresponding to given text
     */
    public static int convertToInt(String text, int fallback) {
        if (Strings.isBlank(text)) {
            return fallback;
        }
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            LoggerFactory.logger().debug(ex, "Cannot parse {0} to int", text);
            return fallback;
        }
    }

    public static String convertToString(InputStream inputStream) {
        try {
            return FileUtils.convertToByteArray(inputStream).toString(StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException ex) {
            LoggerFactory.logger().trace(ex, "Impossible");
            return null;
        }
    }

}
