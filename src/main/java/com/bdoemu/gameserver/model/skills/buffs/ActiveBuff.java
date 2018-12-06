package com.bdoemu.gameserver.model.skills.buffs;

import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.core.network.sendable.SMApplyActiveBuff;
import com.bdoemu.core.network.sendable.SMUnapplyActiveBuff;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.skills.buffs.enums.ApplyBuffType;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.bdoemu.gameserver.model.stats.elements.BuffElement;
import com.bdoemu.gameserver.service.GameTimeService;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ActiveBuff implements Runnable {
    private final BuffTemplate template;
    private final SkillT skillT;
    private final Creature owner;
    private final Creature target;
    private int gameObjectId;
    private BuffElement element;
    private long startTime;
    private long validityTime;
    private ScheduledFuture<?> buffTask;
    private boolean isApplied;

    public ActiveBuff(final SkillT skillT, final BuffTemplate template, final Creature owner, final Creature target, final BuffElement element) {
        this.skillT = skillT;
        this.template = template;
        this.owner = owner;
        this.target = target;
        this.validityTime = template.getValidityTime();
        this.element = element;
    }

    public ActiveBuff(final SkillT skillT, final BuffTemplate template, final Creature owner, final Creature target) {
        this(skillT, template, owner, target, null);
    }

    public boolean startEffect() {
        return this.startEffect(ApplyBuffType.Normal);
    }

    public boolean startEffect(final ApplyBuffType applyBuffType) {
        this.startTime = GameTimeService.getServerTimeInMillis();
        this.isApplied = true;
        if (!applyBuffType.isEnterWorld()) {
            this.isApplied = this.target.getBuffList().addActiveBuff(this);
        }
        if (this.isApplied) {
            if (!applyBuffType.isReApply()) {
                this.template.getModuleType().getEffect().applyEffect(this);
            }
            if (this.getValidityTime() > 0L) {
                this.buffTask = ThreadPool.getInstance().scheduleEffect(this, (this.getRepeatTime() > 0L && this.getRepeatTime() < this.getValidityTime()) ? this.getRepeatTime() : this.getValidityTime(), TimeUnit.MILLISECONDS);
            }
            if (this.isActiveBuff()) {
                this.target.sendBroadcastItSelfPacket(new SMApplyActiveBuff(this));
            }
        }
        return this.isApplied;
    }

    public boolean isApplied() {
        return this.isApplied;
    }

    public SkillT getSkillT() {
        return this.skillT;
    }

    public void applyEffect() {
        this.template.getModuleType().getEffect().applyEffect(this);
    }

    public boolean refreshBuff() {
        if (this.target.getBuffList().removeActiveBuff(this)) {
            this.validityTime = this.template.getValidityTime();
            this.stopTask();
            return this.startEffect(ApplyBuffType.ReApply);
        }
        return false;
    }

    public void endEffect() {
        this.endEffect(false);
    }

    public void unapplyEffect() {
        this.endEffect(true);
    }

    private void endEffect(final boolean unapplyEffect) {
        if (this.target.getBuffList().removeActiveBuff(this)) {
            this.stopTask();
            if (this.isActiveBuff() && unapplyEffect) {
                this.target.sendBroadcastItSelfPacket(new SMUnapplyActiveBuff(this));
            }
            this.template.getModuleType().getEffect().endEffect(this);
            this.releaseObjectId();
        }
    }

    public void stopTask() {
        if (this.buffTask != null && !this.buffTask.isDone()) {
            this.buffTask.cancel(true);
        }
    }

    public long getStartTime() {
        return this.startTime;
    }

    public BuffTemplate getTemplate() {
        return this.template;
    }

    public long getRemainingTime() {
        final long result = this.getEndTime() - GameTimeService.getServerTimeInMillis();
        return (result < 0L) ? 0L : result;
    }

    public boolean isDisplay() {
        return this.template.isDisplay();
    }

    public boolean isActiveBuff() {
        return this.template.getValidityTime() > 0L;
    }

    public long getEndTime() {
        return this.startTime + this.getValidityTime();
    }

    public long getValidityTime() {
        return this.validityTime;
    }

    public void setValidityTime(final long validityTime) {
        this.validityTime = validityTime;
    }

    public long getRepeatTime() {
        return this.template.getRepeatTime();
    }

    public int getGroup() {
        return this.template.getGroup();
    }

    public int getLevel() {
        return this.template.getLevel();
    }

    public int getGameObjectId() {
        return this.gameObjectId;
    }

    public void setGameObjectId(final int gameObjectId) {
        this.gameObjectId = gameObjectId;
    }

    public void initGameObjectId() {
        if (this.gameObjectId == 0) {
            this.gameObjectId = (int) GameServerIDFactory.getInstance().nextId(GSIDStorageType.Buff);
        }
    }

    public int getBuffId() {
        return this.template.getBuffId();
    }

    public BuffElement getElement() {
        return this.element;
    }

    public void setElement(final BuffElement element) {
        this.element = element;
    }

    public Creature getOwner() {
        return this.owner;
    }

    public Creature getTarget() {
        return this.target;
    }

    public int getTargetGameObjId() {
        return this.target.getGameObjectId();
    }

    public void releaseObjectId() {
        if (this.gameObjectId > 0) {
            GameServerIDFactory.getInstance().releaseId(GSIDStorageType.Buff, (long) this.getGameObjectId());
        }
    }

    @Override
    public void run() {
        if (this.getRepeatTime() > 0L && this.getRemainingTime() > 0L) {
            this.applyEffect();
            this.buffTask = ThreadPool.getInstance().scheduleEffect(this, this.getRepeatTime(), TimeUnit.MILLISECONDS);
        } else {
            this.endEffect();
        }
    }
}
