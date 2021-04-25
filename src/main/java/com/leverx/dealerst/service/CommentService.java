package com.leverx.dealerst.service;

import com.leverx.dealerst.entity.Comment;
import com.leverx.dealerst.entity.GameObject;
import com.leverx.dealerst.entity.User;

import java.util.List;

public interface CommentService {

    List<Comment> getByTraderId(User user);

    Comment getByTraderIdAndCommentId(User user, Integer commentId);

    void deleteByTraderIdAndCommentId(User user, Integer commentId);

    void updateByTraderIdAndCommentId(Integer commentId, String text);

    void save(User user, Comment comment, GameObject gameObject);

    void approveComment(Integer id, Boolean approved);
}
