<#macro link_and_script>
<script src="${base}/assets/js/jquery/jquery-2.1.4.min.js"></script>
<script src="${base}/assets/layui/layui.js"></script>
<script src="${base}/assets/js/index/index.js"></script>
<script src="${base}/assets/js/index/freezeheader.js"></script>
<script src="${base}/assets/layui/lay/modules/layer.js"></script>
<script src="${base}/assets/js/index/sliders.js"></script>
<script src="${base}/assets/js/index/html5.js"></script>
<script src="${base}/assets/js/index/article_details.js" type="text/javascript"></script>
<link rel="stylesheet" href="${base}/assets/layui/css/layui.css" media="all"/>
<link rel="stylesheet" href="${base}/assets/layui/css/modules/layer/default/layer.css"/>
<link rel="stylesheet" href="${base}/assets/css/global.css"/>
</#macro>

<#macro header>
<div class="layui-header header" style="background-color: white">
    <div class="main" style="background-color: white">
        <ul class="layui-nav layui-nav-left" lay-filter="filter">

            <li class="layui-nav-item layui-this nav-left" pc>
                <a class="logo" href="index" title="Fly">logo</a>
            </li>
        </ul>
        <ul class="layui-nav layui-layout-right" lay-filter="filter" style="background-color: white">
            <!-- 搜索框 -->
            <form class="layui-form" method="post" action="#" style="margin-right: 200px; margin-top: 10px">
                <div class="layui-form-item">
                    <div class="layui-input-inline" width="60%">
                        <input type="text" name="title" lay-verify="required" placeholder="请输入关键字"
                               class="layui-input">
                    </div>
                    <input type="submit" class="layui-btn layui-btn-normal" lay-filter="*" lay-submit value="搜索"/>
                </div>
            </form>
        </ul>
    </div>
</div>
<div class="layui-header header" style="margin-top: 65px;background-color: #1E9FFF">
    <div class="main" style="background-color: #1E9FFF">
        <ul class="layui-nav layui-nav-left layui-bg-blue" lay-filter="filter">
            <li class="layui-nav-item nav-left" pc>
                <a href="${base}/index">首页</a>
            </li>
            <li class="layui-nav-item">
                <a href="${base}/articlelist">资源共享</a>
            </li>
            <li class="layui-nav-item">
                <a href="#">信息咨讯</a>
            </li>
            <li class="layui-nav-item">
                <a href="javascript:;" onclick="addFavorite(location.href, document.title)">加入收藏</a>
            </li>
            <li class="layui-nav-item">
                <a href="#">关于我们</a>
            </li>
        </ul>
    </div>
</div>
<script>
    function addFavorite(url, title) {
        url = encodeURI(url);
        try {
            window.external.addFavorite(url, title);
        } catch (e) {
            try {
                window.sidebar.addPanel(title, url, "");
            } catch (e) {
                alert("加入收藏失败,请使用Ctrl+D进行添加,或手动在浏览器里进行设置.");
            }
        }
    }
</script>
</#macro>

<#macro recommend>
<script>
    $(function () {
        var oLi = $("#tab li");
        var oUl = $("#ms-main div");
        var sortBy = ["pageview", "favoritecount", "recentReply"];
        for (var i = 0; i < oLi.length; i++) {
            oLi[i].index = i;
            oLi[i].onclick = function () {
                for (var n = 0; n < oLi.length; n++)
                    oLi[n].className = "";
                this.className = "cur";
                var data = {
                    "sortKey": sortBy[this.index]
                };
                $.ajax({
                    url: "${base}/recommend",
                    type: "post",
                    data: data,
                    success: function (json) {
                        console.log(json);
                        var articleList = json.articleList;
                        $("#ms-main div li").each(function (i, cLi) {
                            cLi.innerHTML = "<a href='${base}/article?articleid=" + articleList[i].articleid + "'>" + articleList[i].title + "</a>";
                        });
                    }
                })
            }
        }
    });
</script>
</#macro>

<#macro left>
<ul class="layui-nav layui-nav-tree layui-bg-blue layui-inline" lay-filter="demo">
    <li class="layui-nav-item"><a href="${base}/usercenter">个人资料</a></li>
    <li class="layui-nav-item"><a href="${base}/user/userlist">用户管理</a></li>
    <li class="layui-nav-item"><a href="${base}/enterprise/enterpriseinfo">企业资料</a></li>
    <li class="layui-nav-item"><a href="${base}/enterprise/authentication">企业认证</a></li>
    <li class="layui-nav-item"><a href="${base}/enterprise/authenticationlist">认证管理</a></li>
    <li class="layui-nav-item"><a href="${base}/article/articlelist">文章管理</a></li>
    <li class="layui-nav-item"><a href="${base}/enterprise/enterpriselist">企业管理</a></li>
    <li class="layui-nav-item"><a href="${base}/logout">注销</a></li>
</ul>
</#macro>

<#macro footer>
<div class="footer">
    <hr>
    <p>@layui框架模板</p>
</div>
</#macro>