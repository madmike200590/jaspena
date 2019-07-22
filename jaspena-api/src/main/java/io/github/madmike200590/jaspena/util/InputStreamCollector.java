package io.github.madmike200590.jaspena.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class InputStreamCollector implements Callable<String>{
    
    private InputStream inputStream;

    public InputStreamCollector(InputStream is) {
        this.inputStream = is;
    }
   
    @Override
    public String call() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(this.inputStream));
        String line;
        StringBuilder bld = new StringBuilder();
        while((line = br.readLine()) != null) {
            bld.append(line).append(System.lineSeparator());
        }
        br.close();
        return bld.toString();
    }

}
