package com.shrineoflostsecrets.channel.database.datastore;

import java.util.List;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import com.google.common.collect.Lists;
import com.shrineoflostsecrets.channel.constants.TwitchChannelConstants;


public class ShrineChannelService{
	
	public static List<Entity> listChannels() {
		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

		Query<Entity> query = Query.newEntityQueryBuilder().setKind(TwitchChannelConstants.SHRINECHANNEL)
				.setFilter( PropertyFilter.eq(TwitchChannelConstants.DELETED, false))
						.build();
		// Run the query and retrieve a list of matching entities
		QueryResults<Entity> results = datastore.run(query);
		List<Entity> entities = Lists.newArrayList(results);		
		return entities;
	}		
	
	public static Entity getShrineChannelName(String channel) {
		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
		Query<Entity> query = Query.newEntityQueryBuilder().setKind(TwitchChannelConstants.SHRINECHANNEL)
				.setFilter( PropertyFilter.eq(TwitchChannelConstants.TWITCHCHANNEL, channel)).build();
		// Run the query and retrieve a list of matching entities
		QueryResults<Entity> results = datastore.run(query);
		List<Entity> entities = Lists.newArrayList(results);
		if(entities.size() == 0) {
			return null;
		}
		else{
			return entities.get(0);
		}
	}
	
	

}