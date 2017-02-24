package com.fererlab;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Minutes15 {
    public static void main(String[] args) throws Exception {
        new Minutes15().start();
    }

    private void start() throws Exception {

        for (String fileName : Arrays.asList("me_at_the_zoo", "trending_today", "videos_worth_spreading", "kittens")) {

            URI uri = App.class.getClassLoader().getResource(fileName + ".in").toURI();
            List<String> lines = Files.readAllLines(Paths.get(uri));

            int currentLine = 0;
            String line0 = lines.get(currentLine++);
            String[] split0 = line0.split(" ");
            int numberOfVideos = Integer.parseInt(split0[0]);
            int numberOfEndpoints = Integer.parseInt(split0[1]);
            int numberOfRequestDescriptions = Integer.parseInt(split0[2]);
            int numberOfCaches = Integer.parseInt(split0[3]);
            int initialCacheSize = Integer.parseInt(split0[4]);


            Cache[] caches = new Cache[numberOfCaches];
            for (int i = 0; i < numberOfCaches; i++) {
                caches[i] = new Cache(initialCacheSize);
            }

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
                    Integer cacheIndex = Integer.parseInt(epL[0]);
                    Integer cacheLatency = Integer.parseInt(epL[1]);
                    endpoints[i].cacheIndexLatencyMap.put(cacheIndex, cacheLatency);
                }
            }


            List<Request> requests = new ArrayList<>();
            for (int i = 0; i < numberOfRequestDescriptions; i++) {
                String videoEpReqString = lines.get(currentLine++);
                String[] videoEpReqSplit = videoEpReqString.split(" ");
                int videoIndex = Integer.parseInt(videoEpReqSplit[0]);
                int endPointIndex = Integer.parseInt(videoEpReqSplit[1]);
                int numberOfRequests = Integer.parseInt(videoEpReqSplit[2]);
                if (videos[videoIndex].size <= initialCacheSize) {
                    Request request = new Request(videoIndex, videos[videoIndex], endPointIndex, numberOfRequests);
                    requests.add(request);
                    endpoints[request.endPointIndex].requests.add(request);
                }
            }


            ArrayList<Video> videoList = new ArrayList<>();
            videoList.addAll(Arrays.asList(videos));
            ArrayList<Cache> cacheList = new ArrayList<>();
            cacheList.addAll(Arrays.asList(caches));

            List<Cache> cacheList2 = solve15(videoList, cacheList);

            List<String> writeLines = new ArrayList<>();
            writeLines.add("" + cacheList2.size());
            for (int i = 0; i < cacheList2.size(); i++) {
                String line = i + " " + cacheList2.get(i).videos.stream().map(v -> "" + v).collect(Collectors.joining(" "));
                writeLines.add(line);
            }
            Files.write(Paths.get(fileName + ".out"), writeLines);

        }

    }

    private List<Cache> solve15(List<Video> videos, List<Cache> caches) {
        List<Cache> cacheList = new ArrayList<>();
        try {
            for (int i = 0; i < videos.size(); i++) {
                Video video = videos.get(i);
                Cache cache = caches.get(0);
                while (cache.cacheSize < video.size) {
                    caches.remove(0);
                    cache = caches.get(0);
                }
                cache.videos.add(i);
                cache.cacheSize = cache.cacheSize - video.size;
                if (!cacheList.contains(cache)) {
                    cacheList.add(cache);
                }
            }
        } catch (Exception omit) {
        }
        return cacheList;
    }


}
