package ru.marat;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

@Log4j2
public class BufferedReaderWithLog extends BufferedReader {
    public BufferedReaderWithLog(Reader reader) {
        super(reader);
    }

    @Override
    public String readLine() throws IOException {
        var line = super.readLine();
        log.info("Получено сообщение: {}", line);
        return line;
    }
}
