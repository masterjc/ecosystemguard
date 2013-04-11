package com.ecosystem.guard.app.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ecosystem.guard.engine.TimerService;
import com.ecosystem.guard.engine.config.EcosystemConfig;
import com.ecosystem.guard.logging.EcosystemGuardLogger;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class ContextListener implements ServletContextListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		TimerService.getInstance().stopTimers();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			TimerService.getInstance().registerTimer(new PublicIpUpdater(),
					EcosystemConfig.getAppConfig().getUpdatePublicIpThreshold());
			TimerService.getInstance().startTimers();
		}
		catch (Exception e) {
			EcosystemGuardLogger.logError(e, ContextListener.class);
		}
	}

}
