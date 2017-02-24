package com.fererlab;

import java.util.*;

public class Cache implements DataType {
    @Key
    Integer index;
    Integer cacheSize;
    public List<Request> requests = new ArrayList<>();
    public Map<Video, List<Request>> videoRequestMap = new HashMap<>();
    public Video videoToBeCached;
    public int videoIndex;

    List<Integer> list = new ArrayList<>();

    public List<Integer> videos = new ArrayList<>();

    public Cache(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    public void cacheSelectedVideo(){
          list.add(videoIndex);
        cacheSize=cacheSize-videoToBeCached.size;
        List<Video> list =new ArrayList<Video>(videoRequestMap.keySet());
        for (Video v :list){
            if(v==videoToBeCached || v.size>cacheSize){
                List<Request> l2=videoRequestMap.get(v);
                for(Request r : l2){
                                  if(requests.contains(r)){
                                      requests.remove(r);
                                  }
                }
                videoRequestMap.remove(v);
            }
        }
    }

    public int getMaxRequestSumByVideo() {
        int maxVideoRequests = 0;
        Iterator<Video> iterator = this.videoRequestMap.keySet().iterator();
        while (iterator.hasNext()) {
            Video video = iterator.next();
            if (this.cacheSize >= video.size) {
                List<Request> requestList = this.videoRequestMap.get(video);
                int numberOfRequests = 0;
                int videoIndex = 0;
                for (Request request : requestList) {
                    numberOfRequests = numberOfRequests + request.numberOfRequests;
                    videoIndex = request.videoIndex;
                }
                if(numberOfRequests>maxVideoRequests) {
                    maxVideoRequests = numberOfRequests;
                    this.videoToBeCached=video;
                    this.videoIndex=videoIndex;
                }

            } else {
                List<Request> requests = this.videoRequestMap.get(video);
                this.requests.removeAll(requests);
                iterator.remove();

            }
        }

        return maxVideoRequests;
    }
}
