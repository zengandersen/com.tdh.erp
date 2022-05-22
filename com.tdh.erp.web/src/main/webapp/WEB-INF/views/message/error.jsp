<%@ page language="java" contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <title>找不到页面</title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
      <meta http-equiv="content-type" content="text/html;charset=GB18030"/>
      <script type="text/javascript" src="/js/jquery-1.7.2.min.js"></script>
      <script type="text/javascript" src="/H-ui-admin/lib/layer/2.4/layer.js"></script>
      <link href="/css/style.css" rel="stylesheet" type="text/css"  />
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
<table align="center" style="margin-top: 20px;" >
		<tr>
			<td style="text-align: right;">
				<img src="/images/404_logo.png"/>
			</td>
			<td style="text-align: left;">
				<div class="title-L1">WARNING</div>
				<div class="title-L2">${message}</div>
			</td>
		</tr>
	</table>
<div class="bottomdiv"></div>
	<div class="footer1">
		<div class="footer_div" align="center">
			<a href="###" onclick="javascript:history.back(-1);">返回</a>
		</div>
	</div>
</body>
</html>