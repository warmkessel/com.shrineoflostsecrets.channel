package com.shrineoflostsecrets.channel.database.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.shrineoflostsecrets.channel.constants.Constants;
import com.shrineoflostsecrets.channel.constants.TwitchChannelConstants;


public class ShrineChannel extends BaseEntity implements Comparable<ShrineChannel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -361472214131790072L;
	private static final Logger logger = LoggerFactory.getLogger(ShrineChannel.class);


	private String twitchChannel = "";
	private String discordChannel = "";
	private String discordUserName = "";
	private String twitchUserName = "";
	private long twitchServiceType = 0;


	public ShrineChannel() {
	}

	public String getTwitchUserName() {
		return twitchUserName;
	}

	public void setTwitchUserName(String twitchUserName) {
		this.twitchUserName = twitchUserName;
	}

	public String getDiscordUserName() {
		return discordUserName;
	}

	public void setDiscordUserName(String discordUserName) {
		this.discordUserName = discordUserName;
	}
	
	public String getTwitchChannel() {
		return twitchChannel;
	}

	public void setTwitchChannel(String twitchChannel) {
		this.twitchChannel = twitchChannel;
	}

	public String getDiscordChannel() {
		return discordChannel;
	}

	public void setDiscordChannel(String discordUserName) {
		this.discordChannel = discordUserName;
	}

	public long getTwitchServiceType() {
		return twitchServiceType;
	}

	public void setTwitchServiceType(long twitchServiceType) {
		this.twitchServiceType = twitchServiceType;
	}

	

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		ShrineChannel other = (ShrineChannel) obj;
		return this.getKey().equals(other.getKey());
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + twitchChannel.hashCode();
		result = 31 * result + discordChannel.hashCode();
		result = 31 * result + discordUserName.hashCode();
		result = 31 * result + twitchChannel.hashCode();
		return result;
	}

	public void save() {
		Entity.Builder entity = Entity.newBuilder(getKey());
		entity.set(TwitchChannelConstants.DELETED, getDeleted())
				.set(TwitchChannelConstants.CREATEDDATE, getCreatedDate()).set(TwitchChannelConstants.UPDATEDDATE, getUpdatedDate())
				.set(TwitchChannelConstants.TWITCHCHANNEL, getTwitchChannel())
				.set(TwitchChannelConstants.DISCORDCHANNEL, getDiscordChannel())
				.set(TwitchChannelConstants.TWITCHUSERNAME, getTwitchUserName())
				.set(TwitchChannelConstants.DISCORDUSERNAME, getDiscordUserName())
				.set(TwitchChannelConstants.TWITCHSERVICETYPE, getTwitchServiceType())
				.build();
		getDatastore().put(entity.build());
	}

	public void loadShrineChannel(String key) {
		 loadShrineChannel(Long.parseLong(key));
	}

	public void loadShrineChannel(long key) {
		 loadShrineChannel(Key.newBuilder(Constants.SHRINEOFLOSTSECRETS, TwitchChannelConstants.SHRINECHANNEL, key).build());
	}

	public void loadShrineChannel(Key key) {
		// log.info("key " + key.toString());
		Entity twitchStream = getDatastore().get(key);
		loadFromEntity(twitchStream);
	}

	public void loadFromEntity(Entity entity) {
		super.loadFromEntity(entity);
		if (null != entity) {
			setTwitchChannel(entity.getString(TwitchChannelConstants.TWITCHCHANNEL));
			setDiscordChannel(entity.getString(TwitchChannelConstants.DISCORDCHANNEL));
			setTwitchUserName(entity.getString(TwitchChannelConstants.TWITCHUSERNAME));
			setDiscordUserName(entity.getString(TwitchChannelConstants.DISCORDUSERNAME));
			setTwitchServiceType(entity.getLong(TwitchChannelConstants.TWITCHSERVICETYPE));
		}
	}

	public String toString() {
		return "TwitchStream{" + "" + Constants.KEY + "='" + getKeyString() + '\'' 
				+ ", " + TwitchChannelConstants.TWITCHCHANNEL + "\"='" + twitchChannel + '\''
				+ ", " + TwitchChannelConstants.DISCORDCHANNEL + "\"='" + discordChannel + '\''
				+ ", " + TwitchChannelConstants.TWITCHUSERNAME + "\"='" + twitchUserName + '\''
				+ ", " + TwitchChannelConstants.DISCORDUSERNAME + "\"='" +  discordUserName + '\''
				+ ", " + TwitchChannelConstants.TWITCHSERVICETYPE + "\"='" + twitchServiceType + '\''
				+ '}';
	}

	public int compareTo(ShrineChannel other) {
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
		return TwitchChannelConstants.SHRINECHANNEL;
	}
}
