/*
 *  Copyright (c) 2024-2024, Ai东 (abc-127@live.cn).
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

package cn.mybatis.mp.generator.core.template.engine;

import cn.mybatis.mp.generator.core.template.ITemplateBuilder;
import freemarker.core.PlainTextOutputFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateModelException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FreemarkerTemplateEngine implements TemplateEngine {

    private final Configuration configuration;

    public FreemarkerTemplateEngine() {
        configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setOutputFormat(PlainTextOutputFormat.INSTANCE);
        configuration.setClassForTemplateLoading(FreemarkerTemplateEngine.class, "/");
        try {
            configuration.setSharedVariable("util", new EngineUtil());
        } catch (TemplateModelException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render(ITemplateBuilder templateBuilder) {
        try {
            Template template = configuration.getTemplate(templateBuilder.templateFilePath() + ".ftl");

            File outputFile = new File(templateBuilder.targetFilePath());
            if (outputFile.exists()) {
                if (!templateBuilder.fileCover()) {
                    return;
                }
                outputFile.delete();
            }

            outputFile.getParentFile().mkdirs();
            outputFile.createNewFile();

            try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
                template.process(templateBuilder.contextData(), new OutputStreamWriter(fileOutputStream, templateBuilder.charset()));
            } catch (Exception e) {
                outputFile.delete();
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
