package com.shrineoflostsecrets.channel.collector.twitch;

import com.shrineoflostsecrets.channel.collector.TwitchStream;

public abstract class ServiceAbstract{

	TwitchStream twitchStream = null;

	
	public ServiceAbstract(TwitchStream twitchStream) {
    	this.twitchStream = twitchStream;

	}
	public TwitchStream getTwitchStream() {
		return twitchStream;
	}
}