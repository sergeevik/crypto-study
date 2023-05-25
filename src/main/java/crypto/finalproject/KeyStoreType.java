package crypto.finalproject;

import java.nio.file.Path;
import java.util.Random;

public enum KeyStoreType {
    JKS("jks"),
    JCEKS("jceks");

    private final String extension;

    KeyStoreType(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public static KeyStoreType random(Random random) {
        if (random.nextBoolean()) {
            return JKS;
        } else {
            return JCEKS;
        }
    }

    public static KeyStoreType fromFile(Path pathToFile) {
        String fileName = pathToFile.getFileName().toString();
        if (fileName.endsWith("jks")) {
            return JKS;
        } else if (fileName.endsWith("jceks")) {
            return JCEKS;
        } else {
            throw new RuntimeException("Известный тип keyStore");
        }
    }
}