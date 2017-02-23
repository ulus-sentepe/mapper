package com.fererlab;

import java.util.ArrayList;
import java.util.List;

public class Cache implements DataType {
    @Key
    Integer index;
    Integer capacity;
    List<Video> videos = new ArrayList<>();
    
    public boolean put(SolverData solverData){
    	if(index != solverData.cacheId){
    		return false;
    	}
    	if ((capacity<solverData.getVideo().size)){
    		return false;
    	}
    	capacity = capacity - solverData.getVideo().size;
    	videos.add(solverData.getVideo());
    	return true;
    } 
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append(index);
    	for(Video v : videos){
    		sb.append(" ");
    		sb.append(v.id);
    		
    		
    		
    	}
    	return sb.toString();
    }
}
