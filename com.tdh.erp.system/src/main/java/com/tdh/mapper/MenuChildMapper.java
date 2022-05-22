package com.tdh.mapper;


import com.tdh.common.BaseMapper;
import com.tdh.pojo.MenuChild;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@Mapper
public interface MenuChildMapper extends BaseMapper<MenuChild> {

    public MenuChild findById(@Param("id") String id);


    public void deleteById(@Param("id") String id);

    public Map<String ,Object> queryMenuInfoByHref(@Param("href") String href);

    public void updateStatusById(@Param("id") String id, @Param("status") String status, @Param("update_by") String update_by);

    public List<MenuChild> queryMenuInfoNotBind();

    public List<MenuChild> queryMenuInfoBind(@Param("parent_id") String parent_id);

    public void updateParentIdById(@Param("arr") String []arr ,@Param("parent_id") String parent_id,@Param("update_by") String update_by);

    public void deleteParentIdById(@Param("parent_id") String parent_id,@Param("update_by") String update_by);

    public List<MenuChild> queryMenuChildInfo();
}