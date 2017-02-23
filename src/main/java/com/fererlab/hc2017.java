package com.fererlab;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class hc2017 {
    public static void main(String[] args) throws Exception {
        new hc2017().start();
    }

    private void start() throws Exception {
        URI uri = App.class.getClassLoader().getResource("example.in").toURI();
        List<String> lines = Files.readAllLines(Paths.get(uri));

        int currentLine = 0;
        String line0 = lines.get(currentLine++);
        String[] split0 = line0.split(" ");
        int numberOfVideos = Integer.parseInt(split0[0]);
        int numberOfEndpoints = Integer.parseInt(split0[1]);
        int numberOfRequestDescriptions = Integer.parseInt(split0[2]);
        int numberOfCaches = Integer.parseInt(split0[3]);
        int initialCacheSize = Integer.parseInt(split0[4]);


        String line1 = lines.get(currentLine++);
        String[] videoSizes = line1.split(" ");
        Video[] videos = new Video[numberOfVideos];
        for (int i = 0; i < numberOfVideos; i++) {
            Integer videoSize = Integer.parseInt(videoSizes[i]);
            videos[i] = new Video(videoSize);
        }


        Endpoint[] endpoints = new Endpoint[numberOfEndpoints];
        for (int i = 0; i < numberOfEndpoints; i++) {
            String dcLatencyEPnumber = lines.get(currentLine++);
            String[] dcLepN = dcLatencyEPnumber.split(" ");
            Integer dcLatency = Integer.parseInt(dcLepN[0]);
            Integer epNumbers = Integer.parseInt(dcLepN[1]);
            endpoints[i] = new Endpoint(dcLatency, epNumbers);
            for (int j = 0; j < epNumbers; j++) {
                String epLatencySplit = lines.get(currentLine++);
                String[] epL = epLatencySplit.split(" ");
                Integer epIndex = Integer.parseInt(epL[0]);
                Integer epLatency = Integer.parseInt(epL[1]);
                endpoints[i].epMap.put(epIndex, epLatency);
            }
        }


        List<Request> requests = new ArrayList<>();
        for (int i = 0; i < numberOfRequestDescriptions; i++) {
            String videoEpReqString = lines.get(currentLine++);
            String[] videoEpReqSplit = videoEpReqString.split(" ");
            int videoIndex = Integer.parseInt(videoEpReqSplit[0]);
            int endPointIndex = Integer.parseInt(videoEpReqSplit[1]);
            int numberOfRequests = Integer.parseInt(videoEpReqSplit[2]);
            Request request = new Request(videoIndex, videos[videoIndex], endPointIndex, numberOfRequests);
            requests.add(request);
            endpoints[request.endPointIndex].requests.add(request);
        }




    }

}