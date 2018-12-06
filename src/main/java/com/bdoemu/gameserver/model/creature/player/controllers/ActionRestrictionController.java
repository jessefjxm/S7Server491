package com.bdoemu.gameserver.model.creature.player.controllers;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.cooltimes.ActionRestrictionEntry;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ActionRestrictionController {
    private Player _player;
    private ConcurrentHashMap<Integer, ActionRestrictionEntry> _actionRestrictions;
    private long _end;
    private int hitType;
    private int player_state;
    
    public ActionRestrictionController(Player player) {
        _actionRestrictions = new ConcurrentHashMap<>();
        _player = player;
        _end = 0L;
        player_state = 0;
    }

    public Player getPlayer() {
        return _player;
    }

    public boolean isActionRestricted(int actionType) {
        ActionRestrictionEntry entry = _actionRestrictions.get(actionType);
        //_player.getAi().HandleRigid(_player, null);
        return !(entry == null || entry.isExpired());
    }

    public boolean checkRestrictCondition(List<Integer> actionTypes) {
        
        for (Integer actionType : actionTypes) {
            ActionRestrictionEntry entry = _actionRestrictions.get(actionType);
            //_player.getAi().HandleRigid(_player, null);

            //while(System.currentTimeMillis() < _end) {
              // do something
              // pause to avoid churning
              //Thread.sleep( xxx );
                //_player.getAi().HandleRigid(_player, null);
            //}
            if (isActionRestricted(actionType))
                //if(entry.isExpired()){
                return false;
                //}
        }

        return true;
    }

    public void restrictActions(Creature attacker, List<Integer> actionTypes, int mainActionType, int expireTime) {
        //System.out.println("1) Restriction " + (expireTime) + " ms.");
        for (Integer actionType : actionTypes)
            
            restrictAction(attacker, actionType, expireTime, mainActionType == actionType ? 500 : 0);
        
    }

    public void restrictAction(Creature attacker, int actionType, int expireTime, int offset) {
        Calendar calendar = Calendar.getInstance();
        //expireTime = (expireTime * 100);
        //calendar.add(Calendar.MILLISECOND, (expireTime + (offset + 100000)));
        calendar.add(Calendar.MILLISECOND, expireTime);
        
        System.out.println("2) Restrict Type=" + actionType + ", Expire=" + expireTime + " ms.");

        ActionRestrictionEntry entry = _actionRestrictions.get(actionType);
        
         _actionRestrictions.put(actionType, new ActionRestrictionEntry(actionType, calendar.getTimeInMillis()));
        
        
        //Stiff > Stun > Bound/Knockdown/Float
        //Stun > Stiff > Bound/Knockdown/Float
        //player_state: 1 = rigid, 2 = stun, 3 = bound, 4 = knockdown
        //
        //
        //if(_end<=){
        //} else {
        hitType = actionType;
        System.out.println("HitType: " + hitType + ", player_state: " + player_state + ", timer: " + _end);
        
        if(actionType == 1){
            //rigid
            if(player_state==0){
                _end = System.currentTimeMillis() + 1300L;
                player_state = 1;
            } else if(player_state==2){
                _end = System.currentTimeMillis() + 1300L;
                player_state = 1;
            }
        } else if(actionType == 2){
            //knockdown
            if(player_state==0){
                _end = System.currentTimeMillis() + 500L;
                player_state = 4;
            } else if(player_state==1){
                _end = System.currentTimeMillis() + 500L;
                player_state = 4;
            } else if(player_state==2){
                _end = System.currentTimeMillis() + 500L;
                player_state = 4;
            }
            
            } else if(actionType == 3){
                //rigid
                    if(player_state==0){
                        _end = System.currentTimeMillis() + 1300L;
                        player_state = 1;
                        } else if(player_state==2){
                            _end = System.currentTimeMillis() + 1300L;
                            player_state = 1;
                        }
            } else if(actionType == 4){
                //stun
                if(player_state==0){
                        _end = System.currentTimeMillis() + 1300L;
                        player_state = 2;
                        } else if(player_state==1){
                            _end = System.currentTimeMillis() + 1300L;
                            player_state = 2;
                        }
            } else if(actionType == 6){
                //rigid
                if(player_state==0){
                    _end = System.currentTimeMillis() + 1300L;
                    player_state = 1;
                        } else if(player_state==2){
                            _end = System.currentTimeMillis() + 1300L;
                            player_state = 1;
                        }
            } else if(actionType == 7){
                //bound
                if(player_state==0){
                    _end = System.currentTimeMillis() + 800L;
                    player_state = 3;
                } else if(player_state==1){
                    _end = System.currentTimeMillis() + 800L;
                    player_state = 3;
                } else if(player_state==2){
                    _end = System.currentTimeMillis() + 800L;
                    player_state = 3;
                }
                }else if(actionType == 12){
                    //knockdown
                    if(player_state==0){
                        _end = System.currentTimeMillis() + 500L;
                        player_state = 4;
                    } else if(player_state==1){
                        _end = System.currentTimeMillis() + 500L;
                        player_state = 4;
                    } else if(player_state==2){
                        _end = System.currentTimeMillis() + 500L;
                        player_state = 4;
                    }
            }else {
                //_end = System.currentTimeMillis() + 5000L;
            }
        //}
        System.out.println("HitType: " + hitType + ", player_state: " + player_state + ", timer: " + _end);
        
        //if (entry == null) {
        //calendar.add(Calendar.MILLISECOND, (expireTime + (offset + 100000)));

            //_actionRestrictions.put(actionType, new ActionRestrictionEntry(actionType, calendar.getTimeInMillis()));
            //_actionRestrictions.put(actionType, new ActionRestrictionEntry(actionType, 5000));
        //} else {
            //entry.setExpirationTime(calendar.getTimeInMillis());
            if(_end > System.currentTimeMillis()){
        new Thread(new Runnable() {

            @Override
            public void run() {
                //for(int i=0; i<100; i++) {
                while(System.currentTimeMillis() < _end) {
                    System.out.println("HitType: " + hitType + ", player_state: " + player_state);
                    if(hitType==1){
                    _player.getAi().HandleRigid(_player, null);
                    } else if(hitType==2) {
                        _player.getAi().HandleKnockDown(_player, null);
                    } else if(hitType==3) {
                        _player.getAi().HandleRigid(_player, null);
                    } else if(hitType==4) {
                        _player.getAi().HandleRigid(_player, null);
                    } else if(hitType==6) {
                        _player.getAi().HandleRigid(_player, null);
                    } else if(hitType==7) {
                        _player.getAi().HandleBound(_player, null);
                    } else if(hitType==12) {
                        _player.getAi().HandleKnockDown(_player, null);
                    } else {
                        //_player.getAi().HandleRigid(_player, null);
                    }
                    if(System.currentTimeMillis() >= _end) {
                        if(player_state>0){
                        player_state = 0;
                        }
                        _end = 0L;
                        //Thread.interrupted();
                    }
                    }
                }
            
        }).start();

    }
        //}
    }
    
}