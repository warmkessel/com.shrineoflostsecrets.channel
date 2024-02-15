package com.shrineoflostsecrets.channel.collector;

import java.io.IOException;
import java.net.URISyntaxException;

//import oracle.jdbc.OracleDriver;
import javax.security.auth.login.LoginException;
import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shrineoflostsecrets.channel.collector.twitch.ShrineResponder;
import com.shrineoflostsecrets.channel.database.entity.ShrineChannel;
import com.shrineoflostsecrets.channel.util.DebugMode;

public class Launcher {

	private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

	// Environment Variables
	public static final String AUTHTOKEN = System.getenv("AUTHTOKEN");
	public static final String REFRESH = System.getenv("REFRESH");
	public static final String CLIENT_ID = System.getenv("CLIENT_ID");
	public static final String CLIENT_SECRET = System.getenv("CLIENT_SECRET");

	public static void main(String[] args)
			throws LoginException, InterruptedException, ClassNotFoundException, UnsupportedLookAndFeelException,
			InstantiationException, IllegalAccessException, URISyntaxException, IOException {

		logger.info("Shrine Startup");
		logger.info("Client ID: {}", CLIENT_ID);
		logger.info("Client Secret: [PROTECTED]");
		logger.info("Authenticanion Token: {}", AUTHTOKEN);
		logger.info("Refresh Token: {}", REFRESH);
		logger.info("Debug: {}", DebugMode.getSystemDebug());

		ShrineChannel channel = null;

//		channel = new ShrineChannel();
//		channel.loadShrineChannelName("shrineoflostsecrets");
//		new ShrineCapture(channel);
		
		channel = new ShrineChannel();
		channel.loadShrineChannelName("shrineoflostsecrets");
		new ShrineResponder(channel);
		logger.info("ShrineResponder Loaded: {}", channel.getTwitchChannel());

//		List<Entity> listChannels = ShrineChannelService.listChannels();
//
//		for (Entity entity : listChannels) {
//			channel = new ShrineChannel();
//			channel.loadFromEntity(entity);
//			new ShrineCapture(channel);
//
//			logger.info("ShrineCapture Loaded: {}", channel.getTwitchChannel());
//		}
	}
}