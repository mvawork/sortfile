package ru.mvawork.sortfile;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Collectors;

public class GenerateFile {

    public static void main(String[] args) {
        String symbols = "1234567890abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        long rowCount = (long) Math.pow(10, 6);

        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(Paths.get("datain.txt")))) {
            for (long i = 0; i < rowCount; i++) {
                int size = random.nextInt(4000) + 1;
                pw.println(new Random().ints(size, 0, symbols.length())
                        .mapToObj(symbols::charAt)
                        .map(Object::toString)
                        .collect(Collectors.joining()));
                if ((i+1) % 100000 == 0)
                    System.out.println("Сгенерировано " + (i+1));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
