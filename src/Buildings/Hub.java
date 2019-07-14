package Buildings;

import Consumables.Oxygen;
import World.Building;

public class Hub extends Building{

	public Hub() {
		super("/Assets/Conveyor_01.png", 1, true);
		super.addConsumable(new Oxygen(-1));
	}
	
}
