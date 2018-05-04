package ru.mvawork.sortfile;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class LowMemorySort {

    private static final int MAX_SIZE = 1024 * 1024 * 16;

    public static void main(String[] args) throws IOException {
        File out = new File("dataout.txt");
        if (out.exists())
            out.delete();
        doSort(new File("datain.txt"), out);
    }


    private static void doSort(File inputFile, File outputFile) throws IOException {
        Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(inputFile), MAX_SIZE));
        scanner.useDelimiter("\n");
        if (inputFile.length() > MAX_SIZE && scanner.hasNext()) {
            System.out.print("-");
            File lowerFile = File.createTempFile("quicksort-", "-lower.tmp", new File("."));
            File greaterFile = File.createTempFile("quicksort-", "-greater.tmp", new File("."));
            PrintStream lower = createPrintStream(lowerFile);
            PrintStream greater = createPrintStream(greaterFile);
            PrintStream target;
            String pivot = scanner.next();
            while (scanner.hasNext()) {
                String current = scanner.next();
                if (current.compareTo(pivot) < 0) {
                    target = lower;
                } else {
                    target = greater;
                }
                target.printf("%s\n",current);
            }
            greater.printf("%s\n", pivot );
            scanner.close();
            lower.close();
            greater.close();
            doSort(lowerFile, outputFile);
            lowerFile.delete();
            doSort(greaterFile, outputFile);
            greaterFile.delete();
        } else {
            System.out.print(".");
            List<String> smallFile= new ArrayList<>();
            while (scanner.hasNext()) {
                smallFile.add(scanner.next());
            }
            scanner.close();
            Collections.sort(smallFile);
            PrintStream out = createPrintStream(outputFile);
            for (String s : smallFile) {
                out.printf("%s\n", s);
            }
            out.close();
        }
    }

    private static PrintStream createPrintStream(File file) throws IOException {
        return new PrintStream(new BufferedOutputStream(new FileOutputStream(file, true)));
    }
}