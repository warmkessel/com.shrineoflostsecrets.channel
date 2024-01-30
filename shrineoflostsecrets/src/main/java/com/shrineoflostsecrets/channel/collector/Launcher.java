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
import com.shrineoflostsecrets.channel.database.datastore.TwitchChannelList;
import com.shrineoflostsecrets.channel.database.entity.ShrineChannel;

public class Launcher {

	private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) throws LoginException, InterruptedException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, URISyntaxException, IOException {


    	logger.info("Shrine Startup");

    	List<Entity> listChanels = TwitchChannelList.listChanels();
    			
    	for(Entity entity: listChanels) {
    		ShrineChannel channel = new ShrineChannel();
    		channel.loadFromEntity(entity);
    		new Bot(channel.getTwitchUserName(), channel.getTwitchServiceType()).start();
    		logger.info("Loaded:{}", channel.getTwitchChannel());


    	}
    }
}
