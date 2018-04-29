package com.zhc.core.dao;

import java.io.Serializable;
import java.util.List;


public interface BaseDao<T> {
	/**
	 * 查询数据
	 * @param cls	实体类
	 * @return		实体类对应的表的全部数据
	 */
	List<T> list(Class<T> cls);

	/**
	 * 删除数据
	 * @param entity 实体
	 */
	void delete(T entity);

	/**
	 * 增加或更新数据
	 * @param entity
	 */
	void saveOrUpdate(T entity);

	/**
	 *
	 * @param cls	实体类
	 * @param id	实体类对应的表的主键
	 * @return		通过主键找到的对象
	 */
	T get(Class<T> cls, Serializable id);
}