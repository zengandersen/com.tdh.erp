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
     * εε»Ίθε
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
     * ει‘΅ζ₯θ―’ζ°ζ?
     * @param params
     * @return
     */
    public PageList<Map<String, Object>> selectPageMap(Menu params) throws Exception{
        return this.selectMapPageSize(params, params.getCurPage(), params.getPageSize());
    }

    /**
     * ζ Ήζ?idθ·εθεζ°ζ?
     * @param id
     * @return
     */
    public Menu queryInfobyId(String id) throws Exception{
        Menu menu = menuMapper.findById(id);
        return menu;
    }

    /**
     * ζ Ήζ?idε ι€ζ°ζ?
     * @param id
     */
    public void deleteById(String id)throws Exception{
        menuMapper.deleteById(id);
    }

    /**
     * ζ Ήζ?ε°εθ·εθεζ°ζ?
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
     * ηΆζεζ΄
     * @param id
     * @param status
     * @param update_by
     * @throws Exception
     */
    public void updateStatusById(String id,String status,String update_by) throws Exception{
        menuMapper.updateStatusById(id,status,update_by);
    }

    /**
     * ζ Ήζ?idε ι€
     * @param id
     * @throws Exception
     */
    public void deleteInfoById(String id) throws Exception{
        menuMapper.deleteById(id);
    }

    /**
     * θ·εζͺη»ε?ηζ°ζ?
     * @return
     */
    public List<MenuChild> queryMenuInfoNotBind() throws Exception{
        return menuChildMapper.queryMenuInfoNotBind();
    }

    /**
     * θ·εε·²η»ε?ηζ°ζ?
     * @return
     */
    public List<MenuChild>queryMenuInfoBind(String id) throws Exception{
        return menuChildMapper.queryMenuInfoBind(id);
    }

    /**
     * ζ₯θ―’iconζδΈΎδΏ‘ζ―
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
     * ζ΄ζ°δΈ»δΈ»θεη»ε?ζη»θε
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
     * ε€ηθεζ°ζ?
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
     * θ·εε­θηΉ
     * @param id ηΆθηΉid
     * @param allMenu ζζθεεθ‘¨
     * @return ζ―δΈͺζ ΉθηΉδΈοΌζζε­θεεθ‘¨
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
        //ε­θε
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
        //ιε½
        for (MenuTableList nav : childList) {
                pid++;
                getChildTypeTwo(menuAllList,nav.getId(), allMenu,index,pid);
        }

        return menuAllList;
    }

    /**
     * ζεΊ,ζ Ήζ?orderζεΊ
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
     * ζεΊ,ζ Ήζ?orderζεΊ
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
     * ε€ηθεθΎεΊε―Ήθ±‘
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
     * ε€ηι‘΅ι’θεθΎεΊεζ°θ½¬ζ’
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
     * ε€ηθεζη»ζ°ζ?ε―Ήθ±‘
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
     * ε°θεζ°ζ?εε₯ζδ»Ά
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
     * εε»Ίζδ»Άε€Ή
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
     * εζ°θ½¬ζ’
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
     * θ·εηΆζδΈΊεΌε―ηθε
     * @return
     */
    public List<Map<String,Object>> queryMenuNameByMenuStatusServ(){
        List<Map<String,Object>>  result = menuMapper.queryMenuNameByMenuStatus();
        return result;
    }

    /**
     * ζ Ήζ?θεid θ·εθεηΊ§ε«
     * @param id
     * @return
     */
    public String queryMenuLevelById(String id){
        Menu menu = menuMapper.queryMenuLevelById(id);
        String level = menu.getLevel_flag();
        return level;
    }

    /**
     * θ·εηΆθεηΊ§ε«εid δΏ‘ζ―
     * @param title_name
     * @return
     */
    public Menu queryMenuInfoByNameServ(String title_name){
        Menu menu = menuMapper.queryMenuInfoByName(title_name);
        return menu;
    }
}
