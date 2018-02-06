package controllers;
import java.util.ArrayList;
import enums.abilityType;

public class Ability {

	private String name;
	private int lowroll, highroll;		//the limits defining when an attack is lowroll, standart or highroll
	private int[] cost = new int[3];				//index: 0=Health, 1=Mana, 2=Energy
	private ArrayList<Roll> rolls = new ArrayList<Roll>();		//index: 0=lowroll, 1=standart, 2=highroll
	private abilityType abilityType;
	
	public Ability(String name, abilityType abilityType, int[] cost, int lowroll, int highroll, ArrayList<Roll> rolls){
		this.name = name;							//Name of ability
		this.abilityType = abilityType;				//The type of the ability
		this.cost = cost;							//The costs(health, mana, energy)
		this.lowroll = lowroll;						//Lowroll pool
		this.highroll = highroll;					//Highroll pool
		this.rolls = rolls;							//The three outcomes for lowroll, standart and highroll
	}
	
	public ArrayList<Roll> getRolls(){
		return rolls;
	}

	public int getLowroll() {
		return lowroll;
	}

	public int getHighroll() {
		return highroll;
	}
	
	public abilityType getabilityType() {
		return abilityType;
	}
	
	public int getCost(int costType){	//index: 0=Health, 1=Mana, 2=Energy
		return cost[costType];
	}

	public String getName() {
		return name;
	}
}
