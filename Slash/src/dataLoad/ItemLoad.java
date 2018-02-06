package dataLoad;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import controllers.Effect;
import controllers.Item;
import enums.buffType;
import enums.decayType;
import enums.effectType;
import enums.itemRarety;
import enums.itemType;

public class ItemLoad {

	String readfile;
	final String commonJewelryFile = "commonJewelry_v1.0";
	final String uncommonJewelryFile = "uncommonJewelry_v1.0";
	final String rareJewelryFile = null;						//TODO rare jewelry	
	final String commonWeaponsFile = "commonWeapons_v1.0";
	final String uncommonWeaponsFile = null;					//TODO uncommon weapons
	final String rareWeaponsFile = null;						//TODO rare weapons
	final String commonGearFile = null;							//TODO common gear
	final String uncommonGearFile = null;						//TODO uncommon gear
	final String rareGearFile = null;							//TODO rare gear

	public ArrayList<Item> loadItems(itemRarety rarety) {
		switch(rarety) {
		case COMMON_JEWELRY: readfile = commonJewelryFile;
		break;
		case UNCOMMON_JEWELRY: readfile = uncommonJewelryFile;
		break;
		case RARE_JEWELRY: readfile = rareJewelryFile;
		break;
		case COMMON_WEAPONS: readfile = commonWeaponsFile;
		break;
		case UNCOMMON_WEAPONS: readfile = uncommonWeaponsFile;
		break;
		case RARE_WEAPONS:readfile = rareWeaponsFile;
		break;
		case COMMON_GEAR: readfile = commonGearFile;
		break;
		case UNCOMMON_GEAR: readfile = uncommonGearFile;
		break;
		case RARE_GEAR:readfile = rareGearFile;
		break;
		default: System.err.println("can't recognize itemRarety in method loadItems in class ItemLoad");
		break;
		}

		ArrayList<Item> items = new ArrayList<Item>();
		ArrayList<String> itemsAsString = readLines();
		List<String> tempItem = new ArrayList<String>();

		for(int i = 0; i < itemsAsString.size(); i++) {
			tempItem = readParameters(itemsAsString.get(i));
			String name = tempItem.get(0);								//index 0 holds name
			itemType type = readItemType(tempItem.get(1));				//index 1 holds itemType
			int value = Integer.parseInt(tempItem.get(2));				//index 2 holds value
			ArrayList<Effect> effects = readEffects(tempItem.get(3));	//index 3 holds effects
			items.add(new Item(name, type, value, effects));
		}
		return items;
	}

	private List<String> readParameters(String line){
		return Arrays.asList(line.split("; "));
	}

	private itemType readItemType(String type){
		switch(type) {
		case "head": return itemType.HEAD;
		case "body": return itemType.BODY;
		case "gloves": return itemType.GLOVES;
		case "shoes": return itemType.SHOES;
		case "necklace": return itemType.NECKLACE;
		case "ring": return itemType.RING;
		case "onehanded": return itemType.ONEHANDED;
		case "twohanded": return itemType.TWOHANDED;
		default: System.err.println("can't recognize itemType in method readItemType in class ItemLoad");
		}
		return null;
	}

	private ArrayList<Effect> readEffects(String effectsAsString){
		List<String> powerAndType;		//index 0 is power, index 1 is effecttype
		List<String> sep = Arrays.asList(effectsAsString.split(", "));
		ArrayList<Effect> effects = new ArrayList<Effect>();
		for(int j = 0; j < sep.size(); j++) {
			powerAndType = Arrays.asList(sep.get(j).split(" "));

			buffType tempBuffType = null;
			
			switch(powerAndType.get(1)) {
			case "str": case "strength": tempBuffType = buffType.STRENGTH;
			break;
			case "int": case "intelligence": tempBuffType = buffType.INTELLIGENCE;
			break;
			case "agi": case "agility": tempBuffType = buffType.AGILITY;
			break;
			case "health": case "hp": tempBuffType = buffType.MAX_HEALTH;
			break;
			case "mana": case "mp": tempBuffType = buffType.MAX_MANA;
			break;
			case "energy": case "en": tempBuffType = buffType.MAX_ENERGY;
			break;
			case "defence": case "def": case "defense": tempBuffType = buffType.BLOCK;
			break;
			case "def_melee": tempBuffType = buffType.MELEE_BLOCK;
			break;
			case "def_ranged": tempBuffType = buffType.RANGED_BLOCK;
			break;
			case "def_magic": tempBuffType = buffType.MAGIC_DAMAGE;
			break;
			case "damage": case "dmg": tempBuffType = buffType.DAMAGE;
			break;
			case "dmg_melee": tempBuffType = buffType.MELEE_DAMAGE;
			break;
			case "dmg_ranged": tempBuffType = buffType.RANGED_DAMAGE;
			break;
			case "dmg_magic": tempBuffType = buffType.MAGIC_DAMAGE;
			break;
			case "highroll": tempBuffType = buffType.HIGHROLL;
			break;
			case "highroll_melee": tempBuffType = buffType.MELEE_HIGH;
			break;
			case "highroll_ranged": tempBuffType = buffType.RANGED_HIGH;
			break;
			case "highroll_magic": tempBuffType = buffType.MAGIC_HIGH;
			break;
			case "highroll_buff": tempBuffType = buffType.BUFF_HIGH;
			break;
			case "highroll_debuff": tempBuffType = buffType.DEBUFF_HIGH;
			break;
			case "lowroll": tempBuffType = buffType.LOWROLL;
			break;
			case "lowroll_melee": tempBuffType = buffType.MELEE_LOW;
			break;
			case "lowroll_ranged": tempBuffType = buffType.RANGED_LOW;
			break;
			case "lowroll_magic": tempBuffType = buffType.MAGIC_LOW;
			break;
			case "lowroll_buff": tempBuffType = buffType.BUFF_LOW;
			break;
			case "lowroll_debuff": tempBuffType = buffType.DEBUFF_LOW;
			break;
			default: System.err.println("Can't recognize effectType in method readEffecs in class itemLoad");
			}
			
			effects.add(new Effect(effectType.BUFF_SELF, tempBuffType, decayType.NONE, 1, Integer.parseInt(powerAndType.get(0)), 0, 0, 0));
		}
		return effects;
	}
	
	private ArrayList<String> readLines(){
		Scanner scan = null;
		ArrayList<String> lines = new ArrayList<String>();
		try {
			scan = new Scanner(new File("src/data/" + readfile));
			while(scan.hasNext()) {
				lines.add(scan.nextLine());	
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		scan.close();
		return lines;
	}

}