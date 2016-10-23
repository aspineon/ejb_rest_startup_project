package it.restproj.entities.enums;

public enum Role {
	
	ADMIN("ADMIN"), USER("USER"), SUPERUSER("SUPERUSER");
	
	private Role(String value) {
		this.value = value;
	}
	
	private String value;
	
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
