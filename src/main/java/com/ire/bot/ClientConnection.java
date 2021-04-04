package com.ire.bot;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientConnection implements Runnable {

    private AtomicBoolean running;
    private final Thread thread;

    private final String ip;
    private final int port;
    private Socket clientSocket;
    private PrintStream out;
    private InputStream in;

    public ClientConnection(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.running = new AtomicBoolean(true);
        this.thread = new Thread(this);
        thread.start();
    }

    public void end() {
        try {
            in.close();
            clientSocket.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        running.set(false);
        System.err.println("Exiting...");
        System.out.println("Ended connection.");
        System.exit(0);
        thread.interrupt();
    }

    /*public String sendMessage(String msg) {

        out.println(msg);
        *//*try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }*//*
        return "Error.";
    }*/

    @Override
    public void run() {

        while (running.get()) {

            try {
                clientSocket = new Socket(ip, port);
                out = new PrintStream(clientSocket.getOutputStream(), true);
                in = new BufferedInputStream(clientSocket.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                System.setOut(out);
                System.setIn(in);

                String inputLine;
                while ((inputLine = reader.readLine()) != null) {
                    if (inputLine.equals("TERMINATE_CLIENT")) {
                        System.out.println("Ended connection.");
                        end();
                        break;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        end();
    }
}
