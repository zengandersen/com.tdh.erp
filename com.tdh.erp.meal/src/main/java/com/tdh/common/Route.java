package com.tdh.common;

public class Route {

    public static class MealUrl{
        public static final String TO_MEAL = "/to-meal.do";
        public static final String TO_MEAL_INFO = "/to-meal-info.do";
        public static final String TO_MEAL_INFO_EDIT = "/to-meal-info-edit.do";
        public static final String QUERY_LIST = "/query-meal-list.do";
        public static final String QUERY_INFO = "/query-meal-info.do";
        public static final String ADD= "/add-meal.do";
        public static final String EDIT = "/edit-meal.do";
        public static final String DEL = "/del-meal.do";
        public static final String TO_MEAL_URL = "meal/meal-list";
        public static final String TO_MEAL_INFO_URL = "meal/meal-add";
        public static final String TO_MEAL_INFO_EDIT_URL = "meal/meal-edit";
    }

    public static class MealBindUrl{
        public static final String TO_MEAL_BIND = "/to-meal-bind.do";
        public static final String TO_MEAL_BIND_URL="meal/meal-bind-list";
        public static final String QUERY_LIST = "/query-meal-bind-list.do";
        public static final String QUERY_NOT_BIND_LIST ="/query-meal-notbind-list.do";
        public static final String TO_MEAL_NOT_BIND_LIST = "/to-meal-notBind-list.do";
        public static final String TO_MEAL_NOT_BIND_URL = "meal/meal-notBind-list";
        public static final String TO_DO_BIND = "/to-do-meal-bind.do";
        public static final String DEL_BIND = "/del-bind.do";
    }

}
