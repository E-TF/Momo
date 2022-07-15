package com.project.momo.dto.region;

import com.project.momo.entity.State;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class StateResponseList {
    private List<StateResponse> stateResponseList = new ArrayList<>();

    public StateResponseList(List<State> states) {
        states.stream().map(StateResponse::new).forEach(stateResponseList::add);
    }
}
