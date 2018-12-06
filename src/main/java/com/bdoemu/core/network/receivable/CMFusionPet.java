/*
 * Decompiled with CFR 0_121.
 * 
 * Could not load the following classes:
 *  com.bdoemu.commons.collection.BitMask
 *  com.bdoemu.commons.network.Client
 *  com.bdoemu.commons.network.ReceivablePacket
 *  com.bdoemu.commons.network.SendablePacket
 *  com.bdoemu.commons.utils.Rnd
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.collection.BitMask;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMDeactivePetInfo;
import com.bdoemu.gameserver.dataholders.PetChangeLookTable;
import com.bdoemu.gameserver.dataholders.PetData;
import com.bdoemu.gameserver.dataholders.PetEquipSkillAquireData;
import com.bdoemu.gameserver.dataholders.PetFusionData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.ServantController;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantType;
import com.bdoemu.gameserver.model.creature.servant.templates.PetTemplate;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CMFusionPet
        extends ReceivablePacket<GameClient> {
    private static final Logger log = LoggerFactory.getLogger(CMFusionPet.class);
    private String newPetName;
    private long pet1ObjectId;
    private long pet2ObjectId;
    private int petSkillOption;
    private int petFormOption;

    public CMFusionPet(short opcode) {
        super(opcode);
    }

    protected void read() {
        this.newPetName = this.readS(62);
        this.pet1ObjectId = this.readQ();
        this.pet2ObjectId = this.readQ();
        this.petSkillOption = this.readC();
        this.petFormOption = this.readC();
    }

    public void runImpl() {
        Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            ServantController servantController = player.getServantController();
            Servant pet1 = servantController.getServant(this.pet1ObjectId);
            Servant pet2 = servantController.getServant(this.pet2ObjectId);
            if (pet1 == null || pet2 == null) {
                return;
            }
            if (pet1.getServantType() != EServantType.Pet || pet2.getServantType() != EServantType.Pet) {
                return;
            }
            if (pet1.getServantState() != EServantState.Stable || pet2.getServantState() != EServantState.Stable) {
                return;
            }
            if (Math.abs(pet1.getPetTemplate().getTier() - pet2.getPetTemplate().getTier()) > 1) {
                return;
            }
            servantController.delete(pet1);
            servantController.delete(pet2);
            PetTemplate pet1template = pet1.getPetTemplate();
            PetTemplate pet2template = pet2.getPetTemplate();
            int resultTier = PetFusionData.getInstance().getResultTierFor(pet1template.getRace(), pet1template.getKind(), Math.min(pet1template.getTier(), pet2template.getTier()));
            List petTemplates = PetData.getInstance().getTemplates().stream().filter(template -> template.getTier() == resultTier && template.getRace() == pet1template.getRace() && template.getKind() == pet1template.getKind()).collect(Collectors.toList());
            if (!petTemplates.isEmpty()) {
                int actionIndex = 0;
                PetTemplate resultPetTemplate = (PetTemplate) petTemplates.get(Rnd.get((int) petTemplates.size()));
                switch (this.petFormOption) {
                    case 0: {
                        actionIndex = PetChangeLookTable.getInstance().getRandomActionIndex(resultPetTemplate.getPetChangeLookKey());
                        break;
                    }
                    case 1: {
                        actionIndex = pet2.getActionIndex();
                        break;
                    }
                    case 2: {
                        actionIndex = pet1.getActionIndex();
                    }
                }
                BitMask resultEquipSkillBitMask = new BitMask(38);
                switch (this.petSkillOption) {
                    case 0: {
                        for (int index = 0; index < resultTier; ++index) {
                            int skillIndex = PetEquipSkillAquireData.getInstance().getRandomSkillIndex(resultPetTemplate.getEquipSkillAquireKey(), null);
                            if (skillIndex <= 0) continue;
                            resultEquipSkillBitMask.setBit(skillIndex);
                        }
                        break;
                    }
                    case 1: {
                        resultEquipSkillBitMask = pet2.getEquipSkillsBitMask();
                        int skillIndex = PetEquipSkillAquireData.getInstance().getRandomSkillIndex(resultPetTemplate.getEquipSkillAquireKey(), resultEquipSkillBitMask);
                        if (skillIndex <= 0) break;
                        resultEquipSkillBitMask.setBit(skillIndex);
                        break;
                    }
                    case 2: {
                        resultEquipSkillBitMask = pet1.getEquipSkillsBitMask();
                        int skillIndex = PetEquipSkillAquireData.getInstance().getRandomSkillIndex(resultPetTemplate.getEquipSkillAquireKey(), resultEquipSkillBitMask);
                        if (skillIndex <= 0) break;
                        resultEquipSkillBitMask.setBit(skillIndex);
                    }
                }
                if (resultPetTemplate != null) {
                    Servant servant = new Servant(resultPetTemplate, player, this.newPetName);
                    servant.setFormIndex(actionIndex);
                    servantController.add(servant);
                    servant.getEquipSkillsBitMask().setMask(resultEquipSkillBitMask.getMask());
                    servant.applyEquipSkills();
                    player.sendPacket(new SMDeactivePetInfo(Collections.singletonList(servant), EPacketTaskType.FusionPet));
                }
            } else {
                log.warn("Not found PetTemplate for upgrading pets: {} and {}", (Object) pet1.getCreatureId(), (Object) pet2.getCreatureId());
            }
        }
    }
}

