<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>	
<!Doctype html>
<html>
  <head>
      <title>消息提示</title>
      <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
      <meta http-equiv="content-type" content="text/html;charset=GB18030"/>
      <script type="text/javascript" src="/js/jquery-1.7.2.min.js"></script>
      <script type="text/javascript" src="/H-ui-admin/lib/layer/2.4/layer.js"></script>
      <link href="/css/style.css" rel="stylesheet" type="text/css"  />
  </head>
  <body>
  <div class="div_message">
  		感谢使用
  </div>
  <div class="bottomdiv"></div>
	<div class="footer1">
		<div class="footer_div" align="center">
			<a href="###" onclick="parent.winClose();" >关闭</a>
		</div>
	</div>
  </body>
</html>
