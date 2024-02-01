package com.shrineoflostsecrets.channel.collector;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.philippheuer.credentialmanager.domain.Credential;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.auth.providers.TwitchIdentityProvider;


public class TwitchStream {
	private static final Logger logger = LoggerFactory.getLogger(TwitchStream.class);


	public static final String CLIENT_ID = "ya3olz9mnmbw19lnucg8hbswqmsycw";
	public static final String CLIENT_SECRET = "c2lof46ndfojx7pi56sttmp1gttffv";


	public static final String PATH = "https://id.twitch.tv/oauth2/validate";

	public  String accessToken ="";

	public  String refreshToken ="";
	
	
	TwitchIdentityProvider twitchIdentityProvider = new TwitchIdentityProvider(CLIENT_ID,  CLIENT_SECRET, PATH);

	
	private TwitchClient twitchClient = null;
	private String channel = "";
	private long subscriptionEvents = 0;

	public TwitchStream(String channel, TwitchClient twitchClient, long twitchserviceType) {
		this.channel = channel;
		this.twitchClient = twitchClient;
		this.subscriptionEvents = twitchserviceType;
	}
	public TwitchClient getTwitchClient() {
		if(twitchClient == null || !isValid(twitchClient)){
			twitchClient = initTwitchClient();
		}
		return twitchClient;
	}
	private TwitchClient initTwitchClient() {
		
		TwitchClient theReturn = null;
		
		for(Credential credential : twitchClient.getChat().getCredentialManager().getCredentials()) {
			if(credential instanceof OAuth2Credential) {
				Optional<OAuth2Credential> newCredential =  twitchIdentityProvider.getAdditionalCredentialInformation((OAuth2Credential)credential);
				
					if(newCredential.isPresent()) {
						OAuth2Credential newCred = newCredential.get();
						accessToken = newCred.getAccessToken();
						refreshToken = newCred.getRefreshToken();

						TwitchClientBuilder clientBuilder = TwitchClientBuilder.builder();
						theReturn = clientBuilder
								.withEnableHelix(true).withChatAccount(newCred).withEnableChat(true).withEnablePubSub(true)
								.withEnableTMI(true).withEnableKraken(true).withDefaultAuthToken(newCred).build();
						break;
					}
			}
		}
		return theReturn;
	}

	public boolean isValid(){
		return isValid(twitchClient);
	}
	private boolean isValid(TwitchClient twitchClient ) {
		boolean theReturn = false;
		if(null == twitchClient) {
			return theReturn;
		}
		else {
			for(Credential cred : twitchClient.getChat().getCredentialManager().getCredentials()) {
				if(cred instanceof OAuth2Credential) {
					Optional<Boolean> val = twitchIdentityProvider.isCredentialValid((OAuth2Credential)cred);
						if(val.isPresent()) {
							theReturn = val.get();
						}
				}
			}
			
		}
		return theReturn;
	}
	
	public long getTwitchserviceType() {
		return subscriptionEvents;
	}
	public String getChannel() {
		return channel;
	}
}
