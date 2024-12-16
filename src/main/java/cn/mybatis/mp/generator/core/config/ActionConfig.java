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

package cn.mybatis.mp.generator.core.config;

import cn.mybatis.mp.generator.core.database.meta.EntityInfo;
import lombok.Getter;

@Getter
public class ActionConfig {
    /**
     * 是否启用
     */
    private boolean enable = true;

    /**
     * 是否开启注释
     */
    private boolean comment = true;

    /**
     * 控制器父类
     */
    private String superClass;
    /**
     * 注入service
     */
    private boolean injectService = true;
    /**
     * 是否含有泛型
     */
    private boolean generic = false;
    /**
     * 新增
     */
    private boolean enableSave = true;
    /**
     * 新增的方法名
     */
    private String saveMethodName = "save";
    /**
     * 新增的path
     */
    private String saveUriPath = "/save";
    /**
     * 修改
     */
    private boolean enableUpdate = true;
    /**
     * 修改的方法名
     */
    private String updateMethodName = "update";
    /**
     * 修改的path
     */
    private String updateUriPath = "/update";
    /**
     * 删除
     */
    private boolean enableDelete = true;
    /**
     * 删除的方法名
     */
    private String deleteMethodName = "delete";
    /**
     * 删除的path
     */
    private String deleteUriPath = "/delete";
    /**
     * 分页
     */
    private boolean enableFind = true;
    /**
     * 分页的方法名
     */
    private String findMethodName = "find";
    /**
     * 分页的path
     */
    private String findUriPath = "/find";
    /**
     * 单个查询
     */
    private boolean enableGet = true;
    /**
     * 单个查询的方法名
     */
    private String getMethodName = "get";
    /**
     * 单个查询的path
     */
    private String getUriPath = "/get";
    /**
     * 实体类包名
     */
    private String packageName = "action";
    /**
     * mapper后缀
     */
    private String suffix = "Action";
    /**
     * save update 等返回的类型
     */
    private String returnClass;
    /**
     * 返回的名字
     */
    private String returnClassName = "Object";
    /**
     * 是否开启 swagger
     */
    private boolean swagger = false;

    /**
     * 设置是否启用
     */
    public ActionConfig enable(boolean enable) {
        this.enable = enable;
        return this;
    }

    /**
     * 设置是否生成注释
     * @param comment
     * @return
     */
    public ActionConfig comment(boolean comment) {
        this.comment = comment;
        return this;
    }

    /**
     * 控制器父类
     */
    public ActionConfig superClass(String superClass) {
        this.superClass = superClass;
        return this;
    }

    /**
     * 控制器父类
     */
    public ActionConfig superClass(Class superClass) {
        this.superClass = superClass == null ? null : superClass.getName();
        return this;
    }

    /**
     * 是否注入service
     *
     * @param injectService
     * @return
     */
    public ActionConfig injectService(boolean injectService) {
        this.injectService = injectService;
        return this;
    }

    public boolean isInjectService(GeneratorConfig generatorConfig) {
        return injectService && (generatorConfig.getServiceConfig().isEnable() || generatorConfig.getServiceImplConfig().isEnable());
    }

    /**
     * 启用泛型
     */
    public ActionConfig generic(boolean generic) {
        this.generic = generic;
        return this;
    }

    /**
     * 是否生成save方法
     *
     * @param enable
     * @return
     */
    public ActionConfig enableSave(boolean enable) {
        this.enableSave = enable;
        return this;
    }

    public ActionConfig saveMethodName(String saveMethodName) {
        this.saveMethodName = saveMethodName;
        return this;
    }

    public ActionConfig saveUriPath(String saveUriPath) {
        this.saveUriPath = saveUriPath;
        return this;
    }

    /**
     * 是否生成update方法
     *
     * @param enable
     * @return
     */
    public ActionConfig enableUpdate(boolean enable) {
        this.enableUpdate = enable;
        return this;
    }

    public ActionConfig updateMethodName(String updateMethodName) {
        this.updateMethodName = updateMethodName;
        return this;
    }

    public ActionConfig updateUriPath(String updateUriPath) {
        this.updateUriPath = updateUriPath;
        return this;
    }

    /**
     * 是否生成deleteById方法
     *
     * @param enable
     * @return
     */
    public ActionConfig enableDelete(boolean enable) {
        this.enableDelete = enable;
        return this;
    }

    public ActionConfig deleteMethodName(String deleteMethodName) {
        this.deleteMethodName = deleteMethodName;
        return this;
    }

    public ActionConfig deleteUriPath(String deleteUriPath) {
        this.deleteUriPath = deleteUriPath;
        return this;
    }

    /**
     * 是否生成find方法
     *
     * @param enable
     * @return
     */
    public ActionConfig enableFind(boolean enable) {
        this.enableFind = enable;
        return this;
    }

    public ActionConfig findMethodName(String findMethodName) {
        this.findMethodName = findMethodName;
        return this;
    }

    public ActionConfig findUriPath(String findUriPath) {
        this.findUriPath = findUriPath;
        return this;
    }

    /**
     * 是否生成get方法
     *
     * @param enable
     * @return
     */
    public ActionConfig enableGet(boolean enable) {
        this.enableGet = enable;
        return this;
    }

    public ActionConfig getMethodName(String getMethodName) {
        this.getMethodName = getMethodName;
        return this;
    }

    public ActionConfig getUriPath(String getUriPath) {
        this.getUriPath = getUriPath;
        return this;
    }

    /**
     * 控制器的包名
     *
     * @param packageName
     * @return
     */
    public ActionConfig packageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    /**
     * 控制器的后缀
     *
     * @param suffix
     * @return
     */
    public ActionConfig suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    /**
     * 控制器save,update,...等返回的类
     *
     * @param returnClass
     * @return
     */
    public ActionConfig returnClass(String returnClass) {
        this.returnClass = returnClass;
        int dotIndex = returnClass.lastIndexOf(".");
        if (dotIndex > 0) {
            this.returnClassName = returnClass.substring(dotIndex + 1);
        } else {
            this.returnClassName = returnClass;
        }
        return this;
    }

    /**
     * 控制器save,update,...等返回的类
     *
     * @param returnClass
     * @return
     */
    public ActionConfig returnClass(Class returnClass) {
        return this.returnClass(returnClass.getName());
    }

    /**
     * 是否开启 swagger
     *
     * @param enable
     * @return
     */
    public ActionConfig swagger(boolean enable) {
        this.swagger = enable;
        return this;
    }

    public String injectServiceClassName(GeneratorConfig generatorConfig, EntityInfo entityInfo) {
        if (generatorConfig.getServiceConfig().isEnable()) {
            return entityInfo.getServiceName();
        }
        if (generatorConfig.getServiceImplConfig().isEnable()) {
            return entityInfo.getServiceImplName();
        }
        throw new RuntimeException("service层未开启");
    }

    public boolean isComment() {
        return comment;
    }
}
