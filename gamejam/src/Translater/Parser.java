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
		int sep = msg.indexOf(Separator.KEYWORD);

		if (sep >= 0) {
			String keyword = msg.substring(0, sep);
			String[] values = (msg.substring(sep+1)).split(Separator.VALUE);

			KeyWord key = getFittingKeyWord(keyword);

			switch (key) {
				case MAP:
					int s = Integer.parseInt(values[0]);

					int[][] newMap = new int[s][s];

					//for ()
					break;
			}
		}
	}

	private static KeyWord getFittingKeyWord(String keyword) {
		for (KeyWord keyWord : KeyWord.values()) {
			if (keyWord.name().equals(keyword)) {
				return keyWord;
			}
		}
		return null;
	}

}
