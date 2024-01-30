package com.shrineoflostsecrets.channel.collector;

import java.net.URL;

import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.shrineoflostsecrets.channel.collector.twitch.ShrineCapture;


public class Bot {
	private static final Logger logger = LoggerFactory.getLogger(Bot.class);

	/**
	 * Twitch4J API
	 */
//    public static TwitchClient twitchClient;
	public TwitchStream twitchStream = null;
//    TwitchClient twitchClient;

	/**
	 * JDA API
	 */
//	public static JDA discordClient;

	public static String OAUTH;

	public static String CLIENT_ID;

	public static String CLIENT_SECRET;

	public static String BROADCASTER_ID;

	public static String TOKEN;

	/**
	 * Constructor
	 */
	public Bot(String twitchName, long twitchserviceType) throws LoginException, InterruptedException {
		URL localPackage = this.getClass().getResource("");
		URL urlLoader = Bot.class.getProtectionDomain().getCodeSource().getLocation();
		String localDir = localPackage.getPath();
		String loaderDir = urlLoader.getPath();
		// System.out.printf("loaderDir = %s\nlocalDir = %s\n", loaderDir, localDir);
		logger.debug("loaderDir = {}, localDir = {}", loaderDir, localDir);
		// Load Configuration
		loadConfiguration();
		twitchStream = newTwitchClient(twitchName, twitchserviceType);
	}

	/**
	 * Load the Configuration
	 */
	private void loadConfiguration() {
		try {	
			CLIENT_ID = "ya3olz9mnmbw19lnucg8hbswqmsycw";
			CLIENT_SECRET = "c2lof46ndfojx7pi56sttmp1gttffv";
			BROADCASTER_ID = "shrineoflostsecrets2";
			OAUTH = "vw2t6fq4b343t6drolg2z1virkau4s";
			TOKEN = "rztxun0wtfl4pgo61cgkkvcvpp134ro4ui3ua4aiofogxoi7kz";

		} catch (Exception ex) {
			ex.printStackTrace();
			// System.out.println("Unable to load Configuration ... Exiting.");
			logger.error("Unable to load Configuration ... Exiting.");
			System.exit(1);
		}
	}

	public void start() {
		new ShrineCapture(getTwitchStream());
//		new LogService(getTwitchStream());
		getTwitchStream().getTwitchClient().getChat().joinChannel(getTwitchStream().getChannel());
	    
	}

	public TwitchStream newTwitchClient(String name, long twitchserviceType) {
		OAuth2Credential credential = new OAuth2Credential("twitch", OAUTH);

		TwitchClientBuilder clientBuilder = TwitchClientBuilder.builder();

		// region TwitchClient
		TwitchClient twitchClient = clientBuilder
				// .withClientId(CLIENT_ID)
				// .withClientSecret(CLIENT_SECRET)
				.withEnableHelix(true).withChatAccount(credential).withEnableChat(true).withEnablePubSub(true)
				.withEnableTMI(true).withEnableKraken(true).withDefaultAuthToken(credential).build();
		// endregion
 		twitchClient.getClientHelper().enableStreamEventListener(name);
		twitchClient.getClientHelper().enableFollowEventListener(name);
//		twitchClient.getPubSub().listenForChannelPointsRedemptionEvents(credential, "149223493");


		return new TwitchStream(name, twitchClient, twitchserviceType);
	}
	public TwitchStream getTwitchStream() {
		return twitchStream;
	}
}
