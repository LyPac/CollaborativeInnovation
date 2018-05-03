<#import "/WEB-INF/ftl/common.ftl" as common />
<#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"] >
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>协同创新-资源共享</title>
<@common.link_and_script />
</head>
<body>
<@common.header />
<div class="layui-container container" style="margin-top: 65px">
    <div class="layui-row">

        <div class="layui-col-md12">
            <span class="layui-breadcrumb">
                <a href="${base}/index">首页</a>
                <a><cite>关于我们</cite></a>
            </span>
            <h1 align="center">关于我们</h1>
        </div>
    </div>
</div>
<@common.footer />
</body>
</html>