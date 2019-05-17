package com.example.assignment_1.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileLoader {

    public ArrayList<String[]> loadFile(BufferedReader br) throws IOException{

        ArrayList<String[]> data = new ArrayList<String[]>();

        String line = null;
        while((line = br.readLine()) != null){
            if(!line.startsWith("//")) {
                String txtInfo[] = line.split(",\"");
                for (int i = 0; i < txtInfo.length; i++) {
                    txtInfo[i] = txtInfo[i].replaceAll("\"", "");
                }
                data.add(txtInfo);
            }
        }
        return data;
    }
}
