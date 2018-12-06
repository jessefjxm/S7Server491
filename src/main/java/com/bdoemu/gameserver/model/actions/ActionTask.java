package com.bdoemu.gameserver.model.actions;

import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.commons.utils.HashUtil;
import com.bdoemu.gameserver.model.actions.templates.ActionBranchT;
import com.bdoemu.gameserver.model.actions.templates.ActionChartActionT;
import com.bdoemu.gameserver.model.actions.templates.frameevents.FrameEvent;
import com.bdoemu.gameserver.model.ai.deprecated.EAIBehavior;
import com.bdoemu.gameserver.model.creature.Creature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ActionTask {
    private static final Logger log = LoggerFactory.getLogger(ActionTask.class);
    private final IAction action;
    private final Creature target;
    private final Creature owner;
    private final Consumer<Method> onActionEnd;
    private Future<?> task;
    private ActionChartActionT actionChartActionT;
    private int currentFrameIndex = 0;
    private boolean isCanceled = false;
    private EAIBehavior behavior;

    public ActionTask(IAction action, Creature target, EAIBehavior behavior, Consumer<Method> onActionEnd) {
        this.onActionEnd = onActionEnd;
        this.target = target;
        this.owner = action.getOwner();
        this.action = action;
        this.actionChartActionT = action.getActionChartActionT();
        this.behavior = behavior;
        int actionIndex = this.owner.getActionIndex();
        if (action.getActionChartActionT().getIndexedActionCount() > 0 && actionIndex > 0) {
            long indexedActionHash = HashUtil.generateHashA((String) (action.getActionChartActionT().getActionName() + "_Idx" + actionIndex));
            ActionChartActionT indexedActionChartActionT = this.owner.getActionStorage().getCurrentPackageMap().getActionChartTemplate(indexedActionHash);
            if (indexedActionChartActionT != null) {
                this.actionChartActionT = indexedActionChartActionT;
            }
        }
        this.owner.getActionStorage().setActionTask(this);
    }

    public void start() {
        if (this.behavior.isNoDelayBehavior()) {
            this.onActionEnd.accept(null);
        }
        if (this.actionChartActionT.getFrameEvents().isEmpty()) {
            this.doAction();
        } else {
            this.doNextFrame();
        }
    }

    private void doFrame(FrameEvent frameEvent) {
        frameEvent.doFrame(this.action, this.target);
        this.doNextFrame();
    }

    private void doNextFrame() {
        if (!this.isCanceled) {
            if (this.currentFrameIndex < this.actionChartActionT.getFrameEvents().size()) {
                FrameEvent frameEvent = this.actionChartActionT.getFrameEvents().get(this.currentFrameIndex++);
                this.task = ThreadPool.getInstance().scheduleAi(() -> {
                            this.doFrame(frameEvent);
                        }
                        , (long) frameEvent.getFrameTime(), TimeUnit.MILLISECONDS);
            } else {
                this.doAction();
            }
        }
    }

    private void doAction() {
        this.task = ThreadPool.getInstance().scheduleAi(() -> {
                    if (!this.isCanceled) {
                        if (this.action.getActionChartActionT().isDoBranch()) {
                            ActionBranchT endBranchT = this.actionChartActionT.getBranch("End");
                            if (endBranchT != null && endBranchT.getActionHash() != this.action.getActionHash()) {
                                IAction branchAction = this.action.getOwner().getActionStorage().getNewAction(endBranchT.getActionHash());
                                if (branchAction != null) {
                                    branchAction.setOwner(this.owner);
                                    branchAction.setTargetGameObjId(this.target != null ? this.target.getGameObjectId() : -1024);
                                    ActionTask actionTask = new ActionTask(branchAction, this.target, this.behavior, this.onActionEnd);
                                    actionTask.start();
                                } else if (!this.behavior.isNoDelayBehavior()) {
                                    this.onActionEnd.accept(null);
                                }
                            } else if (!this.behavior.isNoDelayBehavior()) {
                                this.onActionEnd.accept(null);
                            }
                        } else if (!this.behavior.isNoDelayBehavior()) {
                            this.onActionEnd.accept(null);
                        }
                    }
                }
                , (long) this.actionChartActionT.getActionTime(), TimeUnit.MILLISECONDS);
    }

    public void cancelActionTask() {
        if (this.task != null && !this.task.isDone()) {
            this.task.cancel(true);
        }
        this.isCanceled = true;
    }
}

