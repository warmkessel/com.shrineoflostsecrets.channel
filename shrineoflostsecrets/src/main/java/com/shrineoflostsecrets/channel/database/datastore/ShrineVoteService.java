package com.shrineoflostsecrets.channel.database.datastore;

import java.util.List;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import com.google.cloud.datastore.Transaction;
import com.google.common.collect.Lists;
import com.shrineoflostsecrets.channel.constants.TwitchChannelConstants;


public class ShrineVoteService{
	
	public static List<Entity> listVotes(String channel) {
		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

		Query<Entity> query = Query.newEntityQueryBuilder().setKind(TwitchChannelConstants.SHRINEVOTE)
				.setFilter( PropertyFilter.eq(TwitchChannelConstants.TWITCHCHANNEL, channel)).build();

		// Run the query and retrieve a list of matching entities
		QueryResults<Entity> results = datastore.run(query);
		List<Entity> entities = Lists.newArrayList(results);		
		return entities;
	}		
	
	public static Entity listNominations(String channel) {
		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
		Query<Entity> query = Query.newEntityQueryBuilder().setKind(TwitchChannelConstants.SHRINEVOTE)
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
	public static Entity findShrineVoteByEventId(long shrineChannelEventId, Transaction txn) {
	    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	    Query<Entity> query = Query.newEntityQueryBuilder()
	            .setKind(TwitchChannelConstants.SHRINEVOTE)
	            .setFilter(PropertyFilter.eq(TwitchChannelConstants.SHRINECHANNELEVENTID, shrineChannelEventId))
	            .build();
	    
	    // Check if a transaction is provided
	    QueryResults<Entity> results;
	    if (txn != null) {
	        // Execute the query in the context of the provided transaction
	        results = txn.run(query);
	    } else {
	        // Execute the query outside of a transaction
	        results = datastore.run(query);
	    }

	    List<Entity> entities = Lists.newArrayList(results);
	    if (entities.isEmpty()) {
	        return null;
	    } else {
	        return entities.get(0);
	    }
	}

}