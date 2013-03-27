package com.ecosystem.guard.common;

import java.io.File;

/**
 * Utilidad para ejecutar aplicaciones por línea de comandos en un proceso separado al actual
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class CommandLine {
	private static final String SEPARATOR = " ";

	private StringBuffer command;
	private File workDirectory;

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
	 * Ejecuta el comando sin variables de entorno definidas por el usuario
	 * 
	 * @return El proceso correspondiente al comando
	 */
	public Process execute() throws Exception {
		return execute(null);
	}

	/**
	 * Ejecuta el comando utilizando unas variables de entorno concretas.
	 * 
	 * @return El proceso correspondiente al comando
	 */
	public Process execute(String[] environment) throws Exception {
		return Runtime.getRuntime().exec(command.toString(), environment, workDirectory);
	}

}
