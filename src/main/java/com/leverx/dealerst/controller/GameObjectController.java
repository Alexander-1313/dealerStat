package com.leverx.dealerst.controller;

import com.leverx.dealerst.entity.GameObject;
import com.leverx.dealerst.entity.User;
import com.leverx.dealerst.service.GameObjectService;
import com.leverx.dealerst.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class GameObjectController {

    private GameObjectService gameObjectService;
    private UserService userService;

    @Autowired
    public GameObjectController(GameObjectService gameObjectService, UserService userService) {
        this.gameObjectService = gameObjectService;
        this.userService = userService;
    }

    @PutMapping("/object/{id}")
    public void updateObject(@PathVariable Integer id,
                             @RequestParam("title") String title,
                             @RequestParam("text") String text,
                             Principal principal){
        String email = principal.getName();
        gameObjectService.update(email, id, text, title);
    }

    @PostMapping("/object")
    public void addObject(@RequestBody GameObject gameObject,
                          Principal principal){
        String userEmail = principal.getName();
        User user = userService.findByEmail(userEmail);
        gameObject.setGameObjectUser(user);
        gameObjectService.create(gameObject);
    }

    @GetMapping("/object")
    public List<GameObject> getAllObjects(){
        return gameObjectService.findAll();
    }

    @GetMapping("/my")
    public List<GameObject> getUserObject(Principal principal){
        String email = principal.getName();
        return gameObjectService.findAllByUser(email);
    }

    @DeleteMapping("/object/{id}")
    public void deleteObject(@PathVariable Integer id,
                             Principal principal){
        String email = principal.getName();
        gameObjectService.delete(email, id);
    }
}
