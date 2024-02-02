package com.shrineoflostsecrets.channel.collector;

import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
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

	public static String TOKEN;

	
	public static String BROADCASTER_ID;


	/**
	 * Constructor
	 */
	public Bot(String twitchName, long twitchserviceType) throws LoginException, InterruptedException {
        // Load Configuration
        loadConfiguration();
        twitchStream = newTwitchClient(twitchName, twitchserviceType);
    }
	/**
	 * Load the Configuration
	 */
	// Load the Configuration
    private void loadConfiguration() {
        try {    
            // Initialize Google Datastore client
            Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
            
            // Fetch the OAuth token from the datastore
            Query<Entity> query = Query.newEntityQueryBuilder().setKind("auth").build();
            QueryResults<Entity> results = datastore.run(query);
            if (results.hasNext()) {
                Entity entity = results.next();
                OAUTH = entity.getString("access_token");
            }

            BROADCASTER_ID = "shrineoflostsecrets2";
            TOKEN = "";

        } catch (Exception ex) {
            logger.error("Unable to load Configuration: {}", ex.getMessage());
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

		 
		    
			TwitchClient twitchClient = clientBuilder
			.withEnableHelix(true)
			.withClientId(Launcher.CLIENT_ID)
		    .withClientSecret(Launcher.CLIENT_SECRET)
		    .withEnableChat(true)
		    .withEnablePubSub(true)
			//.withEnableTMI(true)
			//.withEnableKraken(true)
		    //.withDefaultAuthToken(credential)
		    .build();
	    
		    // region TwitchClient
//		TwitchClient twitchClient = clientBuilder
//				.withEnableHelix(true).withChatAccount(credential).withEnableChat(true).withEnablePubSub(true)
//				.withEnableTMI(true).withEnableKraken(true).withDefaultAuthToken(credential).build();
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
