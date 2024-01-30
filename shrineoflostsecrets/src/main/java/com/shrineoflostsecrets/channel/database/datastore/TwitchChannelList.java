package com.shrineoflostsecrets.channel.database.datastore;

import java.util.List;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.common.collect.Lists;
import com.shrineoflostsecrets.channel.constants.TwitchChannelConstants;


public class TwitchChannelList{
	
	public static List<Entity> listChanels() {
		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

//				Query<Entity> query = Query.newEntityQueryBuilder().setKind(TwitchChannelConstants.SHRINECHANNEL)
//						.setFilter(CompositeFilter.and(
//								PropertyFilter.eq(TwitchChannelConstants.DELETED, false)))
		Query<Entity> query = Query.newEntityQueryBuilder().setKind(TwitchChannelConstants.SHRINECHANNEL)
						.build();
		// Run the query and retrieve a list of matching entities
		QueryResults<Entity> results = datastore.run(query);
		List<Entity> entities = Lists.newArrayList(results);		
		return entities;
	}		
}