<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>推送测试跨站</title>
    <script>
        var pathToDwrServlet = "http://176.99.2.132:8888/dwr"; // Path to dwr on foreign domain
    </script>
    <script type='text/javascript' src='http://176.99.2.132:8888//dwr/util.js'></script>
    <script type='text/javascript' src='http://176.99.2.132:8888//dwr/engine.js'></script>
    <script type='text/javascript' src='http://176.99.2.132:8888//dwr/interface/DwrPush.js'></script>
    <script type="text/javascript" src="http://176.99.2.132:8888//webjars/jquery/1.11.3/jquery.js"></script>
    <script>

        $(document).ready(function(){
            // dwr改成同步
            //dwr.engine.setAsync(false);
            // 页面加载的时候进行反转的激活
            dwr.engine.setActiveReverseAjax(true) ;
            $("#sendDiv").hide();
            // 点击页面按钮的时候触发的方法
            $("#login").click(function(){
                // 此类即为根据java文件生成的js文件
                var username_login = $("#username_login").val();
                var password_login = $("#password_login").val();
                DwrPush.login(username_login,password_login,function(b){
                    if(b){
                        DwrPush.getUsername(function(uname){
                            var wel = "Welcome,"+uname;
                            DwrPush.SendAll(wel);
                            $("#usernameDiv").html(wel);
                            $("#loginDiv").hide();
                            $("#sendDiv").show();
                        });
                    }else{
                        alert(username_login+" password is error");
                    }
                });
            });

            $("#publishAll").click(function(){
                var data = $("#data").val();
                DwrPush.SendAll(data );
            });
            $("#publish").click(function(){
                var username = $("#username").val();
                var userdata = $("#userdata").val();
                DwrPush.Send(username,userdata,function(b){
                    if(!b){
                        alert("username "+username+" is not Online");
                    }
                });
            });
        });

        //////////////////////////////////////用于后台调取的函数
        function callback(msg){
            $("#ul").html($("#ul").html()+"<br />"+msg);
        }
    </script>
</head>
<body>


<hr>
<div id="loginDiv">
username:<input type="text" id="username_login" name='username' />&nbsp;&nbsp;&nbsp;&nbsp;
password:<input type="password" id="password_login" name='password' />&nbsp;&nbsp;&nbsp;&nbsp;
<input type='button' id="login" value="login">
</div>
<div id="usernameDiv">
</div>
<hr>
<div id="sendDiv">
data:<input type="text" id="data" name='data' />&nbsp;&nbsp;&nbsp;&nbsp;
<input type='button' id="publishAll" value="publishAll">
<hr>
username:<input type="text" id="username" name='username' />&nbsp;&nbsp;&nbsp;&nbsp;
userdata:<input type="text" id="userdata" name='userdata' />&nbsp;&nbsp;&nbsp;&nbsp;
<input type='button' id="publish" value="publish">
<ul id="ul">
</ul>
</div>
</body>
</html>