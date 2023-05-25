package crypto.finalproject;

import java.security.SecureRandom;
import java.util.Random;

public class RandomFactory {
    public Random getRandom(RandomType type) {
        switch (type) {
            case BASIC:
                return new Random();
            case SECURE:
                return new SecureRandom();
            default:
                throw new RuntimeException();
        }
    }
}