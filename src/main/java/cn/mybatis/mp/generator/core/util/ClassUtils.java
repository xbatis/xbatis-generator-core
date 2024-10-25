package cn.mybatis.mp.generator.core.util;

public final class ClassUtils {

    public static String getClassSimpleName(String clazz) {
        int dotIndex = clazz.lastIndexOf(".");
        if (dotIndex > 0) {
            return clazz.substring(dotIndex + 1);
        } else {
            return clazz;
        }
    }
}
