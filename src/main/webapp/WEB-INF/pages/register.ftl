<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>协同创新-注册</title>
    <link rel="stylesheet" href="${base}/assets/layui/css/layui.css">
    <link rel="stylesheet" href="${base}/assets/css/style.css">
</head>
<body class="login-body body">

<div class="login-main">
    <header class="layui-elip" style="margin-top: 100px">协同创新-注册</header>
    <form class="layui-form" action="${base}/user/register" method="post">
        <div class="layui-form-item">
            <div class="layui-input-inline">
                <input id="username" type="text" name="user.username" lay-verify="username" placeholder="用户名" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-inline">
                <input type="text" name="user.realname" lay-verify="realname" placeholder="姓名"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-inline">
                <input type="password" id="password" name="user.password" lay-verify="password" placeholder="密码"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-inline">
                <input type="password" name="" lay-verify="repassword" placeholder="确认密码"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-inline">
                <input id="phone" type="text" name="user.phone" lay-verify="phone" placeholder="手机号码" autocomplete="off"
                       class="layui-input" maxlength="11">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-inline" style="width:210px">
                <input type="text" name="code" lay-verify="code" placeholder="验证码" autocomplete="off"
                       class="layui-input" maxlength="6">
            </div>
            <div class="layui-form-pane">
                <a id="code" class="layui-btn layui-btn-primary" style="width: 130px" href="javascript:;"
                   onclick="sendCode()">发送验证码
                </a>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-inline">
                <input type="submit" lay-submit class="layui-btn layui-btn-normal" value="注册"/>
            </div>
        </div>
        <hr/>
        <p><a href="${base}/login" class="fl">已有账号，登录</a>

    </form>
</div>

<script src="${base}/assets/layui/layui.js"></script>
<script src="${base}/assets/js/jquery/jquery-2.1.4.min.js"></script>
<script>
    layui.use(['form', 'layer'], function () {
        var form = layui.form
                , layer = layui.layer;

        form.verify({
            username: function (value) {
                if (value == "") {
                    return "请输入用户名";
                } else {
                    var verifyStr = "";
                    $.ajax({
                        url: "${base}/verify/username",
                        type: "post",
                        async: false,
                        data: {
                            "username": value
                        },
                        success: function (json) {
                            verifyStr = json.verifyStr;
                        }
                    });
                    return verifyStr;
                }
            },
            realname: function (value) {
                if (value == "") {
                    return "请输入姓名";
                }
            },
            password: function (value) {
                if (value == "") {
                    return "请输入密码";
                }
            },
            repassword: function (value) {
                var pass = $('#password').val();
                if (value != pass) {
                    return "两次输入密码不一致";
                }
            },
            code: function (value) {
                if (value == "") {
                    return "请输入验证码";
                } else {
                    var verifyStr = "";
                    $.ajax({
                        url: "${base}/verify/code",
                        type: "post",
                        async: false,
                        data: {
                            "code": value,
                            "phone":$('#phone').val()
                        },
                        success: function (json) {
                            verifyStr = json.verifyStr;
                        }
                    });
                    return verifyStr;
                }
            }
        });
        <#if msg??>
            layer.msg("${(msg)!}");
        </#if>
    });

    function sendCode() {
        var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
        var phone = $('#phone').val();
        if ('' == phone||phone.length!=11||!mobile.test(phone)) {
            layer.msg("手机号不正确");
        } else {
            $.ajax({
                url: '${base}/verify/sendCode',
                type: "post",
                data:{
                    "username":$('#username').val(),
                    "phone":phone
                },
                dataType:"json",
                success:function (json) {
                    var verifyStr = json.verifyStr;
                    if("success" == verifyStr){
                        var code = $('#code');
                        code.addClass("layui-btn-disabled");
                        code.html("重新发送:" + 30 + "s");
                        code.removeAttr("onclick")
                        t = setTimeout("countDown(30)", 1000);
                        layer.msg("发送验证码成功");
                    }else if("error" == verifyStr){
                        layer.msg("发送验证码失败");
                    }
                },
                error:function (json) {
                    layer.msg("发送验证码失败");
                }
            });
        }
    }

    function countDown(value) {
        value = value - 1;
        var code = $('#code');
        code.html("重新发送：" + value + "s");
        if (value == 0) {
            code.html("发送验证码");
            code.removeClass("layui-btn-disabled");
            code.attr("onclick", "sendCode()");
            return;
        }
        t = setTimeout("countDown(" + value + ")", 1000);
    }
</script>
</body>
</html>