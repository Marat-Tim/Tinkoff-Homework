package ru.marat;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    private static String getHost(String[] args) {
        if (args.length == 1) {
            return args[0];
        } else if (args.length == 0) {
            return "localhost";
        }
        throw new IllegalArgumentException("Неправильное количество аргументов");
    }

    private static int getPort(String[] args) {
        if (args.length == 2) {
            return Integer.parseInt(args[1]);
        } else if (args.length <= 1) {
            return 8080;
        }
        throw new IllegalArgumentException("Неправильное количество аргументов");
    }

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket(getHost(args), getPort(args));
             PrintStream out = new PrintStream(socket.getOutputStream());
             Scanner in = new Scanner(socket.getInputStream())) {
            System.out.println("Клиент начал работу");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                out.println(line);
                out.flush();
                String response;
                while (!(response = in.nextLine()).isEmpty()) {
                    System.out.println(response);
                }
            }
        }
    }
}