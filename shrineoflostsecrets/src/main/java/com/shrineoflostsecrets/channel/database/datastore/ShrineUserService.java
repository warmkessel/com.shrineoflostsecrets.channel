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


public class ShrineUserService{
	
	
	public static List<Entity> listUsers() {
		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

		Query<Entity> query = Query.newEntityQueryBuilder().setKind(TwitchChannelConstants.SHRINEUSER).build();

		// Run the query and retrieve a list of matching entities
		QueryResults<Entity> results = datastore.run(query);
		List<Entity> entities = Lists.newArrayList(results);		
		return entities;
	}		
	
	public static Entity fetchPass(String otp) {
		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
		Query<Entity> query = Query.newEntityQueryBuilder().setKind(TwitchChannelConstants.SHRINEUSER)
				.setFilter( PropertyFilter.eq(TwitchChannelConstants.OTPPASS, otp)).build();
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
	public static Entity fetchUser(String twitchUserName) {
		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
		Query<Entity> query = Query.newEntityQueryBuilder().setKind(TwitchChannelConstants.SHRINEUSER)
				.setFilter( PropertyFilter.eq(TwitchChannelConstants.TWITCHUSERNAME, twitchUserName)).build();
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