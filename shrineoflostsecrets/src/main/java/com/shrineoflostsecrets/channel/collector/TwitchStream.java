package com.shrineoflostsecrets.channel.collector;

import com.github.twitch4j.TwitchClient;


public class TwitchStream {
	private TwitchClient twitchClient = null;
	private String channel = "";
	private long subscriptionEvents = 0;

	public TwitchStream(String channel, TwitchClient twitchClient, long twitchserviceType) {
		this.channel = channel;
		this.twitchClient = twitchClient;
		this.subscriptionEvents = twitchserviceType;
	}
	public TwitchClient getTwitchClient() {
		return twitchClient;
	}
	public long getTwitchserviceType() {
		return subscriptionEvents;
	}
	public String getChannel() {
		return channel;
	}
}
