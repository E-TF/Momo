package com.project.momo.dto.region;

import com.project.momo.entity.State;
import lombok.Getter;

@Getter
public class StateResponse {
    private long id;
    private String name;

    public StateResponse(State state) {
        this.id = state.getId();
        this.name = state.getName();
    }
}
