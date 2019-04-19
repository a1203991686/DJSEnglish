package com.DJSEnglish.controller;

import com.DJSEnglish.common.Const;
import com.DJSEnglish.common.ServerResponse;
import com.DJSEnglish.pojo.User;
import com.DJSEnglish.service.IArticleService;
import com.DJSEnglish.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestMapping("/article/")
@Controller
public class ArticleController {

    @Autowired
    private IArticleService iArticleService;



    @RequestMapping(value = "get_list.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getList( @RequestParam(required = false, defaultValue = "1") Integer pageNum, @RequestParam(required = false, defaultValue = "10")Integer pageSize)
    {
        return iArticleService.getList(pageNum, pageSize);
    }

    @RequestMapping(value = "get_detail.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getDetail(HttpServletRequest request, Integer articleId)
    {
        Integer id = (Integer) request.getAttribute(Const.ID);
        return iArticleService.getDetail(articleId, id);
    }

    @RequestMapping(value = "like_article.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse likeComment(HttpServletRequest request, Integer articleId)
    {
        Integer id = (Integer) request.getAttribute(Const.ID);
        return iArticleService.likeArticle(id, articleId);
    }

    @RequestMapping(value = "dislike_article.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse dislikeComment(HttpServletRequest request, Integer articleId)
    {
        Integer id = (Integer) request.getAttribute(Const.ID);
        return iArticleService.dislikeArticle(id, articleId);
    }

    @RequestMapping(value = "get_collections.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getCollections(HttpServletRequest request)
    {
        Integer id = (Integer) request.getAttribute(Const.ID);
        return iArticleService.getCollections(id);
    }

    @RequestMapping(value = "add_collection.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addCollection(HttpServletRequest request, Integer articleId)
    {
        Integer id = (Integer) request.getAttribute(Const.ID);
        return iArticleService.collectionArticle(id, articleId);
    }

    @RequestMapping(value = "del_collection.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse delCollection(HttpServletRequest request, Integer articleId)
    {
        Integer id = (Integer) request.getAttribute(Const.ID);
        return iArticleService.delColletcion(id, articleId);
    }
}
