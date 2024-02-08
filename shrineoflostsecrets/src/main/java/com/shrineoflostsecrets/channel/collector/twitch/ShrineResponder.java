package com.shrineoflostsecrets.channel.collector.twitch;

import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
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
	OAuth2Credential credential = null;
	IEventSubSocket eventSocket = null;

	/**
	 * Register events of this class with the EventManager
	 *
	 * @param eventManager EventManager
	 */

	public ShrineResponder(ShrineChannel channel) throws LoginException, InterruptedException {
		super(channel);
		getTwitchClient().getChat().joinChannel(getChannel().getTwitchChannel());

		getTwitchClient().getEventManager().getEventHandler(SimpleEventHandler.class).onEvent(ChannelMessageEvent.class,
				this::onChannelMessage);

		getTwitchClient().getEventManager().getEventHandler(SimpleEventHandler.class).onEvent(FollowEvent.class,
				this::onFollow);
	}

	/**
	 * Subscribe to the ChannelMessage Event and write the output to the console
	 */
	public void onFollow(FollowEvent event) {
//		logger.info("FOLLOW");
//		registerUser(event);
	}

	public void onChannelMessage(ChannelMessageEvent event) {
		logger.info("MESSAGE");

		if(event.getMessage().toLowerCase().startsWith("!auth")) {
			logger.info("AUTH");
			registerUser(event);
		}
	}

	private void registerUser(ChannelMessageEvent event) {
//
		logger.info("registerUser "+ event.getUser().getName() + " " + event.getUser().getId() );
		String otp = OTPGenerator.generateOTP();
		ShrineUser su = new ShrineUser();
		su.loadShrineUserName(event.getUser().getName());
		su.setTwitchUserName(event.getUser().getName());
		su.setTwitchUserId(event.getUser().getId());
		su.setOtpPass(otp);
		su.save();

//		getTwitchClient().getChat().sendMessage("shrineoflostsecrets",
//				"Thank you " + event.getUser().getName() + " for the follow check your whispers for your password");
		logger.info("trying whisper message");

		getTwitchClient().getChat().sendMessage("shrineoflostsecrets",
		"Thank you " + event.getUser().getName() + " Log in using https://shrineoflostsecrets.com/a/" + otp);

		
		logger.info("finsihed whisper message");

	//	getTwitchClient().getHelix().sendWhisper(eventSocket.getDefaultToken().getAccessToken(), "1005643393",
	//			event.getUser().getId(), "Test Private Message");

//		logger.info("finished");

//		logger.info("hi");
//
//		
//		logger.info("getCredentialManager" + (twitchStream.getTwitchClient().getChat().getCredentialManager() == null));
//
//		logger.info("getOAuth2CredentialByUserId" + (twitchStream.getTwitchClient().getChat().getCredentialManager().getOAuth2CredentialByUserId("twitch") == null));
//		
//		logger.info("size" + (twitchStream.getTwitchClient().getChat().getCredentialManager().getCredentials().size()));

//		
//		logger.info(".get()" + (twitchStream.getTwitchClient().getChat().getCredentialManager().getOAuth2CredentialByUserId("twitch").get() == null));
//
//		logger.info(".get()" + (twitchStream.getTwitchClient().getChat().getCredentialManager().getOAuth2CredentialByUserId("twitch").get().getAccessToken() == null));
//
//		
//		logger.info("auth" + twitchStream.getTwitchClient().getChat().getCredentialManager().getOAuth2CredentialByUserId("twitch").get().getAccessToken());
//
		// twitchStream.getTwitchClient().getChat().sendMessage("shrineoflostsecrets",
		// "Check your Whipsers, we sent you your credential");

//		logger.info("auth" + twitchStream.getTwitchClient().getChat().getCredentialManager().getOAuth2CredentialByUserId("twitch").get().getAccessToken());

//		HystrixCommand<OutboundFollowing> resultList = getTwitchClient().getHelix().getFollowedChannels(null,
//				twitchUser.getId(), "1005643393", null, null);
//
//		OutboundFollowing outboundFollowing = resultList.execute();
//
//		// Check if the outboundFollowing contains follows data
//		if (outboundFollowing != null && outboundFollowing.getFollows() != null) {
//			// Iterate over the follows and print out the from and to names
//			outboundFollowing.getFollows().forEach(follow -> {
//				logger.info(follow.getBroadcasterName() + " is following");
//
//			});
//		} else {
//			System.out.println("No follow data available or error in fetching data");
//		}

//		twitchStream.getTwitchClient().getHelix().sendWhisper(null, "shrineoflostsecrets", twitchUser, "Test Private Message" + otp);

//		twitchStream.getTwitchClient().getHelix().sendWhisper(twitchStream.getTwitchClient().getChat().getCredentialManager().getOAuth2CredentialByUserId("twitch").get().getAccessToken(), "shrineoflostsecrets", twitchUser, "Test Private Message" + otp);
	}

	public TwitchClient buildTwitchClient() {
		TwitchClientBuilder clientBuilder = TwitchClientBuilder.builder();

		credential = new OAuth2Credential("twitch", Launcher.AUTHTOKEN, Launcher.REFRESH, Launcher.CLIENT_ID,
				Launcher.CLIENT_SECRET, 10000, null);

		TwitchClient twitchClient = clientBuilder.withEnableHelix(true).withChatAccount(credential).withEnableChat(true)
				.withDefaultAuthToken(credential).withEnableEventSocket(true).build();
		
		eventSocket = twitchClient.getEventSocket();		
		twitchClient.getHelix().sendWhisper(eventSocket.getDefaultToken().getAccessToken(), "1005643393", "1005643393", "Test Private Message");
		// endregion

		twitchClient.getClientHelper().enableStreamEventListener(getChannel().getTwitchChannel());
		twitchClient.getClientHelper().enableFollowEventListener(getChannel().getTwitchChannel());
		return twitchClient;
	}

	public String getServiceName() {
		return getClass().getName();
	}

}