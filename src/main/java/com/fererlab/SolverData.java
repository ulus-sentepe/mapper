package com.fererlab;

public class SolverData  implements Comparable<SolverData>{
	Video video;
	Double score;
	Integer cacheId;
	Integer endPointId;
	public SolverData(Integer endPointId,Video video2, double score2, Integer cacheLatencyKey) {
		// TODO Auto-generated constructor stub
		this.video = video2;
		this.score = score2;
		this.cacheId = cacheLatencyKey;
		this.endPointId = endPointId;
	}
	public Video getVideo() {
		return video;
	}
	public void setVideo(Video video) {
		this.video = video;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	@Override
	public int compareTo(SolverData o) {
		// TODO Auto-generated method stub
		return -1*Double.compare(getScore(), o.getScore());
	}
}
