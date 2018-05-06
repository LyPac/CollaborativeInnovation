package com.zhc.collaborativeinnovation.service.impl;

import com.zhc.collaborativeinnovation.dao.ArticleDao;
import com.zhc.collaborativeinnovation.dao.impl.ArticleDaoImpl;
import com.zhc.collaborativeinnovation.vo.Article;
import com.zhc.collaborativeinnovation.service.ArticleService;
import com.zhc.core.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service("articleService")
public class ArticleServiceImpl extends BaseServiceImpl<Article> implements ArticleService{


    @Autowired
    @Qualifier("articleDao")
    private ArticleDao articleDao;

    @Override
    public List<Article> listSortByPublishtime() {
        System.out.println("---------------listSortByPublishtime-----------------");
        return articleDao.orderByCriterion("publishtime");
    }

    @Override
    public List<Article> listSortByPageview() {
        System.out.println("-----------------listSortByPageview------------------");
        return articleDao.orderByCriterion("pageview");
    }

    @Override
    public List<Article> listSortByRecentReply() {

        return articleDao.orderByRecentReply();
    }

    @Override
    public List<Article> listByArticletype(int articletypeid, int page) {
        return articleDao.listByArticletype(articletypeid, page);
    }

    @Override
    public Article get(Serializable id) {
        return this.get(Article.class,id);
    }

    @Override
    public int getPages() {
        return super.getPages(Article.class);
    }
}
