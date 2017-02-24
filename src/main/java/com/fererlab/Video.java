package com.fererlab;

public class Video implements DataType {
    
	@Key
	int id;
	
	Integer size;

    public Video(int id,Integer videoSize) {
        this.id = id;
    	this.size = videoSize;
    }
}
