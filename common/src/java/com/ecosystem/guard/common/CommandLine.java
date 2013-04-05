package com.ecosystem.guard.common;

import java.io.File;

/**
 * Utilidad para ejecutar aplicaciones por línea de comandos
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class CommandLine {
	private static final String SEPARATOR = " ";

	private StringBuffer command;
	private File workDirectory;
	private int execTimeoutSeconds = 0;

	/**
	 * Construye un comando con el ejecutable establecido
	 * 
	 * @param executable
	 */
	public CommandLine(String executable) {
		command = new StringBuffer(executable);
	}

	/**
	 * Construye un comando con el ejecutable establecido
	 * 
	 * @param executable
	 */
	public CommandLine(File workDirectory, String executable) {
		this.workDirectory = workDirectory;
		command = new StringBuffer(executable);
	}

	/**
	 * Añade un argumento al ejecutable
	 * 
	 * @param argument
	 */
	public void addArgument(String argument) {
		command.append(SEPARATOR);
		command.append(argument);
	}

	/**
	 * Añade un argumento del tipo "exe -option value"
	 * 
	 * @param option
	 * @param value
	 */
	public void addArgument(String option, String value) {
		command.append(SEPARATOR);
		command.append(option);
		command.append(SEPARATOR);
		command.append(value);
	}

	/**
	 * Devuelve el comando contenido actualmente por el objeto
	 * 
	 * @return El comando
	 */
	public String getCommand() {
		return command.toString();
	}

	/**
	 * Especifica el timeout en segundos para ejecutar el comando
	 * 
	 * @param execTimeoutSeconds
	 */
	public void setExecTimeoutSeconds(int execTimeoutSeconds) {
		this.execTimeoutSeconds = execTimeoutSeconds;
	}

	/**
	 * Ejecuta el comando sin variables de entorno definidas por el usuario
	 * 
	 * @return El proceso correspondiente al comando
	 */
	public Process execute() throws Exception {
		return execute(null);
	}

	/**
	 * Ejecuta el comando utilizando unas variables de entorno concretas. Este método es síncrono,
	 * no finaliza hasta que no acaba la ejecución del comando. Si se especifica un timeout, el
	 * comando se interrumpe.
	 * 
	 * @return El proceso correspondiente al comando
	 */
	public Process execute(String[] environment) throws Exception {
		CommandWorker execThread = new CommandWorker(command.toString(), environment, workDirectory);
		System.out.println(command.toString());
		execThread.start();
		if (execTimeoutSeconds != 0) {
			execThread.join(execTimeoutSeconds * 1000);
		}
		else {
			execThread.join();
		}
		if (execThread.getException() != null)
			throw execThread.getException();
		if (!execThread.finished()) {
			execThread.interrupt();
			throw new Exception("Command timeout exceeded: " + command.toString());
		}
		return execThread.getProcess();
	}

	private class CommandWorker extends Thread {
		private String command;
		private File workDirectory;
		private String[] environment;
		private Exception exception;
		private Process process;
		private boolean finished;

		public CommandWorker(String command, String[] environment, File workDirectory) {
			this.environment = environment;
			this.command = command;
			this.workDirectory = workDirectory;
		}

		public Exception getException() {
			return exception;
		}

		public Process getProcess() {
			return process;
		}

		public boolean finished() {
			return finished;
		}

		public void run() {
			finished = false;
			try {
				process = Runtime.getRuntime().exec(command.toString(), environment, workDirectory);
				process.waitFor();
			}
			catch (Exception e) {
				e.printStackTrace();
				exception = e;
			}
			finished = true;
		}
	}

}
