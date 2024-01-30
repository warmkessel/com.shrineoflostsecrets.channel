package com.shrineoflostsecrets.channel.database.entity;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.shrineoflostsecrets.channel.constants.Constants;
import com.shrineoflostsecrets.channel.constants.TwitchChannelConstants;
import com.google.cloud.Timestamp;

public class ShrineUser extends BaseEntity implements Comparable<ShrineUser> {

	private static final long serialVersionUID = -1048678659663783295L;
	/**
	 * 
	 */
	// private static final Logger log = Logger.getLogger(TwitchStream.class.getName());

	private String discordUserName = "";
	private String twitchUserName = "";
	private boolean twitchFollow = false;
	private boolean twitchSubscribe = false;
	private Timestamp shrineLastLongedIn = Timestamp.now();
	

	public ShrineUser() {
	}

	public void loadShrineUserKey(Key key) {
		// log.info("key " + key.toString());
		Entity event = getDatastore().get(key);
		loadFromEntity(event);
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
	public boolean getTwitchFollow() {
		return twitchFollow;
	}

	public void setTwitchFollow(boolean twitchFollow) {
		this.twitchFollow = twitchFollow;
	}
	
	public boolean getTwitchSubscribe() {
		return twitchSubscribe;
	}

	public void setTwitchSubscribe(boolean twitchSubscribe) {
		this.twitchSubscribe = twitchSubscribe;
	}
	public Timestamp getShrineLastLongedIn() {
		return shrineLastLongedIn;
	}

	public void setShrineLastLongedIn() {
		setShrineLastLongedIn(Timestamp.now());
		}
	public void setShrineLastLongedIn(Timestamp shrineLastLongedIn) {
		this.shrineLastLongedIn = shrineLastLongedIn;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		ShrineUser other = (ShrineUser) obj;
		return this.getKey().equals(other.getKey());
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + twitchUserName.hashCode();
		result = 31 * result + discordUserName.hashCode();
		result = 31 * result + shrineLastLongedIn.hashCode();
		
		return result;
	}

	
	public void save() {
		Entity.Builder entity = Entity.newBuilder(getKey());
		entity.set(TwitchChannelConstants.DELETED, getDeleted())
				.set(TwitchChannelConstants.CREATEDDATE, getCreatedDate()).set(TwitchChannelConstants.UPDATEDDATE, getUpdatedDate())
				.set(TwitchChannelConstants.TWITCHUSERNAME, getTwitchUserName())
				.set(TwitchChannelConstants.DISCORDUSERNAME, getDiscordUserName())
				.set(TwitchChannelConstants.TWITCHFOLLOW, getTwitchFollow())
				.set(TwitchChannelConstants.TWITCHSUBSCRIBE, getTwitchSubscribe())
				.set(TwitchChannelConstants.SHRINELASTLOGGED, getShrineLastLongedIn())
				
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
			setTwitchUserName(entity.getString(TwitchChannelConstants.TWITCHUSERNAME));
			setDiscordUserName(entity.getString(TwitchChannelConstants.DISCORDUSERNAME));
			setTwitchFollow(entity.getBoolean(TwitchChannelConstants.TWITCHFOLLOW));
			setTwitchSubscribe(entity.getBoolean(TwitchChannelConstants.TWITCHSUBSCRIBE));
			setShrineLastLongedIn(entity.getTimestamp(TwitchChannelConstants.SHRINELASTLOGGED));
		}
	}

	public String toString() {
		return "TwitchStream{" + "" + Constants.KEY + "='" + getKeyString() + '\'' + ", " 
				+ TwitchChannelConstants.TWITCHUSERNAME + "\"='" + discordUserName + '\''
				+ TwitchChannelConstants.DISCORDUSERNAME + "\"='" + twitchUserName + '\''
				+ TwitchChannelConstants.TWITCHFOLLOW + "\"='" + twitchFollow + '\''
				+ TwitchChannelConstants.TWITCHSUBSCRIBE + "\"='" + twitchSubscribe + '\''
				+ TwitchChannelConstants.SHRINELASTLOGGED + "\"='" + shrineLastLongedIn + '\''
				+ '}';
	}
	
	public int compareTo(ShrineUser other) {
		if (this.twitchUserName == null && other.twitchUserName == null) {
			return 0;
		} else if (this.twitchUserName == null) {
			return -1;
		} else if (other.twitchUserName == null) {
			return 1;
		} else {
			return this.twitchUserName.compareTo(other.twitchUserName);
		}
	}

	public String getEventKind() {
		return TwitchChannelConstants.SHRINECHANNELEVENT;
	}
}
