package controllers;
import java.io.IOException;
import java.util.ArrayList;

import dataLoad.CharacterLoad;
import dataLoad.ItemLoad;
import enums.itemRarety;

public class Main_menu {

	public static void main(String[] args) throws IOException {
	/*Single Combat testing*/
//		CharacterLoad playables = new CharacterLoad();
//		ArrayList<Playable_Character> playableCharacters = playables.loadPlayables();
//		Combat testRoundorder = new Combat(playableCharacters.get(0));
//		testRoundorder.startCombat();
		
	/*Muliple Combat testing*/
		while(true) {
			CharacterLoad playables = new CharacterLoad();
			ArrayList<Playable_Character> playableCharacters = playables.loadPlayables();
			Combat testRoundorder = new Combat(playableCharacters.get(0));
			testRoundorder.startCombat();
		}
		
	/*ItemLoad testing*/
//		ItemLoad myLoad = new ItemLoad();
//		
//		ArrayList<Item> theis = myLoad.loadItems(itemRarety.COMMON_JEWELRY);
//		
//		for(Item item: theis) {
//			System.out.println(item.getName() + ", " + item.getItemType());
//		}
	}

}
