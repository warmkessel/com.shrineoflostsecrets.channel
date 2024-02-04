package com.shrineoflostsecrets.channel.collector;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

//import oracle.jdbc.OracleDriver;
import javax.security.auth.login.LoginException;
import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.cloud.datastore.Entity;
import com.shrineoflostsecrets.channel.database.datastore.ShrineChannelService;
import com.shrineoflostsecrets.channel.database.entity.ShrineChannel;

public class Launcher {

    private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

    // Environment Variables
    public static final String DEBUG = System.getenv("DEBUG");
    public static final String CLIENT_ID = System.getenv("CLIENT_ID");
    public static final String CLIENT_SECRET = System.getenv("CLIENT_SECRET");

    public static void main(String[] args) throws LoginException, InterruptedException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, URISyntaxException, IOException {

        logger.info("Shrine Startup");
        logger.info("Client ID: {}", CLIENT_ID);
        logger.info("Client Secret: [PROTECTED]");
        logger.info("Debug: {}", DEBUG);

        List<Entity> listChannels = ShrineChannelService.listChannels();
        
        for(Entity entity: listChannels) {
            ShrineChannel channel = new ShrineChannel();
            channel.loadFromEntity(entity);
            new Bot(channel.getTwitchUserName(), channel.getTwitchServiceType()).start();
            logger.info("Loaded: {}", channel.getTwitchChannel());
        }
    }
}