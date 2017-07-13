package com.systemlibrary.models;

public class Temperature {

private String condition;
private Long tem;
 private String unit;
 
public Temperature() {
	
}

public Temperature(String condition, Long tem,String unit) {
	super();
	this.condition = condition;
	this.tem = tem;
	this.unit = unit;
}

public String getUnit() {
	return unit;
}

public void setUnit(String unit) {
	this.unit = unit;
}

public String getCondition() {
	return condition;
}

public void setCondition(String condition) {
	this.condition = condition;
}

public Long getTem() {
	return tem;
}

public void setTem(Long tem) {
	this.tem = tem;
}

}


