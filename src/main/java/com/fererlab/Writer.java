package com.fererlab;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by selcuk on 23.02.2017.
 */
public class Writer {

    public static void main(String[] args) {

        HashMap<Integer, HashSet<Integer>> cacheVideoSet = new HashMap<>();
        HashSet<Integer> set1 = new HashSet<>();
        set1.add(1);
        set1.add(3);
        set1.add(5);

        cacheVideoSet.put(0,set1);

        HashSet<Integer> set2 = new HashSet<>();
        set2.add(2);

        cacheVideoSet.put(2,set2);

        writeToFile("output.txt", cacheVideoSet);
    }

    public static void writeToFile(String filename, HashMap<Integer, HashSet<Integer>> cacheVideoSet){

        try (FileWriter fw = new FileWriter(filename); BufferedWriter bw = new BufferedWriter(fw); ) {
            bw.write(cacheVideoSet.size()+ "\n");

            for(Map.Entry<Integer, HashSet<Integer>> entry : cacheVideoSet.entrySet()){
                bw.write(entry.getKey() + " ");
                for(Integer integer : entry.getValue()){
                    bw.write(integer + " ");
                }
                bw.write(" \n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
