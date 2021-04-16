package com.ire.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

public class SaveData {

    public void Read(String path) {

        path = path + ".txt";

        try {

            ClassLoader loader = this.getClass().getClassLoader();
            InputStream stream = loader.getResourceAsStream(path);
            Scanner myReader = new Scanner(Objects.requireNonNull(stream));

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();

        } catch (NullPointerException e) {

            System.out.println("An error occurred. Could not read file.");
            e.printStackTrace();
            PrintControl.sleep(1000);
        }
    }

    public void Create() {

        try {

            File f = new File("SaveFile.txt");

            if (f.createNewFile()) {
                System.out.println("File created: " + f.getName());
                PrintControl.sleep(1000);
                return;
            }
            System.out.println("File already exists.");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred. Could not create file.");
            PrintControl.sleep(1000);
        }
    }

    public void Write(String d) {

        try {

            FileWriter myWriter = new FileWriter("SaveFile.txt");
            myWriter.write(d);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
            PrintControl.sleep(1000);

        } catch (IOException e) {
            System.out.println("An error occurred. Could not write to file.");
            e.printStackTrace();
            PrintControl.sleep(1000);
        }
    }
}
