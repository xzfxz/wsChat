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
    <title>报名</title>
    <link rel="stylesheet" href="${path}/static/css/index_userIndex.css" charset="utf-8"/>
    <link rel="stylesheet" href="${path}/static/css/zepto.mtimer.css" charset="UTF-8"/>


</head>
<body>

    <div class="container">

        <div class="form_text" >
            <form action="#" method="post" id="doForm" >
                <ul>
                    <input name = "id"  type="hidden" value="${data.id}" />
                    <li>
                        姓名:
                        <input name = "name" id="name" type="text" maxlength="15" value="${data.name}"/>
                    </li>
                    <li>
                        电话:
                        <input name = "phone" id="phone" type="text" maxlength="11" value="${data.phoneNum}"/>
                    </li>
                    <li>
                        日期:
                        <input name = "time" type="text" id="picktime" value="${data.mark}" readonly/>
                    </li>
                </ul>
            </form>
            <button id="su" type="button" class="" onclick="save2()">提交</button>
        </div>
    </div>
</body>

<script src="${path}/static/js/zepto.js" charset="UTF-8" type="text/javascript"></script>
<script src="${path}/static/js/zepto.mtimer.js" charset="UTF-8" type="text/javascript"></script>
<script>
    function save2() {

        var phone = $('#phone').val().trim();
        var name = $('#name').val().trim();
        if(phone.length<1){
            alert("电话不能为空");
            return;
        }
        var flag = checkPhone(phone);
        if(!flag){
            alert("手机号码有误，请重填");
            return;
        }
        if(name.length<1){
            alert("姓名不能为空！");
            return;
        }

        $("#su").attr("disabled", true);
        $("#doForm").attr("action","${path}/user/add");
//        console.log($("#doForm").attr("action"));
        $("#doForm").submit();

    }

    function checkPhone(phone){

        if(!(/^1[34578]\d{9}$/.test(phone))){
            return false;
        }else{
            return true;
        }
    }

    $(function(){

        $('#picktime').mtimer();


    });

</script>
<%--<script src="${path}/static/js/index.js"></script>--%>


</html>