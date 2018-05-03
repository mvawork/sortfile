package ru.mvawork.sortfile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class SortFile {

    public static void main(String[] args) {
        String fileName = "datain.txt";
        try (Stream<String> stream = Files.lines(Paths.get(fileName));
             PrintWriter pw = new PrintWriter(Files.newBufferedWriter(
                     Paths.get("dataout.txt")))) {
            stream.sorted().forEach(pw::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
