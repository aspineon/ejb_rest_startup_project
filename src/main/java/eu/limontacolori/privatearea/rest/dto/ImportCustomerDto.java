package eu.limontacolori.privatearea.rest.dto;


public class ImportCustomerDto {
	
	public String CODICE;
	public String RAGSOC1;
	public String RAGSOC2;
	public String MAIL;
	public String INDIRIZZO;
	public String CAP;
	public String LOCALITA;
	public String PIVA;
	public String CODFISC;
	public String PROV;
	public String TELEPHONE;
	public String AGENTE;
	public String DESAGENTE;
	
	@Override
	public String toString() {
		return "CustomerDto [codice=" + CODICE + ", ragsoc1=" + RAGSOC1
				+ ", ragsoc2=" + RAGSOC2 + ", mail=" + MAIL + ", indirizzo="
				+ INDIRIZZO + ", cap=" + CAP + ", localita=" + LOCALITA
				+ ", pIva=" + PIVA + ", codFisc=" + CODFISC + ", prov=" + PROV
				+ ", telephone=" + TELEPHONE + ", agente=" + AGENTE
				+ ", desagente=" + DESAGENTE + "]";
	}
	
	
}
