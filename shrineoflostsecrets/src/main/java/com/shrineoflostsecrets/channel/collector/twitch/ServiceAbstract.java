package com.shrineoflostsecrets.channel.collector.twitch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.twitch4j.TwitchClient;
import com.shrineoflostsecrets.channel.collector.Launcher;
import com.shrineoflostsecrets.channel.database.datastore.ShrineChannelService;
import com.shrineoflostsecrets.channel.database.entity.ShrineChannel;
import com.shrineoflostsecrets.channel.database.entity.ShrineChannelEvent;

public abstract class ServiceAbstract {
	private static final Logger logger = LoggerFactory.getLogger(ServiceAbstract.class);

	private TwitchClient twitchClient = null;
	private ShrineChannel channel = null;

	public ServiceAbstract(ShrineChannel channel) {
		this.channel = channel;
		this.twitchClient = buildTwitchClient();

	}
	abstract public TwitchClient buildTwitchClient();
	abstract public String getServiceName();

	public TwitchClient getTwitchClient() {
		return twitchClient;
	}

	public ShrineChannel getChannel() {
		return channel;
	}

	protected void log(String eventType, String eventId, String twitchChannel) {
		log(eventType, eventId, twitchChannel, "", "", "", "", "");
	}

	protected void log(String eventType, String eventId, String twitchChannel, String twitchUser) {
		log(eventType, eventId, twitchChannel, twitchUser, "", "", "", "");
	}

	protected void log(String eventType, String eventId, String twitchChannel, String twitchUser, String message) {
		log(eventType, eventId, twitchChannel, twitchUser, message, "", "", "");
	}

	protected void log(String eventType, String eventId, String twitchChannel, String twitchUser, String message,
			String elevatedChatPayment) {
		log(eventType, eventId, twitchChannel, twitchUser, message, elevatedChatPayment, "", "");

	}

	protected void log(String eventType, String eventId, String twitchChannel, String twitchUser, String message,
			String elevatedChatPayment, String redeemTime, String rewared) {
		logger.info("{}: {} {} {} {} {} {} {} {}", getServiceName(), eventType, eventId, twitchUser, twitchChannel, message,
				elevatedChatPayment, rewared, redeemTime);
		ShrineChannelEvent ts = new ShrineChannelEvent();
		ts.setEventType(eventType);
		ts.setEventId(eventId);
		ts.setTwitchUser(twitchUser);
		ts.setTwitchChannel(twitchChannel);
		ts.setElevatedChatPayment(elevatedChatPayment);
		ts.setRewared(rewared);
		ts.setRedeemTime(redeemTime);
		ts.setMessage(message);
		if (!Launcher.DEBUG.equalsIgnoreCase("true")) {
			ts.save();
		}
	}
}