package com.systemlibrary.models;

import javax.validation.constraints.NotNull;

public class State {
	@NotNull
	private String stateShortName;

	@NotNull
	private String stateName;

	public String getId() {
		return stateShortName;
	}

	public String getName() {
		return stateName;
	}

	public State(String stateShortName, String stateName) {
		super();
		this.stateShortName = stateShortName;
		this.stateName = stateName;
	}

	public State() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getStateShortName() {
		return stateShortName;
	}

	public void setStateShortName(String stateShortName) {
		this.stateShortName = stateShortName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
}
