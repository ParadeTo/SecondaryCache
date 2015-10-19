
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'success.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <form action="<%=basePath%>addPage/" method="get">
        <button type="submit">新增</button>
    </form>
    <form action="<%=basePath%>testActor/" method="get">
        <button type="submit">TestActor</button>
    </form>
    <table>
        <tr>
            Ehcache中的数据有${nEhcache},Redis中的数据有${nRedis}
        </tr>
        <hr/>
    	<tr>
    	  <td>操作</td>
    	  <td>ID</td>
    	  <td>名字</td>
    	  <td>年龄</td>
    	  <td>职业</td>
    	</tr>
        <c:forEach items="${stars}" var="star" varStatus="sn">
            <tr>
              <td><a href="<%=basePath%>updatePage?id=${star.id}">编辑</a><a href="<%=basePath%>del?id=${star.id}">删除</a></td>
              <td><c:out value="${star.id}" default="0"/></td>
              <td><c:out value="${star.name}" default="0"/></td>
              <td><c:out value="${star.age}" default="0"/></td>
              <td><c:out value="${star.vocation}" default="0"/></td>
            </tr>
    	</c:forEach>
    </table>

  </body>
</html>
