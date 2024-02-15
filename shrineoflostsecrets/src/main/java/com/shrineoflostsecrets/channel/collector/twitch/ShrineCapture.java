package com.shrineoflostsecrets.channel.collector.twitch;

import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.api.domain.IDisposable;
import com.github.philippheuer.events4j.reactor.ReactorEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.events.channel.ChannelJoinEvent;
import com.github.twitch4j.chat.events.channel.ChannelLeaveEvent;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.chat.events.channel.DeleteMessageEvent;
import com.github.twitch4j.chat.events.channel.UserBanEvent;
import com.github.twitch4j.events.ChannelGoLiveEvent;
import com.github.twitch4j.events.ChannelGoOfflineEvent;
import com.github.twitch4j.pubsub.events.RewardRedeemedEvent;
import com.shrineoflostsecrets.channel.collector.Launcher;
import com.shrineoflostsecrets.channel.constants.TwitchChannelConstants;
import com.shrineoflostsecrets.channel.database.entity.ShrineChannel;
import com.shrineoflostsecrets.channel.database.entity.ShrineChannelEvent;

public class ShrineCapture extends ServiceAbstract {
	private static final Logger logger = LoggerFactory.getLogger(ShrineCapture.class);

	IDisposable handlerReg = null;

	/**
	 * Register events of this class with the EventManager
	 *
	 * @param eventManager EventManager
	 */

	/**
	 * Constructor
	 */
	public ShrineCapture(ShrineChannel channel) throws LoginException, InterruptedException {
		super(channel);

		TwitchClient client = getTwitchClient();

		client.getClientHelper().enableStreamEventListener(getChannel().getTwitchChannel());
		client.getChat().joinChannel(getChannel().getTwitchChannel());

		client.getChat().joinChannel(getChannel().getTwitchChannel());

		client.getEventManager().getEventHandler(ReactorEventHandler.class).onEvent(ChannelGoOfflineEvent.class,
				this::onGoOfflineEvent);
		client.getEventManager().getEventHandler(ReactorEventHandler.class).onEvent(ChannelGoLiveEvent.class,
				this::onGoLiveEvent);

		client.getEventManager().getEventHandler(ReactorEventHandler.class).onEvent(ChannelMessageEvent.class,
				this::onChannelMessage);

		client.getEventManager().getEventHandler(ReactorEventHandler.class).onEvent(DeleteMessageEvent.class,
				this::onDeleteMessageEvent);

		client.getEventManager().getEventHandler(ReactorEventHandler.class).onEvent(UserBanEvent.class,
				this::onUserBanEvent);

//twitchStream.getTwitchClient().getEventManager().getEventHandler(SimpleEventHandler.class)
//		.onEvent(ChannelJoinEvent.class, this::onChannelJoinEvent);

//twitchStream.getTwitchClient().getEventManager().getEventHandler(SimpleEventHandler.class)
//		.onEvent(ChannelLeaveEvent.class, this::onChannelLeaveEvent);
//
//twitchStream.getTwitchClient().getEventManager().getEventHandler(SimpleEventHandler.class)
//		.onEvent(RewardRedeemedEvent.class, this::onRewardRedeemedEvent);
//
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
		log(TwitchChannelConstants.ONCHANNELLEAVEEVENT, event.getEventId(), event.getChannel().getName(),
				event.getUser().getName(), "");
	}

	public void onDeleteMessageEvent(DeleteMessageEvent event) {
		logger.debug("xxxx Delete " + event.getMsgId());
		log(TwitchChannelConstants.ONDELETEMESSAGE, event.getEventId(), event.getChannel().getName(),
				event.getUserName(), event.getMessage());
		logger.debug("xxxx origEvent");
		ShrineChannelEvent origEvent = new ShrineChannelEvent();
		
		logger.debug("xxxx load");
		origEvent.loadShrineMessage(event.getMessage());
		logger.debug("xxxx origEvent.isValid() " + origEvent.isValid());

		if(origEvent.isValid()) {
			logger.debug("xxxx setDeleted " + origEvent.getDeleted());
			origEvent.setDeleted(true);
			logger.debug("xxxx setDeleted2 " + origEvent.getDeleted());
			origEvent.save();
			logger.debug("xxxx Save ");
		}
	}

	@SuppressWarnings("deprecation")
	public void onUserBanEvent(UserBanEvent event) {
		log(TwitchChannelConstants.ONUSERBAN, event.getEventId(), event.getChannel().getName(),
				event.getUser().getName(), event.getReason());
	}

	public void onChannelMessage(ChannelMessageEvent event) {
		if (event.getElevatedChatPayment().isPresent()) {
			log(TwitchChannelConstants.ONCHANNELMESSAGEELEVATED, event.getEventId(), event.getChannel().getName(),
					event.getUser().getName(), event.getMessage(),
					event.getElevatedChatPayment().get().getValue().toString());
		} else {
			log(TwitchChannelConstants.ONCHANNELMESSAGE, event.getEventId(), event.getChannel().getName(),
					event.getUser().getName(), event.getMessage());
		}
	}
//	public void onChannelMessage(ChannelMessageEvent event) {
//		if (event.getElevatedChatPayment().isPresent()) {
//			logger.info("Event Log: {}", event);
//		}
//		log(TwitchChannelConstants.ONCHANNELMESSAGE, event.getEventId(), event.getChannel().getName(),
//				event.getUser().getName(), event.getMessage());
//	}

	public void onGoOfflineEvent(ChannelGoOfflineEvent event) {
		log(TwitchChannelConstants.ONGOOFFLINEEVENT, event.getEventId(), event.getChannel().getName());
		getChannel().setTwitchLastEnd();
		getChannel().save();
	}

	public void onGoLiveEvent(ChannelGoLiveEvent event) {
		log(TwitchChannelConstants.ONGOLIVEEVENT, event.getEventId(), event.getChannel().getName());
		getChannel().setTwitchLastStart();
		getChannel().save();

	}

	public OAuth2Credential initCred() {
		return null;
	}

	public TwitchClient getTwitchClient() {
		return TwitchClientBuilder.builder().withEnableHelix(true).withClientId(Launcher.CLIENT_ID)
				.withClientSecret(Launcher.CLIENT_SECRET).withEnableChat(true).withEnablePubSub(true)
				.withDefaultEventHandler(ReactorEventHandler.class)
				// .withEnableTMI(true)
				// .withEnableKraken(true)
				.build();
	}

	public String getServiceName() {
		return getClass().getName();
	}

}