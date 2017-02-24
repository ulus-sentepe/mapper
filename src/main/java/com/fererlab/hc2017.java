package com.fererlab;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class hc2017 {
    public static void main(String[] args) throws Exception {
        new hc2017().start();
    }

    private void start() throws Exception {
        URI uri = App.class.getClassLoader().getResource("me_at_the_zoo.in").toURI();
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


        System.out.println();


        Map<Integer, Set<Integer>> integerSetMap = solve1(
                numberOfVideos,
                numberOfEndpoints,
                numberOfRequestDescriptions,
                numberOfCaches,
                initialCacheSize,
                Arrays.asList(videos),
                Arrays.asList(endpoints),
                requests,
                Arrays.asList(caches)
        );
        System.out.println(integerSetMap);

    }

    private Map<Integer, Set<Integer>> solve1(int numberOfVideos,
                                              int numberOfEndpoints,
                                              int numberOfRequestDescriptions,
                                              int numberOfCaches,
                                              int initialCacheSize,
                                              List<Video> videos,
                                              List<Endpoint> endpoints,
                                              List<Request> requests,
                                              List<Cache> caches) {

        Map<Integer, Set<Integer>> cacheIndexVideoIndexMap = new LinkedHashMap<>();

        for (Endpoint endpoint : endpoints) {
            for (Integer cacheIndex : endpoint.cacheIndexLatencyMap.keySet()) {
                Cache cache = caches.get(cacheIndex);
                cache.requests.addAll(endpoint.requests);
                for (Request request : endpoint.requests) {
                    if (!cache.videoRequestMap.containsKey(request.video)) {
                        cache.videoRequestMap.put(request.video, new ArrayList<>());
                    }
                    cache.videoRequestMap.get(request.video).add(request);
                }
            }
        }

        while (true) {

            int maxCacheVideoRequests = -1;
            int maxCacheIndex = -1;
            int maxVideoIndex = -1;

            for (int i = 0; i < caches.size(); i++) {
                Cache cache = caches.get(i);
                int maxRequestSumByVideo = cache.getMaxRequestSumByVideo();
                if(maxRequestSumByVideo>maxCacheVideoRequests){
                    maxCacheVideoRequests=maxRequestSumByVideo;
                    maxCacheIndex=i;
                }
            }

            if (maxCacheIndex != -1) {
                Cache cache = caches.get(maxCacheIndex);
                cache.cacheSelectedVideo();

            } else {
                System.out.println("bitti");
                break;
            }
        }

        return cacheIndexVideoIndexMap;
    }

}
