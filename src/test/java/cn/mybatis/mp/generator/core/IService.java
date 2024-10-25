package cn.mybatis.mp.generator.core;

public interface IService<T, ID> {

    T getById(ID id);

    int deleteById(ID id);
}
