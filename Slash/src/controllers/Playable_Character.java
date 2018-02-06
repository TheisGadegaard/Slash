package controllers;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import data.StaticData;
import enums.decayType;

public class Playable_Character extends Character{

	private String name;
	private int STR, INT, AGI, 
	STR_LVL, INT_LVL, AGI_LVL, 
	currHP, currMP, currEN,
	maxHP, maxMP, maxEN, 
	currSTR, currINT, currAGI,
	xp, xpNEXT_LVL, gold, level, skillpoints;
	private ArrayList<Ability> abilities;
	private ArrayList<Buff> buffs;
	private ArrayList<Item> inventory;

	public Playable_Character(String name, int STR, int INT, int AGI, int STR_LVL, int INT_LVL, int AGI_LVL) {
		super(name, STR, INT, AGI);
		this.name = name;										//Charactertype
		this.STR = STR;											//Strength
		this.INT = INT;											//Intelligence
		this.AGI = AGI;											//Agility
		this.currSTR = this.STR;								//Current strength
		this.currINT = this.INT;								//Current intelligence
		this.currAGI = this.AGI;								//Current agility
		this.abilities = new ArrayList<Ability>();				//Abilities
		this.STR_LVL = STR_LVL;									//Strength gained per level
		this.INT_LVL = INT_LVL;									//Intelligence gained per level
		this.AGI_LVL = AGI_LVL;									//Agility gained per level	
		inventory = new ArrayList<Item>();						//Inventory
		buffs = new ArrayList<Buff>();							//Active buffs and debuffs on character
		this.maxHP = StaticData.baseHealth + (StaticData.healthScaling*STR);								//Maximum health
		this.maxMP = StaticData.baseMana + (StaticData.manaScaling*INT);								//Maximum mana
		this.maxEN = StaticData.baseEnergy + (StaticData.energyScaling*AGI);								//Maximum energy
		this.currHP = maxHP;									//Current health
		this.currMP = maxMP;									//Current mana
		this.currEN = maxEN;									//Current energy
		this.xp = 0;											//Experience
		this.xpNEXT_LVL = 100;									//Experience needed for next level
		this.skillpoints = 0;									//Available skillpoints
		this.gold = 0;											//Amount of gold owned
		this.level = 1;
	}

	public void cleanPlayerStats() {
		this.buffs.clear();
		this.currSTR = this.STR;								
		this.currINT = this.INT;								
		this.currAGI = this.AGI;								
		this.maxHP = StaticData.baseHealth + (StaticData.healthScaling*STR);
		this.maxMP = StaticData.baseMana + (StaticData.manaScaling*INT);
		this.maxEN = StaticData.baseEnergy + (StaticData.energyScaling*AGI);
		this.currHP = maxHP;									
		this.currMP = maxMP;									
		this.currEN = maxEN;									
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

	public Ability chooseAttack() {
		Scanner attackScan = new Scanner(System.in);
		ArrayList<Ability> availableAbilities = getAvailableAbilities();
		System.out.println("\nChoose your ability:");
		int i = 1;
		for(Ability ability: availableAbilities) {
			System.out.println(i++ + ":\t" + ability.getName());
		}
		String playerInput = "";
		while(true) {
			try {
				playerInput = attackScan.next();
				switch(playerInput) {
				case "1": 
					return availableAbilities.get(Integer.parseInt(playerInput)-1);
				case "2": if(availableAbilities.size() >= 2) {
					return availableAbilities.get(Integer.parseInt(playerInput)-1);}
				break;
				case "3": if(availableAbilities.size() >= 3) {
					return availableAbilities.get(Integer.parseInt(playerInput)-1);}
				break;
				case "4": if(availableAbilities.size() >= 4) {
					return availableAbilities.get(Integer.parseInt(playerInput)-1);}
				break;
				case "5": if(availableAbilities.size() == 5) {
					return availableAbilities.get(Integer.parseInt(playerInput)-1);}
				break;
				default:
				}
			} catch (InputMismatchException e) {
				System.err.println("Input is not a valid integer");
			}
		}
	}

	public void payCosts(Ability activeAbility) {
		this.setCurrHP(this.getCurrHP()-activeAbility.getCost(0));
		this.setCurrMP(this.getCurrMP()-activeAbility.getCost(1));
		this.setCurrEN(this.getCurrEN()-activeAbility.getCost(2));
	}

	private ArrayList<Ability> getAvailableAbilities(){
		ArrayList<Ability> availableAbilities = new ArrayList<Ability>();
		for(Ability ability: abilities) {
			if(ability.getCost(0) < getCurrHP() && 			//Healthcost is Less than current health
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

	public int getCurrHP() {
		return currHP;
	}

	public void setCurrHP(int currHP) {
		this.currHP = currHP;
	}

	public int getCurrMP() {
		return currMP;
	}

	public void setCurrMP(int currMP) {
		this.currMP = currMP;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public int getMaxMP() {
		return maxMP;
	}

	public void setMaxMP(int maxMP) {
		this.maxMP = maxMP;
	}

	public int getMaxEN() {
		return maxEN;
	}

	public void setMaxEN(int maxEN) {
		this.maxEN = maxEN;
	}

	public int getBaseSTR() {
		return STR;
	}

	public void setSTR(int sTR) {
		STR = sTR;
	}

	public int getBaseINT() {
		return INT;
	}

	public void setINT(int iNT) {
		INT = iNT;
	}

	public int getBaseAGI() {
		return AGI;
	}

	public void setAGI(int aGI) {
		AGI = aGI;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCurrEN() {
		return currEN;
	}

	public void setCurrEN(int currEN) {
		this.currEN = currEN;
	}

	public ArrayList<Ability> getAbilities() {
		return abilities;
	}
	public void addAbility(Ability ability) {
		abilities.add(ability);
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
}
