// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.templates;

import com.bdoemu.gameserver.model.creature.player.enums.EClassType;
import com.bdoemu.gameserver.model.items.enums.EPurchaseSubject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CashItemT {
    private int productNo;
    private int purchaseCountLimit;
    private int resetHour;
    private long priceCash;
    private long pricePearl;
    private long priceMileage;
    private long discountPrice;
    private long salesStartPeriod;
    private long salesEndPeriod;
    private long discountStartPeriod;
    private long discountEndPeriod;
    private boolean isMallDisplay;
    private int[] classPermissions;
    private EPurchaseSubject purchaseSubject;
    private List<CashProductT> products;

    public CashItemT(final ResultSet rs) throws SQLException {
        this.classPermissions = new int[32];
        this.products = new ArrayList<CashProductT>();
        this.productNo = rs.getInt("ProductNo");
        this.priceCash = rs.getLong("PriceCash");
        this.pricePearl = rs.getLong("PricePearl");
        this.priceMileage = rs.getLong("PriceMileage");
        this.discountPrice = rs.getLong("DiscountPrice");
        this.salesStartPeriod = rs.getLong("SalesStartPeriod");
        this.salesEndPeriod = rs.getLong("SalesEndPeriod");
        this.discountStartPeriod = rs.getLong("DiscountStartPeriod");
        this.discountEndPeriod = rs.getLong("DiscountEndPeriod");
        this.purchaseCountLimit = rs.getInt("PurchaseCountLimit");
        this.purchaseSubject = EPurchaseSubject.values()[rs.getInt("PurchaseSubject")];
        this.resetHour = rs.getInt("ResetHour");
        this.isMallDisplay = (rs.getByte("IsMallDisplay") == 1);
        for (int i = 0; i < 10; ++i) {
            final int itemId = rs.getInt("ProductItemKey" + i);
            final long count = rs.getLong("StackCount" + i);
            if (itemId > 0) {
                this.products.add(new CashProductT(itemId, count));
            }
        }
        for (int i = 0; i < this.classPermissions.length; ++i) {
            this.classPermissions[i] = rs.getInt(String.valueOf(i));
        }
    }

    public EPurchaseSubject getPurchaseSubject() {
        return this.purchaseSubject;
    }

    public int getResetHour() {
        return this.resetHour;
    }

    public int getPurchaseCountLimit() {
        return this.purchaseCountLimit;
    }

    public List<CashProductT> getProducts() {
        return this.products;
    }

    public boolean canUse(final EClassType classType) {
        return this.classPermissions[classType.getId()] == 1;
    }

    public long getSalesStartPeriod() {
        return this.salesStartPeriod;
    }

    public long getSalesEndPeriod() {
        return this.salesEndPeriod;
    }

    public long getDiscountStartPeriod() {
        return this.discountStartPeriod;
    }

    public long getDiscountEndPeriod() {
        return this.discountEndPeriod;
    }

    public long getDiscountPrice() {
        return this.discountPrice;
    }

    public long getPricePearl() {
        return this.pricePearl;
    }

    public long getPriceMileage() {
        return this.priceMileage;
    }

    public long getPriceCash() {
        return this.priceCash;
    }

    public int getProductNo() {
        return this.productNo;
    }

    public boolean isMallDisplay() {
        return this.isMallDisplay;
    }
}
