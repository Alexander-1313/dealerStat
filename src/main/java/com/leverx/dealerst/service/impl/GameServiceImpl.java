package com.leverx.dealerst.service.impl;

import com.leverx.dealerst.entity.Game;
import com.leverx.dealerst.repository.GameRepository;
import com.leverx.dealerst.service.GameService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private final Logger LOGGER = LogManager.getLogger(GameServiceImpl.class);
    private GameRepository gameRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public void save(Game game) {
        if(game != null){
            gameRepository.save(game);
            LOGGER.info("game with name " + game.getName() + " has been created");
        }else{
            LOGGER.info("game wasn't created");
        }
    }

    @Override
    public void update(Integer id, String name) {
        Game game = gameRepository.getOne(id);
        if(game != null){
            if(name != null) {
                game.setName(name);
            }
            gameRepository.save(game);
            System.out.println("game was updated");
        }else{
            System.out.println("game is null");
        }
    }

    @Override
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    @Override
    public Game findById(Integer id) {
        return gameRepository.getOne(id);
    }
}
