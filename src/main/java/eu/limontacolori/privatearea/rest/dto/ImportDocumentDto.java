package eu.limontacolori.privatearea.rest.dto;

public class ImportDocumentDto {
	
	public String NR_DOC;
    public String DT_DOC;
    public String TIPO;
    public String CLIENTE;
    public String SOCIETA;
    
	@Override
	public String toString() {
		return "ImportDocumentDto [NR_DOC=" + NR_DOC + ", DT_DOC=" + DT_DOC
				+ ", TIPO=" + TIPO + ", CLIENTE=" + CLIENTE + ", SOCIETA="
				+ SOCIETA + "]";
	}    
   	
}
