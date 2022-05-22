package com.tdh.mapper;


import com.tdh.common.BaseMapper;
import com.tdh.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    public Menu findById(@Param("id") String id);


    public void deleteById(@Param("id") String id);

    public Map<String ,Object> queryMenuInfoByHref(@Param("href") String href);

    public void updateStatusById(@Param("id") String id, @Param("status") String status, @Param("update_by") String update_by);

    public List<Map<String ,Object>> queryParentInfoByStatus(@Param("level_flag") String level_flag);

    public List<Menu>queryMenuInfo ();

    public List<Menu> queryMenuInfoIndex();

    public List<Map<String,Object>>  queryMenuNameByMenuStatus();

    public Menu queryMenuLevelById(@Param("id") String id);

    public Menu queryMenuInfoByName(@Param("title_name") String title_name);
}