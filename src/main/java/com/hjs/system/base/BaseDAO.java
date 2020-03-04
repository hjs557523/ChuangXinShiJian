package com.hjs.system.base;

import java.util.List;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/3 21:28
 * @Modified By:
 */
public interface BaseDAO<T> {

    /**
     * 保存
     * @param t
     */
    void save(T t);


    /**
     * 更新
     * @param t
     */
    void update(T t);


    /**
     * 删除
     * @param id
     */
    void delete(Integer id);


    /**
     * 查询
     * @param id
     * @return
     */
    T find(Integer id);


    /**
     * 批量保存
     * @param list
     */
    void batchSave(List<T> list);


    /**
     * 批量删除
     * @param idlist
     */
    void batchDelete(List<Integer> idlist);
}
