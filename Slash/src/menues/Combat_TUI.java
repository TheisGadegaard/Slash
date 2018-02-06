package menues;

import controllers.Enemy;
import controllers.Playable_Character;

public class Combat_TUI {

	private Playable_Character player;
	private Enemy NPC;

	public Combat_TUI(Playable_Character player, Enemy NPC) {
		this.player = player;
		this.NPC = NPC;
	}

	public void startOfCombat() {
		System.out.println("--------------------- Combat -----------------------");
		System.out.println(player.getName() + " vs " + NPC.getName());
	}

	public void playerBasicInfo() {
		System.out.println("\n---------> Info about " + player.getName());
		System.out.println("Health: " + player.getName() + " " + player.getCurrHP() + "/" + player.getMaxHP());
		System.out.println("Mana: " + player.getName() + " " + player.getCurrMP() + "/" + player.getMaxMP());
		System.out.println("Energy: " + player.getName() + " " + player.getCurrEN() + "/" + player.getMaxEN());
	}
	
	public void enemyBasicInfo() {
		System.out.println("\n---------> Info about " + NPC.getName());
		System.out.println("Health: " + NPC.getCurrHP() + "/" + NPC.getMaxHP());
		System.out.println("Mana: " + NPC.getCurrMP() + "/" + NPC.getMaxMP());
		System.out.println("Energy: " + NPC.getCurrEN() + "/" + NPC.getMaxEN());
	}
	
	public void playerBuffs() {
		for(int j = 0; j < player.getBuffs().size(); j++) {
			System.out.print(" | " + player.getBuffs().get(j).getBufftype().toString() + ":" + player.getBuffs().get(j).getPower() + ":" + player.getBuffs().get(j).getbuffIterator());
		}
		if(player.getBuffs().size() != 0) {
			System.out.print(" \n");
		}
	}
	
	public void enemyBuffs() {
		for(int j = 0; j < NPC.getBuffs().size(); j++) {
			System.out.print(" | " + NPC.getBuffs().get(j).getBufftype().toString() + ":" + NPC.getBuffs().get(j).getPower() + ":" + NPC.getBuffs().get(j).getbuffIterator());
		}
		if(NPC.getBuffs().size() != 0) {
			System.out.print(" \n");
		}
	}
}
