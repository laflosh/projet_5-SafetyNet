package com.safetynet.projet_5_safetynet_api.model.specific;

import java.util.List;

/**
 * The data's model for housemember and getter/setter methods for unitary endpoint "/childAlert?address=<address>"
 */
public class HouseMember {

	private List<Child> child;
	
	private List<Parent> parent;
	
	public List<Child> getChild(){
		return child;
	}
	
	public void setChild(List<Child> child) {
		this.child = child;
	}
	
	public List<Parent> getParent() {
		return parent;
	}
	
	public void setParent(List<Parent> parent) {
		this.parent = parent;
	}
	
}
