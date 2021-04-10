package com.ire.bot;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

public class ClientConnection implements Runnable {
    private final String ip;
    private final int port;
    private Socket clientSocket;
    private PrintStream out;
    private InputStream in;

    public ClientConnection(String ip, int port) {
        this.ip = ip;
        this.port = port;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void end() {
        System.out.println("TERMINATE_CLIENT");
        try {
            in.close();
            clientSocket.close();
            out.close();

        } catch (NullPointerException | IOException e) {
            System.exit(-1);
        }
        System.exit(0);
    }


    @Override
    public void run() {
        try {
            BufferedReader reader = connect();
            readInputs(reader);

        } catch (ConnectException e) {
            System.err.println("Lost connection. Terminating.");

        } catch (IOException e) {
            e.printStackTrace();
        }
        end();
    }

    private BufferedReader connect() throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintStream(clientSocket.getOutputStream(), true, "UTF-8");
        in = new BufferedInputStream(clientSocket.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        System.setOut(out);
        System.setErr(out);
        System.setIn(in);
        return reader;
    }

    private void readInputs(BufferedReader reader) throws IOException {
        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            if (inputLine.equals("TERMINATE_CLIENT")) {
                System.out.println("Successfully ended game.");
                end();
                break;
            }
            out.println("REPEAT" + inputLine);
        }
    }
}
