package com.bdoemu.gameserver.model.creature.player.trade;

import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.network.sendable.SMTradeGameDice;
import com.bdoemu.core.network.sendable.SMTradeGameEnd;
import com.bdoemu.core.network.sendable.SMTradeGameStart;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.enums.ETradeGameResult;
import com.bdoemu.gameserver.model.items.enums.ETradeGameSwitchType;
import com.bdoemu.gameserver.utils.MathUtils;

/**
 * @author Nullbyte
 */
public class Bargain {
    private Player _player;
    private int _minGoal;
    private int _maxGoal;
    private int _tryCount;
    private int _startDice;
    private boolean _isCompleted;
    private boolean _isCorrect;

    public Bargain(Player player, int minGoal, int maxGoal, int tryCount) {
        _player = player;
        _minGoal = minGoal;
        _maxGoal = maxGoal;
        _tryCount = tryCount;
    }

    public boolean isCompleted() {
        return _isCompleted;
    }

    public boolean isCorrect() {
        return _isCorrect;
    }

    public void createDice() {
        _startDice = Rnd.get(-100, -65);
        _player.sendPacket(new SMTradeGameStart(_minGoal, _maxGoal, _tryCount, _startDice));
    }

    public boolean dice(ETradeGameSwitchType type) {
        boolean result = true;
        boolean isWinner = false;
        --_tryCount;

        int diceValue = 0;
        switch(type) {
            case Small:
                diceValue = Rnd.get(-20, 40);
                break;
            case Large:
                diceValue = Rnd.get(-50, 100);
                break;
        }
        _startDice -= diceValue;

        ETradeGameResult gameResult;
        if (diceValue > 0)
            gameResult = ETradeGameResult.Less;
        else
            gameResult = ETradeGameResult.More;

        if (_tryCount < 0) {
            if (MathUtils.isInRangeInclude(-10, 10, _startDice))
            {
                result = true;
                isWinner = true;
                gameResult = ETradeGameResult.Correct;
                _isCompleted = true;
                _isCorrect = true;
            } else {
                result = false;
                isWinner = false;
                gameResult = ETradeGameResult.NoTryCount;
                _isCompleted = true;
                _isCorrect = false;
            }
        }

        _player.sendPacket(new SMTradeGameDice(gameResult, diceValue));
        if (_tryCount < 0 || isWinner)
            _player.sendPacket(new SMTradeGameEnd(isWinner));
        return result;
    }

    public int getMinGoal() {
        return _minGoal;
    }

    public int getMaxGoal() {
        return _maxGoal;
    }

    public int getTryCount() {
        return _tryCount;
    }

    public int getStartDice() {
        return _startDice;
    }
}
