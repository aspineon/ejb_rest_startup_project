package eu.limontacolori.privatearea.services.importers;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Singleton
public class ImportChrono {
	
	Logger logger = LogManager.getLogger(this.getClass());
	
	@Inject
	ImportCustomersService importCustomersService;
	
	@Inject
	ImportDocumentsService importDocumentsService;
	
	@Lock(LockType.READ)
	@Schedule(minute = "00", hour = "00")
	private void importLimcaData() {
		
		logger.info("[IMPORT] Import Limca data chrono started");
		
		try {
			importCustomersService.importData(null);
		} catch (Exception exc) {
			logger.fatal("[IMPORT] Problems occurred during customers import", exc);
		}
		
		try {
			importDocumentsService.importData(null);
		} catch (Exception exc) {
			logger.fatal("[IMPORT] Problems occurred during documents import", exc);
		}
		
		logger.info("[IMPORT] Import Limca data chrono ended");
		
	}
	
}
