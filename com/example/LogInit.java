package com.example;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogInit {
    private FileWriter writer;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public void initializeLog(String fileName) throws IOException {
        writer = new FileWriter(fileName);
    }

    public void logMaker(String message) {
        try {
            String log = "[" + LocalDateTime.now().format(formatter) + "] " + message + "\n";
            writer.write(log);
            System.out.print(log);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeLog() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}