package com.leverx.dealerst.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@Table(name = "GameObject")
@EqualsAndHashCode
@ToString
public class GameObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @ToString.Exclude
    private User gameObjectUser;

    @ManyToMany
    @JoinTable(name = "Game_GameObj",
            joinColumns = @JoinColumn(name = "id_gameObj"),
            inverseJoinColumns = @JoinColumn(name = "id_game")
    )
    @JsonIgnore
    private List<Game> games = new ArrayList<>();

    @OneToMany(mappedBy = "gameObject")
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

}
