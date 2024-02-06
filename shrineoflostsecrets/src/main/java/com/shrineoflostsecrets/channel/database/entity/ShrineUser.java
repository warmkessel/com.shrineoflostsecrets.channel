package com.shrineoflostsecrets.channel.database.entity;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.shrineoflostsecrets.channel.constants.Constants;
import com.shrineoflostsecrets.channel.constants.TwitchChannelConstants;
import com.shrineoflostsecrets.channel.database.datastore.ShrineUserService;
import com.google.cloud.Timestamp;

public class ShrineUser extends BaseEntity implements Comparable<ShrineUser> {

	private static final long serialVersionUID = -1048678659663783295L;
	/**
	 * 
	 */
	// private static final Logger log =
	// Logger.getLogger(TwitchStream.class.getName());

	private String twitchUserName = "";
	private String otpPass = "";
	private Timestamp shrineLastLongedIn = Timestamp.now();

	public ShrineUser() {
	}

	public void loadShrinePass(String pass) {
		loadFromEntity(ShrineUserService.fetchPass(pass));
	}

	public void loadShrineUserName(String pass) {
		loadFromEntity(ShrineUserService.fetchUser(pass));
	}
	public void loadShrineUser(String key) {
		loadShrineUser(Long.parseLong(key));
	}
	public void loadShrineUser(long key) {
		loadShrineUser(
				Key.newBuilder(Constants.SHRINEOFLOSTSECRETS, TwitchChannelConstants.SHRINEUSER, key).build());
	}

	public void loadShrineUser(Key key) {
		// log.info("key " + key.toString());
		Entity twitchStream = getDatastore().get(key);
		loadFromEntity(twitchStream);
	}

	public String getTwitchUserName() {
		return twitchUserName;
	}

	public void setTwitchUserName(String twitchUserName) {
		this.twitchUserName = twitchUserName;
	}

	public String getOtpPass() {
		return otpPass;
	}

	public void setOtpPass(String otpPass) {
		this.otpPass = otpPass;
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
		result = 31 * result + otpPass.hashCode();
		result = 31 * result + shrineLastLongedIn.hashCode();
		return result;
	}

	public void save() {
		Entity.Builder entity = Entity.newBuilder(getKey());
		entity.set(TwitchChannelConstants.DELETED, getDeleted())
				.set(TwitchChannelConstants.CREATEDDATE, getCreatedDate())
				.set(TwitchChannelConstants.UPDATEDDATE, getUpdatedDate())
				.set(TwitchChannelConstants.TWITCHUSERNAME, getTwitchUserName())
				.set(TwitchChannelConstants.SHRINELASTLOGGED, getShrineLastLongedIn())
				.set(TwitchChannelConstants.OTPPASS, getOtpPass()).build();
		getDatastore().put(entity.build());
	}

	public void loadEvent(String key) {
		loadEvent(Long.parseLong(key));
	}

	public void loadEvent(long key) {
		loadEvent(
				Key.newBuilder(Constants.SHRINEOFLOSTSECRETS, TwitchChannelConstants.SHRINECHANNELEVENT, key).build());
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
			setOtpPass(entity.getString(TwitchChannelConstants.OTPPASS));
			setShrineLastLongedIn(entity.getTimestamp(TwitchChannelConstants.SHRINELASTLOGGED));
		}
	}

	public String toString() {
		return "TwitchStream{" + "" + Constants.KEY + "='" + getKeyString() + '\'' + ", "
				+ TwitchChannelConstants.TWITCHUSERNAME + "\"='" + twitchUserName + '\''
				+ TwitchChannelConstants.SHRINELASTLOGGED + "\"='" + shrineLastLongedIn + '\''
				+ TwitchChannelConstants.OTPPASS + "\"='" + otpPass + '\'' + '}';
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
		return TwitchChannelConstants.SHRINEUSER;
	}
}
