package eu.limontacolori.privatearea.rest.dto;

import java.util.Set;

public class ListUsersDto {
	private int page;
	private int usersPerPage;
	private int totalCount;
	private String orderBy;
	private String orderDir;
	private Set<UserDto> users;
	
	public ListUsersDto(int page, int usersPerPage, int totalCount, String orderBy, String orderDir, Set<UserDto> users) {
		this.page = page;
		this.usersPerPage = usersPerPage;
		this.totalCount = totalCount;
		this.orderBy = orderBy;
		this.orderDir = orderDir;
		this.users = users;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getUsersPerPage() {
		return usersPerPage;
	}
	public void setUsersPerPage(int usersPerPage) {
		this.usersPerPage = usersPerPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public Set<UserDto> getUsers() {
		return users;
	}
	public void setUsers(Set<UserDto> users) {
		this.users = users;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getOrderDir() {
		return orderDir;
	}
	public void setOrderDir(String orderDir) {
		this.orderDir = orderDir;
	}
}
