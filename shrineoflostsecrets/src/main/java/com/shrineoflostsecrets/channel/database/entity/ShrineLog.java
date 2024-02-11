package com.shrineoflostsecrets.channel.database.entity;

import com.google.cloud.datastore.Entity;
import com.shrineoflostsecrets.channel.constants.TwitchChannelConstants;
import com.shrineoflostsecrets.channel.enumerations.ShrineDebug;
import com.shrineoflostsecrets.channel.util.DebugMode;

public class ShrineLog extends BaseEntity {

	private static final long serialVersionUID = 830510474461126515L;

	private boolean debug = false;
	private String message = "";
	private String eventType = ""; // Added support for eventType

	public ShrineLog() {
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public void setDebug(ShrineDebug shrineDebug) {
		setDebug(shrineDebug.getState());
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	// Method to set the eventType
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	// Method to get the eventType
	public String getEventType() {
		return eventType;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		ShrineLog other = (ShrineLog) obj;
		return this.getKey().equals(other.getKey());
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + message.hashCode();
		result = 31 * result + eventType.hashCode(); // Include eventType in hashCode
		return result;
	}

	public void save() {
		Entity.Builder entityBuilder = Entity.newBuilder(getKey()).set(TwitchChannelConstants.DELETED, getDeleted())
				.set(TwitchChannelConstants.CREATEDDATE, getCreatedDate())
				.set(TwitchChannelConstants.UPDATEDDATE, getUpdatedDate()).set(TwitchChannelConstants.DEBUG, isDebug())
				.set(TwitchChannelConstants.MESSAGE, getMessage())
				.set(TwitchChannelConstants.EVENTTYPE, getEventType()); // Save eventType

		getDatastore().put(entityBuilder.build());

	}

	public void loadFromEntity(Entity entity) {
		super.loadFromEntity(entity);
		setMessage(entity.getString(TwitchChannelConstants.MESSAGE));
		setDebug(entity.getBoolean(TwitchChannelConstants.DEBUG));
		setEventType(entity.getString(TwitchChannelConstants.EVENTTYPE));

	}

	@Override
	public String toString() {
		return "ShrineLog{" + "key='" + getKeyString() + '\'' + ", message='" + message + '\'' + ", debug='" + debug
				+ '\'' + ", eventType='" + eventType + '\'' + '}';
	}

	public static void log(String eventType, ShrineDebug shrineDebug, String message) { // Include eventType parameter
		if (DebugMode.isDebug(shrineDebug)) {
			ShrineLog log = new ShrineLog();
			log.setDebug(shrineDebug);
			log.setMessage(message);
			log.setEventType(eventType); // Set the eventType
			log.save();
		}
	}
	public String getEventKind() {
		return TwitchChannelConstants.SHRINLOG;
	}
}
