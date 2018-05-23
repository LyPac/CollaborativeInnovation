package com.zhc.collaborativeinnovation.action.tradition.front;

import com.zhc.collaborativeinnovation.service.NeedsService;
import com.zhc.collaborativeinnovation.service.NewsService;
import com.zhc.collaborativeinnovation.service.PolicyService;
import com.zhc.collaborativeinnovation.service.SettingService;
import com.zhc.collaborativeinnovation.service.impl.NeedsServiceImpl;
import com.zhc.collaborativeinnovation.service.impl.PolicyServiceImpl;
import com.zhc.collaborativeinnovation.vo.Needs;
import com.zhc.collaborativeinnovation.vo.News;
import com.zhc.collaborativeinnovation.vo.Policy;
import com.zhc.core.action.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.util.List;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
public class InformationAction extends BaseAction {

    @Autowired
    @Qualifier("newsService")
    private NewsService newsService;

    @Autowired
    @Qualifier("needsService")
    private NeedsService needsService;

    @Autowired
    @Qualifier("policyService")
    private PolicyService policyService;

    @Autowired
    @Qualifier("settingService")
    private SettingService settingService;

    private List<News> newsList;

    private List<Needs> needsList;

    private List<Policy> policyList;

    private News news;

    private Needs needs;

    private Policy policy;

    private List<String> imageList;

    @Action(value = "information",results = {@Result(name = "success", type = "freemarker", location = "information.ftl")})
    public String information(){
        imageList = settingService.getSetting().getImageList();
        needsList = needsService.listTop5();
        newsList = newsService.listTop5();
        policyList = policyService.listTop5();
        return SUCCESS;
    }

    @Action(value = "newslist",results = {@Result(name = "success", type = "freemarker", location = "newslist.ftl")})
    public String newslist(){
        newsList = newsService.findByPage(curPage, 6);
        return SUCCESS;
    }

    @Action(value = "needslist",results = {@Result(name = "success", type = "freemarker", location = "needslist.ftl")})
    public String needslist(){
        needsList = needsService.findByPage(curPage, 6);
        return SUCCESS;
    }

    @Action(value = "policylist",results = {@Result(name = "success", type = "freemarker", location = "policylist.ftl")})
    public String policylist(){
        policyList = policyService.findByPage(curPage, 6);
        return SUCCESS;
    }

    @Action(value = "policy",results = {@Result(name = "success", type = "freemarker", location = "policy.ftl")})
    public String policy(){
        policy = policyService.get(Policy.class, policy.getId());
        return SUCCESS;
    }

    @Action(value = "news",results = {@Result(name = "success", type = "freemarker", location = "news.ftl")})
    public String news(){
        news =  newsService.get(News.class, news.getId());
        return SUCCESS;
    }

    @Action(value = "needs",results = {@Result(name = "success", type = "freemarker", location = "needs.ftl")})
    public String needs(){
        needs = needsService.get(Needs.class, needs.getId());
        return SUCCESS;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public List<Needs> getNeedsList() {
        return needsList;
    }

    public void setNeedsList(List<Needs> needsList) {
        this.needsList = needsList;
    }

    public List<Policy> getPolicyList() {
        return policyList;
    }

    public void setPolicyList(List<Policy> policyList) {
        this.policyList = policyList;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public Needs getNeeds() {
        return needs;
    }

    public void setNeeds(Needs needs) {
        this.needs = needs;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }
}
