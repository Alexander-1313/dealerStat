package com.leverx.dealerst.repository;

import com.leverx.dealerst.entity.Comment;
import com.leverx.dealerst.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> getCommentByCommentUser(User user);

//    Comment getCommentByIdAndCommentUser(Integer id, User user);

//    Comment getCommentByIdAndCommentUserAndApproved(Integer id, User user, Boolean approved);

    @Transactional
    @Modifying
    @Query("delete from Comment c WHERE c.commentUser = :traderId and c.Id = :id")
    void deleteByUserAndId(Integer traderId, Integer id);

}
