package com.shrineoflostsecrets.channel.database.entity;

import java.io.Serializable;
import java.util.Objects;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.shrineoflostsecrets.channel.constants.BaseEntityConstants;

public  abstract class BaseEntity implements  Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7160882192926634429L;
//	private static final Logger logger = LoggerFactory.getLogger(BaseEntity.class);


	private Key key = null;
	private boolean deleted = false;
	private Timestamp createdDate = Timestamp.now();
	private Timestamp updatedDate = Timestamp.now();

	public BaseEntity() {

	}

	public BaseEntity(Key key, boolean deleted, Timestamp createdDate, Timestamp updatedDate) {

		this.key = Objects.requireNonNull(key);
		this.deleted = deleted;
		this.createdDate = Objects.requireNonNull(createdDate);
		this.updatedDate = Objects.requireNonNull(updatedDate);
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + key.hashCode();
		result = 31 * result + createdDate.hashCode();
		result = 31 * result + updatedDate.hashCode();
		return result;
	}
	public void loadFromEntity(Entity entity) {		  
		if(null != entity) {
			setKey(entity.getKey());
			setDeleted(entity.getBoolean(BaseEntityConstants.DELETED));
			setCreatedDate(entity.getTimestamp(BaseEntityConstants.CREATEDDATE));
			setUpdatedDate(entity.getTimestamp(BaseEntityConstants.UPDATEDDATE));
		}	
	}
	public abstract String getEventKind();
	
	public Key getKey() {
		 if(null == key) {
			 KeyFactory keyFactory = getDatastore().newKeyFactory().setKind(getEventKind());  
			 key = getDatastore().allocateId(keyFactory.newKey());
		 }
		return key;
	}
	public Long getId() {
		return getKeyLong();
	}
	public Long getKeyLong() {
		//log.info("getKey " + ((getKey() == null) ? "null" : "not Null"));
		return getKey().getId();
	}
	public String getKeyString() {
		return Long.toString(getKeyLong());
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public boolean getDeleted() {
		return this.deleted;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate() {
		setUpdatedDate(Timestamp.now());
	}
	
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	protected Datastore getDatastore(){
	    // Attempt to get default datastore instance
return  DatastoreOptions.getDefaultInstance().getService();
//
//	    try {
//	        // Load default application credentials
//	        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
//
//	        // Refresh credentials if they are expired
//	        credentials.refreshIfExpired();
//
//	        // Create a new Datastore instance with the updated credentials
//	        DatastoreOptions options = DatastoreOptions.newBuilder().setCredentials(credentials).build();
//	        datastore = options.getService();
//	    } catch (IOException e) {
//	        // Log the exception or handle it as per your application's requirements
//	        logger.error("Error obtaining Datastore credentials: " + e.getMessage(), e);
//	    }
	}
}
