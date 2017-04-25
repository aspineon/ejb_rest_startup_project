package eu.limontacolori.privatearea.rest.dto;

public class ListDocumentsReqDto {
	
	public String customerId;
	public String dateStart;
	public String dateEnd;
	public PaginationReqDto pagination;
	
	@Override
	public String toString() {
		return "ListDocumentsReqDto [customerId=" + customerId + ", dateStart="
				+ dateStart + ", dateEnd=" + dateEnd + ", pagination="
				+ pagination + "]";
	}
	
}
