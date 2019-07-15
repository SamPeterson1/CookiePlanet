package Buildings;

import Consumables.Oxygen;
import Consumables.Power;
import World.Building;

public class Greenhouse extends Building {
	
	public Greenhouse() {
		
		super(3, 2, "/Assets/Greenhouse.png", 2, false);
		super.addConsumable(new Power(2));
		super.addConsumable(new Oxygen(10));
		
	}
	
}
