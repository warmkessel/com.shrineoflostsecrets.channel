package com.shrineoflostsecrets.channel.database.datastore;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static final Logger logger = LoggerFactory.getLogger(TwitchChannelEventList.class);

	public static List<Entity> listChanelEvents(String channel) {
		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
		Query<Entity> query = Query.newEntityQueryBuilder().setKind(TwitchChannelConstants.SHRINECHANNELEVENT)
				.setFilter(CompositeFilter.and(PropertyFilter.eq(TwitchChannelConstants.DELETED, false),
						PropertyFilter.eq(TwitchChannelConstants.TWITCHCHANNEL, channel), 
						CompositeFilter.or(
						PropertyFilter.eq(TwitchChannelConstants.TWITCHEVENTTYPE, TwitchChannelConstants.ONCHANNELMESSAGE),
						PropertyFilter.eq(TwitchChannelConstants.TWITCHEVENTTYPE, TwitchChannelConstants.ONCHANNELMESSAGEELEVATED),
						PropertyFilter.eq(TwitchChannelConstants.TWITCHEVENTTYPE, TwitchChannelConstants.ONUSERBAN),
						PropertyFilter.eq(TwitchChannelConstants.TWITCHEVENTTYPE, TwitchChannelConstants.ONDELETEMESSAAGE)
						)))
				.setOrderBy(OrderBy.desc(TwitchChannelConstants.CREATEDDATE))
				.setLimit(500).build();
		
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
		logger.info("channel size: {})", entities.size());

		return entities;
	}
	
	public static List<Entity> listChanelEventsDeleted(String channel) {
		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
		Query<Entity> query = Query.newEntityQueryBuilder().setKind(TwitchChannelConstants.SHRINECHANNELEVENT)
				.setFilter(CompositeFilter.and(PropertyFilter.eq(TwitchChannelConstants.DELETED, false),
						PropertyFilter.eq(TwitchChannelConstants.TWITCHCHANNEL, channel), 
						CompositeFilter.or(
						PropertyFilter.eq(TwitchChannelConstants.TWITCHEVENTTYPE, TwitchChannelConstants.ONUSERBAN),
						PropertyFilter.eq(TwitchChannelConstants.TWITCHEVENTTYPE, TwitchChannelConstants.ONDELETEMESSAAGE)
						)))
				.setOrderBy(OrderBy.desc(TwitchChannelConstants.CREATEDDATE))
				.setLimit(100).build();
		
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
		logger.info("channel size: {})", entities.size());

		return entities;
	}
}