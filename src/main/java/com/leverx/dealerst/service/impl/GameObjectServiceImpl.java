package com.leverx.dealerst.service.impl;

import com.leverx.dealerst.entity.GameObject;
import com.leverx.dealerst.entity.User;
import com.leverx.dealerst.repository.GameObjectRepository;
import com.leverx.dealerst.service.GameObjectService;
import com.leverx.dealerst.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GameObjectServiceImpl implements GameObjectService {

    private final Logger LOGGER = LogManager.getLogger(GameServiceImpl.class);

    private GameObjectRepository gameObjectRepository;
    private UserService userService;

    @Autowired
    public GameObjectServiceImpl(GameObjectRepository gameObjectRepository, UserService userService) {
        this.gameObjectRepository = gameObjectRepository;
        this.userService = userService;
    }

    @Override
    public void create(GameObject gameObject) {
        if (gameObject != null) {
            gameObject.setCreatedAt(new Date());
            gameObject.setUpdatedAt(new Date());
            gameObjectRepository.save(gameObject);
            LOGGER.info("gameObject with title " + gameObject.getTitle() + " was added to DB");
        } else {
            LOGGER.info("gameObj is null");
        }
    }

    @Override
    public List<GameObject> findAll() {
        return gameObjectRepository.findAll();
    }

    @Override
    public List<GameObject> findAllByUser(String email) {
        User user = userService.findByEmail(email);
        return gameObjectRepository.findByGameObjectUser(user);
    }

    @Override
    public void delete(String email, Integer id) {
        User user = userService.findByEmail(email);
        GameObject gameObject = gameObjectRepository.getOne(id);
        if (user.getGameObjects().contains(gameObject)) {
            gameObjectRepository.deleteById(id);
            LOGGER.info("gameObject with id " + gameObject.getId() + " was deleted");
        } else {
            LOGGER.warn("no auth for deleting obj...");
        }
    }

    @Override
    public void update(String email, Integer id, String text, String title) {
        User byEmail = userService.findByEmail(email);
        GameObject gameObject = gameObjectRepository.getOne(id);

        if (title != null) gameObject.setTitle(title);
        if (text != null) gameObject.setText(text);

        gameObject.setUpdatedAt(new Date());
        LOGGER.info("gameObject with id " + gameObject.getId() + " was updated");
        gameObjectRepository.save(gameObject);
    }

    @Override
    public GameObject findById(Integer id) {
        return gameObjectRepository.getOne(id);
    }


}
