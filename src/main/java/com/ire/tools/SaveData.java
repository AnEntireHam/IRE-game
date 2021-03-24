package com.ire.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// TODO: Remove extraneous comments
public class SaveData {

    public void Read(String name) {

        try {
            //String path = name;

            File f = new File(name);
            Scanner myReader = new Scanner(f);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            //System.out.println("No save data found. Creating file...");
            //Create();
            System.out.println("File not found.");
            Tools.sleep(1000);
        }
    }

    public void Create() {

        try {
            //String path = "SaveFile.txt";
            File f = new File("SaveFile.txt");

            if (f.createNewFile()) {
                System.out.println("File created: " + f.getName());
                Tools.sleep(1000);
            } /*else {
                System.out.println("File already exists.");
            }*/

        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("An error occurred. Could not create file.");
            Tools.sleep(1000);
        }
    }

    public void Write(String d) {

        try {
            //String path = "SaveFile.txt";
            FileWriter myWriter = new FileWriter("SaveFile.txt");
            myWriter.write(d);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
            Tools.sleep(1000);

        } catch (IOException e) {
            System.out.println("An error occurred. Could not write to file.");
            //e.printStackTrace();
            Tools.sleep(1000);
        }
    }
}

//String desktopPath = System.getProperty("user.home") + "/Desktop\\" + "SaveFile.txt";
//String path = (desktopPath.replace("\\", "/"));