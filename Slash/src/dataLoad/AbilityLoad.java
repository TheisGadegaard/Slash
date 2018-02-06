package dataLoad;
import java.util.ArrayList;

import controllers.Ability;
import controllers.Effect;
import controllers.Roll;
import enums.*;

public class AbilityLoad {

	
	public Ability loadAbility(String abilityName){
		switch(abilityName) {
		case "Slash":
		ArrayList<Roll> slashRolls = new ArrayList<Roll>();
			Roll slashLow = new Roll();
				slashLow.add(new Effect(effectType.DAMAGE, null, null, 0, -10, 1, 0, 1));
			Roll slashSta = new Roll();
				slashSta.add(new Effect(effectType.HEAL_ENERGY, null, null, 0, 2, 0, 0, 0));
				slashSta.add(new Effect(effectType.DAMAGE, null, null, 0, 0, 1, 0, 1));
			Roll slashHigh = new Roll();	 
				slashHigh.add(new Effect(effectType.HEAL_ENERGY, null, null, 0, 4, 0, 0, 0));
				slashHigh.add(new Effect(effectType.DAMAGE, null, null, 0, 5, 1, 0, 1));
		slashRolls.add(slashLow); slashRolls.add(slashSta); slashRolls.add(slashHigh);
		return new Ability("Slash", abilityType.MELEE, new int[]{0, 0, 0}, 20, 20, slashRolls);
		case "Warcry":
			ArrayList<Roll> warcryRolls = new ArrayList<Roll>();
			Roll warcryLow = new Roll();
				warcryLow.add(new Effect(effectType.HEAL_LIFE, null, null, 0, 5, 0, 0, 0));
				warcryLow.add(new Effect(effectType.BUFF_SELF, buffType.LOWROLL, decayType.ATTACK, 4, -10, 0, 0, 0));
				warcryLow.add(new Effect(effectType.BUFF_SELF, buffType.HIGHROLL, decayType.ATTACK, 4, 10, 0, 0, 0));
			Roll warcrySta = new Roll();
				warcrySta.add(new Effect(effectType.HEAL_LIFE, null, null, 0, 15, 0, 0, 0));
				warcrySta.add(new Effect(effectType.BUFF_SELF, buffType.LOWROLL, decayType.ATTACK, 4, -16, 0, 0, 0));
				warcrySta.add(new Effect(effectType.BUFF_SELF, buffType.HIGHROLL, decayType.ATTACK, 4, 16, 0, 0, 0));
			Roll warcryHigh = new Roll();
				warcryHigh.add(new Effect(effectType.HEAL_LIFE, null, null, 0, 10, 1, 0, 0));
				warcryHigh.add(new Effect(effectType.BUFF_SELF, buffType.LOWROLL, decayType.ATTACK, 4, -24, 0, 0, 0));
				warcryHigh.add(new Effect(effectType.BUFF_SELF, buffType.HIGHROLL, decayType.ATTACK, 4, 24, 0, 0, 0));
			warcryRolls.add(warcryLow); warcryRolls.add(warcrySta); warcryRolls.add(warcryHigh);
			return new Ability("Warcry", abilityType.BUFF, new int[]{0, 5, 0}, 5, 10, warcryRolls);
		case "Axe Throw":
		ArrayList<Roll> axeThrowRolls = new ArrayList<Roll>();
			Roll axeThrowLow = new Roll();
				axeThrowLow.add(new Effect(effectType.DAMAGE, null, null, 0, -10, 1, 0, 1));
			Roll axeThrowSta = new Roll();
				axeThrowSta.add(new Effect(effectType.DAMAGE, null, null, 0, 5, 1, 0, 1));
			Roll axeThrowHigh = new Roll();
				axeThrowHigh.add(new Effect(effectType.DAMAGE, null, null, 0, 0, 2, 0, 1));
			axeThrowRolls.add(axeThrowLow); axeThrowRolls.add(axeThrowSta); axeThrowRolls.add(axeThrowHigh);
			return new Ability("Axe Throw", abilityType.RANGED, new int[] {0, 0, 5}, 20, 30, axeThrowRolls);
		case "Shieldblock":
			ArrayList<Roll> shieldblockRolls = new ArrayList<Roll>();
			Roll shieldblockLow = new Roll();
				shieldblockLow.add(new Effect(effectType.BUFF_SELF, buffType.MELEE_BLOCK, decayType.DEFENCE, 3, 40, 0, 0, 0));
				shieldblockLow.add(new Effect(effectType.BUFF_SELF, buffType.RANGED_BLOCK, decayType.DEFENCE, 3, 30, 0, 0, 0));
				shieldblockLow.add(new Effect(effectType.BUFF_SELF, buffType.MAGIC_BLOCK, decayType.DEFENCE, 3, 30, 0, 0, 0));
			Roll shieldblockSta = new Roll();
				shieldblockSta.add(new Effect(effectType.BUFF_SELF, buffType.MELEE_BLOCK, decayType.DEFENCE, 3, 60, 0, 0, 0));
				shieldblockSta.add(new Effect(effectType.BUFF_SELF, buffType.RANGED_BLOCK, decayType.DEFENCE, 3, 50, 0, 0, 0));
				shieldblockSta.add(new Effect(effectType.BUFF_SELF, buffType.MAGIC_BLOCK, decayType.DEFENCE, 3, 40, 0, 0, 0));
			Roll shieldblockHigh = new Roll();
				shieldblockHigh.add(new Effect(effectType.BUFF_SELF, buffType.MELEE_BLOCK, decayType.DEFENCE, 3, 90, 0, 0, 0));
				shieldblockHigh.add(new Effect(effectType.BUFF_SELF, buffType.RANGED_BLOCK, decayType.DEFENCE, 3, 70, 0, 0, 0));
				shieldblockHigh.add(new Effect(effectType.BUFF_SELF, buffType.MAGIC_BLOCK, decayType.DEFENCE, 3, 60, 0, 0, 0));
			shieldblockRolls.add(shieldblockLow); shieldblockRolls.add(shieldblockSta); shieldblockRolls.add(shieldblockHigh);
			return new Ability("Shieldblock", abilityType.BUFF, new int[]{0, 2, 2}, 10, 10, shieldblockRolls);	
		case "Staff Jab":
		ArrayList<Roll> staffJabRolls = new ArrayList<Roll>();
			Roll staffJabLow = new Roll();
				staffJabLow.add(new Effect(effectType.HEAL_MANA, null, null, 0, 5, 0, 0, 0));
				staffJabLow.add(new Effect(effectType.DAMAGE, null, null, 0, -10, 1, 0, 0));
			Roll staffJabSta = new Roll();
				staffJabSta.add(new Effect(effectType.HEAL_MANA, null, null, 0, 0, 0, 1, 0));
				staffJabSta.add(new Effect(effectType.DAMAGE, null, null, 0, -10, 1, 0, 1));
			Roll staffJabHigh = new Roll();
				staffJabHigh.add(new Effect(effectType.HEAL_MANA, null, null, 0, 0, 0, 2, 0));
				staffJabHigh.add(new Effect(effectType.DAMAGE, null, null, 0, -5, 1, 0, 1));
			staffJabRolls.add(staffJabLow); staffJabRolls.add(staffJabSta); staffJabRolls.add(staffJabHigh);
			return new Ability("Staff Jab", abilityType.MELEE, new int[] {0, 0, 0}, 20, 20, staffJabRolls);
		case "Lightningbolt":
			ArrayList<Roll> lightningboltRolls = new ArrayList<Roll>();
			Roll lightningboltLow = new Roll();
				lightningboltLow.add(new Effect(effectType.DAMAGE, null, null, 0, 0, 0, 1, 0));
			Roll lightningboltSta = new Roll();
				lightningboltSta.add(new Effect(effectType.DAMAGE, null, null, 0, 0, 0, 2, 0));
			Roll lightningboltHigh = new Roll();
				lightningboltHigh.add(new Effect(effectType.DAMAGE, null,null, 0, 0, 0, 3, 0));
				lightningboltHigh.add(new Effect(effectType.BUFF_ENEMY, buffType.HIGHROLL, decayType.ATTACK, 2, -10, 0, 0, 0));
				lightningboltRolls.add(lightningboltLow); lightningboltRolls.add(lightningboltSta); lightningboltRolls.add(lightningboltHigh);
			return new Ability("Lightningbolt", abilityType.MAGIC, new int[] {0, 15, 0}, 40, 15, lightningboltRolls);
		case "Fireball":
		ArrayList<Roll> fireballRolls = new ArrayList<Roll>();
			Roll fireballLow = new Roll();
				fireballLow.add(new Effect(effectType.DAMAGE, null, null, 0, -10, 0, 2, 0));
			Roll fireballSta = new Roll();
				fireballSta.add(new Effect(effectType.DAMAGE, null, null, 0, 0, 0, 2, 0));
			Roll fireballHigh = new Roll();
				fireballHigh.add(new Effect(effectType.DAMAGE, null,null, 0, 0, 0, 3, 0));
			fireballRolls.add(fireballLow); fireballRolls.add(fireballSta); fireballRolls.add(fireballHigh);
			return new Ability("Fireball", abilityType.MAGIC, new int[] {0, 5, 0}, 30, 20, fireballRolls);
		}
		return null;
		
	}
}
