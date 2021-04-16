package com.ire;

import com.ire.audio.AudioClip;
import com.ire.bot.ClientConnection;
import com.ire.tools.PrintControl;
import com.ire.tools.SaveData;
import com.ire.world.Arena;

public class Main {

    public static void main(String[] args) {

        ClientConnection connect = null;

        if ((args.length > 2) && args[0].equals("useBot")) {
            connect = new ClientConnection(args[1], Integer.parseInt(args[2]));
            PrintControl.setBotClient(true);
            PrintControl.sleep(3000);

        } else if (args.length > 0) {
            System.err.println("Passed arguments are insufficient to create client.");
            for (int i = 0; i < args.length; i++) {
                System.out.println("Arg " + i + ": " + args[i]);
            }
            PrintControl.sleep(3000);

        } else {
            displayTitleScreen();
        }

        Arena a = new Arena();
        a.startArenaLoop();


        if ((args.length > 2) && args[0].equals("useBot")) {
            assert connect != null;
            connect.end();
        }
    }

    protected static void displayTitleScreen() {
        PrintControl.clear();

        AudioClip start = new AudioClip("woosh");
        start.play();
        PrintControl.sleep(500);
        SaveData s = new SaveData();
        s.Read("startArt");
        PrintControl.sleep(300);

        System.out.println("Press ENTER to begin...");
        PrintControl.emptyPrompt();
        PrintControl.clear();
        start.end();
    }

}
