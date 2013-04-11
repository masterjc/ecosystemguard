package com.ecosystem.guard.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

import com.ecosystem.guard.logging.EcosystemGuardLogger;

/**
 * Servicio para gestionar un conjunto de acciones que se repiten cada cierto tiempo.
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class TimerService {
	private static TimerService instance = new TimerService();

	private List<TimerInstance> timerInstances = new ArrayList<TimerInstance>();
	private List<Timer> runningTimers = new ArrayList<Timer>();

	private class TimerInstance {
		private long secondsPeriod;
		private Callable<Void> action;

		public TimerInstance(Callable<Void> action, long secondsPeriod) {
			this.secondsPeriod = secondsPeriod;
			this.action = action;
		}

		public long getSecondsPeriod() {
			return secondsPeriod;
		}

		public Callable<Void> getAction() {
			return action;
		}
	}

	private class CallableTimerTask extends TimerTask {
		private Callable<Void> action;

		public CallableTimerTask(Callable<Void> action) {
			this.action = action;
		}

		@Override
		public void run() {
			try {
				action.call();
			}
			catch (Exception e) {
				EcosystemGuardLogger.logError(e, CallableTimerTask.class);
			}
		}

	}

	/**
	 * Obtener la instancia del singleton
	 * 
	 * @return
	 * @throws Exception
	 */
	public static TimerService getInstance() {
		return instance;
	}

	/**
	 * Registra una accion que se ejecutará cada cierto tiempo
	 * 
	 * @param timerAction
	 * @param secondsPeriod
	 */
	public void registerTimer(Callable<Void> timerAction, long secondsPeriod) {
		timerInstances.add(new TimerInstance(timerAction, secondsPeriod));
	}

	/**
	 * Arranca todas las acciones periódicas registradas
	 */
	public void startTimers() {
		for (TimerInstance instance : timerInstances) {
			Timer timer = new Timer();
			CallableTimerTask task = new CallableTimerTask(instance.getAction());
			timer.schedule(task, 0, instance.getSecondsPeriod() * 1000);
			runningTimers.add(timer);
		}
	}

	/**
	 * Detiene todas las acciones periódicas arrancadas
	 */
	public void stopTimers() {
		for (Timer timer : runningTimers) {
			timer.cancel();
		}
	}
}
