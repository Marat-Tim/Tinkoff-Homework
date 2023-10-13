package ru.marat;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.marat.command.Command;
import ru.marat.io.BufferedReaderWithLog;
import ru.marat.io.ServerPrintStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;

@SpringBootApplication
@Log4j2
public class ServerMain implements CommandLineRunner {
    private final Map<String, Command> commands;

    private final int port;

    public ServerMain(Map<String, Command> commands, @Value("${port:8080}") int port) {
        this.commands = commands;
        this.port = port;
    }

    @Override
    public void run(String... args) {
        try (ServerSocket server = new ServerSocket(port)) {
            log.info("Сервер начал работу на сокете {}", server);
            processClients(server);
        } catch (IOException e) {
            log.error("Не получилось запустить серверный сокет", e);
        }
    }

    private void processClients(ServerSocket server) {
        //noinspection InfiniteLoopStatement
        while (true) {
            try (Socket client = server.accept();
                 ServerPrintStream writer = new ServerPrintStream(client.getOutputStream());
                 BufferedReader reader = new BufferedReaderWithLog(
                         new InputStreamReader(client.getInputStream()))) {
                log.info("Пользователь {} подключился", client);
                new CommandHandler(writer, reader, commands).start();
            } catch (NullPointerException | SocketException e) {
                log.info("Пропало соединение с клиентом");
            } catch (IOException e) {
                log.error(e);
            }
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerMain.class, args);
    }
}