<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>关注QQ</title>
</head>
<body>
<s:form action="input" theme="simple" method="get">
<s:actionmessage />
<table width="80%" align="center" cellspacing="0" border="1">
	<tr>
		<td width="80%">QQ号：
          <input name="concern.qqId" alt="你关注的QQ" /></td>
		<td rowspan="2" align="center"><input type="submit" value="开始关注" /></td>
	</tr>
	<tr>
		<td>接收邮箱：
		  <input name="concern.mail" size="50" alt="用于接收对方QQ使用情况" />
，每天
<input name="concern.receivedTime" value="8" size="3" />
点接收。</td>
	</tr>
</table>
</s:form>
<p>&nbsp;</p>
<s:form action="showpic" method="get">
<s:date name="nowtime" format="yyyy-MM-dd" var="datestr" />
<table width="80%" align="center" cellspacing="0" border="0">
	<tr>
		<td><p>即时查看QQ状态：</p>
	    <p>QQ：
	      <input name="qqId" alt="你关注的QQ" />
	      日期：
	      <input name="date" value="${datestr }" />
	      <input name="submit" type="submit" value="查看" />
	    </p></td>
	</tr>
</table>
</s:form>
</body>
</html>
