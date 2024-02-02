package com.shrineoflostsecrets.channel.collector;

import com.github.twitch4j.TwitchClient;
import com.shrineoflostsecrets.channel.database.entity.ShrineChannel;

public class TwitchStream {
	private TwitchClient twitchClient = null;
	private ShrineChannel channel = null;
	private long subscriptionEvents = 0;
	private ShrineChannel shrineChannel = null;

	public TwitchStream(ShrineChannel channel, TwitchClient twitchClient, long twitchserviceType) {
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
	public ShrineChannel getChannel() {
		return channel;
	}
	public ShrineChannel getShrineChannel() {
		if(shrineChannel == null) {
			shrineChannel = new ShrineChannel();
			if(getChannel().getId() != 0) {
				shrineChannel.loadShrineChannel(getChannel().getId());
			}
		}
		return shrineChannel;
	}
}
