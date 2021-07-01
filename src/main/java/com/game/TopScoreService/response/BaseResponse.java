package com.game.TopScoreService.response;

import com.game.TopScoreService.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BaseResponse<T> {
    private Status status;
    private String message;
    private T data;

}
