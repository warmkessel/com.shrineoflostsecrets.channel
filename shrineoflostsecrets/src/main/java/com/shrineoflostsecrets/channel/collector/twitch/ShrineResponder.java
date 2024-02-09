package com.shrineoflostsecrets.channel.collector.twitch;

import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.reactor.ReactorEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.chat.events.channel.FollowEvent;
import com.github.twitch4j.eventsub.socket.IEventSubSocket;
import com.shrineoflostsecrets.channel.collector.Launcher;
import com.shrineoflostsecrets.channel.database.entity.ShrineChannel;
import com.shrineoflostsecrets.channel.database.entity.ShrineUser;
import com.shrineoflostsecrets.channel.util.OTPGenerator;

public class ShrineResponder extends ServiceAbstract {
	private static final Logger logger = LoggerFactory.getLogger(ShrineResponder.class);
	// twitch token -u -s 'user:write:chat user:read:chat user:manage:whispers
	// user:read:follows moderator:read:followers'

	//
	IEventSubSocket eventSocket = null;

	/**
	 * Register events of this class with the EventManager
	 *
	 * @param eventManager EventManager
	 */
	TwitchClient client = null;

	public ShrineResponder(ShrineChannel channel) throws LoginException, InterruptedException {
		super(channel);
		
//		// credential manager
//		CredentialManager credentialManager = CredentialManagerBuilder.builder().build();
//		credentialManager.registerIdentityProvider(new TwitchIdentityProvider(Launcher.CLIENT_ID,
//				Launcher.CLIENT_SECRET, ""));
//		// twitch4j - chat
//		TwitchChat client = TwitchChatBuilder.builder()
//		        .withCredentialManager(credentialManager)
//		        .withChatAccount(initCred())
//		        .build();
//
//
//
//		client.sendMessage("shrineoflostsecrets", "I am live2!");

		client = getTwitchClient();

		client.getClientHelper().enableFollowEventListener(getChannel().getTwitchChannel());
		client.getClientHelper().enableStreamEventListener(getChannel().getTwitchChannel());
		client.getChat().joinChannel(getChannel().getTwitchChannel());

		client.getEventManager().getEventHandler(ReactorEventHandler.class).onEvent(ChannelMessageEvent.class,
				this::onChannelMessage);

		client.getEventManager().getEventHandler(ReactorEventHandler.class).onEvent(FollowEvent.class, this::onFollow);


		client.getChat().sendMessage("shrineoflostsecrets", "Welcome to the Shrine Of Lost Secrets");

	}

	/**
	 * Subscribe to the ChannelMessage Event and write the output to the console
	 */
	public void onFollow(FollowEvent event) {
		logger.info("FOLLOW" + event.getUser().getId());
		registerUser(event);
		logger.info("FOLLOW Done");

	}

	public void onChannelMessage(ChannelMessageEvent event) {
		logger.info("MESSAGE " + event.getUser().getId() + " " + event.getMessage());
		client.getChat().sendMessage("shrineoflostsecrets", "Hello " + event.getUser().getName());
	}

	private void registerUser(FollowEvent event) {
		String otp = OTPGenerator.generateOTP();
		ShrineUser su = new ShrineUser();
		su.loadShrineUserName(event.getUser().getName());
		su.setTwitchUserName(event.getUser().getName());
		su.setTwitchUserId(event.getUser().getId());
		su.setOtpPass(otp);
		su.save();

		client.getChat().sendMessage("shrineoflostsecrets", "Hello " + event.getUser().getName());
		client.getHelix().sendWhisper(getCredential().getAccessToken(), "1005643393", event.getUser().getId(), 
				"Thank you " + event.getUser().getName() + " for the follow us. Log in using https://shrineoflostsecrets.com/a/" + otp).queue();

	}

	public OAuth2Credential initCred() {
		return new OAuth2Credential("twitch", Launcher.AUTHTOKEN, Launcher.REFRESH, Launcher.CLIENT_ID,
				Launcher.CLIENT_SECRET, 10000, null);
	}

	public TwitchClient getTwitchClient() {
		TwitchClientBuilder clientBuilder = TwitchClientBuilder.builder();
		return clientBuilder
				.withEnableHelix(true)
				.withChatAccount(getCredential())
				.withEnableChat(true)
				.withDefaultEventHandler(ReactorEventHandler.class)
				.withDefaultAuthToken(getCredential())
				.withEnableEventSocket(true)
				.build();
	}

	public String getServiceName() {
		return getClass().getName();
	}

}