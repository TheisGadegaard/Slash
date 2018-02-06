package controllers;
import enums.buffType;
import enums.decayType;

public class Buff {

	private buffType bufftype;
	private int buffIterator;
	private int power;
	private decayType decaytype;
	
	public Buff(buffType bufftype, int power, int buffIterator, decayType decaytype) {
		this.bufftype = bufftype;					//The bufftype of this buff
		this.power = power;							//The base power of this buff
		this.buffIterator = buffIterator;			//Number of times this buff is applied
		this.decaytype = decaytype;	//True = buff decays per attack/defence. False = buff decays per round. Null = does not decay
	}

	public int getbuffIterator() {
		return buffIterator;
	}

	public void setbuffIterator(int buffIterator) {
		this.buffIterator = buffIterator;
	}

	public buffType getBufftype() {
		return bufftype;
	}

	public int getPower() {
		return power;
	}

	public decayType getdecayType() {
		return decaytype;
	}
}
