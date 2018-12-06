package com.bdoemu.gameserver.model.ai.deprecated;

//@Deprecated
public enum EAIBehavior {
    idle,
    dead,
    knockback,
    none,
    turn,
    recovery,
    chase,
    around,
    action,
    revive,
    escape,
    walk,
    return_,
    skill,
    trace,
    attack;

    public boolean isReturn() {
        return this == EAIBehavior.return_;
    }

    public boolean isChase() {
        return this == EAIBehavior.chase;
    }

    public boolean isKnockback() {
        return this == EAIBehavior.knockback;
    }

    public boolean isNoDelayBehavior() {
        return this != EAIBehavior.knockback && this != EAIBehavior.skill && this != EAIBehavior.action;
    }
}