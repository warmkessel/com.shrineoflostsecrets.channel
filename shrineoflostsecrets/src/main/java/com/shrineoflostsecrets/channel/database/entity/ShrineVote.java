package com.shrineoflostsecrets.channel.database.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.shrineoflostsecrets.channel.constants.Constants;
import com.shrineoflostsecrets.channel.constants.TwitchChannelConstants;
import com.shrineoflostsecrets.channel.database.datastore.ShrineChannelService;
import com.shrineoflostsecrets.channel.enumerations.ShrineVoteCategoryEnum;

public class ShrineVote extends BaseEntity implements Comparable<ShrineVote> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -361472214131790072L;
	private static final Logger logger = LoggerFactory.getLogger(ShrineVote.class);

	private boolean safe = false;
	private long shrineChannelEventId = 0l;
	private String twitchUser = "";
	private String twitchChannel = "";
	private Timestamp voteDate = Timestamp.now();
	private long amount = 0l;
	private long shrineVoteCategoryId = 0;

	public ShrineVote() {
	}

	public String getTwitchChannel() {
		return twitchChannel;
	}

	public void setTwitchChannel(String twitchChannel) {
		this.twitchChannel = twitchChannel;
	}

	public Timestamp getVoteDate() {
		return voteDate;
	}

	public void setVoteDate() {
		setVoteDate(Timestamp.now());
	}

	public void setVoteDate(Timestamp voteDate) {
		this.voteDate = voteDate;
	}

	public void setVoteDate(Date voteDate) {
		setVoteDate(Timestamp.of(voteDate));
	}
	public boolean isSafe() {
		return safe;
	}

	public void setSafe(boolean safe) {
		this.safe = safe;
	}

	public void setVoteDate(String voteDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			// Parse the string to a Date object
			Date parsedDate = dateFormat.parse(voteDate);

			// Convert Date to Google Cloud Timestamp
			Timestamp timestamp = Timestamp.of(parsedDate);

			// Call the setTwitchLastStart method that accepts a Google Cloud Timestamp
			setVoteDate(timestamp);

		} catch (ParseException e) {
			logger.info("Error parsing the date string: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public String getTwitchUser() {
		return twitchUser;
	}

	public void setTwitchUser(String twitchUser) {
		this.twitchUser = twitchUser;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public long getShrineVoteCategoryId() {
		return shrineVoteCategoryId;
	}

	public void setShrineVoteCategoryId(long shrineVoteCategoryId) {
		this.shrineVoteCategoryId = shrineVoteCategoryId;
	}

	public ShrineVoteCategoryEnum getShrineVoteCategory() {
		return ShrineVoteCategoryEnum.findById((int) getShrineVoteCategoryId());
	}

	public void setShrineVoteCategory(ShrineVoteCategoryEnum shrineVoteCategory) {
		setShrineVoteCategory(shrineVoteCategory.getId());
	}
	public void setShrineVoteCategory(long shrineVoteCategoryId) {
		this.shrineVoteCategoryId = shrineVoteCategoryId;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		ShrineVote other = (ShrineVote) obj;
		return this.getKey().equals(other.getKey());
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + twitchUser.hashCode();
		result = 31 * result + twitchChannel.hashCode();
		result = 31 * result + voteDate.hashCode();
		result = 31 * result + (int) amount;
		return result;
	}

	public void save() {
		Entity.Builder entity = Entity.newBuilder(getKey());
		entity.set(TwitchChannelConstants.DELETED, getDeleted())
				.set(TwitchChannelConstants.CREATEDDATE, getCreatedDate())
				.set(TwitchChannelConstants.UPDATEDDATE, getUpdatedDate())
				.set(TwitchChannelConstants.TWITCHCHANNEL, getTwitchChannel())
				.set(TwitchChannelConstants.TWITCHUSERNAME, getTwitchUser())
				.set(TwitchChannelConstants.SHRINEVOTEAMOUNT, getAmount())
				.set(TwitchChannelConstants.SHRINEVOTEDATE, getVoteDate())
				.set(TwitchChannelConstants.SHRINEVOTECATEGORY, getShrineVoteCategoryId())
				.set(TwitchChannelConstants.SHRINECHANNELEVENTID, getShrineChannelEventId())
				.set(TwitchChannelConstants.SAFE, isSafe());
		getDatastore().put(entity.build());
	}

	public void loadShrineVote(Key key) {
		// log.info("key " + key.toString());
		Entity twitchStream = getDatastore().get(key);
		loadFromEntity(twitchStream);
	}

	public void loadFromEntity(Entity entity) {
		super.loadFromEntity(entity);
		if (null != entity) {
			setTwitchChannel(entity.getString(TwitchChannelConstants.TWITCHCHANNEL));
			setTwitchUser(entity.getString(TwitchChannelConstants.TWITCHUSERNAME));
			setAmount(entity.getLong(TwitchChannelConstants.SHRINEVOTEAMOUNT));
			setVoteDate(entity.getTimestamp(TwitchChannelConstants.SHRINEVOTEDATE));
			setShrineVoteCategoryId(entity.getLong(TwitchChannelConstants.SHRINEVOTECATEGORY));
			setShrineChannelEventId(entity.getLong(TwitchChannelConstants.SHRINECHANNELEVENTID));
			if (entity.contains(TwitchChannelConstants.SAFE)) {
				setSafe(entity.getBoolean(TwitchChannelConstants.SAFE));
			}

		}
	}

	public String toString() {
		return "TwitchStream{" + "" + Constants.KEY + "='" + getKeyString() + '\'' + ", "
				+ TwitchChannelConstants.TWITCHCHANNEL + "\"='" + twitchChannel + '\'' + ", "
				+ TwitchChannelConstants.TWITCHUSERNAME + "\"='" + twitchUser + '\'' + ", "
				+ TwitchChannelConstants.SHRINEVOTEDATE + "\"='" + voteDate + '\'' + ", "
				+ TwitchChannelConstants.SHRINEVOTEAMOUNT + "\"='" + amount + '\'' + ", "
				+ TwitchChannelConstants.SHRINEVOTECATEGORY + "\"='" + shrineVoteCategoryId + '\'' + ", "
				+ TwitchChannelConstants.SAFE + "\"='" + safe + '\'' + ", "
				+ TwitchChannelConstants.SHRINECHANNELEVENTID + "\"='" + shrineChannelEventId + '\'' + '}';
	}

	public int compareTo(ShrineVote other) {
		if (this.twitchChannel == null && other.twitchChannel == null) {
			return 0;
		} else if (this.twitchChannel == null) {
			return -1;
		} else if (other.twitchChannel == null) {
			return 1;
		} else {
			return this.twitchChannel.compareTo(other.twitchChannel);
		}
	}

	public String getEventKind() {
		return TwitchChannelConstants.SHRINEVOTE;
	}

	public static ShrineVote getShrineChannelName(String channelName) {
		Entity entities = ShrineChannelService.getShrineChannelName(channelName);
		if (entities == null) {
			ShrineVote theReturn = new ShrineVote();
			theReturn.save();
			return theReturn;
		} else {
			ShrineVote theReturn = new ShrineVote();
			theReturn.loadFromEntity(entities);
			return theReturn;
		}
	}

	public long getShrineChannelEventId() {
		return shrineChannelEventId;
	}

	public ShrineChannelEvent getShrineChannelEvent() {
		ShrineChannelEvent sce = new ShrineChannelEvent();
		sce.loadEvent(getShrineChannelEventId());
		return sce;
	}

	public void setShrineChannelEventId(long shrineChannelEventId) {
		this.shrineChannelEventId = shrineChannelEventId;
	}

	
}
