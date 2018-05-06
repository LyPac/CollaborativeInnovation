package com.zhc.collaborativeinnovation.action.tradition;

import com.zhc.collaborativeinnovation.service.ArticleService;
import com.zhc.collaborativeinnovation.service.ReplyService;
import com.zhc.collaborativeinnovation.vo.Article;
import com.zhc.collaborativeinnovation.vo.Articletype;
import com.zhc.collaborativeinnovation.vo.Reply;
import com.zhc.collaborativeinnovation.vo.User;
import com.zhc.core.action.BaseAction;
import com.zhc.core.realms.LoginRealm;
import com.zhc.core.service.BaseService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
public class IndexAction extends BaseAction {

    @Autowired
    @Qualifier("articleService")
    private ArticleService articleService;

    @Autowired
    @Qualifier("baseService")
    private BaseService<Articletype> articletypeService;

    @Autowired
    @Qualifier("replyService")
    private ReplyService replyService;

    private User user;

    private Reply reply;

    private int articletypeid = 1;

    private List<Article> articleList;

    private List<Articletype> articletypeList;

    private List<Article> pageviewArticleList;

    private List<Reply> replyList;

    private Article article;

    @Action(value = "index", results = {@Result(name = "success", type = "freemarker", location = "index.ftl")})
    public String index() {
        articleList = articleService.listSortByPublishtime();
        pageviewArticleList = articleService.listSortByPageview();
        return SUCCESS;
    }

    @Action(value = "usercenter", results = {@Result(name = "success", type = "freemarker", location = "usercenter.ftl")})
    public String userCenter() {

        return SUCCESS;
    }

    @Action(value = "articlelist", results = {@Result(name = "success", type = "freemarker", location = "articlelist.ftl")})
    public String articlelist() {
        articleList = articleService.listByArticletype(articletypeid, curPage);
        articletypeList = articletypeService.list(Articletype.class);
        pages = articleService.getPages();
        return SUCCESS;
    }

    @Action(value = "article", results = {
            @Result(name = "success", type = "freemarker", location = "article.ftl")
            ,@Result(name = "error", type = "redirect", location = "articlelist")})
    public String article() {
        if (article == null) {
            msg="该文章已不存在";
            return ERROR;
        } else {
            article = articleService.get(article.getArticleid());
            article.setPageview(article.getPageview()+1);
            article.setReviewcount(article.getReplySet().size());
            articleService.saveOrUpdate(article);
            replyList = replyService.listByPageInUser(article.getArticleid(), curPage);
            pages = replyService.getPages(article.getArticleid());
            articleList = articleService.listSortByPageview();
            articletypeList = articletypeService.list(Articletype.class);
            return SUCCESS;
        }
    }

    @Action(value = "reply", results = {
            @Result(name = "success", type = "redirect", location = "/article?article.articleid=${reply.article.articleid}")
            ,@Result(name = "error", type = "redirect", location = "/login")})
    public String reply(){
        Timestamp replytime = new Timestamp(new Date().getTime());
        Subject subject = SecurityUtils.getSubject();
        LoginRealm.ShiroUser shiroUser = (LoginRealm.ShiroUser) subject.getPrincipal();
        if(shiroUser == null){
            return ERROR;
        }
        user = new User();
        user.setUsername(shiroUser.getUsername());
        reply.setUser(user);
        reply.setReplytime(replytime);
        System.out.println(reply);
        replyService.saveOrUpdate(reply);
        return SUCCESS;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Article> getPageviewArticleList() {
        return pageviewArticleList;
    }

    public void setPageviewArticleList(List<Article> pageviewArticleList) {
        this.pageviewArticleList = pageviewArticleList;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public List<Articletype> getArticletypeList() {
        return articletypeList;
    }

    public void setArticletypeList(List<Articletype> articletypeList) {
        this.articletypeList = articletypeList;
    }

    public int getArticletypeid() {
        return articletypeid;
    }

    public void setArticletypeid(int articletypeid) {
        this.articletypeid = articletypeid;
    }

    public List<Reply> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<Reply> replyList) {
        this.replyList = replyList;
    }

    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }
}
