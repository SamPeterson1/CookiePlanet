package World;

public class Consumable {
	
	int ID;
	int amount;
	int production;
	int max;
	String name;
	boolean tetherable;
	
	public Consumable(int ID, String name, int production, int max, boolean tetherable) {
		this.ID = ID;
		this.name = name;
		this.tetherable = tetherable;
		this.amount = 0;
		this.production = production;
		this.max = max;
	}
	
	public boolean tick() {
		System.out.println(this.production);
		return this.setAmount(this.production + this.amount) == 0;
	}
	
	public int setAmount(int amount) {
		if(amount <= this.max) {
			this.amount = amount;
			return 0;
		} else {
			this.amount = this.max;
			return amount - this.max;
		}
	}
	
	public int removeAmount(int amount) {
		return this.setAmount(this.amount - amount);
	}
	
	public int addAmount(int amount) {
		return this.setAmount(this.amount + amount);
	}

	public int getProduction() {
		return this.production;
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean isTetherable() {
		return this.tetherable;
	}
	
}
