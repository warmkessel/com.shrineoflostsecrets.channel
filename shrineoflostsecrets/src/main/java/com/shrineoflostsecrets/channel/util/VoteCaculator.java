package com.shrineoflostsecrets.channel.util;

public class VoteCaculator {
	public static long caculateVote(long vote, long mult) {
		return Math.max(0, vote) * Math.max(1, mult);
	}
}
