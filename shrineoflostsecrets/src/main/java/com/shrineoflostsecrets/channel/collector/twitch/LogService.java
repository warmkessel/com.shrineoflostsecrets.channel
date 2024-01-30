package com.shrineoflostsecrets.channel.collector.twitch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.shrineoflostsecrets.channel.collector.TwitchStream;

    public class LogService extends ServiceAbstract {
    	private static final Logger logger = LoggerFactory.getLogger(LogService.class);
        /**
         * Register events of this class with the EventManager
         *
         * @param eventManager EventManager
         */
    	
        public LogService(TwitchStream twitchStream) {
        	super(twitchStream);
        	twitchStream.getTwitchClient().getEventManager().getEventHandler(SimpleEventHandler.class).onEvent(ChannelMessageEvent.class, this::onChannelMessage);
        }

        /**
         * Subscribe to the ChannelMessage Event and write the output to the console
         */

    public void onChannelMessage(ChannelMessageEvent event) {
    	logger.info("Channel:  {} | {} | {}: {}",
    			event.getEventId(),
    			event.getChannel().getName(),
    			event.getUser().getName(),
    			event.getMessage());
    }
}