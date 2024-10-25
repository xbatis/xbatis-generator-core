package cn.mybatis.mp.generator.core.util;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Objects;

public class RuntimeUtils {

    /**
     * 打开指定输出文件目录
     *
     * @param path 输出文件目录
     */
    public static void openDir(String path) {
        String osName = System.getProperty("os.name");
        if (Objects.nonNull(osName)) {
            try {
                if (osName.contains("Mac")) {
                    Runtime.getRuntime().exec("open " + path);
                } else if (osName.contains("Windows")) {
                    Runtime.getRuntime().exec(MessageFormat.format("cmd /c start \"\" \"{0}\"", path));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
