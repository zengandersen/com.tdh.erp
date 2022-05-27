package com.tdh.common;

public class Route {

    public static class FactoryUrl{
        public static final String TO_FACTORY_LIST = "/to-factory.do";
        public static final String TO_FACTORY_DETAIL = "/to-factory-detail.do";
        public static final String QUERY_LIST = "/query-factory-list.do";
        public static final String QUERY_DETAIL = "/query-factory-detail.do";
        public static final String MARGE = "/marge-factory.do";
        public static final String EDIT = "/edit-factory.do";
        public static final String DEL = "/delete-factory.do";
        public static final String ENUM = "/enum-factory.do";
        public static final String TO_FACTORY_URL = "base/factory/factory-list";
        public static final String TO_FACTORY_DETAIL_URL = "base/factory/factory-edit";
    }

    public static class GoodsUrl{
        public static final String TO_GOODS_LIST = "/to-goods.do";
        public static final String TO_GOODS_DETAIL = "/to-goods-detail.do";
        public static final String TO_GOODS_ADD = "/to-goods-add.do";
        public static final String QUERY_LIST = "/query-goods-list.do";
        public static final String EDIT = "/edit-goods.do";
        public static final String ADD = "/add-goods.do";
        public static final String DEL= "/del-goods.do";
        public static final String QUERY_DETAIL = "/query-goods-detail.do";
        public static final String UPLOAD_GOODS = "/upload-goods.do";
        public static final String QUERY_GOODS_BY_FACOTRY = "/query-goods-by-factory.do";
        public static final String QUERY_GOODS_BY_FACTORY_PAGELIST = "/query-goods-by-factory-pageList.do";
        public static final String QUERY_GOODSENUM_BY_ID = "/query-goodsenum-by-id.do";
        public static final String TO_GOODS_URL = "base/goods/goods-list";
        public static final String TO_GOODS_DETAIL_URL = "base/goods/goods-edit";
        public static final String TO_GOODS_ADD_URL = "base/goods/goods-add";
    }

    public static class ConsumerUrl{
        public static final String TO_CONSUMER = "/to-consumer.do";
        public static final String ADD = "/add-consumer.do";
        public static final String EDIT = "/edit-consumer.do";
        public static final String DEL = "/del-consumer.do";
        public static final String QUERY_LIST ="/query-consumer-list.do";
        public static final String QUERY_DETAIL = "/query-consumer-detail.do";
        public static final String QEURY_ENUM = "/query-consumer-enum.do";
        public static final String TO_CONSUMER_EDIT = "/to-consumer-edit.do";
        public static final String TO_CONSUMER_ADD = "/to-consumer-add.do";
        public static final String TO_CONSUMER_EDIT_URL = "base/consumer/consumer-edit";
        public static final String TO_CONSUMER_ADD_URL = "base/consumer/consumer-add";
        public static final String TO_CONSUMER_URL = "base/consumer/consumer-list";
    }
}
