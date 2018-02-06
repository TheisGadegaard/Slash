package controllers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import data.StaticData;
import dataLoad.CharacterLoad;
import enums.abilityType;
import enums.buffType;
import enums.rollType;
import menues.Combat_TUI;

public class Combat {

	private Playable_Character player;
	private Enemy NPC;
	private ArrayList<Character> roundOrder;
	private ArrayList<Enemy> allEnemies;
	private ArrayList<Enemy> possibleEnemies;
	private int roundNumber = 0;
	private Character activeChar;
	private Character defender;
	private int gameFinished;					//0 = game is running, 1 = player won, 2 = player lost

	public Combat(Playable_Character player) {
		CharacterLoad enemyLoad = new CharacterLoad();
		allEnemies = enemyLoad.loadEnemies();
		possibleEnemies = new ArrayList<Enemy>();
		this.player = player;
	}

	public void startCombat() throws IOException {
		//TODO apply buffs from items here
		setPossibleEnemies();
		this.NPC = getOpponent();
		applyStartBuffs(player);
		applyStartBuffs(NPC);
		Combat_TUI tui = new Combat_TUI(player, NPC);
		tui.startOfCombat();
		System.in.read();
		gameFinished = 0;		//0 = game is running, 1 = player won, 2 = player lost
		while(gameFinished == 0) {
			System.out.println("\n---------> Round number " + ++roundNumber);
			roundOrder = setRoundOrder(this.NPC);
			for(int i = 0; i < roundOrder.size(); i++) {
				if(roundOrder.get(i) == this.player) {
					activeChar = player;
					defender = NPC;
				} else if (roundOrder.get(i) == this.NPC) {
					activeChar = NPC;
					defender = player;
				} else {
					System.err.println("Error: Who has turn?");
				}
				applyBleed(activeChar);
				setGameStatus();
				if(gameFinished == 0) {
					System.out.println("It's " + activeChar.getName() + "'s turn");
					System.in.read();
					Ability activeAbility = activeChar.chooseAttack();
					activeChar.payCosts(activeAbility);
					System.out.println(activeChar.getName() + " uses " + activeAbility.getName() + "\n");
					switch(getRollType(activeChar, activeAbility)) {
					case LOWROLL: System.in.read();
					System.out.println(activeChar.getName() + " lowrolled");
					System.in.read();
					resolveEffects(activeChar, defender, activeAbility.getRolls().get(0), activeAbility);
					break;
					case STANDART: System.in.read();
					System.out.println(activeChar.getName() + " standartrolled");
					System.in.read();
					resolveEffects(activeChar, defender, activeAbility.getRolls().get(1), activeAbility);
					break;
					case HIGHROLL: System.in.read();
					System.out.println(activeChar.getName() + " highrolled");
					System.in.read();
					resolveEffects(activeChar, defender, activeAbility.getRolls().get(2), activeAbility);
					break;
					default: System.err.println("Problem with resolving effects in class Combat");
					}
					activeChar.decayBuffsPerAttack();
				}
				System.in.read();
				tui.playerBasicInfo();
				tui.playerBuffs();
				System.in.read();
				tui.enemyBasicInfo();
				tui.enemyBuffs();
				setGameStatus();
				if(gameFinished != 0) {
					break;
				}
			}
			activeChar.decayBuffsPerRound();
			defender.decayBuffsPerRound();
		}
		player.cleanPlayerStats();
		System.out.println("Game finished with statuscode: " + gameFinished);
	}

	private void setGameStatus() {
		if(player.getCurrHP() <= 0) {
			gameFinished = 2;
		} else if(NPC.getCurrHP() <= 0) {
			gameFinished = 1;
		}
	}

	private void applyBleed(Character activeChar) {
		//		System.out.println("Buffs: " + activeChar.getBuffs().size());
		for(int i = 0; i < activeChar.getBuffs().size(); i++) {
			if(activeChar.getBuffs().get(i).getBufftype() == buffType.BLEED) {
				activeChar.setCurrHP(activeChar.getCurrHP()-activeChar.getBuffs().get(i).getPower());
			}
		}
	}

	private void setPossibleEnemies(){
		for(int i = 0; i < this.allEnemies.size(); i++) {
			if(this.player.getLevel()-1 <= this.allEnemies.get(i).getLevel() &&
					this.player.getLevel()+1 >= this.allEnemies.get(i).getLevel()){
				this.possibleEnemies.add(this.allEnemies.get(i));
			}
		}
		System.out.println("----------------- Possible enemies -----------------");
		for(int j = 0; j < this.possibleEnemies.size(); j++) {
			System.out.println(possibleEnemies.get(j).getName() + " (Level " + possibleEnemies.get(j).getLevel() + ")");
		}
	}

	@SuppressWarnings("incomplete-switch")	//We only need the effects that change damage procents
	private double getDamageProcent(Character activeChar, Ability activeAbility) {
		int damageamount = 0;
		for(int i = 0; i < activeChar.getBuffs().size(); i++) {
			switch(activeChar.getBuffs().get(i).getBufftype()) {
			case DAMAGE: damageamount += activeChar.getBuffs().get(i).getPower();
			break;
			case MELEE_DAMAGE: if(activeAbility.getabilityType() == abilityType.MELEE) {
				damageamount += activeChar.getBuffs().get(i).getPower();
			}
			break;
			case RANGED_DAMAGE: if(activeAbility.getabilityType() == abilityType.RANGED) {
				damageamount += activeChar.getBuffs().get(i).getPower();
			}
			break;
			case MAGIC_DAMAGE: if(activeAbility.getabilityType() == abilityType.MAGIC) {
				damageamount += activeChar.getBuffs().get(i).getPower();
			}
			break;
			}
		}
		double damageProcent = 1+((double) damageamount/100);
		return damageProcent;
	}

	private double getBlockProcent(Character defender, Ability activeAbility) {
		int blockamount = 0;
		for(int i = 0; i < defender.getBuffs().size();i++) {
			switch(defender.getBuffs().get(i).getBufftype()) {
			case BLOCK: blockamount += defender.getBuffs().get(i).getPower();
			break;
			case MELEE_BLOCK: if(activeAbility.getabilityType() == abilityType.MELEE) {
				blockamount += defender.getBuffs().get(i).getPower();
			}
			break;
			case RANGED_BLOCK: if(activeAbility.getabilityType() == abilityType.RANGED) {
				blockamount += defender.getBuffs().get(i).getPower();
			}
			break;
			case MAGIC_BLOCK: if(activeAbility.getabilityType() == abilityType.MAGIC) {
				blockamount += defender.getBuffs().get(i).getPower();
			}
			break;
			}
		}
		if(blockamount > StaticData.maxBlock) {
			blockamount = StaticData.maxBlock;		//Can't block more than 95% of the damage
		}
		double blockProcent = (double) blockamount/100;
		defender.decayBuffsPerDefence();
		return blockProcent;
	}

	private rollType getRollType(Character activeChar, Ability activeAbility) {
		int numLowrolls = activeAbility.getLowroll();
		int numStandartrolls = StaticData.standartRolls;
		int numHighrolls = activeAbility.getHighroll();
		for(int i = 0; i < activeChar.getBuffs().size(); i++) {
			switch(activeChar.getBuffs().get(i).getBufftype()) {
			case LOWROLL: numLowrolls += activeChar.getBuffs().get(i).getPower();
			break;
			case HIGHROLL:	numHighrolls += activeChar.getBuffs().get(i).getPower();
			break;
			case STANDARTROLL: numStandartrolls += activeChar.getBuffs().get(i).getPower();
			break;
			case BUFF_HIGH:	if(activeAbility.getabilityType() == abilityType.BUFF) {
				numHighrolls += activeChar.getBuffs().get(i).getPower();
			}
			break;
			case BUFF_LOW: if(activeAbility.getabilityType() == abilityType.BUFF) {
				numLowrolls += activeChar.getBuffs().get(i).getPower();
			}
			break;
			case DEBUFF_HIGH: if(activeAbility.getabilityType() == abilityType.DEBUFF) {
				numHighrolls += activeChar.getBuffs().get(i).getPower();
			}
			break;
			case DEBUFF_LOW: if(activeAbility.getabilityType() == abilityType.DEBUFF) {
				numLowrolls += activeChar.getBuffs().get(i).getPower();
			}
			break;
			case MAGIC_HIGH: if(activeAbility.getabilityType() == abilityType.MAGIC) {
				numHighrolls += activeChar.getBuffs().get(i).getPower();
			}
			break;
			case MAGIC_LOW: if(activeAbility.getabilityType() == abilityType.MAGIC) {
				numLowrolls += activeChar.getBuffs().get(i).getPower();
			}
			break;
			case MELEE_HIGH: if(activeAbility.getabilityType() == abilityType.MELEE) {
				numHighrolls += activeChar.getBuffs().get(i).getPower();
			}
			break;
			case MELEE_LOW: if(activeAbility.getabilityType() == abilityType.MELEE) {
				numLowrolls += activeChar.getBuffs().get(i).getPower();
			}
			break;
			case RANGED_HIGH: if(activeAbility.getabilityType() == abilityType.RANGED) {
				numHighrolls += activeChar.getBuffs().get(i).getPower();
			}
			break;
			case RANGED_LOW: if(activeAbility.getabilityType() == abilityType.RANGED) {
				numLowrolls += activeChar.getBuffs().get(i).getPower();
			}
			break;
			default: 
				break;
			}
		}
		System.out.println("---------> Rollchances \n\tLowroll = " + numLowrolls + " \n\tStandart = " + numStandartrolls + " \n\tHighroll = " + numHighrolls);
		ArrayList<rollType> chanceTokens = new ArrayList<rollType>();
		for(int i = 0; i < numLowrolls; i++) {
			chanceTokens.add(rollType.LOWROLL);
		}
		for(int i = 0; i < numStandartrolls; i++) {
			chanceTokens.add(rollType.STANDART);
		}
		for(int i = 0; i < numHighrolls; i++) {
			chanceTokens.add(rollType.HIGHROLL);
		}
		return chanceTokens.get((int) (Math.random()*(chanceTokens.size())));
	}

	private ArrayList<Character> setRoundOrder(Enemy NPC) {
		//		Playable_Character player = this.player;
		ArrayList<Character> roundOrder = new ArrayList<Character>();
		Character highestAGI = null;
		Character lowestAGI = null;
		if(player.getCurrAGI() > NPC.getCurrAGI()) {
			highestAGI = player;
			lowestAGI = NPC;
		} else if(player.getCurrAGI() < NPC.getCurrAGI()){
			highestAGI = NPC;
			lowestAGI = player;
		} else if(player.getCurrAGI() == NPC.getCurrAGI()){
			roundOrder.add(player);						//If the agility of player and enemy is tied, the player starts
			roundOrder.add(NPC);
			return roundOrder;
		}
		roundOrder.add(highestAGI);
		int extraAttacks = (int) (highestAGI.getCurrAGI()/lowestAGI.getCurrAGI())-1;
		System.out.println("ExtraAttacks: " + extraAttacks);
		System.out.println();
		if(extraAttacks != 0){
			ArrayList<Character> extraAttackOrder = new ArrayList<Character>();
			extraAttackOrder.add(lowestAGI);
			for(int i = 0; i < extraAttacks; i++) {
				extraAttackOrder.add(highestAGI);
			}
			Collections.shuffle(extraAttackOrder);
			for(int i = 0; i < extraAttackOrder.size(); i++) {
				roundOrder.add(extraAttackOrder.get(i));
			}
		} else {
			roundOrder.add(lowestAGI);
		}
		//		System.out.println("RoundOrder: " + roundOrder);
		return roundOrder;
	}

	private void applyStartBuffs(Character character) {
		character.setCurrAGI(character.getBaseAGI());
		character.setCurrINT(character.getBaseINT());
		character.setCurrSTR(character.getBaseSTR());
		for(Buff buff: character.getBuffs()) {
			switch(buff.getBufftype()) {
			case STRENGTH: character.setCurrSTR(character.getCurrSTR()+buff.getPower());
			break;
			case AGILITY: character.setCurrAGI(character.getCurrAGI()+buff.getPower());
			break;
			case INTELLIGENCE: character.setCurrINT(character.getCurrINT()+buff.getPower());
			break;
			}
		}
		character.setMaxHP(StaticData.baseHealth+(StaticData.healthScaling*character.getCurrSTR()));
		character.setMaxMP(StaticData.baseMana+(StaticData.manaScaling*character.getCurrINT()));
		character.setMaxEN(StaticData.baseEnergy+(StaticData.energyScaling*character.getCurrAGI()));
		for(Buff buff: character.getBuffs()) {
			switch(buff.getBufftype()) {
			case MAX_HEALTH: character.setMaxHP(character.getMaxHP()+buff.getPower());
			break;
			case MAX_MANA: character.setMaxMP(character.getMaxMP()+buff.getPower());
			break;
			case MAX_ENERGY: character.setMaxEN(character.getMaxEN()+buff.getPower());
			break;
			}
		}
		if(character.getMaxHP() < 1) {		//A character can't have less than 1 max health
			character.setMaxHP(1);
		}
		character.setCurrHP(character.getMaxHP());
		character.setCurrMP(character.getMaxMP());
		character.setCurrEN(character.getMaxEN());
		
		if(character.getCurrAGI() < 1) {	//A character can't have less than 1 agility
			character.setCurrAGI(1);
		}
	}

	private Enemy getOpponent() {
		return possibleEnemies.get((int) (Math.random()*(possibleEnemies.size())));
	}

	private void resolveEffects(Character character, Character defender, Roll outcome, Ability activeAbility){
		for(int i = 0; i < outcome.getEffects().size(); i++) {
			switch(outcome.getEffects().get(i).getEffectType()) {
			case DAMAGE: int damage = ((int) (outcome.getEffects().get(i).getPower(character.getCurrSTR(), character.getCurrINT(), character.getBaseAGI())*(1-(getBlockProcent(defender, activeAbility)))*getDamageProcent(activeChar, activeAbility)));
			defender.setCurrHP(defender.getCurrHP()-damage);
			System.out.println(character.getName() + " dealt " + damage + " damage to " + defender.getName());
			break;
			case HEAL_ENERGY: character.setCurrEN(character.getCurrEN() + 
					outcome.getEffects().get(i).getPower(character.getCurrSTR(), character.getCurrINT(), character.getBaseAGI()));
			if(character.getCurrEN() > character.getMaxEN()) {
				character.setCurrEN(character.getMaxEN());	}
			break;
			case HEAL_MANA: character.setCurrMP(character.getCurrMP() + 
					outcome.getEffects().get(i).getPower(character.getCurrSTR(), character.getCurrINT(), character.getBaseAGI()));
			if(character.getCurrMP() > character.getMaxMP()) {
				character.setCurrMP(character.getMaxMP());	}
			break;
			case HEAL_LIFE: character.setCurrHP(character.getCurrHP() + 
					outcome.getEffects().get(i).getPower(character.getCurrSTR(), character.getCurrINT(), character.getBaseAGI()));
			if(character.getCurrHP() > character.getMaxHP()) {
				character.setCurrHP(character.getMaxHP());	}
			break;
			case BUFF_SELF:	character.addBuff(outcome.getEffects().get(i));
			break;
			case BUFF_ENEMY: defender.addBuff(outcome.getEffects().get(i));
			break;
			default: System.err.println("EffectType invalid (class: Combat. Method: resolveEffects)");
			}
		}
	}
}
