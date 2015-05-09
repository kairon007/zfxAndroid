package com.zifei.corebeau.bean;

import java.io.Serializable;

public class UserUploadPicture implements Serializable {

	private static final long serialVersionUID = 4271455998134836402L;

	private int id;

	private String itemId;

	private String url;

	private int bwidth;
	private int bheight;
	private int mwidth;
	private int mheight;
	private int swidth;
	private int sheight;

	public int getBwidth() {
		return bwidth;
	}

	public void setBwidth(int bwidth) {
		this.bwidth = bwidth;
	}

	public int getBheight() {
		return bheight;
	}

	public void setBheight(int bheight) {
		this.bheight = bheight;
	}

	public int getMwidth() {
		return mwidth;
	}

	public void setMwidth(int mwidth) {
		this.mwidth = mwidth;
	}

	public int getMheight() {
		return mheight;
	}

	public void setMheight(int mheight) {
		this.mheight = mheight;
	}

	public int getSwidth() {
		return swidth;
	}

	public void setSwidth(int swidth) {
		this.swidth = swidth;
	}

	public int getSheight() {
		return sheight;
	}

	public void setSheight(int sheight) {
		this.sheight = sheight;
	}

	public UserUploadPicture() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
