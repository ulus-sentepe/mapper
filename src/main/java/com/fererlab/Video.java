package com.fererlab;

public class Video implements DataType {
    @Key
    Integer size;

    public Video(Integer videoSize) {
        this.size = videoSize;
    }
}
