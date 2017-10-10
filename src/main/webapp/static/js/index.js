function sendMessage(){

    var name = $('#name').val().trim();
    var phone =$('#phone').val().trim();
    var time = $('#picktime').val().trim();

    if(name.length<1){
        alert("不能发送空内容！");
        return;
    }
    if(phone.length<1){
        alert("不能发送空内容！");
        return;
    }
    console.log(time);
    //时间格式转时间戳2017-08-29 9:00:00
    // var stringTime = "2014-07-10 10:21:12";
    var timestamp = Date.parse(new Date(time));

    var timestamp = timestamp / 1000;

    var user={
        name:name,
        phoneNum:phone,
        lat:timestamp,
        cat:timestamp,
        mark:1
    };

    var mStr = JSON.stringify(user);
    ws.send(mStr);

}

