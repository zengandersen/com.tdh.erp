package com.tdh.common;

public class Config {

    public static final String loginUser ="loginUser";
    public static final String searchField = "name";
    public static final String sysTrackCode = "sysTrackCode";
    public static final String page = "page";
    public static final String limit = "limit";
    public static final String data = "data";
    public static final String SQL = "SQL";
    public static final String OTHER = "OTHER";
    public static class logClass {
        public static final String error ="error";
        public static final String truce ="truce";
    }

    public static class statusClass{
        public static final String open = "1";
        public static final String close = "0";
        public static final String trueStatus = "true";
        public static final String failStatus = "fail";
        public static final int interget_open = 1;
        public static final int interget_close= 0;
    }

    public static class integerClass{
        public static final int one = 1;
        public static final int zero = 0;
        public static final int two = 2;
        public static final int negative = -1;

    }

    public static class strClassNum{
        public static final String one = "1";
        public static final String two = "2";
        public static final String zero = "0";
        public static final String negative = "-1";
        public static final String three = "3";
    }

    public static class tableClass{
        public static final String title="柒丫头棉袜铺";
        public static final String index = "首页";
        public static final String logo = "images/logo.jpg";
        public static final String url = "https://mms.pinduoduo.com/home/";
    }

    public static class levelCnClass{
        public static final String one = "1";
        public static final String two = "2";
        public static final String three = "3";
        public static final String four = "4";
        public static final String zero = "0";

        public static final String oneZhc = "一级";
        public static final String twoZhc = "二级";
        public static final String threeZhc = "三级";
        public static final String fourZhc = "四级";
        public static final String zeroZhc = "零级";

        public static final String undefined = "未分配";

    }


    public static class isSignRoomClass{
        public static final int dept = 0;
        public static final int dept_room = 1;

        public static final String dept_chn = "科室";
        public static final String dept_room_chn = "诊室";
    }

    public static class isSignInClass{
        public static final int register = 1;
        public static final int notRegister = 0;

        public static final String register_chn = "1";
        public static final String notRegister_chn = "0";
    }

    public static class deptTypeClass{
        public static final int out_patient  = 1;
        public static final int in_patient =2;
        public static final int physical_examination =3;
        public static final int check_out =4;
        public static final int drugstore =5;

        public static final String out_patient_chn = "门诊";
        public static final String in_patient_chn = "住院";
        public static final String physical_examination_chn = "体检";
        public static final String check_out_chn = "检验";
        public static final String drugstore_chn="药房";
    }



    public static class baseUrl{
        public static final String oracleUrl = "jdbc:oracle:thin:@";
        public static final String connect_test = "connect_test";
        public static final String execute_test = "execute_test";
    }

    public static class TestStatusClass{
        public static final String NOT_OPERATED = "0";
        public static final String OPERATION_FAILED = "1";
        public static final String OPERATION_SUCCESS = "2";
    }

    public static class addressTypeClass{
        public static final String automatic = "0";
        public static final String manualInput = "1";
    }

    public static class scheduleParam{
        public static final String CRON = "CRON";
        public static final String BEAN = "BEAN";
        public static final int SCHEDULEOPEN = 1;
        public static final int SCHEDULECLOSE= 0;
    }

    public static class passwordConfig{
        public static final String CIPHERTEXT ="******";
        public static final String APPENTEXT = "youaresmart";
    }

    public static class numberType{
        public static final String general = "1";
        public static final String retain = "2";
        public static final String add = "3";
    }

    public static class optType{
        public static final String UPDATE = "update";
        public static final String INSERT = "insert";
    }


    public static class checkBoxStatus{
        public static final String on = "on";
        public static final String off = "off";
        public static final int open = 1;
        public static final int close = 2;
    }

    public static class appStatus{
        public static final int yes = 1;
        public static final int no =0;
    }

}
