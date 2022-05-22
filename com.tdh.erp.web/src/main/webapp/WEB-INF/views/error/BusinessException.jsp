<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <title>非常遗憾，您的操作出现了错误，请与管理员联系！</title>
  <style type="text/css">

  	.title-L1{
  		font-size: 20pt;
  		color: #D6A41C;
  		margin-left:20px;
  	}
  	.title-L2{
  		margin-left:20px;
  		font-size: 14pt;
  		color: red;
  	}
  	.title-L3{
  		margin-left:20px;
  		font-size: 11pt;
  		color: #77766F;
  	}

  	.logo{
  		position: absolute;
  		margin: 0 auto;
  	}
  </style>
</head>
<body style="background-color: #fff; margin-top: 15%;">
<table align="center" style="margin-top: 20px;">
		<tr>
			<td style="text-align: right;">
				<img src="/images/404_logo.png"/>
			</td>
			<td style="text-align: left;">
				<div class="title-L1">Unknow Error</div>
				<div class="title-L2">非常遗憾，您的操作出现了错误，请与管理员联系！</div>
				<div class="title-L3">${exception.message}</div>
			</td>
		</tr>
	</table>
</body>
</html>