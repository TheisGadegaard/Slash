package controllers;
import java.util.ArrayList;

public class Roll {

	ArrayList<Effect> effects;
	
	public Roll(){
		this.effects = new ArrayList<Effect>();
	}
	
	public ArrayList<Effect> getEffects(){
		return this.effects;
	}
	
	public void add(Effect effect) {
		this.effects.add(effect);
	}
	
	
}
