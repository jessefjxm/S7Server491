// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.weather;

import org.apache.commons.lang3.Range;

public class CultivationWeatherT {
    private Range<Integer> range;
    private Integer productSpeedRate;

    public CultivationWeatherT(final Range<Integer> range, final Integer productSpeedRate) {
        this.range = range;
        this.productSpeedRate = productSpeedRate;
    }

    public Range<Integer> getRange() {
        return this.range;
    }

    public void setRange(final Range<Integer> range) {
        this.range = range;
    }

    public Integer getProductSpeedRate() {
        return this.productSpeedRate;
    }

    public void setProductSpeedRate(final Integer productSpeedRate) {
        this.productSpeedRate = productSpeedRate;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CultivationWeatherT)) {
            return false;
        }
        final CultivationWeatherT other = (CultivationWeatherT) o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$range = this.getRange();
        final Object other$range = other.getRange();
        Label_0065:
        {
            if (this$range == null) {
                if (other$range == null) {
                    break Label_0065;
                }
            } else if (this$range.equals(other$range)) {
                break Label_0065;
            }
            return false;
        }
        final Object this$productSpeedRate = this.getProductSpeedRate();
        final Object other$productSpeedRate = other.getProductSpeedRate();
        if (this$productSpeedRate == null) {
            if (other$productSpeedRate == null) {
                return true;
            }
        } else if (this$productSpeedRate.equals(other$productSpeedRate)) {
            return true;
        }
        return false;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CultivationWeatherT;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $range = this.getRange();
        result = result * 59 + (($range == null) ? 43 : $range.hashCode());
        final Object $productSpeedRate = this.getProductSpeedRate();
        result = result * 59 + (($productSpeedRate == null) ? 43 : $productSpeedRate.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "CultivationWeatherT(range=" + this.getRange() + ", productSpeedRate=" + this.getProductSpeedRate() + ")";
    }
}
