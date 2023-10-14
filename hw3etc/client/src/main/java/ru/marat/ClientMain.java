package ru.marat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

@SpringBootApplication
public class ClientMain implements CommandLineRunner {
    private final String host;
    
    private final int port;
    
    public ClientMain(@Value("${host:localhost}") String host, @Value("${port:8080}") int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Клиент начал работу");
        String command = "";
        while (!"/exit".equals(command)) {
            try (Socket socket = new Socket(host, port);
                 BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                startCommunicatingWithServer(scanner, out, in);
                return;
            } catch (IOException | NoSuchElementException e) {
                System.out.printf("Нет соединения с сервером %s%n", e);
                System.out.println(
                        "Если хотите выйти напишите /exit. Если хотите переподключиться к серверу нажмите enter");
                command = scanner.nextLine();
            }
        }
    }

    private static void startCommunicatingWithServer(Scanner scanner, BufferedWriter out, BufferedReader in) throws IOException {
        System.out.println("Соединение с сервером установлено. Можете вводить команды");
        String line = scanner.nextLine();
        while (!"/exit".equals(line)) {
            out.write(line);
            out.newLine();
            out.flush();
            String response;
            while (!(response = in.readLine()).isEmpty()) {
                System.out.println(response);
            }
            line = scanner.nextLine();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientMain.class, args);
    }
}