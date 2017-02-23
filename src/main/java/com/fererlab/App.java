package com.fererlab;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        App app = new App();
//        app.withKey();
//        app.withOutKey();
        app.hc2016();
    }

    private void hc2016() {

    }

    private void withOutKey() {
        Mapper mapper = new Mapper();
        try {
            URI uri = App.class.getClassLoader().getResource("example.in").toURI();
            List<String> lines = Files.readAllLines(Paths.get(uri));
            Map<Class<? extends DataType>, List<DataType>> map = mapper.mapWithOutKey(lines, new ArrayList<Class<? extends DataType>>() {{
                        add(DataTypeFirst.class);
                        add(DataTypeSecond.class);
                    }},
                    null);
            System.out.println(map);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void withKey() {
        Mapper mapper = new Mapper();
        try {
            URI uri = App.class.getClassLoader().getResource("example.in").toURI();
            List<String> lines = Files.readAllLines(Paths.get(uri));
            Map<Class<? extends DataType>, Map<Object, DataType>> map = mapper.mapWithKey(lines, new ArrayList<Class<? extends DataType>>() {{
                        add(DataTypeFirst.class);
                        add(DataTypeSecond.class);
                    }},
                    null);
            System.out.println(map);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
