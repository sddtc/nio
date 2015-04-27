package com.sddtc.thread;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sddtc
 * 
 */
@SuppressWarnings("serial")
public class DataPacket implements Serializable {

	private long id;
	private String content;
	private Date sendTime;

	/**
	 * 
	 * @return {@link #id id}
	 **/
	public long getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 *            {@link #id id}
	 **/
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 
	 * @return {@link #content content}
	 **/
	public String getContent() {
		return content;
	}

	/**
	 * 
	 * @param content
	 *            {@link #content content}
	 **/
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 
	 * @return {@link #sendTime sendTime}
	 **/
	public Date getSendTime() {
		return sendTime;
	}

	/**
	 * 
	 * @param sendTime
	 *            {@link #sendTime sendTime}
	 **/
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
}