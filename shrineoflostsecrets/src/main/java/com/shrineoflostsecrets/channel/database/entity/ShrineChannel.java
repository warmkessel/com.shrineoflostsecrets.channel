package com.shrineoflostsecrets.channel.database.entity;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.shrineoflostsecrets.channel.constants.Constants;
import com.shrineoflostsecrets.channel.constants.TwitchChannelConstants;


public class ShrineChannel extends BaseEntity implements Comparable<ShrineChannel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -361472214131790072L;
	// private static final Logger log = Logger.getLogger(TwitchStream.class.getName());


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

	public String getDiscordStream() {
		return discordChannel;
	}

	public void setDiscordStream(String discordUserName) {
		this.discordUserName = discordUserName;
	}
	
	public void loadTwitchStream(Key key) {
		// log.info("key " + key.toString());
		Entity event = getDatastore().get(key);
		loadFromEntity(event);

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
				.set(TwitchChannelConstants.DISCORDCHANNEL, getDiscordStream())
				.set(TwitchChannelConstants.TWITCHUSERNAME, getTwitchUserName())
				.set(TwitchChannelConstants.DISCORDUSERNAME, getDiscordUserName())
				.set(TwitchChannelConstants.TWITCHSERVICETYPE, getTwitchServiceType())
				.build();
		getDatastore().put(entity.build());
	}

	public void loadEvent(String key) {
		loadEvent(Long.parseLong(key));
	}

	public void loadEvent(long key) {
		loadEvent(Key.newBuilder(Constants.SHRINEOFLOSTSECRETS, TwitchChannelConstants.SHRINECHANNELEVENT, key).build());
	}

	public void loadEvent(Key key) {
		// log.info("key " + key.toString());
		Entity twitchStream = getDatastore().get(key);
		loadFromEntity(twitchStream);

	}

	public void loadFromEntity(Entity entity) {
		super.loadFromEntity(entity);
		if (null != entity) {
			setTwitchChannel(entity.getString(TwitchChannelConstants.TWITCHCHANNEL));
			setDiscordStream(entity.getString(TwitchChannelConstants.DISCORDCHANNEL));
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
