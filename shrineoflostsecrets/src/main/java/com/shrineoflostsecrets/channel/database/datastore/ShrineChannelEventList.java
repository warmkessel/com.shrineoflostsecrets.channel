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
import com.google.cloud.datastore.StructuredQuery.Filter;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import com.google.common.collect.Lists;
import com.shrineoflostsecrets.channel.constants.TwitchChannelConstants;

public class ShrineChannelEventList {
	private static final Logger logger = LoggerFactory.getLogger(ShrineChannelEventList.class);

	public static List<Entity> listChanelEvents(String channel, String userName, Boolean ban, Boolean unlimitedSize){
		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
		// Run the query and retrieve a list of matching entities
		QueryResults<Entity> results = datastore.run(buildQuery(channel, userName, ban, unlimitedSize));
		List<Entity> entities = Lists.newArrayList(results);
		return entities;
	}

	private static Query<Entity> buildQuery(String channel, String userName, Boolean ban,
			Boolean unlimitedSize) {
		// Start with a basic filter for the mandatory 'DELETED' property
		Filter baseFilter = PropertyFilter.eq(TwitchChannelConstants.DELETED, false);

		// Add channel filter if provided
		if (channel != null && !channel.isEmpty() && !channel.equals("null")) {
			baseFilter = CompositeFilter.and(baseFilter,
					PropertyFilter.eq(TwitchChannelConstants.TWITCHCHANNEL, channel));
		}

		// Add userName filter if provided
		if (userName != null && !userName.isEmpty() && !userName.equals("null")) {
			baseFilter = CompositeFilter.and(baseFilter,
					PropertyFilter.eq(TwitchChannelConstants.TWITCHUSERNAME, userName));
		}

		// Add filters based on the ban flag

		if (ban != null && ban) {
			baseFilter = CompositeFilter.and(baseFilter, CompositeFilter.or(
					PropertyFilter.eq(TwitchChannelConstants.TWITCHEVENTTYPE, TwitchChannelConstants.ONUSERBAN),
					PropertyFilter.eq(TwitchChannelConstants.TWITCHEVENTTYPE, TwitchChannelConstants.ONDELETEMESSAGE)));
		} else if (ban != null) {
			baseFilter = CompositeFilter.and(baseFilter, CompositeFilter.or(
					PropertyFilter.eq(TwitchChannelConstants.TWITCHEVENTTYPE, TwitchChannelConstants.ONCHANNELMESSAGE),
					PropertyFilter.eq(TwitchChannelConstants.TWITCHEVENTTYPE, TwitchChannelConstants.ONUSERBAN),
					PropertyFilter.eq(TwitchChannelConstants.TWITCHEVENTTYPE, TwitchChannelConstants.ONDELETEMESSAGE)));
		}
		Query<Entity> query = Query.newEntityQueryBuilder().setKind(TwitchChannelConstants.SHRINECHANNELEVENT)
				.setFilter(baseFilter).setOrderBy(OrderBy.desc(TwitchChannelConstants.CREATEDDATE)).setLimit(500).build();
		
		if(unlimitedSize) {
		// Construct the query with the built filters
		query = Query.newEntityQueryBuilder().setKind(TwitchChannelConstants.SHRINECHANNELEVENT)
				.setFilter(baseFilter).setOrderBy(OrderBy.desc(TwitchChannelConstants.CREATEDDATE)).build();
		}
		
		return query;
	}
}