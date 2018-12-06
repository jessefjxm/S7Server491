package com.bdoemu.gameserver.model.skills.packageeffects.enums;

import com.bdoemu.gameserver.model.skills.packageeffects.*;

public enum EChargeUserType {
    StarterPackage {
        @Override
        AChargeUserEffect newInstance(final long startTime) {
            return new StarterPackageEffect(startTime, this);
        }
    },
    PremiumPackage {
        @Override
        AChargeUserEffect newInstance(final long startTime) {
            return new PremiumPackageEffect(startTime, this);
        }
    },
    PearlPackage {
        @Override
        AChargeUserEffect newInstance(final long startTime) {
            return new PearlPackageEffect(startTime, this);
        }
    },
    PcRoom {
        @Override
        AChargeUserEffect newInstance(final long startTime) {
            return null;
        }
    },
    CustomizationPackage {
        @Override
        AChargeUserEffect newInstance(final long startTime) {
            return new CustomizationPackageEffect(startTime, this);
        }
    },
    DyeingPackage {
        @Override
        AChargeUserEffect newInstance(final long startTime) {
            return new MervsPalettePackageEffect(startTime, this);
        }
    },
    Kamasilve {
        @Override
        AChargeUserEffect newInstance(final long startTime) {
            return new KamasilvesBlessingPackageEffect(startTime, this);
        }
    },
    UnlimitedSkillChange {
        @Override
        AChargeUserEffect newInstance(final long startTime) {
            return new ArmstrongsSkillAddonPackageEffect(startTime, this);
        }
    },
    UnlimitedSkillAwakening {
        @Override
        AChargeUserEffect newInstance(final long startTime) {
            return new ArmstrongsSkillPackageEffect(startTime, this);
        }
    },
    RussiaPack3 {
        @Override
        AChargeUserEffect newInstance(final long startTime) {
            return null;
        }
    },
    BlackSpiritTraining {
        @Override
        AChargeUserEffect newInstance(final long startTime) {
            return null;
        }
    },
    PcRoomUserHomeBuff {
        @Override
        AChargeUserEffect newInstance(final long startTime) {
            return null;
        }
    };

    abstract AChargeUserEffect newInstance(final long p0);

    public AChargeUserEffect newChargeUserEffectInstance(final long startTime) {
        return this.newInstance(startTime);
    }
}
