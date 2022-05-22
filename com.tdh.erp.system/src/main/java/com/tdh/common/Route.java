package com.tdh.common;

public class Route {
    public static class dictTypeUrl{
        //请求地址
        public static final String QUERY_LIST = "/dict-type-list.do";
        public static final String TO_DICT_TYPE_LIST = "/to-dict-type-list.do";
        public static final String ADD = "/dict-type-add.do";
        public static final String DEL = "/dict-type-del.do";
        //页面路径
        public static final String DICT_TYPE_LIST_URL = "base/dict-type/dict-type-list";
    }

    public static class dictUrl{
        //请求地址
        public static final String TO_DICT_LIST = "/to-dict-list.do";
        public static final String QUERY_LIST = "/dict-list.do";
        public static final String ADD= "/dict-add.do";
        public static final String DEL = "/dict-del.do";
        public static final String QUERY_DICT_DETAILINFO = "/query-dict-detail.do";
        //页面路径
        public static final String DICT_LIST_URL = "base/dict-detail/dict-detail-list";
    }

    public static class userUrl{
        //请求地址
        public static final String TO_USER_URL = "/to-user-list.do";
        public static final String QUERY_LIST = "/user-list.do";
        public static final String ADD = "/user-add.do";
        public static final String DEL = "/user-del.do";
        //页面地址
        public static final String USER_LIST_URL = "system/user/user-list";
    }

    public static class menuUrl{
        public static final String  QUERY_LIST = "/query-list.do";
        public static final String  GENERATE_DIRECTORY= "/generate-directory.do";
        public static final String  TO_MENU_ADD = "/menu-add.do";
        public static final String  QUERY_DETAIL = "/query-menu-detail.do";
        public static final String  ADD_UPDATE_MENU_INFO = "/add_update_menu_info.do";
        public static final String  DEL = "/menu-delete.do";

        public static final String MENU_ADD_URL = "system/menu/menu-edit";
    }

    public static class hospitalUrl{
        //请求地址
        public static final String TO_HOSPITAL_URL = "/to-hospital-list.do";
        public static final String QUERY_LIST = "/hospital-list.do";
        public static final String ADD = "/hospital-add.do";
        public static final String DEL = "/hospital-del.do";

        //获取医院信息枚举
        public static final String QUERY_HOSPITALNUM_INFO = "/query-hospitalEnumInfo.do";

        //页面地址
        public static final String HOSPITAL_LIST_URL = "system/hospital/hospital-list";
    }


    public static class compoundUrl{
        //请求地址
        public static final String TO_COMPOUND_URL = "/to-compound-list.do";
        public static final String QUERY_LIST = "/compound-list.do";
        public static final String ADD = "/compound-add.do";
        public static final String DEL = "/compound-del.do";

        //获取分院信息枚举
        public static final String QUERY_COMPOUNDNUM_INFO = "/query-compoundEnumInfo.do";

        //页面地址
        public static final String COMPOUND_LIST_URL = "system/compound/compound-list";
    }


    public static class areaUrl{
        //请求地址
        //菜单跳转
        public static final String TO_AREA_URL = "/to-area-list.do";
        //查询列表方法
        public static final String QUERY_LIST = "/area-list.do";
        //添加方法
        public static final String ADD = "/area-add.do";
        //删除方法
        public static final String DEL = "/area-del.do";
        //获取诊区信息枚举
        public static final String QUERY_AREAENUM_INFO = "/query-areaEnumInfo.do";

        //跳转页面地址
        public static final String AREA_LIST_URL = "system/area/area-list";
    }
    public static class roleUrl{
        public static final String TO_ROLE_URL = "/to-role-list.do";
        public static final String QUERY_LIST = "/role-list.do";
        public static final String EDIT = "/role-edit.do";
        public static final String DEL = "/role-del.do";
        public static final String QUERY_ENUM = "/query_role_enum.do";
        public static final String TO_ROLE_BIND_URL = "/to-role-menu-bind.do";
        public static final String ROLE_BOND_URL = "system/role/role-menu-bind";
        public static final String QUERY_BIND = "/query-bind.do";
        public static final String TO_DO_BIND = "/to-do-bind.do";
        public static final String ROLE_LIST_URL = "system/role/role-list";
    }

    public static class deptUrl{
        //请求地址
        //菜单跳转
        public static final String TO_DEPT_URL = "/to-dept-list.do";
        //查询列表方法
        public static final String QUERY_LIST = "/dept-list.do";
        //添加方法
        public static final String ADD = "/dept-add.do";
        //删除方法
        public static final String DEL = "/dept-del.do";
        //获取科室信息枚举
        public static final String QUERY_DEPTENUM_INFO = "/query-deptEnumInfo.do";

        //跳转页面地址
        public static final String DEPT_LIST_URL = "system/dept/dept-list";
    }
}
