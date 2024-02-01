package com.shrineoflostsecrets.channel.collector.twitch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.philippheuer.events4j.api.domain.IDisposable;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelJoinEvent;
import com.github.twitch4j.chat.events.channel.ChannelLeaveEvent;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.chat.events.channel.DeleteMessageEvent;
import com.github.twitch4j.chat.events.channel.UserBanEvent;
import com.github.twitch4j.events.ChannelGoLiveEvent;
import com.github.twitch4j.events.ChannelGoOfflineEvent;
import com.github.twitch4j.pubsub.events.RewardRedeemedEvent;
import com.shrineoflostsecrets.channel.collector.Launcher;
import com.shrineoflostsecrets.channel.collector.TwitchStream;
import com.shrineoflostsecrets.channel.constants.TwitchChannelConstants;
import com.shrineoflostsecrets.channel.database.entity.ShrineChannelEvent;

public class ShrineCapture extends ServiceAbstract {
	private static final Logger logger = LoggerFactory.getLogger(ShrineCapture.class);

	IDisposable handlerReg = null;

	/**
	 * Register events of this class with the EventManager
	 *
	 * @param eventManager EventManager
	 */

	public ShrineCapture(TwitchStream twitchStream) {
		super(twitchStream);
		twitchStream.getTwitchClient().getEventManager().getEventHandler(SimpleEventHandler.class)
				.onEvent(ChannelGoOfflineEvent.class, this::onGoOfflineEvent);
		twitchStream.getTwitchClient().getEventManager().getEventHandler(SimpleEventHandler.class)
				.onEvent(ChannelGoLiveEvent.class, this::onGoLiveEvent);

		twitchStream.getTwitchClient().getEventManager().getEventHandler(SimpleEventHandler.class)
				.onEvent(ChannelMessageEvent.class, this::onChannelMessage);

		twitchStream.getTwitchClient().getEventManager().getEventHandler(SimpleEventHandler.class)
				.onEvent(DeleteMessageEvent.class, this::onDeleteMessageEvent);

		twitchStream.getTwitchClient().getEventManager().getEventHandler(SimpleEventHandler.class)
				.onEvent(UserBanEvent.class, this::onUserBanEvent);

//		twitchStream.getTwitchClient().getEventManager().getEventHandler(SimpleEventHandler.class)
//				.onEvent(ChannelJoinEvent.class, this::onChannelJoinEvent);

		twitchStream.getTwitchClient().getEventManager().getEventHandler(SimpleEventHandler.class)
				.onEvent(ChannelLeaveEvent.class, this::onChannelLeaveEvent);

		twitchStream.getTwitchClient().getEventManager().getEventHandler(SimpleEventHandler.class)
				.onEvent(RewardRedeemedEvent.class, this::onRewardRedeemedEvent);

	}

	/**
	 * Subscribe to the ChannelMessage Event and write the output to the console
	 */

	public void onRewardRedeemedEvent(RewardRedeemedEvent event) {
		log(TwitchChannelConstants.ONREWARDREDEEMEVENT, event.getEventId(), event.getRedemption().getChannelId(),
				event.getRedemption().getUser().getDisplayName(), "", "", event.getRedemption().getReward().toString(),
				event.getRedemption().getRedeemedAt());
	}

	public void onChannelJoinEvent(ChannelJoinEvent event) {
		log(TwitchChannelConstants.ONCHANNELJOINEVENT, event.getEventId(), event.getChannel().getName(),
				event.getUser().getName(), "");

	}

	public void onChannelLeaveEvent(ChannelLeaveEvent event) {
		log(TwitchChannelConstants.ONCHANNELMESSAGEELEVATED, event.getEventId(), event.getChannel().getName(),
				event.getUser().getName(), "");
	}

	public void onDeleteMessageEvent(DeleteMessageEvent event) {
		log(TwitchChannelConstants.ONDELETEMESSAAGE, event.getEventId(), event.getChannel().getName(),
				event.getUserName(), event.getMessage());
	}

	public void onUserBanEvent(UserBanEvent event) {
		log(TwitchChannelConstants.ONUSERBAN, event.getEventId(), event.getChannel().getName(),
				event.getUser().getName(), event.getReason());
	}

//	public void onChannelMessage(ChannelMessageEvent event) {
//		if (event.getElevatedChatPayment().isPresent()) {
//			log(TwitchChannelConstants.ONCHANNELMESSAGEELEVATED, event.getEventId(), event.getChannel().getName(),
//					event.getUser().getName(), event.getMessage(), event.getElevatedChatPayment().get().getValue().toString());
//		} else {
//			log(TwitchChannelConstants.ONCHANNELMESSAGE, event.getEventId(), event.getChannel().getName(),
//					event.getUser().getName(), event.getMessage());
//		}
//	}
	public void onChannelMessage(ChannelMessageEvent event) {
		if (event.getElevatedChatPayment().isPresent()) {
			logger.info("Event Log: {}", event);
		}
		log(TwitchChannelConstants.ONCHANNELMESSAGE, event.getEventId(), event.getChannel().getName(),
				event.getUser().getName(), event.getMessage());

	}

	public void onGoOfflineEvent(ChannelGoOfflineEvent event) {
		log(TwitchChannelConstants.ONGOOFFLINEEVENT, event.getEventId(), event.getChannel().getName());
	}

	public void onGoLiveEvent(ChannelGoLiveEvent event) {
		log(TwitchChannelConstants.ONGOLIVEEVENT, event.getEventId(), event.getChannel().getName());
	}

	private void log(String eventType, String eventId, String twitchChannel) {
		log(eventType, eventId, twitchChannel, "", "", "", "", "");
	}

	private void log(String eventType, String eventId, String twitchChannel, String twitchUser, String message) {
		log(eventType, eventId, twitchChannel, twitchUser, message, "", "", "");
	}

	private void log(String eventType, String eventId, String twitchChannel, String twitchUser, String message,
			String elevatedChatPayment) {
		log(eventType, eventId, twitchChannel, twitchUser, message, elevatedChatPayment, "", "");

	}

	private void log(String eventType, String eventId, String twitchChannel, String twitchUser, String message,
			String elevatedChatPayment, String redeemTime, String rewared) {
		logger.info("{}: {} {} {} {} {} {} {}", eventType, eventId, twitchUser, twitchChannel, message,
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
		if(!Launcher.DEBUG.equalsIgnoreCase("true")) {
			ts.save();
		}
	}
}