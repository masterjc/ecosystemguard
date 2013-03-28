package com.ecosystem.guard.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import junit.framework.Assert;

import org.junit.Test;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class FileStreamPumpTest {

	@Test(expected=Exception.class)
	public void testFileCreationTimeout() throws Exception {
		FileStreamPump pump = new FileStreamPump();
		pump.setCreationFileTimeout(2000);
		pump.startPumping(new File("incorrectFile.in"), null);
		Thread.sleep(3000);
		pump.stopPumping();
	}
	
	@Test
	public void testCorrectPump() throws Exception {
		File file = File.createTempFile("temp", ".test");
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		FileStreamPump pump = new FileStreamPump();
		pump.setAvailableThreshold(10);
		pump.startPumping(file, output);
		writeDataToFile(file, 500000);
		Thread.sleep(500);
		pump.stopPumping();
		Assert.assertEquals(500000, output.size());
	}
	
	@Test
	public void testCorrectPausedPump() throws Exception {
		File file = File.createTempFile("temp", ".test");
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		FileStreamPump pump = new FileStreamPump();
		pump.setAvailableThreshold(10);
		pump.startPumping(file, output);
		writePausedDataToFile(file, 20000);
		Thread.sleep(500);
		pump.stopPumping();
		Assert.assertEquals(20000, output.size());
	}
	
	private void writeDataToFile(File file, int bytes) throws Exception {
		FileOutputStream output = new FileOutputStream(file);
		try {
			int nIts = bytes / 4;
			int nByte = 0;
			byte[] data = new byte[] { 'a', 'a', 'a', 'a'};
			while(nByte++ < nIts) {
				output.write(data);
			}
		} finally {
			output.flush();
			output.getChannel().force(true);
			output.close();
		}
	}
	
	private void writePausedDataToFile(File file, int bytes) throws Exception {
		FileOutputStream output = new FileOutputStream(file);
		try {
			int nIts = bytes / 4;
			int nByte = 0;
			while(nByte++ < nIts) {
				output.write(new byte[] { 'a', 'a', 'a', 'a'});
				if(nByte % 1024 == 0) {
					Thread.sleep(1000);
				}
			}
		} finally {
			output.flush();
			output.getChannel().force(true);
			output.close();
		}
	}
}