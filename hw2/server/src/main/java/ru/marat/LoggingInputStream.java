package ru.marat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LoggingInputStream extends InputStream {
    private final InputStream inputStream;

    private final OutputStream loggingStream;

    public LoggingInputStream(InputStream inputStream, OutputStream loggingStream) {
        this.inputStream = inputStream;
        this.loggingStream = loggingStream;
    }

    @Override
    public int read() throws IOException {
        int data = inputStream.read();
        if (data != -1) {
            loggingStream.write(data);
        }
        return data;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int size = inputStream.read(b, off, len);
        if (size != -1) {
            loggingStream.write(b, off, size);
        }
        return size;
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
        super.close();
    }
}
