package com.leverx.dealerst.service;

import com.leverx.dealerst.entity.GameObject;

import java.util.List;

public interface GameObjectService {

    void create(GameObject gameObject);

    List<GameObject> findAll();

    List<GameObject> findAllByUser(String email);

    void delete(String email, Integer id);

    void update(String email, Integer id, String text, String title);

    GameObject findById(Integer id);
}
