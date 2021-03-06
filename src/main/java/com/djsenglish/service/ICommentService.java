package com.djsenglish.service;

import com.djsenglish.common.ServerResponse;
import com.djsenglish.pojo.ArticleComment;

public interface ICommentService {

    ServerResponse getList(Integer pageNum, Integer pageSize, Integer userId, Integer articleId);

    ServerResponse addComment(ArticleComment articleComment);

    ServerResponse delComment(Integer commentId, Integer userId);

    ServerResponse likeComment(Integer UserId, Integer commentId);

    ServerResponse dislikeComment(Integer userId, Integer commentId);
}
