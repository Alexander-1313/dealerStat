package com.leverx.dealerst.controller;

import com.leverx.dealerst.entity.Game;
import com.leverx.dealerst.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/games")
    public List<Game> getAllGames(){
        return gameService.findAll();
    }

    @PostMapping("/games")
    public String addGame(@RequestBody Game game){
        gameService.save(game);
        return game.getName();
    }

    @PutMapping("/games/{id}")
    public String updateGame(@PathVariable Integer id,
                           @RequestParam("name") String name){
        gameService.update(id, name);
        return "game was updated: " + name;
    }
}
