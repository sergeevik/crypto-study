package crypto.lesson7;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.security.DrbgParameters;
import java.security.SecureRandom;

import static java.security.DrbgParameters.Capability.RESEED_ONLY;

public class SecureRandomHolder {

    static SecureRandom getSecureRandom() throws Exception {
        return SecureRandom.getInstance("DRBG",
                DrbgParameters.instantiation(128, RESEED_ONLY, getCpuRandomByteArray()));
    }

    private static int getCpuRandom() {
        OperatingSystemMXBean operatingSystemMXBean =
                (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return (int) operatingSystemMXBean.getProcessCpuTime() + (int) operatingSystemMXBean.getSystemCpuLoad();
    }

    private static byte[] getCpuRandomByteArray() {
        byte[] result = new byte[440];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) getCpuRandom();
        }
        return result;
    }
}
