package controllers;
import enums.effectType;
import enums.decayType;
import enums.buffType;

public class Effect {

	effectType effectType;
	buffType buffType;
	int basePower, buffProcent, strScaling, intScaling, agiScaling, numAttack;
	decayType decaytype;
	
	public Effect(effectType effectType, buffType buffType, decayType decaytype, int numAttack, int basePower, int strScaling, int intScaling, int agiScaling) {
		this.effectType = effectType;				//The effecttype of this effect
		this.buffType = buffType;					//The bufftype of this effect
		this.basePower = basePower;					//The base power of this effect
		this.strScaling = strScaling;				//Strength scaling
		this.intScaling = intScaling;				//Intelligence scaling
		this.agiScaling = agiScaling;				//Agility scaling
		this.numAttack = numAttack;					//Number of times a buff is applied
		this.decaytype = decaytype;					//True = buff decays per attack/defence. False = buff decays per round. Null = does not decay
	}

	public int getPower(int strength, int intelligence, int agility) {
		int power = this.basePower + (strScaling*strength) + (intScaling*intelligence) + (agiScaling*agility);
		if(power < 0) {
			return 0;
		}
	return power;
	}
	public decayType getdecayType() {
		return decaytype;
	}
	
	public effectType getEffectType() {
		return effectType;
	}

	public buffType getBuffType() {
		return buffType;
	}

	public int getBasePower() {
		return basePower;
	}

	public int getBuffProcent() {
		return buffProcent;
	}

	public int getStrScaling() {
		return strScaling;
	}

	public int getIntScaling() {
		return intScaling;
	}

	public int getAgiScaling() {
		return agiScaling;
	}

	public int getNumRounds() {
		return numAttack;
	}
}
