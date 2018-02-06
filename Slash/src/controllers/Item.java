package controllers;
import java.util.ArrayList;

import enums.itemType;

public class Item {

	private String name;
	private int value;
	private itemType itemType;
	private ArrayList<Effect> effects;
	
	public Item(String name, itemType itemType, int value, ArrayList<Effect> effects){
		this.name = name;
		this.value = value;
		this.itemType = itemType;
		this.effects = effects;		
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public itemType getItemType() {
		return itemType;
	}

	public ArrayList<Effect> getEffects() {
		return effects;
	}
}
