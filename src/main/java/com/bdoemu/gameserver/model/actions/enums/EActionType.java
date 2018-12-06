package com.bdoemu.gameserver.model.actions.enums;

import com.bdoemu.gameserver.model.actions.IAction;
import com.bdoemu.gameserver.model.actions.actioncharts.*;
import com.bdoemu.gameserver.model.actions.templates.ActionChartActionT;

public enum EActionType {
    Move {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new MoveAction(actionChartActionT);
        }
    },
    ForcedMove(true) {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new ForcedMoveAction(actionChartActionT);
        }
    },
    Attack(true) {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new AttackAction(actionChartActionT);
        }
    },
    ArrowAttack(true) {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new ArrowAttackAction(actionChartActionT);
        }
    },
    Skill(true) {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new SkillAction(actionChartActionT);
        }
    },
    ArrowSkill(true) {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new ArrowSkillAction(actionChartActionT);
        }
    },
    CombineWave {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new CombineWaveAction(actionChartActionT);
        }
    },
    Defence {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new DefenceAction(actionChartActionT);
        }
    },
    Misc {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new MiscAction(actionChartActionT);
        }
    },
    Catch {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new CatchAction(actionChartActionT);
        }
    },
    Collect {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new CollectAction(actionChartActionT);
        }
    },
    Product_Prepare {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    },
    Product_Failed {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    },
    Product_Start {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    },
    Product_Wait {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    },
    Product_Progress {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    },
    Product_Finish {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    },
    RideOn_Prepare {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new RideOnPrepareAction(actionChartActionT);
        }
    },
    RideOn_Do {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new RideOnDoAction(actionChartActionT);
        }
    },
    RideOn_Failed {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new RideOnFailedAction(actionChartActionT);
        }
    },
    RideOff_Prepare {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new RideOffPrepareAction(actionChartActionT);
        }
    },
    RideOff_Do {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new RideOffDoAction(actionChartActionT);
        }
    },
    RideOff_Failed {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new RideOffFailedAction(actionChartActionT);
        }
    },
    LadderOn {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new LadderOnAction(actionChartActionT);
        }
    },
    LadderOff {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new LadderOffAction(actionChartActionT);
        }
    },
    LadderMove {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new LadderMoveAction(actionChartActionT);
        }
    },
    Droving_Start {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    },
    Droving_Wait {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    },
    Droving_Do {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    },
    Droving_End {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    },
    Arrest_Target {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    },
    Die {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new DieAction(actionChartActionT);
        }
    },
    Fishing_Casting {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new FishingCastingAction(actionChartActionT);
        }
    },
    Fishing_Landing {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new FishingLandingAction(actionChartActionT);
        }
    },
    Build_Tent {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new BuildTentAction(actionChartActionT);
        }
    },
    TakeDown_Tent {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new TakeDownTentAction(actionChartActionT);
        }
    },
    MiniGame0 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new MiniGame0Action(actionChartActionT);
        }
    },
    MiniGame1 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new MiniGame1Action(actionChartActionT);
        }
    },
    MiniGame2 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new MiniGame2Action(actionChartActionT);
        }
    },
    MiniGame3 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new MiniGame3Action(actionChartActionT);
        }
    },
    MiniGame4 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new MiniGame4Action(actionChartActionT);
        }
    },
    MiniGame5 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new MiniGame5Action(actionChartActionT);
        }
    },
    MiniGame6 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new MiniGame6Action(actionChartActionT);
        }
    },
    MiniGame7 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new MiniGame7Action(actionChartActionT);
        }
    },
    MiniGame8 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new MiniGame8Action(actionChartActionT);
        }
    },
    MiniGame9 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new MiniGame9Action(actionChartActionT);
        }
    },
    MiniGame10 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new MiniGame10Action(actionChartActionT);
        }
    },
    MiniGame11 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new MiniGame11Action(actionChartActionT);
        }
    },
    MiniGame12 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new MiniGame12Action(actionChartActionT);
        }
    },
    MiniGame13 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new MiniGame13Action(actionChartActionT);
        }
    },
    MiniGame14 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new MiniGame14Action(actionChartActionT);
        }
    },
    MiniGame15 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new MiniGame15Action(actionChartActionT);
        }
    },
    MiniGame16 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new MiniGame16Action(actionChartActionT);
        }
    },
    MiniGame17 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new MiniGame17Action(actionChartActionT);
        }
    },
    MiniGame18 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new MiniGame18Action(actionChartActionT);
        }
    },
    MiniGame19 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new MiniGame19Action(actionChartActionT);
        }
    },
    VehicleSkill {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new VehicleSkillAction(actionChartActionT);
        }
    },
    UseItem {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new UseItemAction(actionChartActionT);
        }
    },
    Manufacture {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new ManufactureAction(actionChartActionT);
        }
    },
    ClimbWall1 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new ClimbWall1Action(actionChartActionT);
        }
    },
    ClimbWall2 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new ClimbWall2Action(actionChartActionT);
        }
    },
    ClimbWall3 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new ClimbWall3Action(actionChartActionT);
        }
    },
    RepairItem {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    },
    Taming_Start {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new TamingStartAction(actionChartActionT);
        }
    },
    Taming_Step1 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new TamingStep1Action(actionChartActionT);
        }
    },
    Taming_Step2 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new TamingStep2Action(actionChartActionT);
        }
    },
    Taming_End {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new TamingEndAction(actionChartActionT);
        }
    },
    ReadBook {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new ReadBookAction(actionChartActionT);
        }
    },
    Campfire {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    },
    SwimmingStep {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new SwimmingStepAction(actionChartActionT);
        }
    },
    CanntSwimmingStep {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new CanntSwimmingStepAction(actionChartActionT);
        }
    },
    UseInstallation_Prepare {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new UseInstallationPrepareAction(actionChartActionT);
        }
    },
    UseInstallation_Do {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new UseInstallationDoAction(actionChartActionT);
        }
    },
    UseInstallation_Failed {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new UseInstallationFailedAction(actionChartActionT);
        }
    },
    UsingInstallation {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new UsingInstallationAction(actionChartActionT);
        }
    },
    FinishUseInstallation {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new FinishUseInstallationAction(actionChartActionT);
        }
    },
    WaitIdle {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new WaitIdleAction(actionChartActionT);
        }
    },
    PetOwnerOn_Prepare {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    },
    PetOwnerOn {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    },
    Takedown_Cannon {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    },
    SummonCharacter {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new SummonCharacterAction(actionChartActionT);
        }
    },
    Alchemy {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new AlchemyAction(actionChartActionT);
        }
    },
    Commond_Repair {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    },
    ActiveSkill {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    },
    Building_Upgrade {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    },
    SiegeObject_Prepare {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new SiegeObjectPrepareAction(actionChartActionT);
        }
    },
    SiegeObject_Doing {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new SiegeObjectDoingAction(actionChartActionT);
        }
    },
    SiegeObject_Finish {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new SiegeObjectFinishAction(actionChartActionT);
        }
    },
    SiegeObject_BuildingStart {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return new SiegeObjectBuildingStartAction(actionChartActionT);
        }
    },
    Banquete {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    },
    LanternO {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    },
    LanternOff {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    },
    DefencePose {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    },
    Training {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    },
    Unk1 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    },
    Unk2 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    },
    Unk3 {
        @Override
        IAction newAction(final ActionChartActionT actionChartActionT) {
            return null;
        }
    };

    private boolean isRepeatable;

    private EActionType(final boolean isRepeatable) {
        this.isRepeatable = false;
        this.isRepeatable = isRepeatable;
    }

    private EActionType() {
        this.isRepeatable = false;
    }

    public static EActionType valueof(final int id) {
        if (id < 0 || id > values().length - 1) {
            throw new IllegalArgumentException("Found unknown ActionType with id= " + id);
        }
        return values()[id];
    }

    public boolean isRepeatable() {
        return this.isRepeatable;
    }

    public boolean isDefence() {
        return this == EActionType.Defence;
    }

    public boolean isMove() {
        return this == EActionType.Move;
    }

    public boolean isCatch() {
        return this == EActionType.Catch;
    }

    abstract IAction newAction(final ActionChartActionT p0);

    public IAction getNewActionInstance(final ActionChartActionT actionChartActionT) {
        return this.newAction(actionChartActionT);
    }
}
