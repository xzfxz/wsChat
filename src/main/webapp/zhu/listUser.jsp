<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>
    <title></title>
    <link rel="stylesheet" href="css/index.css" charset="utf-8"/>
    <link rel="stylesheet" href="css/zepto.mtimer.css" charset="UTF-8"/>


</head>
<body>

<div class="container">

    <div class="form_text" >
        <form role="form" id="chat_form" onsubmit="sendMessage(); return false;">
            <ul>
                <input id = "id" type="hidden" value="" />
                <li>
                    姓名:
                    <input id = "name" type="text" maxlength="15" value=""/>
                </li>
                <li>
                    电话:
                    <input id = "phone" type="text" maxlength="11" value=""/>
                </li>
                <li>
                    日期:
                    <input type="text" id="picktime" value="" readonly/>
                </li>
                <li>
                    <button type="button" id="send" class="btn btn-primary"
                            onclick="sendMessage();">提交</button>
                </li>
            </ul>
        </form>
    </div>
</div>
</body>

<script src="js/zepto.js" charset="UTF-8" type="text/javascript"></script>
<script src="js/zepto.mtimer.js" charset="UTF-8" type="text/javascript"></script>
<script>
    $(function(){

        $('#picktime').mtimer();

    });
</script>
<script src="js/index.js"></script>


</html>