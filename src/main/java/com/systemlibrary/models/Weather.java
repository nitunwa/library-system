package com.systemlibrary.models;

public class Weather {

	private Long uv;
	private String humidity;
	private Temperature tem;

	public Weather() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Weather(Long uv, String humidity, Temperature tem) {
		super();
		this.uv = uv;
		this.humidity = humidity;
		this.tem = tem;
	}

	public Temperature getTem() {
		return tem;
	}

	public void setTem(Temperature tem) {
		this.tem = tem;
	}

	public Long getUv() {
		return uv;
	}

	public void setUv(Long uv) {
		this.uv = uv;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

}
