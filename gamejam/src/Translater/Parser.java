package Translater;

import Logic.WorldState;

public class Parser {

	private static WorldState worldState;

	public static void parse(String msg) {
		if (worldState != null) {
			String[] infos = msg.split(Separator.INFO);
			for (String info : infos) {
				parseInfo(info);
			}
		} else {
			System.out.println("Cannot parse, because there is no world state.");
		}
	}

	private static void parseInfo(String msg) {

	}

}
