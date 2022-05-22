<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />


<!--[if lt IE 9]>
<script type="text/javascript" src="/H-ui-admin/lib/html5shiv.js"></script>
<script type="text/javascript" src="/H-ui-admin/lib/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="/H-ui-admin/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="/H-ui-admin/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="/H-ui-admin/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="/H-ui-admin/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="/H-ui-admin/static/h-ui.admin/css/style.css" />
<link href="/css/style.css" rel="stylesheet" type="text/css"  />
<!--[if IE 6]>
<script type="text/javascript" src="/H-ui-admin/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>《智能预约平台》V2.0</title>
<meta name="keywords" content="《智能预约平台》V2.0">
<meta name="description" content="《智能预约平台》V2.0">
</head>
<body onload="onPrint()">
<div class="col-sm-12">
				<div class="mt-20">
						<table class="table table-border table-bordered table-hover table-bg table-sort">
							<thead>
								<tr class="text-c">
									<th width="">序号</th>
									<th width="">检查类别</th>
									<th width="">项目名称</th>
									<th width="">预约信息</th>
									<th width="">提示信息</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${exam_list}" var="item" varStatus="i">
									<tr class="text-c"  <c:if test="${item.app_date==null }">style="color: red"</c:if>>
											<td class="text-c">
												<c:if test="${item.app_date!=null }">
													<input type="hidden" name="exam_req_id" value="${item.exam_req_id }">
												</c:if>
												${i.index+1 }
											</td>
											<td class="text-c">
												${item.exam_class }
											</td>
											<td class="text-c">
												${item.exam_item_name }
												<c:if test="${item.check_site_name!=null }"><span class="label label-danger radius">${item.check_site_name}</span></c:if>
											</td>
											<td class="text-l">
												${item.app_date }
											</td>
											<td class="text-c">
												${item.tips}
											</td>
									</tr>
								</c:forEach>	
								</tbody>
						</table>
				</div>
			</div> 
<div class="bottomdiv"></div>
		<div class="footer1">
		<div class="footer_div" align="center">
			<a href="###" onclick="parent.winClose();" >关闭</a>
		</div>
	</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="/H-ui-admin/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="/H-ui-admin/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="/H-ui-admin/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="/H-ui-admin/static/h-ui.admin/js/H-ui.admin.js"></script>
<!--/_footer 作为公共模版分离出去-->

<!-- 打印 -->
<script type="text/javascript" src="/js/LodopFuncs.js"></script>

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="/H-ui-admin/lib/hcharts/Highcharts/5.0.6/js/highcharts.js"></script>
<script type="text/javascript" src="/H-ui-admin/lib/hcharts/Highcharts/5.0.6/js/modules/exporting.js"></script>
<script type="text/javascript" src="/H-ui-admin/lib/jquery.contextmenu/jquery.contextmenu.r2.js"></script>
<script type="text/javascript" src="/print/LodopFuncs.js"></script>
<script type="text/javascript" src="/print/number_.js"></script>
<script type="text/javascript" src="/print/prompt_.js"></script>
<c:if test="${param.message!=null}">
<script type="text/javascript">layer.msg("${param.message}");</script>
</c:if>
<script type="text/javascript">
var print_type='${print.print_type}';
var ticket_drive='${print.ticket_drive}';
var receipt_drive='${print.receipt_drive}';
function onPrint(){
	setTimeout("startPrint()",500);
	//setTimeout("winClose()",2000);
}

function startPrint(){
	if(ticket_drive=="-1" && receipt_drive=="-1"){
		layer.msg("未配置驱动打印", {icon:2,time:1000, shift: 6});
		return;
	}
	var exam_req_length = $("input[name='exam_req_id']").length;
	if(exam_req_length==0){
		//layer.msg("请选择已预约的申请单", {icon:2,time:1000, shift: 6});
		return;
	}else{
		var exam_req_ids = "";
		for(var i=0;i<exam_req_length;i++){
			if(i==0){
				exam_req_ids = $("input[name='exam_req_id']").eq(i).val();
			}else{
				exam_req_ids = exam_req_ids + ","+$("input[name='exam_req_id']").eq(i).val();
			}
		}
		//打印号票
		if(ticket_drive!='-1'){
			$.ajax({
		        url:"./query-number-tick.do?exam_req_ids="+exam_req_ids,
		        type:"get",
		        dataType:"json",
		        success: function(data) {
		        	console.log(data._number_ticket);
		        	for(var j=0;j<data._number_ticket.length;j++){
		        		var _index = data._number_ticket[j];
		        		//胃肠镜
		        		if(_index.ticket_no_temp==1){
				        	number_ticket(_index.app_start_date,_index.app_end_date,_index.pla,_index.hospital_name,_index.title,_index.patient_id,_index.name,_index.sex,_index.age,_index.req_dept_name,_index.app_dept_room_name,_index.app_number_no,_index.app_am_pm,_index.app_date,_index.dept_room_address,_index.exam_item_names,_index.tips,print_type,ticket_drive);
		        		//超声
		        		}else if(_index.ticket_no_temp==2){
		        			number_ticket_cs(_index.app_start_date,_index.app_end_date,_index.pla,_index.hospital_name,_index.title,_index.patient_id,_index.name,_index.sex,_index.age,_index.req_dept_name,_index.app_dept_room_name,_index.app_number_no,_index.app_am_pm,_index.app_day,_index.dept_room_address,_index.exam_item_names,_index.tips,print_type,ticket_drive);
			        	//通用	
		        		}else{
				        	number_ticket(_index.app_start_date,_index.app_end_date,_index.pla,_index.hospital_name,_index.title,_index.patient_id,_index.name,_index.sex,_index.age,_index.req_dept_name,_index.app_dept_room_name,_index.app_number_no,_index.app_am_pm,_index.app_date,_index.dept_room_address,_index.exam_item_names,_index.tips,print_type,ticket_drive);
		        		} 
			        	/* if(receipt_drive!='-1'){
			        		$.ajax({
			    		        url:"./query-prompt-tick.do?patient_id="+_index.patient_id+"&app_date="+_index.app_date+"&app_equipment_id="+_index.app_equipment_id,
			    		        type:"get",
			    		        dataType:"json",
			    		        success: function(data) {
			    		        	console.log(data._promopt_ticket);
			    		        	prompt_ticket(_index.name,_index.sex,_index.age,_index.patient_id,_index.app_date,_index.app_dept_room_name,_index.exam_item_names,_index.dept_room_address,data._promopt_ticket,print_type,receipt_drive,_index.hospital_name,'检查回执单');
			    		        }
			    		    })
			        	} */
		        	}
		        }
		    })
		}
		
	}
}

function winClose(){
	parent.winClose();
}

</script>
</body>
</html>
