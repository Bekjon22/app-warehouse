package uz.pdp.helper;

import java.util.UUID;

public class Utils {

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isEmpty(Object obj) {
        return obj == null;
    }

    public static String generateCode() {
        return UUID.randomUUID().toString();
    }
}
