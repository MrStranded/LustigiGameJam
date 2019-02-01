package Logic;

public class ComponentGen {

	public static void placeShip(int playerId, int typeId, WorldState worldState) {
		Component ship = new Component();
		ship.setId(typeId);
		ship.setPosition(new Position(10,10));

		ship.set(Attributes.PLAYER, playerId);
		ship.set(Attributes.ACCELERATION, 0.01d);

		worldState.addUnit(ship);
	}

}
