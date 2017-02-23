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


    public static void writeToFile(String filename, HashMap<Integer, HashSet<Video>> cacheVideoSet){

        try (FileWriter fw = new FileWriter(filename); BufferedWriter bw = new BufferedWriter(fw); ) {
            bw.write(cacheVideoSet.size()+ "\n");

            for(Map.Entry<Integer, HashSet<Video>> entry : cacheVideoSet.entrySet()){
                bw.write(entry.getKey() + " ");
                for(Video video : entry.getValue()){
                    bw.write(video.id + " ");
                }
                bw.write(" \n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
