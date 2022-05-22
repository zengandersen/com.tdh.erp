package com.tdh.service;


import com.tdh.common.BaseService;
import com.tdh.common.CommonUtil;
import com.tdh.common.Config;
import com.tdh.common.PageList;
import com.tdh.mapper.MenuChildMapper;
import com.tdh.mapper.MenuMapper;
import com.tdh.pojo.Menu;
import com.tdh.pojo.MenuChild;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service("menuChildService")
public class MenuChildService extends BaseService<MenuChild,MenuChildMapper> {

    @Resource
    private MenuChildMapper menuChildMapper;

    @Resource
    private MenuMapper menuMapper;

    /**
     * 创建菜单
     * @param
     */
    public void insert(HttpServletRequest request,String user_code){
        MenuChild menu = new MenuChild();
        menu.setId(CommonUtil.createUUIDNoFlag());
        String parent_id = String.valueOf(request.getParameter("parents"));
        if("null".equals(parent_id)){
            menu.setParent_id("");
        }else{
            menu.setParent_id(parent_id);
        }
        menu.setTitle_name( String.valueOf(request.getParameter("title_name")));
        menu.setHref(String.valueOf(request.getParameter("href")));
        menu.setIcon(String.valueOf(request.getParameter("icon")));
        menu.setTarget(String.valueOf(request.getParameter("target")));
        menu.setLevel_flag(String.valueOf(request.getParameter("level_flag")));
        menu.setStatus(Config.statusClass.open);
        menu.setCreate_by(user_code);
        menuChildMapper.insert(menu);
    }

    public void update(HttpServletRequest request,String user_code){
        MenuChild menu = new MenuChild();
        menu.setId(String.valueOf(request.getParameter("id")));
        String parent_id = String.valueOf(request.getParameter("parents"));
        if("null".equals(parent_id)){
            menu.setParent_id("");
        }else{
            menu.setParent_id(parent_id);
        }
        menu.setTitle_name( String.valueOf(request.getParameter("title_name")));
        menu.setHref(String.valueOf(request.getParameter("href")));
        menu.setIcon(String.valueOf(request.getParameter("icon")));
        menu.setTarget(String.valueOf(request.getParameter("target")));
        menu.setLevel_flag(String.valueOf(request.getParameter("level_flag")));
        menu.setUpdate_by(user_code);
        menuChildMapper.update(menu);
    }

    /**
     * 分页查询数据
     * @param params
     * @return
     */
    public PageList<Map<String, Object>> selectPageMap(MenuChild params){
        return this.selectMapPageSize(params, params.getCurPage(), params.getPageSize());
    }

    /**
     * 根据id获取菜单数据
     * @param id
     * @return
     */
    public MenuChild queryInfobyId(String id){
        MenuChild menu = menuChildMapper.findById(id);
        return menu;
    }

    /**
     * 根据id删除数据
     * @param id
     */
    public void deleteById(String id){
        menuChildMapper.deleteById(id);
    }

    public boolean queryMenuInfoByHref(String href){
        boolean flag = false;
        Map<String ,Object> map  = menuChildMapper.queryMenuInfoByHref(href);
        int count = Integer.parseInt(String.valueOf(map.get("count")));
        if(count == 0){
            flag = true;
        }
        return flag;
    }

    public void updateStatusById(String id,String status,String update_by){
        menuChildMapper.updateStatusById(id,status,update_by);
    }

    public void deleteInfoById(String id){
        menuChildMapper.deleteById(id);
    }

    /**
     *
     * @return
     */
    public List<Map<String ,Object>> queryParentInfoByStatus(String level_flag){
        List<Map<String ,Object>>result = menuMapper.queryParentInfoByStatus(level_flag);
        return result;
    }

    /**
     *
     * @return
     */
    public List<Menu> querMenuInfo(){
        List<Menu> result = menuMapper.queryMenuInfo();
        return result;
    }


}
