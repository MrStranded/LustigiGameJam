package Logic;

import Globals.MasterSwitch;
import Graphics.Window;
import Graphics.Gui.GUI;
import Graphics.Gui.GUICreater;
import Loader.ImageLoader;
import Translater.Parser;
import Translater.Sender;

public class GameLoop extends Thread {

    private Window window;
    private WorldState worldState;
    private GUI gui;

    private long t;
    private long tLastPlayerListSend = 0;
    private long millisPerFrame = 1000 / 60;

    public GameLoop() {
        window = new Window();

        worldState = new WorldState();

        Sender.setWorldState(worldState);
        Parser.setWorldState(worldState);
        Parser.setScreen(window.getScreen());

	    ImageLoader.loadImages();
    }

    @Override
    public void run() {
        while (true) {
            t = System.currentTimeMillis();

            if (MasterSwitch.isServer) {
                if (System.currentTimeMillis() - tLastPlayerListSend > 1000) {
                    Sender.sendPlayers();
                    tLastPlayerListSend = System.currentTimeMillis();
                }
            }

            Parser.update();

            if (worldState.isGameRunning()) {
                Logic.locigIteration(worldState);
            }

            window.registerInput(worldState);
            window.drawWorld(worldState);

            long waitTime = millisPerFrame - (System.currentTimeMillis() - t);
            if (waitTime > 0) {
                try {
                    sleep(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
