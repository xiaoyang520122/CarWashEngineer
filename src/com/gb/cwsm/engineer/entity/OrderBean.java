package com.gb.cwsm.engineer.entity;

import java.io.Serializable;

public class OrderBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String sn;
	private String createDate;
	private String rPrice;
	private String product;
	private String orderType;
	private String status;
	
	private Car car; 
	private Address address; 
	
	private String mberId;
	private String mberName;
	private String mberMobile;
	private String engName;
	private String engId;
	private String engMobile;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getrPrice() {
		return rPrice;
	}
	public void setrPrice(String rPrice) {
		this.rPrice = rPrice;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getMberId() {
		return mberId;
	}
	public void setMberId(String mberId) {
		this.mberId = mberId;
	}
	public String getMberName() {
		return mberName;
	}
	public void setMberName(String mberName) {
		this.mberName = mberName;
	}
	public String getMberMobile() {
		return mberMobile;
	}
	public void setMberMobile(String mberMobile) {
		this.mberMobile = mberMobile;
	}
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
	}
	public String getEngId() {
		return engId;
	}
	public void setEngId(String engId) {
		this.engId = engId;
	}
	public String getEngMobile() {
		return engMobile;
	}
	public void setEngMobile(String engMobile) {
		this.engMobile = engMobile;
	}
	
	

}
