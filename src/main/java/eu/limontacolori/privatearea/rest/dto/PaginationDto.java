package eu.limontacolori.privatearea.rest.dto;

import java.util.List;

public class PaginationDto<T> {
	
	public PaginationReqDto paginationReq;
	public int totalCount;
	public List<T> results;
	
}
