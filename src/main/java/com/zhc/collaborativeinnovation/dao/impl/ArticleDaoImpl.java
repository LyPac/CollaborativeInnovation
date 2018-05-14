package com.zhc.collaborativeinnovation.dao.impl;

import com.zhc.collaborativeinnovation.dao.ArticleDao;
import com.zhc.collaborativeinnovation.vo.Article;
import com.zhc.core.dao.impl.BaseDaoImpl;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("articleDao")
public class ArticleDaoImpl extends BaseDaoImpl<Article> implements ArticleDao {
    @Override
    public List<Article> orderByCriterion(String oderBy) {
        DetachedCriteria criterion = DetachedCriteria.forClass(Article.class);
        criterion.addOrder(Order.desc(oderBy));
        criterion.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Article> articleList = (List<Article>) hibernateTemplate.findByCriteria(criterion);
        List<Article> tempList = new ArrayList<>();
        for (int i = 0; i < articleList.size(); i++) {
            if (i >= 6) {
                break;
            }
            if (!tempList.contains(articleList.get(i))) {
                tempList.add(articleList.get(i));
            }
        }
        return articleList;
    }

    @Override
    public List<Article> orderByRecentReply() {
        String hql = "from Article where articleid in(select reply.article.articleid from Reply reply order by replytime desc)";
        return findByPage(hql, 0, 6);
    }

    @Override
    public List<Article> listByArticletype(int articletypeid, int page) {
        String hql = "from Article where articletype.articletypeid=? order by publishtime desc";
        return findByPage(hql, page - 1, 6, articletypeid);
    }

    @Override
    public List<Article> listByUsername(String username, int page) {
        String hql = "from Article where author.username=? order by publishtime desc";
        return findByPage(hql, page -1, 8, username);
    }

}
