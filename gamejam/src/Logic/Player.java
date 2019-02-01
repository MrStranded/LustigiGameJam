package Logic;

public class Player {

	private String name;
	private int id;
	private int cash;
	private int ping;

	public int getPing() {
		return ping;
	}

	public void setPing(int ping) {
		this.ping = ping;
	}

	public Player(int id) {
		this.id = id;
		name = "MickeyDonaldPhantom";
		cash = 100;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	public String getName() {

		return name;
	}

	public int getId() {
		return id;
	}
}
