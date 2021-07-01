package com.game.TopScoreService.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="user_score")
@Data
public class UserScore {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column(name="user_id")
    private int userId;
    private int score;
    @Column(name="created_at")
    private Date createdAt;
}
