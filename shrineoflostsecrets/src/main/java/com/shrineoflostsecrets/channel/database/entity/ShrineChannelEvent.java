package com.shrineoflostsecrets.channel.database.entity;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.shrineoflostsecrets.channel.constants.Constants;
import com.shrineoflostsecrets.channel.constants.TwitchChannelConstants;
import com.shrineoflostsecrets.channel.database.datastore.ShrineChannelEventServcie;
import com.shrineoflostsecrets.channel.database.datastore.ShrineUserService;

public class ShrineChannelEvent extends BaseEntity implements Comparable<ShrineChannelEvent> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -361472214131790072L;
	// private static final Logger log = Logger.getLogger(ShrineChannelEntity.class.getName());


	private String eventType = "";
	private String eventId = "";
	private String twitchChannel = "";
	private String twitchUser = "";
	private String redeemTime = "";
	private String rewared = "";
	private String message = "";
	private String elevatedChatPayment = "";
	

	
	public ShrineChannelEvent() {
	}

	public boolean isValid() {
		return (getTwitchChannel() != null && getTwitchChannel().length() > 0);
	}	
	public void loadShrineOrigEventId(String msgId) {
		// log.info("key " + key.toString());
		loadFromEntity(ShrineChannelEventServcie.fetchMsgId(msgId));

	}
	
	public void loadTwitchStream(Key key) {
		// log.info("key " + key.toString());
		Entity event = getDatastore().get(key);
		loadFromEntity(event);

	}
	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getTwitchChannel() {
		return twitchChannel;
	}

	public void setTwitchChannel(String twitchChannel) {
		this.twitchChannel = twitchChannel;
	}

	public String getTwitchUser() {
		return twitchUser;
	}

	public void setTwitchUser(String twitchUser) {
		this.twitchUser = twitchUser;
	}

	public String getRedeemTime() {
		return redeemTime;
	}

	public void setRedeemTime(String redeemTime) {
		this.redeemTime = redeemTime;
	}

	public String getRewared() {
		return rewared;
	}

	public void setRewared(String rewared) {
		this.rewared = rewared;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getElevatedChatPayment() {
		return elevatedChatPayment;
	}

	public void setElevatedChatPayment(String elevatedChatPayment) {
		this.elevatedChatPayment = elevatedChatPayment;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		ShrineChannelEvent other = (ShrineChannelEvent) obj;
		return this.getKey().equals(other.getKey());
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + eventType.hashCode();
		result = 31 * result + twitchChannel.hashCode();
		result = 31 * result + twitchUser.hashCode();
		result = 31 * result + redeemTime.hashCode();
		result = 31 * result + rewared.hashCode();
		result = 31 * result + message.hashCode();
		result = 31 * result + elevatedChatPayment.hashCode();
		
		return result;
	}

	public void save() {
		Entity.Builder entity = Entity.newBuilder(getKey());
        setUpdatedDate();
        entity.set(TwitchChannelConstants.DELETED, getDeleted())
				.set(TwitchChannelConstants.CREATEDDATE, getCreatedDate()).set(TwitchChannelConstants.UPDATEDDATE, getUpdatedDate())
				.set(TwitchChannelConstants.TWITCHEVENTTYPE, getEventType())
				.set(TwitchChannelConstants.TWITCHEVENTID, getEventId())
				.set(TwitchChannelConstants.TWITCHREDEEMTIME, getRedeemTime())
				.set(TwitchChannelConstants.TWITCHREWARDED, getRewared())
				.set(TwitchChannelConstants.MESSAGE, getMessage())
				.set(TwitchChannelConstants.TWITCHELEVATEDCHATPAYMENT, getElevatedChatPayment())
				.set(TwitchChannelConstants.TWITCHCHANNEL, getTwitchChannel())
				.set(TwitchChannelConstants.TWITCHUSERNAME, getTwitchUser())
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
			setEventType(entity.getString(TwitchChannelConstants.TWITCHEVENTTYPE));
			setEventId(entity.getString(TwitchChannelConstants.TWITCHEVENTID));
			setRedeemTime(entity.getString(TwitchChannelConstants.TWITCHREDEEMTIME));
			setRewared(entity.getString(TwitchChannelConstants.TWITCHREWARDED));
			setMessage(entity.getString(TwitchChannelConstants.MESSAGE));
			setElevatedChatPayment(entity.getString(TwitchChannelConstants.TWITCHELEVATEDCHATPAYMENT));
			setTwitchChannel(entity.getString(TwitchChannelConstants.TWITCHCHANNEL));
			setTwitchUser(entity.getString(TwitchChannelConstants.TWITCHUSERNAME));
		}
	}

	public String toString() {
		return "SHRINECHANNELEVENT{" + "" + Constants.KEY + "='" + getKeyString() + '\'' 
				+ ", " + TwitchChannelConstants.TWITCHEVENTTYPE + "\"='" + eventType + '\''
				+ ", " + TwitchChannelConstants.TWITCHEVENTID + "\"='" + eventId + '\''
				+ ", " + TwitchChannelConstants.TWITCHREDEEMTIME + "\"='" + redeemTime + '\''
				+ ", " + TwitchChannelConstants.TWITCHREWARDED + "\"='" + rewared + '\''
				+ ", " + TwitchChannelConstants.MESSAGE + "\"='" + message + '\''
				+ ", " + TwitchChannelConstants.TWITCHELEVATEDCHATPAYMENT + "\"='" + elevatedChatPayment + '\''
				+ ", " + TwitchChannelConstants.TWITCHCHANNEL + "\"='" + twitchChannel + '\''
				+ ", " + TwitchChannelConstants.TWITCHUSERNAME + "\"='" + twitchUser + '\''
				+ '}';
	}

	public int compareTo(ShrineChannelEvent other) {
		if (this.eventId == null && other.eventId == null) {
			return 0;
		} else if (this.eventId == null) {
			return -1;
		} else if (other.eventId == null) {
			return 1;
		} else {
			return this.eventId.compareTo(other.eventId);
		}
	}

	public String getEventKind() {
		return TwitchChannelConstants.SHRINECHANNELEVENT;
	}
}
