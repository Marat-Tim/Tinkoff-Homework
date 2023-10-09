package ru.marat.io;

import java.io.OutputStream;
import java.io.PrintStream;

public class ServerPrintStream extends PrintStream {
    public ServerPrintStream(OutputStream out) {
        super(out);
    }

    public void sendToClient() {
        println();
        flush();
    }
}
