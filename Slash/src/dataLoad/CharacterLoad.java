package dataLoad;
import java.util.ArrayList;

import controllers.Enemy;
import controllers.Playable_Character;

public class CharacterLoad {
	private ArrayList<Playable_Character> playableCharacters;
	private ArrayList<Enemy> allEnemies;

	public CharacterLoad() {
		this.playableCharacters = new ArrayList<Playable_Character>();
		this.allEnemies = new ArrayList<Enemy>();
	}
	public ArrayList<Playable_Character> loadPlayables(){
		int charCount = 0;
		AbilityLoad abiLoad = new AbilityLoad();
		playableCharacters.add(new Playable_Character("Warrior", 6, 5, 4, 3, 0, 2));
			playableCharacters.get(charCount).addAbility(abiLoad.loadAbility("Slash"));
			playableCharacters.get(charCount).addAbility(abiLoad.loadAbility("Warcry"));
			playableCharacters.get(charCount).addAbility(abiLoad.loadAbility("Axe Throw"));
			playableCharacters.get(charCount).addAbility(abiLoad.loadAbility("Shieldblock"));
			charCount++;
		playableCharacters.add(new Playable_Character("Wizard", 4, 6, 5, 1, 3, 1));
		playableCharacters.add(new Playable_Character("Rogue", 5, 4, 6, 1, 1, 3));
		playableCharacters.add(new Playable_Character("Shaman", 5, 5, 5, 2, 2, 1));
		return playableCharacters;
	}

	public ArrayList<Enemy> loadEnemies(){
		int enemyCount = 0;
		AbilityLoad abiLoad = new AbilityLoad();
		allEnemies.add(new Enemy("Goblin Thrall", 3, 0, 4, 40, 0, 10, 1));
			allEnemies.get(enemyCount).addAbility(abiLoad.loadAbility("Slash"));
			enemyCount++;
		allEnemies.add(new Enemy("Goblin Shaman", 10, 10, 5, 60, 20, 0, 2));
			allEnemies.get(enemyCount).addAbility(abiLoad.loadAbility("Staff Jab"));
			allEnemies.get(enemyCount).addAbility(abiLoad.loadAbility("Lightningbolt"));
		return allEnemies;
	}
}
