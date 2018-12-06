package com.bdoemu.gameserver.model.creature.player.challenge.enums;

import com.bdoemu.gameserver.model.creature.player.challenge.*;

public enum EChallengeCompleteType {
    Unk0(0) {
        @Override
        AChallenge newInstance() {
            return null;
        }
    },
    EveryDay(1) {
        @Override
        AChallenge newInstance() {
            return new EveryDayChallenge();
        }
    },
    LevelUp(2) {
        @Override
        AChallenge newInstance() {
            return new LevelUpChallenge();
        }
    },
    Daily(3) {
        @Override
        AChallenge newInstance() {
            return new DailyChallenge();
        }
    },
    LifeExpLevel(4) {
        @Override
        AChallenge newInstance() {
            return new LifeExpLevelChallenge();
        }
    },
    Online(5) {
        @Override
        AChallenge newInstance() {
            return new OnlineChallenge();
        }
    },
    Unk6(6) {
        @Override
        AChallenge newInstance() {
            return null;
        }
    },
    Unk7(7) {
        @Override
        AChallenge newInstance() {
            return null;
        }
    },
    Coupon(8) {
        @Override
        AChallenge newInstance() {
            return null;
        }
    };

    private byte id;

    EChallengeCompleteType(final int id) {
        this.id = (byte) id;
    }

    public static EChallengeCompleteType valueOf(final int id) {
        for (final EChallengeCompleteType type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid ChallengeCompleteType id: " + id);
    }

    public byte getId() {
        return this.id;
    }

    abstract AChallenge newInstance();

    public AChallenge newChallengeInstance() {
        return this.newInstance();
    }
}
