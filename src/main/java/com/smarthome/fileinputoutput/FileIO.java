package com.smarthome.fileinputoutput;

import java.io.*;

public class FileIO {

    public static void writeToFile(String path, String src) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
        bufferedWriter.write(src);
        bufferedWriter.close();
    }

    public static String readAllFromFile(String path) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader(path));
        String str = "";
        String temp;
        while ((temp = bufferedReader.readLine()) != null)
            str = str + temp + "\n";
        bufferedReader.close();
        return str;
    }

    public static void writeToFile(String path, String src, boolean append) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, append));
        bufferedWriter.write(src);
        bufferedWriter.close();
    }
}

