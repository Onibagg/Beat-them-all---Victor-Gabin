package com.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogInit {
    public FileWriter writer;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public void initializeLog(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs(); // Create directories if they do not exist
        }
        writer = new FileWriter(file);
    }

    public void logMaker(String message) {
        try {
            if (writer == null) {
                throw new IllegalStateException("LogInit is not initialized. Call initializeLog() first.");
            }
            String log = "[" + LocalDateTime.now().format(formatter) + "] " + message + "\n";
            String logTerm = message + "\n";

            writer.write(log);
            System.out.print(logTerm);
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