package com.bdoemu.gameserver.model.creature.servant.templates;

import com.bdoemu.gameserver.model.creature.servant.enums.EServantKind;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServantSetTemplate {
    private static final Logger log = LoggerFactory.getLogger(ServantSetTemplate.class);
    private int characterKey;
    private EServantKind servantKind;
    private EServantType servantType;
    private int tier;
    private int grade;
    private boolean isTrade;
    private boolean isMale;
    private boolean isMatingResult;
    private int matingCount;
    private int linkCount;
    private int lifeSpan;
    private int basePrice;
    private int pricePerLevel;
    private int formIndex;
    private int actionIndex;
    private int basicLearnSkillMaxCount;
    private int basicLearnSkillKey;

    public ServantSetTemplate(final ResultSet rs) throws SQLException {
        try {
            this.characterKey = rs.getInt("CharacterKey");
            this.servantKind = EServantKind.valueOf(rs.getInt("RaceType"));
            this.servantType = this.servantKind.getServantType();
            this.tier = rs.getInt("Tier");
            this.grade = rs.getInt("Grade");
            this.isTrade = (rs.getInt("isTrade") == 1);
            this.isMale = (rs.getInt("IsMale") == 1);
            this.matingCount = rs.getInt("MatingCount");
            this.linkCount = rs.getInt("LinkCount");
            this.lifeSpan = rs.getInt("Lifespan");
            this.isMatingResult = (rs.getInt("IsMatingResult") == 1);
            this.basePrice = rs.getInt("basePrice");
            this.pricePerLevel = rs.getInt("PricePerLevel");
            this.formIndex = rs.getInt("FormIndex");
            this.actionIndex = rs.getInt("ActionIndex");
            this.basicLearnSkillMaxCount = rs.getInt("BasicLearnSkillMaxCount");
            this.basicLearnSkillKey = rs.getInt("BasicLearnSkillKey");
        } catch (Exception e) {
            ServantSetTemplate.log.error("Error while loading ServantSetTemplate with id=[{}]", (Object) rs.getInt("CharacterKey"), (Object) e);
        }
    }

    public int getBasicLearnSkillMaxCount() {
        return this.basicLearnSkillMaxCount;
    }

    public int getBasicLearnSkillKey() {
        return this.basicLearnSkillKey;
    }

    public int getCharacterKey() {
        return this.characterKey;
    }

    public EServantKind getServantKind() {
        return this.servantKind;
    }

    public EServantType getServantType() {
        return this.servantType;
    }

    public int getTier() {
        return this.tier;
    }

    public int getGrade() {
        return this.grade;
    }

    public boolean isTrade() {
        return this.isTrade;
    }

    public boolean isMale() {
        return this.isMale;
    }

    public boolean isMatingResult() {
        return this.isMatingResult;
    }

    public int getMatingCount() {
        return this.matingCount;
    }

    public int getLifeSpan() {
        return this.lifeSpan;
    }

    public int getLinkCount() {
        return this.linkCount;
    }

    public int getBasePrice() {
        return this.basePrice;
    }

    public int getPricePerLevel() {
        return this.pricePerLevel;
    }

    public int getActionIndex() {
        return this.actionIndex;
    }

    public int getFormIndex() {
        return this.formIndex;
    }
}
