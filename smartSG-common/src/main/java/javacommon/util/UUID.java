package javacommon.util;

/**
 * @author Andy
 * @since 2016/4/14
 */
public class UUID {

    private UUID() {
    }

    public static String getUUID() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }
}
