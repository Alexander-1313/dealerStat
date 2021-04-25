package com.leverx.dealerst.service;

import com.leverx.dealerst.entity.Game;

import java.util.List;

public interface GameService {

    void save(Game game);

    void update(Integer id, String name);

    List<Game> findAll();

    Game findById(Integer id);
}
