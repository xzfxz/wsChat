<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>
    <title>报名列表</title>
    <link rel="stylesheet" href="${path}/static/css/index_userList.css" charset="utf-8"/>
</head>
<body>
<div class="form_container">
    <table cellpadding="0" cellspacing="0">

        <thead>
            <tr>
                <th>姓名</th>
                <th>电话</th>
                <th>时间</th>
                <th>操作</th>
            </tr>
        </thead>

        <tbody>
            <c:forEach var="item" items="${data}">
                <tr>
                    <td>${item.name}</td>
                    <td>${item.phoneNum}</td>

                    <jsp:useBean id="dateObject" class="java.util.Date" scope="page"></jsp:useBean>
                    <jsp:setProperty property="time" name="dateObject" value="${item.lat}"/>
                    <td><fmt:formatDate value="${dateObject}" pattern="MM-dd HH:mm:ss" /></td>

                    <td><a href="${path}/user/update?id=${item.id}" >修改</a>  <a href="${path}/user/delete?id=${item.id}">删除</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <button id="su" type="button" class="" onclick="add2()">继续添加</button>
</div>
</body>
<script src="${path}/static/js/zepto.js" charset="UTF-8" type="text/javascript"></script>
<script>
    function add2() {
        window.location.href = "${path}/user/index";
    }
</script>
</html>