package cn.mybatis.mp.generator.core;

import cn.mybatis.mp.core.mybatis.mapper.MybatisMapper;

import java.io.Serializable;

public abstract class ServiceImpl<T, ID> implements IService<T, ID> {

    protected abstract MybatisMapper<T> getMapper();

    @Override
    public T getById(ID id) {
        return getMapper().getById((Serializable) id);
    }

    @Override
    public int deleteById(ID id) {
        return getMapper().deleteById((Serializable) id);
    }
}
