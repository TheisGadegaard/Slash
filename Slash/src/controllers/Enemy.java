package controllers;
import java.util.ArrayList;

import enums.decayType;

public class Enemy extends Character{

	String name;
	int STR, INT, AGI, currHP, currMP, currEN, maxHP, maxMP, maxEN, level, currSTR, currINT, currAGI;
	ArrayList<Ability> abilities;
	ArrayList<Buff> buffs;

	public Enemy(String name, int STR, int INT, int AGI, int maxHP, int maxMP, int maxEN, int level) {
		super(name, STR, INT, AGI);
		this.name = name;										//Charactertype
		this.STR = STR;											//Strength
		this.INT = INT;											//Intelligence
		this.AGI = AGI;											//Agility
		this.currSTR = this.STR;								//Current strength
		this.currINT = this.INT;								//Current intelligence
		this.currAGI = this.AGI;								//Current agility
		this.maxHP = maxHP;										//Maximum health
		this.maxMP = maxMP;										//Maximum mana
		this.maxEN = maxEN;										//Maximum energy
		this.currHP = maxHP;									//Current health
		this.currMP = maxMP;									//Current mana
		this.currEN = maxEN;						 			//Current energy
		this.level = level;										//This characters level
		this.abilities = new ArrayList<Ability>();				//Abilities
		buffs = new ArrayList<Buff>();
	}

	public void addBuff(Effect effect) {
		buffs.add(new Buff(effect.getBuffType(), effect.getBasePower(), effect.getNumRounds(), effect.getdecayType()));
	}
	
	public void decayBuffsPerAttack() {
		for(int i = 0; i < buffs.size(); i++) {
			if(buffs.get(i).getdecayType() == decayType.ATTACK) {
				buffs.get(i).setbuffIterator(buffs.get(i).getbuffIterator()-1);
				if(buffs.get(i).getbuffIterator() == 0) {
					buffs.remove(buffs.get(i--));
				} else if(buffs.get(i).getbuffIterator() < 0) {
					System.err.println("Negative buffIterator on Buff: " + buffs.get(i));
				}
			}
		}
	}
	
	public void decayBuffsPerRound() {
		for(int i = 0; i < buffs.size(); i++) {
			if(buffs.get(i).getdecayType() == decayType.ROUND) {
				buffs.get(i).setbuffIterator(buffs.get(i).getbuffIterator()-1);
				if(buffs.get(i).getbuffIterator() == 0) {
					buffs.remove(buffs.get(i--));
				} else if(buffs.get(i).getbuffIterator() < 0) {
					System.err.println("Negative buffIterator on Buff: " + buffs.get(i));
				}
			}
		}
	}
	
	public void decayBuffsPerDefence() {
		for(int i = 0; i < buffs.size(); i++) {
			if(buffs.get(i).getdecayType() == decayType.DEFENCE) {
				buffs.get(i).setbuffIterator(buffs.get(i).getbuffIterator()-1);
				if(buffs.get(i).getbuffIterator() == 0) {
					buffs.remove(buffs.get(i--));
				} else if(buffs.get(i).getbuffIterator() < 0) {
					System.err.println("Negative buffIterator on Buff: " + buffs.get(i));
				}
			}
		}
	}

	public ArrayList<Buff> getBuffs() {
		return buffs;
	}

	public Ability chooseAttack() {		//TODO create logic for chooseAttack
		ArrayList<Ability> availableAbilities = getAvailableAbilities();
		return availableAbilities.get((int) (Math.random()*availableAbilities.size()));
	}
	
	public void payCosts(Ability activeAbility) {
		this.setCurrHP(this.getCurrHP()-activeAbility.getCost(0));
		this.setCurrMP(this.getCurrMP()-activeAbility.getCost(1));
		this.setCurrEN(this.getCurrEN()-activeAbility.getCost(2));
	}
	
	private ArrayList<Ability> getAvailableAbilities(){
		ArrayList<Ability> availableAbilities = new ArrayList<Ability>();
		for(Ability ability: abilities) {
			if(ability.getCost(0) < getCurrHP() && 	//Healthcost is Less than current health
				ability.getCost(1) <= getCurrMP() &&	//Manacost is less than or equal to current mana
				ability.getCost(2) <= getCurrEN()) {	//Energycost is less than or equal to current energy
				availableAbilities.add(ability);
			}
		}
		if(availableAbilities.size() == 0) {
			System.err.println("No available abilities");
		}
		return availableAbilities;
	}

	public void setSTR(int sTR) {
		STR = sTR;
	}

	public void setINT(int iNT) {
		INT = iNT;
	}

	public void setAGI(int aGI) {
		AGI = aGI;
	}

	public int getCurrHP() {
		return currHP;
	}

	public void setCurrHP(int currHp) {
		this.currHP = currHp;
	}

	public int getCurrMP() {
		return currMP;
	}

	public void setCurrMP(int currMP) {
		this.currMP = currMP;
	}

	public int getCurrEN() {
		return currEN;
	}

	public void setCurrEN(int currEN) {
		this.currEN = currEN;
	}

	public String getName() {
		return name;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public int getMaxMP() {
		return maxMP;
	}

	public int getMaxEN() {
		return maxEN;
	}

	public ArrayList<Ability> getAbilities() {
		return abilities;
	}
	public void addAbility(Ability ability) {
		abilities.add(ability);
	}

	public int getBaseSTR() {
		return STR;
	}

	public int getBaseINT() {
		return INT;
	}

	public int getBaseAGI() {
		return AGI;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getCurrSTR() {
		return currSTR;
	}

	public int getCurrINT() {
		return currINT;
	}

	public int getCurrAGI() {
		return currAGI;
	}

	public void setCurrSTR(int newStr) {
		this.currSTR = newStr;
	}

	public void setCurrINT(int newInt) {
		this.currINT = newInt;
	}

	public void setCurrAGI(int newAgi) {
		this.currAGI = newAgi;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public void setMaxMP(int maxMP) {
		this.maxMP = maxMP;
	}

	public void setMaxEN(int maxEN) {
		this.maxEN = maxEN;
	}
}
