<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML>
<html>
<head>
	<meta charset="utf-8">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport"
		  content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,role-scalable=no"/>
	<meta http-equiv="Cache-Control" content="no-siteapp"/>
	<link rel="Bookmark" href="/H-ui-admin//favicon.ico">
	<link rel="Shortcut Icon" href="/H-ui-admin//favicon.ico"/>

	<script type="text/javascript" src="/H-ui-admin/lib/html5shiv.js"></script>
	<script type="text/javascript" src="/H-ui-admin/lib/respond.min.js"></script>
	<link rel="stylesheet" type="text/css" href="/lib/layui-v2.6.3/css/layui.css">
	<link rel="stylesheet" type="text/css" href="/css/public.css">
	<!--_footer 作为公共模版分离出去-->
	<script type="text/javascript" src="/H-ui-admin/lib/jquery/1.9.1/jquery.min.js"></script>
	<!--请在下方写此页面业务相关的脚本-->
	<script src="/lib/layui-v2.6.3/layui.js"></script>

	<!--/meta 作为公共模版分离出去-->

	<title>《进销存系统》 Ver 2022</title>
	<meta name="keywords" content="《进销存系统》 Ver 2022">
	<meta name="description" content="《进销存系统》 Ver 2022">

</head>
<body>
<body>
<div class="layuimini-container">
	<div class="layuimini-main">
		<fieldset class="table-search-fieldset">
			<legend>操作栏</legend>
			<div style="margin: 10px 10px 10px 10px">
				<form class="layui-form layui-form-pane" action="">
					<div class="layui-form-item">
						<div class="layui-inline">
							<button class="layui-btn layui-btn-primary" id="btn-refresh">刷新表格</button>
							<a  class="layui-btn" id="add-menu"><i class="layui-icon layui-icon-add-1"></i>添加菜单</a>
						</div>

					</div>
				</form>
			</div>
		</fieldset>
		<div id="tableRes" class="table-overlay">
			<table class="layui-hide" id="dataTable" lay-filter="dataTable"></table>
		</div>
	</div>
	<!-- 操作列 -->
	<script type="text/html" id="oper-col">
		<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">修改</a>
		<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
	</script>
</div>
</body>
<!--_footer 作为公共模版分离出去-->


<script type="text/javascript" src="/H-ui-admin/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="/H-ui-admin/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="/H-ui-admin/static/h-ui.admin/js/H-ui.admin.js"></script>

<script type="text/javascript">

   var config =  layui.config({
       base: '/layer/module/'
   }).extend({
       treetable: 'treetable-lay/treetable'
   });
    window.viewObj ={
        //下拉参数构建
        renderSelectOptions: function(data, settings){
            return  renderSelectHandle(data,settings);
        },

        renderDiv:function(data,settings){
            return renderDivHandle(data,settings);
		}
    };
    function winClose(){
        layer.closeAll();
        $("#table1").submit();
    }
   config.use(['layer', 'table', 'treetable','jquery'], function () {
        var $ = layui.$;
        var form = layui.form;
        var table = layui.table;
        var layer = layui.layer;
        var treetable = layui.treetable;
        var layTableId = "layTable";
        // 渲染表格
        var renderTable = function () {
            layer.load(2);
            treetable.render({
				id :layTableId,
                treeColIndex: 2,
                treeSpid: -1,
                treeIdName: 'id_no',
                treePidName: 'pid_no',
                treeDefaultClose: false,
                treeLinkage: true,
                elem: '#dataTable',
                url: '/query-list.do',
                page: false,
                cols: [[
                    {title:'序号',type: 'numbers'},
                    {field: 'title_name', title: '菜单名称',templet:function(d){
                            var options = viewObj.renderDiv(menu_info_enum, {valueField: "id", textField: "name", selectedValue: d.title_name});
                            return options;
                        }},
                    {field: 'level_flag', title: '菜单级别'},
                    {field: 'href', title: '链接地址'},
                    {field: 'icon', title: '图片样式',templet:function(d){
                            var options = viewObj.renderDiv(menu_icon_enum, {valueField: "id", textField: "name", selectedValue: d.icon});
                            return options;
                        }},
                    {field: 'target', title: '位置对象'},
                    {templet: '#oper-col', title: '操作'}
                ]],
                done: function () {
                    layer.closeAll();
                }
            });
        };
	   	renderTable();

        $('#btn-refresh').click(function () {
            renderTable();
        });



       $('#add-menu').click(function () {
           var data = "";
               var index = layer.open({
                   title: '新增菜单',
                   type: 2,
                   shade: 0.2,
                   maxmin: true,
                   shadeClose: true,
                   area: ['100%', '100%'],
                   content: '/menu-add.do',
                   success: function (layer, index) {
                       var iframe = window['layui-layer-iframe'+index];
                       iframe.child(data,menu_info_enum,menu_icon_enum)
                   }
               });
               $(window).on("resize", function () {
                   layer.full(index);
               });
               return false;
       });
       table.on('tool(dataTable)', function (obj) {
           var data = obj.data;
         if (obj.event === 'edit') {
               var index = layer.open({
                   title: '菜单编辑',
                   type: 2,
                   shade: 0.2,
                   maxmin:true,
                   shadeClose: true,
                   area: ['100%', '100%'],
                   content: '/menu-add.do',
				   success:function(layer,index){
                       var iframe = window['layui-layer-iframe'+index];
                       iframe.child(data.id,menu_info_enum,menu_icon_enum)
				   }
               });
               $(window).on("resize", function () {
                   layer.full(index);
               });
               return false;
           } else if (obj.event === 'del') {
               layer.confirm('真的删除行么', function (index) {
                   obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                   layer.close(index);
                   $.ajax({
                       url: '/menu-delete.do',
                       type: 'POST',
                       async: false,
                       data: {"data":data.id}
                   })
                       .done(function (message) {
                           var json = eval("(" + message + ")")
                           if (json.code == "success") {
                               layer.msg(json.msg);
                           }
                           if (json.code == "fail") {
                               layer.msg(json.msg);
                           }
                       })
               });
           }
       });
    });
   //字典变量
   var menu_info_enum;
   var menu_icon_enum;
   //字典内容初始化
   $(document).ready(function () {
       $.ajax({
           url: '/query-dict-detail.do',
           type: 'POST',
           async: false,
           data: {"data":'MENU_INFO,MENU_ICON'}
       })
           .done(function (message) {
               var json = eval("(" + message + ")")
               if (json.code == "success") {
                   for(var i =0 ;i<json.data.length;i++){
                       var obj = json.data[i];
                       for(var key in obj){
                           if('MENU_INFO'== key){
                               menu_info_enum = json.data[i].MENU_INFO;
                               console.log(menu_info_enum);
                           }
                           if('MENU_ICON' == key){
                               menu_icon_enum= json.data[i].MENU_ICON;
                               console.log(menu_icon_enum);

                           }
                       }

                   }
               }
               if (json.code == "fail") {
                   layer.msg(json.msg);
               }
           })
   });
</script>


<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>