package com.welab.backend_user.common.type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 특정 작업(Action)과 그 작업이 수행된 대상의 ID를 함께 전달하기 위한 DTO
public class ActionAndId {
    private String action;  // Create, Update 등
    private Long id;

    public static ActionAndId of(String action, Long id) {
        ActionAndId actionAndId = new ActionAndId();
        actionAndId.action = action;
        actionAndId.id = id;
        return actionAndId;
    }
}
