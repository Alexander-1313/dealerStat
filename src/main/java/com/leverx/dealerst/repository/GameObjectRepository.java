package com.leverx.dealerst.repository;

import com.leverx.dealerst.entity.GameObject;
import com.leverx.dealerst.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameObjectRepository extends JpaRepository<GameObject, Integer> {

    List<GameObject> findByGameObjectUser(User user);

}
