package Logic;

import Globals.Components;

public class ComponentGen {

	public static void placeShip(int playerId, int typeId, WorldState worldState) {
		Component ship = Components.copyOriginal(typeId);
		ship.setPosition(new Position(Math.random() * worldState.getSize(),Math.random() * worldState.getSize()));

		ship.set(Attributes.PLAYER, playerId);
		ship.set(Attributes.ACCELERATION, 0.01d);
		ship.set(Attributes.MAXSPEED, 2);
		ship.set(Attributes.TURNANGLE, 1);

		worldState.addUnit(ship);
	}

}
