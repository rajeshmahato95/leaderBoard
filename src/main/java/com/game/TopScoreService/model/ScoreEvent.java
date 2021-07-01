package com.game.TopScoreService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreEvent {
    private Integer userId;
    private Integer score;
}
