package Buildings;

import Consumables.Oxygen;
import Consumables.Power;
import World.Building;

public class Greenhouse extends Building {
	
	public Greenhouse() {
		
		super("/Assets/East_01.png", 2, false);
		super.addConsumable(new Power(1));
		super.addConsumable(new Oxygen(2));
		
	}
	
}
