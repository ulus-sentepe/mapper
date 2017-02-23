package com.fererlab;

public class Request implements DataType {
    Integer videoIndex;
    Video video;
    Integer endPointIndex;
    Integer numberOfRequests;

    public Request(Integer videoIndex, Video video, Integer endPointIndex, Integer numberOfRequests) {
        this.videoIndex = videoIndex;
        this.video = video;
        this.endPointIndex = endPointIndex;
        this.numberOfRequests = numberOfRequests;
    }
}
