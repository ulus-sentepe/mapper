package com.fererlab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Endpoint implements DataType {
    Integer latencyToDataCenter;
    Integer epNumbers;
    public Map<Integer, Integer> cacheIndexLatencyMap = new HashMap<>();
    public List<Request> requests = new ArrayList<>();

    public Endpoint(Integer dcLatency, Integer epNumbers) {
        latencyToDataCenter = dcLatency;
        this.epNumbers = epNumbers;
    }
}
