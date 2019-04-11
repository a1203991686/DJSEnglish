package com.DJSEnglish.service.impl;

import com.DJSEnglish.common.ServerResponse;
import com.DJSEnglish.dao.ArticleLikeMapper;
import com.DJSEnglish.dao.ArticleMapper;
import com.DJSEnglish.dao.CollectionMapper;
import com.DJSEnglish.dao.CommentLikeMapper;
import com.DJSEnglish.pojo.Article;
import com.DJSEnglish.pojo.ArticleLike;
import com.DJSEnglish.service.IArticleService;
import com.DJSEnglish.util.DateTimeUtil;
import com.DJSEnglish.vo.ArticleVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("iArticleService")
public class ArticleServiceImpl implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleLikeMapper articleLikeMapper;

    @Autowired
    private CollectionMapper collectionMapper;

    @Override
    public ServerResponse getList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Article> articles = articleMapper.getList();
        List<ArticleVo> articleVos = new ArrayList<>();
        if(articles == null || articles.size() <= 0)
        {
            return ServerResponse.createByErrorMsg("页码超出范围");
        }
        for (Article article : articles) {
            articleVos.add(toArticleVo(article));
        }
        PageInfo articlePage = new PageInfo(articleVos);

        articlePage.setOrderBy("updateTime");
        return ServerResponse.createBySuccess(articlePage);
    }

    @Override
    public ServerResponse getDetail(Integer articleId, Integer userId)
    {
        Article article = articleMapper.selectByPrimaryKey(articleId);
        if(article == null)
        {
            return ServerResponse.createByErrorMsg("文章不存在");
        }
//        article.setCreateTime(DateTimeUtil.strToDate(article.getCreateTime().toString()));
//        article.setUpdateTime(DateTimeUtil.strToDate(article.getUpdateTime().toString()));
        ArticleVo articleVo = toArticleVo(article, userId);
        return ServerResponse.createBySuccess(articleVo);
    }

    public ArticleVo toArticleVo(Article article, Integer userId)
    {
        ArticleVo articleVo = new ArticleVo();
        int length = article.getText().length();
        if(articleLikeMapper.selectById(userId, article.getId()) > 0)
        {
            articleVo.setLike(true);
        }
        if(collectionMapper.selectById(userId, article.getId()) > 0)
        {
            articleVo.setCollection(true);
        }
        articleVo.setBegin(article.getText().substring(0, length > 50 ? 50 : length));
        articleVo.setCreateTime(DateTimeUtil.dateToStr(article.getCreateTime()));
        articleVo.setUpdateTime(DateTimeUtil.dateToStr(article.getUpdateTime()));
        articleVo.setCollection(article.getCollection());
        articleVo.setId(article.getId());
        articleVo.setImg(article.getImg());
        articleVo.setLikes(article.getLikes());
        articleVo.setText(article.getText());
        return  articleVo;
    }

    public ArticleVo toArticleVo(Article article)
    {
        ArticleVo articleVo = new ArticleVo();
        int length = article.getText().length();
        articleVo.setBegin(article.getText().substring(0, length > 50 ? 50 : length));
        articleVo.setCreateTime(DateTimeUtil.dateToStr(article.getCreateTime()));
        articleVo.setUpdateTime(DateTimeUtil.dateToStr(article.getUpdateTime()));
        articleVo.setCollection(article.getCollection());
        articleVo.setId(article.getId());
        articleVo.setImg(article.getImg());
        articleVo.setLikes(article.getLikes());
        articleVo.setText(article.getText());
        return  articleVo;
    }


}
