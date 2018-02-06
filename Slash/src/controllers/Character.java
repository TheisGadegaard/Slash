package controllers;
import java.util.ArrayList;

public abstract class Character {

	private String name;
	private int STR, INT, AGI;
	private ArrayList<Ability> abilities;
	
	protected Character(String name, int STR, int INT, int AGI) {
		this.name = name;										//Charactertype
		this.STR = STR;											//Strength
		this.INT = INT;											//Intelligence
		this.AGI = AGI;											//Agility
		this.abilities = new ArrayList<Ability>();								//Abilities
	}

	abstract String getName();
	abstract int getBaseSTR();
	abstract int getBaseINT();
	abstract int getBaseAGI();
	abstract int getCurrSTR();
	abstract int getCurrINT();
	abstract int getCurrAGI();
	abstract void setCurrSTR(int i);
	abstract void setCurrINT(int i);
	abstract void setCurrAGI(int i);
	abstract ArrayList<Ability> getAbilities();
	abstract void addAbility(Ability ability);
	abstract int getCurrHP();
	abstract void setCurrHP(int i);
	abstract int getCurrEN();
	abstract void setCurrEN(int i);
	abstract int getCurrMP();
	abstract void setCurrMP(int i);
	abstract int getMaxHP();
	abstract int getMaxMP();
	abstract int getMaxEN();
	abstract void setMaxHP(int i);
	abstract void setMaxMP(int i);
	abstract void setMaxEN(int i);
	abstract Ability chooseAttack();
	abstract void addBuff(Effect effect);
	abstract void decayBuffsPerAttack();
	abstract void decayBuffsPerRound();
	abstract void decayBuffsPerDefence();
	abstract ArrayList<Buff> getBuffs();
	abstract void payCosts(Ability activeAbility);
}
