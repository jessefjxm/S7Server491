package com.bdoemu.gameserver.model.actions.enums;

public enum EMoveDirectionType {
    Foward,
    Backward,
    Foward_Direction,
    Backward_Direction,
    Foward_Direction_Fix,
    Backward_Direction_Fix,
    LadderUp,
    LadderDown,
    Foward_Camera_Direction,
    Backward_Camera_Direction;

    public boolean isFoward() {
        return this == EMoveDirectionType.Foward;
    }

    public boolean isFixedMoveDirection() {
        return this == EMoveDirectionType.Foward_Direction_Fix || this == EMoveDirectionType.Backward_Direction_Fix;
    }
}