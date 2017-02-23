package com.fererlab;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

/**
 * Created by selcuk on 23.02.2017.
 */
public class Writer {

    public static final String FILENAME = "output.txt";

    public static void main(String[] args) {

        HashSet<Integer>[] setArr = new HashSet[2];
        setArr[0] = new HashSet<>();
        setArr[0].add(1);
        setArr[0].add(2);
        setArr[0].add(3);

        setArr[1] = new HashSet<>();
        setArr[1].add(6);
        setArr[1].add(7);
        setArr[1].add(8);

        writeToFile(setArr);
    }

    public static void writeToFile(HashSet<Integer>[] cacheVideoSet){

        try (FileWriter fw = new FileWriter(FILENAME); BufferedWriter bw = new BufferedWriter(fw); ) {
            bw.write(cacheVideoSet.length+ "\n");

            int cacheIdx = 0;
            for(HashSet<Integer> videos : cacheVideoSet){
                bw.write(cacheIdx + " ");
                for(Integer integer : videos){
                    bw.write(integer + " ");
                }
                bw.write(" \n");
                cacheIdx++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
