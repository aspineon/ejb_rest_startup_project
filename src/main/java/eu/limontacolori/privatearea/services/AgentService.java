package eu.limontacolori.privatearea.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import eu.limontacolori.privatearea.dao.AgentDAO;
import eu.limontacolori.privatearea.entities.Agent;

@Stateless
public class AgentService {
	
	@Inject
	AgentDAO agentDao;
	
	public Agent getAgent(String id) {
		return agentDao.getById(id);
	}
	
	public void updateAgent(Agent agent) {
		agentDao.update(agent);
	}
}
