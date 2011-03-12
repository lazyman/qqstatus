<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="cn.com.lazyhome.qqstatus.LineChart"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.GregorianCalendar"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 不允许缓存  -->
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache,   must-revalidate">
<meta http-equiv="expires" content="0">
<title>Insert title here</title>
</head>
<%
	String qqid = request.getParameter("qqid");
	String date = request.getParameter("date");

	LineChart chart = new LineChart(qqid);
	if(date != "") {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = sdf.parse(date);
		Calendar c = new GregorianCalendar();
		c.setTime(d);
		chart.setBegintime(c);
	}
	chart.writeImage();
%>
<body>
<form>
QQ:<input name="qqid" value="<%= qqid %>" />
日期：<input name="date" value="<%= date %>" />
<input type="submit" />
</form>
<img alt="" src="statimg/<%=qqid %>.png">
</body>
</html>