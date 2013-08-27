package com.ecosystem.guard.app.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ecosystem.guard.engine.TimerService;
import com.ecosystem.guard.engine.config.EcosystemConfig;
import com.ecosystem.guard.logging.EcosystemGuardLogger;
import com.ecosystem.guard.phidgets.SensorManager;
import com.ecosystem.guard.phidgets.sensors.LcdScreen;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class ContextListener implements ServletContextListener {
	private static final String ECOSYSTEMGUARD_MESSAGE = "EcosystemGuard:";
	private static final String STARTING_MESSAGE = "*** Starting...";
	private static final String STOPPING_MESSAGE = "*** Shutting down...";

	/*
	 * (non-Javadoc) 
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			TimerService.getInstance().stopTimers();
			LcdScreen lcd = SensorManager.getInstance().getSensor(LcdScreen.class);
			lcd.showAsynchronousMessage(ECOSYSTEMGUARD_MESSAGE, STOPPING_MESSAGE, 15000);
		}
		catch (Exception e) {
			EcosystemGuardLogger.logError(e, ContextListener.class);
		}
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
			LcdScreen lcd = SensorManager.getInstance().getSensor(LcdScreen.class);
			lcd.showAsynchronousMessage(ECOSYSTEMGUARD_MESSAGE, STARTING_MESSAGE, 15000);
			TimerService.getInstance().registerTimer(new PublicIpUpdater(), Integer.parseInt(EcosystemConfig.getAppConfig().getUpdateIpThreshold()));
			TimerService.getInstance().startTimers();
		}
		catch (Exception e) {
			EcosystemGuardLogger.logError(e, ContextListener.class);
		}
	}

}
