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
	private Timestamp twitchLastStart = Timestamp.now();
	private Timestamp twitchLastEnd = Timestamp.now();

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

	public Timestamp getTwitchLastStart() {
		return twitchLastStart;
	}

	public String getTwitchLastStartString() {
		Date date = getTwitchLastStart().toDate();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return dateFormat.format(date);
	}

	public void setTwitchLastStart(Timestamp lastStart) {
		this.twitchLastStart = lastStart;
	}

	public void setTwitchLastStart(Date lastStart) {
		setTwitchLastStart(Timestamp.of(lastStart));
	}
	public void setTwitchLastStart(String lastStart) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            // Parse the string to a Date object
            Date parsedDate = dateFormat.parse(lastStart);
            
            // Convert Date to Google Cloud Timestamp
            Timestamp timestamp = Timestamp.of(parsedDate);
            
            // Call the setTwitchLastStart method that accepts a Google Cloud Timestamp
            setTwitchLastStart(timestamp);

        } catch (ParseException e) {
            logger.info("Error parsing the date string: " + e.getMessage());
            e.printStackTrace();
        }
    }
	public void setTwitchLastStart() {
		setTwitchLastStart(Timestamp.now());
	}

	public Timestamp getTwitchLastEnd() {
		return twitchLastEnd;
	}
	public String getTwitchLastEndString() {
		Date date = getTwitchLastEnd().toDate();
        //logger.info("date " + date);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		
       // logger.info("dateFormat.format(date) " + dateFormat.format(date));

		return dateFormat.format(date);
	}

	public void setTwitchLastEnd(Timestamp lastLastEnd) {
		this.twitchLastEnd = lastLastEnd;
	}

	public void setTwitchLastEnd(String lastStart) {
        //logger.info("lastStart " + lastStart);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            // Parse the string to a Date object
            Date parsedDate = dateFormat.parse(lastStart);
            
           // logger.info("parsedDate " + parsedDate);

            
            // Convert Date to Google Cloud Timestamp
            Timestamp timestamp = Timestamp.of(parsedDate);
            
            //logger.info("timestamp " + timestamp);

            // Call the setTwitchLastStart method that accepts a Google Cloud Timestamp
            setTwitchLastEnd(timestamp);

        } catch (ParseException e) {
            logger.info("Error parsing the date string: " + e.getMessage());
            e.printStackTrace();
        }
    }
	public void setTwitchLastEnd() {
		setTwitchLastEnd(Timestamp.now());
	}
	public void setTwitchLastEnd(Date lastLastEnd) {
		setTwitchLastEnd(Timestamp.of(lastLastEnd));
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
		result = 31 * result + twitchLastStart.hashCode();
		result = 31 * result + twitchLastEnd.hashCode();

		return result;
	}

	public void save() {
		Entity.Builder entity = Entity.newBuilder(getKey());
		entity.set(TwitchChannelConstants.DELETED, getDeleted())
				.set(TwitchChannelConstants.CREATEDDATE, getCreatedDate())
				.set(TwitchChannelConstants.UPDATEDDATE, getUpdatedDate())
				.set(TwitchChannelConstants.TWITCHCHANNEL, getTwitchChannel())
				.set(TwitchChannelConstants.DISCORDCHANNEL, getDiscordChannel())
				.set(TwitchChannelConstants.TWITCHUSERNAME, getTwitchUserName())
				.set(TwitchChannelConstants.DISCORDUSERNAME, getDiscordUserName())
				.set(TwitchChannelConstants.TWITCHSERVICETYPE, getTwitchServiceType())
				.set(TwitchChannelConstants.TWITCHLASTSTART, getTwitchLastStart())
				.set(TwitchChannelConstants.TWITCHLASTEND, getTwitchLastEnd()).build();
		getDatastore().put(entity.build());
	}
	public void loadShrineChannel(String key) {
		loadShrineChannel(Long.parseLong(key));
	}

	public void loadShrineChannel(long key) {
		loadShrineChannel(
				Key.newBuilder(Constants.SHRINEOFLOSTSECRETS, TwitchChannelConstants.SHRINECHANNEL, key).build());
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
			if (entity.contains(TwitchChannelConstants.TWITCHLASTSTART)) {
				setTwitchLastStart(entity.getTimestamp(TwitchChannelConstants.TWITCHLASTSTART));
			}
			if (entity.contains(TwitchChannelConstants.TWITCHLASTEND)) {
				setTwitchLastEnd(entity.getTimestamp(TwitchChannelConstants.TWITCHLASTEND));

			}
		}
	}

	public String toString() {
		return "TwitchStream{" + "" + Constants.KEY + "='" + getKeyString() + '\'' + ", "
				+ TwitchChannelConstants.TWITCHCHANNEL + "\"='" + twitchChannel + '\'' + ", "
				+ TwitchChannelConstants.DISCORDCHANNEL + "\"='" + discordChannel + '\'' + ", "
				+ TwitchChannelConstants.TWITCHUSERNAME + "\"='" + twitchUserName + '\'' + ", "
				+ TwitchChannelConstants.DISCORDUSERNAME + "\"='" + discordUserName + '\'' + ", "
				+ TwitchChannelConstants.TWITCHSERVICETYPE + "\"='" + twitchServiceType + '\'' + ", "
				+ TwitchChannelConstants.TWITCHLASTSTART + "\"='" + twitchLastStart + '\'' + ", "
				+ TwitchChannelConstants.TWITCHLASTEND + "\"='" + twitchLastEnd + '\'' + '}';
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
	public static ShrineChannel getShrineChannelName(String channelName) {
		Entity entities = ShrineChannelService.getShrineChannelName(channelName);
		if(entities == null) {
			ShrineChannel theReturn = new ShrineChannel();
			theReturn.save();
			return theReturn;
		}
		else{
			ShrineChannel theReturn = new ShrineChannel();
			theReturn.loadFromEntity(entities);
			return theReturn;
		}
	}

}
