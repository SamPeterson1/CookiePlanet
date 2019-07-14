package World;

import java.util.ArrayList;

public class BaseNetwork {
	
	ArrayList<Building> buildings;
	ArrayList<Consumable> consumables;
	
	public BaseNetwork() {
		this.buildings = new ArrayList<Building>();
		this.consumables = new ArrayList<Consumable>();
	}
	
	public void transferResource(String name) {
		int surplus = 0;
		float leftOver = 0;
		float totalGiven = 0;
		int sumConsumption = 0;
		int numNonConsuming = 0;
		for(Building b: this.buildings) {
			Consumable c = b.getConsumable(name);
			if(b.linked()) {
				if(c.getProduction() >= 0) {
					surplus += c.getAmount();
					numNonConsuming ++;
				} else {
					sumConsumption -= c.getProduction();
				}
			}
		}
		
		for(Building b: this.buildings) {
			Consumable c = b.getConsumable(name);
			if(c.getProduction() < 0) {
				int consumption = -(c.getProduction());
				float given = surplus*consumption/sumConsumption;
				totalGiven += given;
				leftOver += c.addAmount((int)(given));
			}
		}
		
		for(Building b: this.buildings) {
			Consumable c = b.getConsumable(name);
			if(c.getProduction() >= 0) {
				c.addAmount((int) ((leftOver-totalGiven)/numNonConsuming));
			}
		}
	}
	
	public void addBuilding(Building b) {
		this.buildings.add(b);
		for(Consumable c: b.getConsumables()) {
			if(!this.consumables.contains(c)) {
				this.consumables.add(c);
			}
		}
	}
	
}