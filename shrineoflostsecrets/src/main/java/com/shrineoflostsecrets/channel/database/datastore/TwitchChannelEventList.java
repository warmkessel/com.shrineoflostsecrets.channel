package com.shrineoflostsecrets.channel.database.datastore;

import java.util.List;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.CompositeFilter;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import com.google.common.collect.Lists;
import com.shrineoflostsecrets.channel.constants.TwitchChannelConstants;

public class TwitchChannelEventList {
	public static List<Entity> listChanelEvents(String channel) {
		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

		Query<Entity> query = Query.newEntityQueryBuilder().setKind(TwitchChannelConstants.SHRINECHANNELEVENT)
				.setFilter(CompositeFilter.and(PropertyFilter.eq(TwitchChannelConstants.DELETED, false),
						PropertyFilter.eq(TwitchChannelConstants.TWITCHCHANNEL, channel), 
						CompositeFilter.or(
						PropertyFilter.eq(TwitchChannelConstants.TWITCHEVENTTYPE, TwitchChannelConstants.ONCHANNELMESSAGE),
						PropertyFilter.eq(TwitchChannelConstants.TWITCHEVENTTYPE, TwitchChannelConstants.ONCHANNELMESSAGEELEVATED),
						PropertyFilter.eq(TwitchChannelConstants.TWITCHEVENTTYPE, TwitchChannelConstants.ONDELETEMESSAAGE)
						)))
				.setOrderBy(OrderBy.asc(TwitchChannelConstants.CREATEDDATE)).build();
		
//		Query<Entity> query = Query.newEntityQueryBuilder().setKind(TwitchChannelConstants.SHRINECHANNELEVENT)
//				.setFilter(CompositeFilter.and(PropertyFilter.eq(TwitchChannelConstants.DELETED, false),
//						PropertyFilter.eq(TwitchChannelConstants.TWITCHCHANNEL, channel), 
//						CompositeFilter.or(
//						PropertyFilter.eq(TwitchChannelConstants.TWITCHEVENTTYPE, TwitchChannelConstants.ONCHANNELMESSAGE),
//						PropertyFilter.eq(TwitchChannelConstants.TWITCHEVENTTYPE, TwitchChannelConstants.ONCHANNELMESSAGEELEVATED),
//						PropertyFilter.eq(TwitchChannelConstants.TWITCHEVENTTYPE, TwitchChannelConstants.ONDELETEMESSAAGE)
//						)))
//				.setOrderBy(OrderBy.asc(TwitchChannelConstants.CREATEDDATE)).build();

		// Run the query and retrieve a list of matching entities
		QueryResults<Entity> results = datastore.run(query);
		List<Entity> entities = Lists.newArrayList(results);
		return entities;
	}
}