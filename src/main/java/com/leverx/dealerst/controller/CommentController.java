package com.leverx.dealerst.controller;

import com.leverx.dealerst.entity.Comment;
import com.leverx.dealerst.entity.GameObject;
import com.leverx.dealerst.entity.User;
import com.leverx.dealerst.entity.UserRole;
import com.leverx.dealerst.service.CommentService;
import com.leverx.dealerst.service.GameObjectService;
import com.leverx.dealerst.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class CommentController {

    private UserService userService;
    private CommentService commentService;
    private GameObjectService gameObjectService;

    @Autowired
    public CommentController(UserService userService, CommentService commentService, GameObjectService gameObjectService) {
        this.userService = userService;
        this.commentService = commentService;
        this.gameObjectService = gameObjectService;
    }

    @GetMapping("/users/{traderId}/comments")
    public List<Comment> getAllCommentByTrader(@PathVariable Integer traderId, Principal principal){
        String email = principal.getName();
        User userByEmail = userService.findByEmail(email);
        if(userByEmail.getId() == traderId){
            return commentService.getByTraderId(userByEmail);
        }
        return null;
    }

    @GetMapping("/users/{traderId}/comments/{id}")
    public Comment getCommentByTraderAndCommentId(@PathVariable Integer traderId, @PathVariable Integer id, Principal principal){
        String email = principal.getName();
        User userByEmail = userService.findByEmail(email);
        if(userByEmail.getId() == traderId){
            return commentService.getByTraderIdAndCommentId(userByEmail, id);
        }
        return null;
    }

    @DeleteMapping("/users/{traderId}/comments/{id}")
    public void deleteCommentByTraderAndCommentId(@PathVariable Integer traderId, @PathVariable Integer id, Principal principal){
        String email = principal.getName();
        User userByEmail = userService.findByEmail(email);
        if(userByEmail.getId() == traderId){
            commentService.deleteByTraderIdAndCommentId(userByEmail, id);
        }
    }

    @PostMapping("/users/{traderId}/comments/{id}")
    public void updateCommentByTraderAndCommentId(@PathVariable Integer traderId,
                                                  @PathVariable Integer id,
                                                  Principal principal,
                                                  @RequestParam("message") String text){
        String email = principal.getName();
        User userByEmail = userService.findByEmail(email);
        if(userByEmail.getId() == traderId){
            commentService.updateByTraderIdAndCommentId(id, text);
        }
    }

    @PostMapping("comments/{id}/approve")
    public void approveComment(@PathVariable Integer id,
                               Principal principal){
        String email = principal.getName();
        User userByEmail = userService.findByEmail(email);
        if(userByEmail.getUserRole() == UserRole.ADMIN){
            commentService.approveComment(id, true);
        }
    }

    @PostMapping("/object/{objectId}/comments")
    public void saveComment(@PathVariable Integer objectId,
                            @RequestBody Comment comment,
                            Principal principal){
        String email = principal.getName();
        User userByEmail = userService.findByEmail(email);
        GameObject objectById = gameObjectService.findById(objectId);
        commentService.save(userByEmail, comment, objectById);
    }

}
