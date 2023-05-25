package crypto.finalproject;

import java.util.Arrays;

public enum RandomType {
    BASIC, SECURE;

    public static RandomType parse(String input) {
        return Arrays.stream(values())
                .filter((it) -> it.name().equalsIgnoreCase(input))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Не найден тип рандома [" + input + "], доступны " + Arrays.toString(values()) + ""));
    }
}