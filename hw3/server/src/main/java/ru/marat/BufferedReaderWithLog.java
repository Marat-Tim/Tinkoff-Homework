package ru.marat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.net.Socket;

public class BufferedReaderWithLog extends BufferedReader {
    private final PrintStream out;

    private final Socket client;

    public BufferedReaderWithLog(Reader reader, PrintStream out, Socket client) {
        super(reader);
        this.out = out;
        this.client = client;
    }

    @Override
    public String readLine() throws IOException {
        var line = super.readLine();
        out.printf("Сообщение от клиента %s: %s%n", client, line);
        return line;
    }
}
