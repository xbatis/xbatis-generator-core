/*
 *  Copyright (c) 2024-2025, Ai东 (abc-127@live.cn).
 *
 *  Licensed under the Apache License, Version 2.0 (the "License").
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and limitations under the License.
 *
 */

package cn.xbatis.generator.core.util;

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
