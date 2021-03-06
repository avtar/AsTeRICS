/*
 *    AsTeRICS - Assistive Technology Rapid Integration and Construction Set
 * 
 * 
 *        d8888      88888888888       8888888b.  8888888 .d8888b.   .d8888b. 
 *       d88888          888           888   Y88b   888  d88P  Y88b d88P  Y88b
 *      d88P888          888           888    888   888  888    888 Y88b.     
 *     d88P 888 .d8888b  888   .d88b.  888   d88P   888  888         "Y888b.  
 *    d88P  888 88K      888  d8P  Y8b 8888888P"    888  888            "Y88b.
 *   d88P   888 "Y8888b. 888  88888888 888 T88b     888  888    888       "888
 *  d8888888888      X88 888  Y8b.     888  T88b    888  Y88b  d88P Y88b  d88P
 * d88P     888  88888P' 888   "Y8888  888   T88b 8888888 "Y8888P"   "Y8888P" 
 *
 *
 *                    homepage: http://www.asterics.org 
 *
 *         This project has been funded by the European Commission, 
 *                      Grant Agreement Number 247730
 *  
 *  
 *         Dual License: MIT or GPL v3.0 with "CLASSPATH" exception
 *         (please refer to the folder LICENSE)
 * 
 */

package eu.asterics.component.actuator.fS20Sender;

import java.io.IOException;

import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDDeviceNotFoundException;
import com.codeminders.hidapi.HIDManager;

import eu.asterics.mw.services.AstericsErrorHandling;

public class PCSDevice {

	private int vid = 0x18EF;
	private int pid = 0xE015;
	
	private HIDDevice dev = null;
	
	public PCSDevice() {
	}
	
	public boolean open() {
		try {			
			dev = HIDManager.getInstance().openById(0x18EF, 0xE015, null);			
		}catch (HIDDeviceNotFoundException e) {
			return false;
		} catch (IOException ioe) {
			return false;
		}
		return true;
	}

	
	public boolean close() {
		try {
			dev.close();
		} catch (IOException ioe) {
			return false;
		}
		return true;
	}
	
	public boolean send(int houseCode, int addr, int command) {
		byte [] buf = new byte[11];
		buf[0] = 0x01; // hid report id
		buf[1] = 0x06; // byte anzahl
		buf[2] = (byte) 0xF1; // Befehl ID
		byte [] hc = FS20Utils.houseCodeToHex(houseCode);
		buf[3] = hc[0]; // 1111 HC
		buf[4] = hc[1]; // 1111 HC
		buf[5] = FS20Utils.addressToHex(addr); // 1111 Adresse
		buf[6] = (byte) command; // Befehl
		buf[7] = 0x00; // Erweiterung
		try {
			if (dev != null) dev.write(buf);
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		return true;
	}
}
