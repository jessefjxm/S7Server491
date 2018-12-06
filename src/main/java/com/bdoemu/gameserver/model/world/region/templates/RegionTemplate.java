package com.bdoemu.gameserver.model.world.region.templates;

import com.bdoemu.commons.utils.DatabaseUtils;
import com.bdoemu.gameserver.dataholders.SkillData;
import com.bdoemu.gameserver.model.misc.enums.EVillageSiegeType;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.bdoemu.gameserver.model.weather.enums.EWeatherFactorType;
import com.bdoemu.gameserver.model.world.enums.ETerritoryKeyType;
import com.bdoemu.gameserver.model.world.region.enums.ERegionType;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.EnumMap;

public class RegionTemplate {
    private int regionId;
    private int regionGroupKey;
    private int waypointKey;
    private int exploreKey;
    private ERegionType regionType;
    private ETerritoryKeyType territoryKey;
    private EVillageSiegeType villageSiegeType;
    private boolean safe;
    private boolean villageWarArea;
    private boolean desert;
    private boolean prison;
    private boolean ocean;
    private boolean sea;
    private boolean notDig;
    private Color color;
    private float returnX;
    private float returnY;
    private float returnZ;
    private SkillT skillNo;
    private SkillT nightSkillNo;
    private SkillT skillNo2;
    private SkillT skillNo3;
    private int skillApplyRate;
    private int nightApplyRate;
    private int skill2ApplyRate;
    private int skill3ApplyRate;
    private EnumMap<EWeatherFactorType, Float> weatherMap;
    private int rainTickKeep;
    private int rainTickReady;

    public RegionTemplate(final ResultSet rs, final ResultSetMetaData rsMeta) throws SQLException {
        this.weatherMap = new EnumMap<>(EWeatherFactorType.class);
        this.regionId = rs.getInt("Index");
        this.regionGroupKey = rs.getInt("RegionGroupKey");
        this.color = new Color(rs.getInt("R"), rs.getInt("G"), rs.getInt("B"));
        this.waypointKey = rs.getInt("WaypointKey");
        this.exploreKey = rs.getInt("ExploreKey");
        this.territoryKey = ETerritoryKeyType.values()[rs.getInt("TerritoryKey")];
        this.regionType = ERegionType.values()[rs.getInt("RegionType")];
        this.safe = (rs.getByte("IsSafe") == 1);
        this.desert = (rs.getByte("IsDesert") == 1);
        this.prison = (rs.getByte("IsPrison") == 1);
        this.ocean = (rs.getByte("IsOcean") == 1);
        this.sea = (rs.getByte("IsSea") == 1);
        this.notDig = (rs.getByte("IsNotDig") == 1);
        this.villageWarArea = (rs.getByte("IsVillageWarArea") == 1);
        if (DatabaseUtils.hasColumn(rsMeta, "VillageSiegeType")) {
            this.villageSiegeType = EVillageSiegeType.values()[rs.getInt("VillageSiegeType")];
        }
        this.returnX = rs.getFloat("ReturnPositionX");
        this.returnY = rs.getFloat("ReturnPositionZ");
        this.returnZ = rs.getFloat("ReturnPositionY");
        this.weatherMap.put(EWeatherFactorType.Celsius, rs.getFloat("Temperature"));
        this.weatherMap.put(EWeatherFactorType.Humidity, rs.getFloat("Humility"));
        this.weatherMap.put(EWeatherFactorType.Oil, rs.getFloat("OilRate"));
        this.weatherMap.put(EWeatherFactorType.Water, rs.getFloat("WaterRate"));
        this.weatherMap.put(EWeatherFactorType.RainAmount, rs.getFloat("RainAmount"));
        this.rainTickKeep = rs.getInt("RainTickKeep");
        this.rainTickReady = rs.getInt("RainTickReady");
        this.skillNo = SkillData.getInstance().getTemplate(rs.getInt("SkillNo"));
        this.nightSkillNo = SkillData.getInstance().getTemplate(rs.getInt("NightSkillNo"));
        this.skillNo2 = SkillData.getInstance().getTemplate(rs.getInt("SkillNo2"));
        this.skillNo3 = SkillData.getInstance().getTemplate(rs.getInt("SkillNo3"));
        this.skillApplyRate = rs.getInt("SkillApplyRate");
        this.nightApplyRate = rs.getInt("NightSkillApplyRate");
        this.skill2ApplyRate = rs.getInt("SkillApplyRate2");
        this.skill3ApplyRate = rs.getInt("SkillApplyRate3");
    }

    public float getWeatherFactorValue(final EWeatherFactorType factorType) {
        return this.weatherMap.get(factorType);
    }

    public float getRainAmount() {
        return this.weatherMap.get(EWeatherFactorType.RainAmount);
    }

    public int getRegionId() {
        return this.regionId;
    }

    public void setRegionId(final int regionId) {
        this.regionId = regionId;
    }

    public int getRegionGroupKey() {
        return this.regionGroupKey;
    }

    public void setRegionGroupKey(final int regionGroupKey) {
        this.regionGroupKey = regionGroupKey;
    }

    public int getWaypointKey() {
        return this.waypointKey;
    }

    public void setWaypointKey(final int waypointKey) {
        this.waypointKey = waypointKey;
    }

    public int getExploreKey() {
        return this.exploreKey;
    }

    public void setExploreKey(final int exploreKey) {
        this.exploreKey = exploreKey;
    }

    public ERegionType getRegionType() {
        return this.regionType;
    }

    public void setRegionType(final ERegionType regionType) {
        this.regionType = regionType;
    }

    public ETerritoryKeyType getTerritoryKey() {
        return this.territoryKey;
    }

    public void setTerritoryKey(final ETerritoryKeyType territoryKey) {
        this.territoryKey = territoryKey;
    }

    public EVillageSiegeType getVillageSiegeType() {
        return this.villageSiegeType;
    }

    public void setVillageSiegeType(final EVillageSiegeType villageSiegeType) {
        this.villageSiegeType = villageSiegeType;
    }

    public boolean isSafe() {
        return this.safe;
    }

    public void setSafe(final boolean safe) {
        this.safe = safe;
    }

    public boolean isVillageWarArea() {
        return this.villageWarArea;
    }

    public void setVillageWarArea(final boolean villageWarArea) {
        this.villageWarArea = villageWarArea;
    }

    public boolean isDesert() {
        return this.desert;
    }

    public void setDesert(final boolean desert) {
        this.desert = desert;
    }

    public boolean isPrison() {
        return this.prison;
    }

    public void setPrison(final boolean prison) {
        this.prison = prison;
    }

    public boolean isOcean() {
        return this.ocean;
    }

    public void setOcean(final boolean ocean) {
        this.ocean = ocean;
    }

    public boolean isSea() {
        return this.sea;
    }

    public void setSea(final boolean sea) {
        this.sea = sea;
    }

    public boolean isNotDig() {
        return this.notDig;
    }

    public void setNotDig(final boolean notDig) {
        this.notDig = notDig;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(final Color color) {
        this.color = color;
    }

    public float getReturnX() {
        return this.returnX;
    }

    public void setReturnX(final float returnX) {
        this.returnX = returnX;
    }

    public float getReturnY() {
        return this.returnY;
    }

    public void setReturnY(final float returnY) {
        this.returnY = returnY;
    }

    public float getReturnZ() {
        return this.returnZ;
    }

    public void setReturnZ(final float returnZ) {
        this.returnZ = returnZ;
    }

    public SkillT getSkillNo() {
        return this.skillNo;
    }

    public void setSkillNo(final SkillT skillNo) {
        this.skillNo = skillNo;
    }

    public SkillT getNightSkillNo() {
        return this.nightSkillNo;
    }

    public void setNightSkillNo(final SkillT nightSkillNo) {
        this.nightSkillNo = nightSkillNo;
    }

    public SkillT getSkillNo2() {
        return this.skillNo2;
    }

    public void setSkillNo2(final SkillT skillNo2) {
        this.skillNo2 = skillNo2;
    }

    public SkillT getSkillNo3() {
        return this.skillNo3;
    }

    public void setSkillNo3(final SkillT skillNo3) {
        this.skillNo3 = skillNo3;
    }

    public int getSkillApplyRate() {
        return this.skillApplyRate;
    }

    public void setSkillApplyRate(final int skillApplyRate) {
        this.skillApplyRate = skillApplyRate;
    }

    public int getNightApplyRate() {
        return this.nightApplyRate;
    }

    public void setNightApplyRate(final int nightApplyRate) {
        this.nightApplyRate = nightApplyRate;
    }

    public int getSkill2ApplyRate() {
        return this.skill2ApplyRate;
    }

    public void setSkill2ApplyRate(final int skill2ApplyRate) {
        this.skill2ApplyRate = skill2ApplyRate;
    }

    public int getSkill3ApplyRate() {
        return this.skill3ApplyRate;
    }

    public void setSkill3ApplyRate(final int skill3ApplyRate) {
        this.skill3ApplyRate = skill3ApplyRate;
    }

    public EnumMap<EWeatherFactorType, Float> getWeatherMap() {
        return this.weatherMap;
    }

    public void setWeatherMap(final EnumMap<EWeatherFactorType, Float> weatherMap) {
        this.weatherMap = weatherMap;
    }

    public int getRainTickKeep() {
        return this.rainTickKeep;
    }

    public void setRainTickKeep(final int rainTickKeep) {
        this.rainTickKeep = rainTickKeep;
    }

    public int getRainTickReady() {
        return this.rainTickReady;
    }

    public void setRainTickReady(final int rainTickReady) {
        this.rainTickReady = rainTickReady;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RegionTemplate)) {
            return false;
        }
        final RegionTemplate other = (RegionTemplate) o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getRegionId() != other.getRegionId()) {
            return false;
        }
        if (this.getRegionGroupKey() != other.getRegionGroupKey()) {
            return false;
        }
        if (this.getWaypointKey() != other.getWaypointKey()) {
            return false;
        }
        if (this.getExploreKey() != other.getExploreKey()) {
            return false;
        }
        final Object this$regionType = this.getRegionType();
        final Object other$regionType = other.getRegionType();
        Label_0117:
        {
            if (this$regionType == null) {
                if (other$regionType == null) {
                    break Label_0117;
                }
            } else if (this$regionType.equals(other$regionType)) {
                break Label_0117;
            }
            return false;
        }
        final Object this$territoryKey = this.getTerritoryKey();
        final Object other$territoryKey = other.getTerritoryKey();
        Label_0154:
        {
            if (this$territoryKey == null) {
                if (other$territoryKey == null) {
                    break Label_0154;
                }
            } else if (this$territoryKey.equals(other$territoryKey)) {
                break Label_0154;
            }
            return false;
        }
        final Object this$villageSiegeType = this.getVillageSiegeType();
        final Object other$villageSiegeType = other.getVillageSiegeType();
        Label_0191:
        {
            if (this$villageSiegeType == null) {
                if (other$villageSiegeType == null) {
                    break Label_0191;
                }
            } else if (this$villageSiegeType.equals(other$villageSiegeType)) {
                break Label_0191;
            }
            return false;
        }
        if (this.isSafe() != other.isSafe()) {
            return false;
        }
        if (this.isVillageWarArea() != other.isVillageWarArea()) {
            return false;
        }
        if (this.isDesert() != other.isDesert()) {
            return false;
        }
        if (this.isPrison() != other.isPrison()) {
            return false;
        }
        if (this.isOcean() != other.isOcean()) {
            return false;
        }
        if (this.isSea() != other.isSea()) {
            return false;
        }
        if (this.isNotDig() != other.isNotDig()) {
            return false;
        }
        final Object this$color = this.getColor();
        final Object other$color = other.getColor();
        Label_0319:
        {
            if (this$color == null) {
                if (other$color == null) {
                    break Label_0319;
                }
            } else if (this$color.equals(other$color)) {
                break Label_0319;
            }
            return false;
        }
        if (Float.compare(this.getReturnX(), other.getReturnX()) != 0) {
            return false;
        }
        if (Float.compare(this.getReturnY(), other.getReturnY()) != 0) {
            return false;
        }
        if (Float.compare(this.getReturnZ(), other.getReturnZ()) != 0) {
            return false;
        }
        final Object this$skillNo = this.getSkillNo();
        final Object other$skillNo = other.getSkillNo();
        Label_0404:
        {
            if (this$skillNo == null) {
                if (other$skillNo == null) {
                    break Label_0404;
                }
            } else if (this$skillNo.equals(other$skillNo)) {
                break Label_0404;
            }
            return false;
        }
        final Object this$nightSkillNo = this.getNightSkillNo();
        final Object other$nightSkillNo = other.getNightSkillNo();
        Label_0441:
        {
            if (this$nightSkillNo == null) {
                if (other$nightSkillNo == null) {
                    break Label_0441;
                }
            } else if (this$nightSkillNo.equals(other$nightSkillNo)) {
                break Label_0441;
            }
            return false;
        }
        final Object this$skillNo2 = this.getSkillNo2();
        final Object other$skillNo2 = other.getSkillNo2();
        Label_0478:
        {
            if (this$skillNo2 == null) {
                if (other$skillNo2 == null) {
                    break Label_0478;
                }
            } else if (this$skillNo2.equals(other$skillNo2)) {
                break Label_0478;
            }
            return false;
        }
        final Object this$skillNo3 = this.getSkillNo3();
        final Object other$skillNo3 = other.getSkillNo3();
        Label_0515:
        {
            if (this$skillNo3 == null) {
                if (other$skillNo3 == null) {
                    break Label_0515;
                }
            } else if (this$skillNo3.equals(other$skillNo3)) {
                break Label_0515;
            }
            return false;
        }
        if (this.getSkillApplyRate() != other.getSkillApplyRate()) {
            return false;
        }
        if (this.getNightApplyRate() != other.getNightApplyRate()) {
            return false;
        }
        if (this.getSkill2ApplyRate() != other.getSkill2ApplyRate()) {
            return false;
        }
        if (this.getSkill3ApplyRate() != other.getSkill3ApplyRate()) {
            return false;
        }
        final Object this$weatherMap = this.getWeatherMap();
        final Object other$weatherMap = other.getWeatherMap();
        if (this$weatherMap == null) {
            if (other$weatherMap == null) {
                return this.getRainTickKeep() == other.getRainTickKeep() && this.getRainTickReady() == other.getRainTickReady();
            }
        } else if (this$weatherMap.equals(other$weatherMap)) {
            return this.getRainTickKeep() == other.getRainTickKeep() && this.getRainTickReady() == other.getRainTickReady();
        }
        return false;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof RegionTemplate;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getRegionId();
        result = result * 59 + this.getRegionGroupKey();
        result = result * 59 + this.getWaypointKey();
        result = result * 59 + this.getExploreKey();
        final Object $regionType = this.getRegionType();
        result = result * 59 + (($regionType == null) ? 43 : $regionType.hashCode());
        final Object $territoryKey = this.getTerritoryKey();
        result = result * 59 + (($territoryKey == null) ? 43 : $territoryKey.hashCode());
        final Object $villageSiegeType = this.getVillageSiegeType();
        result = result * 59 + (($villageSiegeType == null) ? 43 : $villageSiegeType.hashCode());
        result = result * 59 + (this.isSafe() ? 79 : 97);
        result = result * 59 + (this.isVillageWarArea() ? 79 : 97);
        result = result * 59 + (this.isDesert() ? 79 : 97);
        result = result * 59 + (this.isPrison() ? 79 : 97);
        result = result * 59 + (this.isOcean() ? 79 : 97);
        result = result * 59 + (this.isSea() ? 79 : 97);
        result = result * 59 + (this.isNotDig() ? 79 : 97);
        final Object $color = this.getColor();
        result = result * 59 + (($color == null) ? 43 : $color.hashCode());
        result = result * 59 + Float.floatToIntBits(this.getReturnX());
        result = result * 59 + Float.floatToIntBits(this.getReturnY());
        result = result * 59 + Float.floatToIntBits(this.getReturnZ());
        final Object $skillNo = this.getSkillNo();
        result = result * 59 + (($skillNo == null) ? 43 : $skillNo.hashCode());
        final Object $nightSkillNo = this.getNightSkillNo();
        result = result * 59 + (($nightSkillNo == null) ? 43 : $nightSkillNo.hashCode());
        final Object $skillNo2 = this.getSkillNo2();
        result = result * 59 + (($skillNo2 == null) ? 43 : $skillNo2.hashCode());
        final Object $skillNo3 = this.getSkillNo3();
        result = result * 59 + (($skillNo3 == null) ? 43 : $skillNo3.hashCode());
        result = result * 59 + this.getSkillApplyRate();
        result = result * 59 + this.getNightApplyRate();
        result = result * 59 + this.getSkill2ApplyRate();
        result = result * 59 + this.getSkill3ApplyRate();
        final Object $weatherMap = this.getWeatherMap();
        result = result * 59 + (($weatherMap == null) ? 43 : $weatherMap.hashCode());
        result = result * 59 + this.getRainTickKeep();
        result = result * 59 + this.getRainTickReady();
        return result;
    }

    @Override
    public String toString() {
        return "RegionTemplate(regionId=" + this.getRegionId() + ", regionGroupKey=" + this.getRegionGroupKey() + ", waypointKey=" + this.getWaypointKey() + ", exploreKey=" + this.getExploreKey() + ", regionType=" + this.getRegionType() + ", territoryKey=" + this.getTerritoryKey() + ", villageSiegeType=" + this.getVillageSiegeType() + ", safe=" + this.isSafe() + ", villageWarArea=" + this.isVillageWarArea() + ", desert=" + this.isDesert() + ", prison=" + this.isPrison() + ", ocean=" + this.isOcean() + ", sea=" + this.isSea() + ", notDig=" + this.isNotDig() + ", color=" + this.getColor() + ", returnX=" + this.getReturnX() + ", returnY=" + this.getReturnY() + ", returnZ=" + this.getReturnZ() + ", skillNo=" + this.getSkillNo() + ", nightSkillNo=" + this.getNightSkillNo() + ", skillNo2=" + this.getSkillNo2() + ", skillNo3=" + this.getSkillNo3() + ", skillApplyRate=" + this.getSkillApplyRate() + ", nightApplyRate=" + this.getNightApplyRate() + ", skill2ApplyRate=" + this.getSkill2ApplyRate() + ", skill3ApplyRate=" + this.getSkill3ApplyRate() + ", weatherMap=" + this.getWeatherMap() + ", rainTickKeep=" + this.getRainTickKeep() + ", rainTickReady=" + this.getRainTickReady() + ")";
    }
}
