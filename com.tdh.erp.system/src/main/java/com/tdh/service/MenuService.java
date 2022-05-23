package com.tdh.service;



import com.tdh.common.*;
import com.tdh.mapper.MenuChildMapper;
import com.tdh.mapper.MenuMapper;
import com.tdh.mapper.RoleBindMapper;
import com.tdh.mapper.UserMapper;
import com.tdh.pojo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

@Service("menuService")
public class MenuService extends BaseService<Menu,MenuMapper> {

    @Resource
    private MenuMapper menuMapper;
    @Resource
    private MenuChildMapper menuChildMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleBindMapper roleBindMapper;

    /**
     * 创建菜单
     * @param
     */
    public void insert(HttpServletRequest request,String user_code,String main_id,String level) throws Exception{
        Menu menu = new Menu();
        menu.setId(CommonUtil.createUUIDNoFlag());
        menu.setTitle_name( String.valueOf(request.getParameter("title_name")));
        menu.setParent_id(main_id);
        if(Integer.parseInt(level)==Config.integerClass.zero){
            menu.setHref("");
            menu.setImage("");
        }else{
            menu.setHref(String.valueOf(request.getParameter("href")));
            menu.setImage( String.valueOf(request.getParameter("image")));
        }
        menu.setIcon("fa "+String.valueOf(request.getParameter("icon")));
        menu.setTarget(String.valueOf(request.getParameter("target")));
        menu.setStatus(Config.statusClass.open);
        menu.setLevel_flag(level);
        menu.setCreate_by(user_code);
        menuMapper.insert(menu);
    }

    public void update(HttpServletRequest request,String user_code,Menu parent_menu) throws Exception{
        Menu menu = new Menu();
        menu.setId(String.valueOf(request.getParameter("id")));
        menu.setTitle_name( String.valueOf(request.getParameter("title_name")));
        menu.setHref(String.valueOf(request.getParameter("href")));
        menu.setIcon("fa "+String.valueOf(request.getParameter("icon")));
        menu.setParent_id(parent_menu.getId());
        String level = String.valueOf(Integer.parseInt(parent_menu.getLevel_flag())+Config.integerClass.one);
        menu.setLevel_flag(level);
        String image = String.valueOf(request.getParameter("image"));
        String target = String.valueOf(request.getParameter("target"));
        if(StringUtils.isNotEmpty(image)){
            menu.setImage(image);
        }else{
            menu.setImage("");
        }
        if(StringUtils.isNotEmpty(target)){
            menu.setTarget(target);
        }else{
            menu.setTarget("");
        }
        menu.setUpdate_by(user_code);
        menuMapper.update(menu);
    }

    /**
     * 分页查询数据
     * @param params
     * @return
     */
    public PageList<Map<String, Object>> selectPageMap(Menu params) throws Exception{
        return this.selectMapPageSize(params, params.getCurPage(), params.getPageSize());
    }

    /**
     * 根据id获取菜单数据
     * @param id
     * @return
     */
    public Menu queryInfobyId(String id) throws Exception{
        Menu menu = menuMapper.findById(id);
        return menu;
    }

    /**
     * 根据id删除数据
     * @param id
     */
    public void deleteById(String id)throws Exception{
        menuMapper.deleteById(id);
    }

    /**
     * 根据地址获取菜单数据
     * @param href
     * @return
     */
    public boolean queryMenuInfoByHref(String href){
        boolean flag = false;
        Map<String ,Object> map  = menuMapper.queryMenuInfoByHref(href);
        int count = Integer.parseInt(String.valueOf(map.get("count")));
        if(count == 0){
            flag = true;
        }
        return flag;
    }

    /**
     * 状态变更
     * @param id
     * @param status
     * @param update_by
     * @throws Exception
     */
    public void updateStatusById(String id,String status,String update_by) throws Exception{
        menuMapper.updateStatusById(id,status,update_by);
    }

    /**
     * 根据id删除
     * @param id
     * @throws Exception
     */
    public void deleteInfoById(String id) throws Exception{
        menuMapper.deleteById(id);
    }

    /**
     * 获取未绑定的数据
     * @return
     */
    public List<MenuChild> queryMenuInfoNotBind() throws Exception{
        return menuChildMapper.queryMenuInfoNotBind();
    }

    /**
     * 获取已绑定的数据
     * @return
     */
    public List<MenuChild>queryMenuInfoBind(String id) throws Exception{
        return menuChildMapper.queryMenuInfoBind(id);
    }

    /**
     * 查询icon枚举信息
     * @return
     * @throws Exception
     */
    public List<Map<String ,String>> queryIconInfo() throws  Exception{
        String iconid = PropertiesUtils.getProperties("icon");
        String []arr = iconid.split(",");
        List<Map<String,String>> result =new ArrayList<>();
        for(String str :arr){
            Map<String ,String> map = new HashMap<>();
            map.put("iconid",str);
            result.add(map);
        }
        return result;
    }

    /**
     * 更新主主菜单绑定明细菜单
     * @param update_by
     * @param parent_id
     * @param arr
     * @throws Exception
     */
    public void updateParentIdById(String update_by,String parent_id,String []arr)throws  Exception {
        if(null == arr){
            menuChildMapper.deleteParentIdById(parent_id,update_by);
        }else{
            menuChildMapper.updateParentIdById(arr,parent_id,update_by);
        }
    }


    /**
     * 处理菜单数据
     * @throws Exception
     */
    public  Map<String,Object> handleCreateTable(User login_user) throws Exception{
        Map<String,Object> data = new HashMap();
        List<Menu> initMenu = menuMapper.queryMenuInfo();
        List<Menu> menu = new ArrayList<>();
        Map<String ,Object> userMap = userMapper.queryLoginUserOfRoleInfoById(login_user.getUser_code());
        List<RoleBind> roleBindList = roleBindMapper.findBindInfoById(String.valueOf(userMap.get("role_id")));
        if(!CollectionUtils.isEmpty(roleBindList)){
            for(RoleBind a : roleBindList){
                for(Menu b : initMenu){
                    if(a.getMenu_id().equals(b.getId())){
                        b.setLAY_CHECKED(true);
                        menu.add(b);
                    }
                }
            }
        }
        List<SubLeveLTable> subLeveLMenu = new ArrayList<>();
        Map<String ,Object> homeInfo = new HashMap<>();
        homeInfo.put("title",Config.tableClass.index);
        homeInfo.put("href","static/welcome-1.jsp");
        Map<String ,Object> logoInfo = new HashMap<>();
        logoInfo.put("title",Config.tableClass.title);
        logoInfo.put("image",Config.tableClass.logo);
        logoInfo.put("href",Config.tableClass.url);
        List<Map<String ,Object>>menuInfo = new ArrayList<>();
        String menuChildId = "";
        List<SubLeveLTable> rootMenu  = new ArrayList<>();
        for(Menu menulist : menu){
            if(menulist.getLevel_flag().equals(Config.strClassNum.zero)){
                SubLeveLTable sub = this.handleSystemOutObject(menulist);
                rootMenu.add(sub);
                menu.remove(rootMenu);
            }else{
                SubLeveLTable sub = this.handleSystemOutObject(menulist);
                subLeveLMenu.add(sub);
            }
        }
        Collections.sort(rootMenu, order());

        for (SubLeveLTable nav : rootMenu) {

            List<SubLeveLTable> childList = getChild(nav.getId(), subLeveLMenu);
            nav.setChild(childList);
        }
        data.put("homeInfo",homeInfo);
        data.put("logoInfo",logoInfo);
        data.put( "menuInfo" , rootMenu);
        return data;
    }




    public Map<String ,Object> handleQueryMenuList() throws  Exception{
        List<Menu> data = new ArrayList<>();
        List<Menu> menuList= menuMapper.queryMenuInfoIndex();
        List<MenuTableList> menuTable = new ArrayList<>();
        List<MenuTableList> rootMenu = new ArrayList<>();
        List<MenuTableList> menuAllList = new ArrayList<>();

        int index = 0;
        for(int i=0;i<menuList.size();i++){
            MenuTableList obj = new MenuTableList();
            if(Config.strClassNum.zero.equals(menuList.get(i).getLevel_flag())){
                obj = this.handleMenuTableObject(menuList.get(i));
                rootMenu.add(obj);
            }else{
                MenuTableList mub = this.handleMenuTableObject(menuList.get(i));
                menuTable.add(mub);
            }
        }
        Collections.sort(rootMenu, orderTypeTwo());
        List<MenuTableList> menuTwoList = new ArrayList<>();
        for (MenuTableList nav : rootMenu) {
            nav.setId_no(index++);
            nav.setPid_no(Config.integerClass.negative);
            int pid = index;
            menuTwoList= getChildTypeTwo(menuAllList,String.valueOf(nav.getId()), menuTable,index,pid);
        }
        for(MenuTableList men : menuTwoList){
            rootMenu.add(men);
        }
        Map<String ,Object> result = new HashMap<>();
        result.put("code",0);
        result.put("msg","ok");
        result.put("data",rootMenu);
        result.put("count",rootMenu.size());
        return result;
    }

    public Object  fun(List<SubLeveLTable> list, List<Map<String, Object>> result) {
        for (SubLeveLTable map : list) {
            Map<String, Object> data = new HashMap<>();
            data.put("id",map.getId());
            data.put("title", map.getTitle());
            data.put("spread", Config.statusClass.trueStatus);
            List<Map<String, Object>> result1 = new ArrayList<>();
            List<SubLeveLTable> children = map.getChild();
            data.put("children", fun(children, result1));
            result.add(data);
        }
        return result;
    }

    public List<SubLeveLTable> handleTableData(Map<String ,Object> data){
        List<SubLeveLTable> result = new ArrayList<>();
        result = (List<SubLeveLTable>)data.get("menuInfo");
        return result;
    }

    /**
     * 获取子节点
     * @param id 父节点id
     * @param allMenu 所有菜单列表
     * @return 每个根节点下，所有子菜单列表
     */
    public List<SubLeveLTable> getChild(String id,List<SubLeveLTable> allMenu) throws Exception{
        List<SubLeveLTable> childList = new ArrayList<SubLeveLTable>();
        for (SubLeveLTable nav : allMenu) {
            if (nav.getParent_id().equals(id)){
                childList.add(nav);
            }
        }
        for (SubLeveLTable nav : childList) {
            nav.setChild(getChild(nav.getId(), allMenu));
        }
        Collections.sort(childList,order());

        if (childList.size() == 0 ){
            return new ArrayList<SubLeveLTable>();
        }
        return childList;
    }

    /**
     *
     * @param id
     * @param allMenu
     * @param index
     * @param pid
     * @return
     * @throws Exception
     */
    public  List<MenuTableList> getChildTypeTwo(List<MenuTableList> menuAllList,String id,List<MenuTableList> allMenu,int index,int pid) throws Exception{
        //子菜单
        List<MenuTableList> childList = new ArrayList<>();
        for (MenuTableList nav : allMenu) {
            if (nav.getPid().equals(id)){
                nav.setPid_no(pid);
                nav.setId_no(++index);
                childList.add(nav);
            }
        }
        for(MenuTableList m : childList){
            menuAllList.add(m);
        }
        //递归
        for (MenuTableList nav : childList) {
                pid++;
                getChildTypeTwo(menuAllList,nav.getId(), allMenu,index,pid);
        }

        return menuAllList;
    }

    /**
     * 排序,根据order排序
     * @return
     */
    public Comparator<SubLeveLTable> order() throws Exception{
        Comparator<SubLeveLTable> comparator = new Comparator<SubLeveLTable>() {
            @Override
            public int compare(SubLeveLTable o1, SubLeveLTable o2) {
                if (o1.getLevel() != o2.getLevel()){
                    return Integer.parseInt(o1.getLevel()) - Integer.parseInt(o2.getLevel());
                }
                return 0 ;
            }
        };
        return comparator;
    }


    /**
     * 排序,根据order排序
     * @return
     */
    public Comparator<MenuTableList> orderTypeTwo() throws Exception{
        Comparator<MenuTableList> comparator = new Comparator<MenuTableList>() {
            @Override
            public int compare(MenuTableList o1, MenuTableList o2) {
                if (o1.getLevel_flag() != o2.getLevel_flag()){
                    return Integer.parseInt(o1.getLevel_flag()) - Integer.parseInt(o2.getLevel_flag());
                }
                return 0 ;
            }
        };
        return comparator;
    }
    /**
     * 处理菜单输出对象
     * @param menu
     * @return
     */
    public SubLeveLTable handleSystemOutObject(Menu menu) throws Exception{
        SubLeveLTable sub = new SubLeveLTable();
        sub.setTitle(menu.getTitle_name());
        sub.setHref(menu.getHref());
        sub.setIcon(menu.getIcon());
        sub.setTarget(menu.getTarget());
        sub.setLevel(menu.getLevel_flag());
        sub.setParent_id(menu.getParent_id());
        sub.setId(menu.getId());
        sub.setLAY_CHECKED(menu.isLAY_CHECKED());
        return sub;
    }

    /**
     * 处理页面菜单输出参数转换
     * @param menu
     * @return
     */
    public MenuTableList handleMenuTableObject(Menu menu){
        MenuTableList obj = new MenuTableList();
        obj.setId(menu.getId());
        obj.setParent_title_code(menu.getParent_title_code());
        obj.setTitle_name(menu.getTitle_name());
        obj.setPid(menu.getParent_id());
        if("".equals(menu.getHref())){
            obj.setHref("");
        }else{
            obj.setHref(menu.getHref());
        }
        obj.setImage("");
        obj.setIcon(menu.getIcon());
        obj.setTarget(menu.getTarget());
        obj.setLevel_flag(this.changeLevelValue(menu.getLevel_flag()));
        obj.setCreate_by(menu.getCreate_by());
        obj.setCreate_time(menu.getCreate_time());
        obj.setUpdate_by(menu.getUpdate_by());
        obj.setUpdate_time(menu.getUpdate_time());
        return obj;
    }

    /**
     * 处理菜单明细数据对象
     * @param menu
     * @return
     */
    public MenuChild mergeMenuObject(Menu menu) throws Exception{
        MenuChild menuChild = new MenuChild();
        menuChild.setId(menu.getId());
        menuChild.setTitle_name(menu.getTitle_name());
        menuChild.setHref("");
        menuChild.setTarget(menu.getTarget());
        menuChild.setIcon(menu.getIcon());
        menuChild.setLevel_flag(Config.strClassNum.zero);
        return menuChild;
    }

    /**
     * 将菜单数据写入文件
     * @param data
     * @throws Exception
     */
    public void sendFileTableInfo(Map<String ,Object> data) throws Exception{
        String path = PropertiesUtils.class.getResource("/").getPath();
        String mainURL = (path.replace("/target/classes", "/src/main/webapp/api").replace("%20"," ") + "init.json").replaceFirst("/", "");
        File file = this.createFile(mainURL);
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        FileWriter fileOut = new FileWriter(mainURL);
        fileOut.write("");
        fileOut.close();
        bw.write(CommonUtil.getPrettyGsonStr(data));
        bw.close();
        fw.close();
    }
    /**
     * 创建文件夹
     *
     * @param path
     * @return
     */
    public  File createFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
    /**
     * 参数转换
     *
     * @param level
     * @return
     */
    public String changeLevelValue(String level){
        if(level.equals(Config.levelCnClass.zero)){
            return Config.levelCnClass.zeroZhc;
        }
        if(level.equals(Config.levelCnClass.one)){
            return Config.levelCnClass.oneZhc;
        }
        if(level.equals(Config.levelCnClass.two)){
            return Config.levelCnClass.twoZhc;
        }
        if(level.equals(Config.levelCnClass.three)){
            return Config.levelCnClass.threeZhc;
        }
        if(level.equals(Config.levelCnClass.four)){
            return Config.levelCnClass.fourZhc;
        }
        return Config.levelCnClass.undefined;
    }

    /**
     * 获取状态为开启的菜单
     * @return
     */
    public List<Map<String,Object>> queryMenuNameByMenuStatusServ(){
        List<Map<String,Object>>  result = menuMapper.queryMenuNameByMenuStatus();
        return result;
    }

    /**
     * 根据菜单id 获取菜单级别
     * @param id
     * @return
     */
    public String queryMenuLevelById(String id){
        Menu menu = menuMapper.queryMenuLevelById(id);
        String level = menu.getLevel_flag();
        return level;
    }

    /**
     * 获取父菜单级别和id 信息
     * @param title_name
     * @return
     */
    public Menu queryMenuInfoByNameServ(String title_name){
        Menu menu = menuMapper.queryMenuInfoByName(title_name);
        return menu;
    }
}
