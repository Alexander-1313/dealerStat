package com.leverx.dealerst.service.impl;

import com.leverx.dealerst.entity.Comment;
import com.leverx.dealerst.entity.GameObject;
import com.leverx.dealerst.entity.User;
import com.leverx.dealerst.repository.CommentRepository;
import com.leverx.dealerst.service.CommentService;
import com.leverx.dealerst.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final Logger LOGGER = LogManager.getLogger(CommentServiceImpl.class);
    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getByTraderId(User user) {
        return commentRepository.getCommentByCommentUser(user);
    }

    @Override
    public Comment getByTraderIdAndCommentId(User user, Integer commentId) {
//        return commentRepository.getCommentByIdAndCommentUser(commentId, user);
        return null;
    }

    @Override
    public void deleteByTraderIdAndCommentId(User user, Integer commentId) {
        commentRepository.deleteByUserAndId(user.getId(), commentId);
    }

    @Override
    public void updateByTraderIdAndCommentId(Integer commentId, String text) {
        Comment comment = commentRepository.getOne(commentId);
        if(comment != null){
            comment.setMessage(text);
            comment.setCreatedAt(new Date());
            commentRepository.save(comment);
            LOGGER.info("comment with id = " + comment.getId() + " was updated");
        }else{
            LOGGER.info("There are no such comment...");
        }
    }

    @Override
    public void save(User user, Comment comment, GameObject gameObject) {
        comment.setCommentUser(user);
        comment.setCreatedAt(new Date());
        comment.setGameObject(gameObject);
        comment.setApproved(false);
        commentRepository.save(comment);
        LOGGER.info("comment was created");
    }

    @Override
    public void approveComment(Integer id, Boolean approved) {
        Comment comment = commentRepository.getOne(id);
        comment.setApproved(true);
        commentRepository.save(comment);
        LOGGER.info("comment with id = " + id + " was approved");

    }
}
