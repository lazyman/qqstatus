<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 不允许缓存  -->
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache,   must-revalidate">
<meta http-equiv="expires" content="0">
<title>qq status</title>
</head>

<body>
<s:form theme="simple" action="showpic" method="get">
<s:date name="date" format="yyyy-MM-dd" var="datestr" />
QQ:<s:textfield name="qqId"></s:textfield>
日期：<input name="date" value="${datestr}" />
<input type="submit" value="查看" />

<s:if test="qqId != null">
<a href="http://${qqId}.qzone.qq.com" target="_blank">查看Q空间</a>
</s:if>

<s:submit method="showtext"></s:submit>

</s:form>
<br />
<img alt="" src="statimg/${qqId }-${datestr }.png">

<br />
<a name="bottom"></a>
<s:form theme="simple" action="showpic#bottom" method="get">

<s:date name="date" format="yyyy-MM-dd" var="datestr" />
QQ:<s:textfield name="qqId"></s:textfield>
日期：<input name="date" value="${datestr}" />
<input type="submit" value="查看" />

<s:if test="qqId != null">
<a href="http://${qqId}.qzone.qq.com" target="_blank">查看Q空间</a>
</s:if>

<s:submit method="showtext"></s:submit>

</s:form>
</body>
</html>
