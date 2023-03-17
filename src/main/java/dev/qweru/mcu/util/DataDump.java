package dev.qweru.mcu.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataDump {

    protected File file;
    protected String rememberContent;

    public DataDump(String src, String content) {
        this.rememberContent = "{"+content+"}";
        this.file = new File(src);
        try {
            FileWriter fileWriter = new FileWriter(src);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            Chat.sendMessage("An error occured while dumping data (IOException)");
        }
    }

    public DataDump(String src) {
        this.rememberContent = "";
        this.file = new File(src);
    }

    public void addData(String content) {
        try {
            this.rememberContent+="{"+content+"}";
            FileWriter fileWriter = new FileWriter(this.file.getPath());
            fileWriter.write(rememberContent);
            fileWriter.close();
        } catch (IOException e) {
            Chat.sendMessage("An error occured while dumping data (IOException)");
        }
    }



}
