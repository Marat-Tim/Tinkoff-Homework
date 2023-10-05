package ru.marat;

import java.io.OutputStream;
import java.io.PrintStream;

public class MyPrintStream extends PrintStream {
    public MyPrintStream(OutputStream out) {
        super(out);
    }

    @Override
    public void flush() {
        println();
        super.flush();
    }
}
