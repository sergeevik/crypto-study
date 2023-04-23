package crypto.lesson;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.security.DrbgParameters;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.security.DrbgParameters.Capability.RESEED_ONLY;

public class RandomLesson {
    private static final List<String> forecastList = new ArrayList<>();
    private static Random random;

    public static void main(String[] args) throws Exception {
        forecastList.add("у вас сегодня будет удача в делах!");
        forecastList.add("сегодня хороший день для саморазвития!");

        String name = args[0];
        String randomOption = args[1];

        if ("Basic".equals(randomOption)) {
            random = new Random(getCpuRandom());
        } else if ("Secure".equals(randomOption)) {
            random = SecureRandom.getInstance("DRBG",
                    DrbgParameters.instantiation(128, RESEED_ONLY, getCpuRandom(440))
            );
        }

        int forecastIndex = random.nextInt(forecastList.size());
        System.out.println(name + ", " + forecastList.get(forecastIndex));
    }

    private static int getCpuRandom() {
        OperatingSystemMXBean operatingSystemMXBean =
                (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return (int) operatingSystemMXBean.getProcessCpuTime() + (int) operatingSystemMXBean.getSystemCpuLoad();
    }

    private static byte[] getCpuRandom(int byteArraySize) {
        byte[] result = new byte[byteArraySize];
        for (int i = 0; i < byteArraySize; i++) {
            result[i] = (byte) getCpuRandom();
        }
        return result;
    }
}
