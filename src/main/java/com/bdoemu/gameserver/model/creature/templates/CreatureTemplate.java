package com.bdoemu.gameserver.model.creature.templates;

import com.bdoemu.gameserver.model.ai.deprecated.EAIFindTargetType;
import com.bdoemu.gameserver.model.conditions.ConditionService;
import com.bdoemu.gameserver.model.conditions.accept.IAcceptConditionHandler;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.enums.ECharacterGradeType;
import com.bdoemu.gameserver.model.creature.enums.ETribeType;
import com.bdoemu.gameserver.model.creature.enums.EVehicleType;
import com.bdoemu.gameserver.model.stats.templates.CreatureStatsTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class CreatureTemplate {
    private int creatureId;
    private int characterGroup;
    private String displayName;
    private int level;
    private int exp;
    private int skillPointExp;
    private int stunGauge;
    private int variedTendencyOnDead;
    private int attackRange;
    private int findTargetDistance;
    private EAIFindTargetType findTargetType;
    private int vanishTime;
    private int lootAuthorityTime;
    private int spawnDelayTime;
    private int spawnVariableTime;
    private Integer dropId;
    private Integer collectDropId;
    private Integer stealDropId;
    private ECharKind charKind;
    private ECharacterGradeType charGrade;
    private ETribeType tribeType;
    private CreatureStatsTemplate gameStatsTemplate;
    private CreatureFunctionT creatureFunctionT;
    private Integer activeCondition;
    private String aiScriptClassName;
    private String aiScriptActionScriptPath;
    private String aiScriptActionScriptName;
    private ObjectTemplate objectTemplate;
    private int bodySize;
    private int bodyHeight;
    private EVehicleType vehicleType;
    private int vehicleSeatCount;
    private boolean vehicleDriverRidable;
    private boolean isAggro;
    private boolean isPushable;
    private boolean isFixed;
    private boolean isTaming;
    private boolean isAttackedPvPModeOnly;
    private int talkable;
    private int inventoryMax;
    private int resetDistance;
    private Set<Integer> contentsGroupKey;
    private IAcceptConditionHandler[][] interactionConditions;

    public CreatureTemplate(final ResultSet rs, final CreatureFunctionT creatureFunctionT, final ObjectTemplate objectTemplate) throws SQLException {
        this.findTargetType = EAIFindTargetType.Enemy;
        this.contentsGroupKey = new HashSet<>();
        this.creatureFunctionT = creatureFunctionT;
        this.objectTemplate = objectTemplate;
        this.gameStatsTemplate = new CreatureStatsTemplate(rs);
        this.creatureId = rs.getInt("Index");
        this.displayName = rs.getString("DisplayName");
        this.charKind = ECharKind.valueOf(rs.getByte("CharKind"));
        this.characterGroup = rs.getInt("CharacterGroup");
        this.charGrade = ECharacterGradeType.valueOf(rs.getByte("GradeType"));
        this.tribeType = ETribeType.valueOf(rs.getByte("TribeType"));
        this.level = rs.getInt("Level");
        this.exp = rs.getInt("Exp");
        this.skillPointExp = rs.getInt("SkillPointExp");
        this.vanishTime = rs.getInt("VanishTime");
        this.lootAuthorityTime = rs.getInt("LootAuthorityTime");
        this.stunGauge = rs.getInt("StunGauge");
        if (rs.getString("DropID") != null) {
            this.dropId = rs.getInt("DropID");
        }
        if (rs.getString("CollectDropID") != null) {
            this.collectDropId = rs.getInt("CollectDropID");
        }
        if (rs.getString("StealDropID") != null) {
            this.stealDropId = rs.getInt("StealDropID");
        }
        this.attackRange = rs.getInt("AttackRange");
        this.spawnDelayTime = rs.getInt("SpawnDelayTime");
        this.spawnVariableTime = rs.getInt("SpawnVariableTime");
        this.findTargetDistance = rs.getInt("FindTargetDistance");
        final String findTargetTypeValue = rs.getString("FindTargetType").trim();
        if (!findTargetTypeValue.equals("0")) {
            this.findTargetType = EAIFindTargetType.valueOf(findTargetTypeValue);
        }
        final String activeConditionValue = rs.getString("ActiveCondition");
        if (activeConditionValue != null) {
            this.activeCondition = Integer.parseInt(activeConditionValue.toLowerCase().replace("getknowledge(", "").replace(");", ""));
        }
        this.variedTendencyOnDead = rs.getInt("variedTendencyOnDead");
        this.aiScriptClassName = rs.getString("AiScriptClassName").trim().toLowerCase();
        this.aiScriptActionScriptPath = rs.getString("ActionScript FilePrefix").toLowerCase() + "_" + rs.getString("ActionScript File0").toLowerCase();
        final String[] aiScriptActionScriptPathData = this.aiScriptActionScriptPath.split("/");
        this.aiScriptActionScriptName = aiScriptActionScriptPathData[aiScriptActionScriptPathData.length - 1];
        this.bodySize = rs.getInt("BodySize");
        this.bodyHeight = rs.getInt("BodyHeight");
        this.resetDistance = rs.getInt("ResetDistance");
        this.vehicleType = EVehicleType.valueOf(rs.getInt("VehicleType"));
        this.vehicleSeatCount = rs.getInt("VehicleSeatCount");
        this.vehicleDriverRidable = (rs.getInt("VehicleDriverRidable") == 1);
        this.isFixed = (rs.getInt("IsFixed") == 1);
        this.isAggro = (rs.getInt("UseAggroSystem") == 1);
        this.isPushable = (rs.getInt("IsPushable") == 1);
        this.talkable = rs.getInt("TalkAble");
        this.isTaming = (rs.getInt("isTaming") == 1);
        this.isAttackedPvPModeOnly = (rs.getInt("IsAttackedPvPModeOnly") == 1);
        this.inventoryMax = rs.getInt("InventoryMax");
        final String[] contentGroupKeyData = rs.getString("ContentsGroupKey").replace(",", " ").split(" ");
        for (final String contentGroupData : contentGroupKeyData) {
            if (!contentGroupData.isEmpty()) {
                this.contentsGroupKey.add(Integer.parseInt(contentGroupData.trim()));
            }
        }
        this.interactionConditions = ConditionService.getAcceptConditions(rs.getString("InteractionCondition"));
    }

    public int getResetDistance() {
        return this.resetDistance;
    }

    public boolean isTaming() {
        return this.isTaming;
    }

    public boolean isFixed() {
        return this.isFixed;
    }

    public int getAttackRange() {
        return this.attackRange;
    }

    public int getFindTargetDistance() {
        return this.findTargetDistance;
    }

    public EAIFindTargetType getFindTargetType() {
        return this.findTargetType;
    }

    public Integer getStealDropId() {
        return this.stealDropId;
    }

    public int getBodySize() {
        return this.bodySize;
    }

    public int getBodyHeight() {
        return this.bodyHeight;
    }

    public ObjectTemplate getObjectTemplate() {
        return this.objectTemplate;
    }

    public int getVanishTime() {
        return this.vanishTime;
    }

    public int getLootAuthorityTime() {
        return this.lootAuthorityTime;
    }

    public String getAiScriptClassName() {
        return this.aiScriptClassName;
    }

    public String getAiScriptActionScriptPath() {
        return this.aiScriptActionScriptPath;
    }

    public String getAiScriptActionScriptName() {
        return this.aiScriptActionScriptName;
    }

    public Integer getActiveCondition() {
        return this.activeCondition;
    }

    public Integer getDropId() {
        return this.dropId;
    }

    public Integer getCollectDropId() {
        return this.collectDropId;
    }

    public int getStunGauge() {
        return this.stunGauge;
    }

    public int getCharacterGroup() {
        return this.characterGroup;
    }

    public CreatureFunctionT getCreatureFunctionT() {
        return this.creatureFunctionT;
    }

    public CreatureStatsTemplate getGameStatsTemplate() {
        return this.gameStatsTemplate;
    }

    public int getCreatureId() {
        return this.creatureId;
    }

    public int getLevel() {
        return this.level;
    }

    public int getVariedTendencyOnDead() {
        return this.variedTendencyOnDead;
    }

    public int getExp() {
        return this.exp;
    }

    public int getSkillPointExp() {
        return this.skillPointExp;
    }

    public ECharKind getCharKind() {
        return this.charKind;
    }

    public ECharacterGradeType getCharGrade() {
        return this.charGrade;
    }

    public ETribeType getTribeType() {
        return this.tribeType;
    }

    public int getSpawnDelayTime() {
        return this.spawnDelayTime;
    }

    public int getSpawnVariableTime() {
        return this.spawnVariableTime;
    }

    public EVehicleType getVehicleType() {
        return this.vehicleType;
    }

    public int getVehicleSeatCount() {
        return this.vehicleSeatCount;
    }

    public boolean isVehicleDriverRidable() {
        return this.vehicleDriverRidable;
    }

    public boolean isAggro() {
        return this.isAggro;
    }

    public boolean isPushable() {
        return this.isPushable;
    }

    public int getTalkable() {
        return this.talkable;
    }

    public Set<Integer> getContentsGroupKey() {
        return this.contentsGroupKey;
    }

    public IAcceptConditionHandler[][] getInteractionConditions() {
        return this.interactionConditions;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public int getInventoryMax() {
        return this.inventoryMax;
    }

    public boolean isAttackedPvPModeOnly() {
        return this.isAttackedPvPModeOnly;
    }
}
