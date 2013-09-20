package me.carl230690.signedit.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;

import me.carl230690.signedit.Config;
import me.carl230690.signedit.SignEdit;
import me.carl230690.signedit.data.LogType;

public class SignEditLogger {
	private SignEdit plugin; 
	private Logger log;

	private File logFile;
	private BufferedWriter fileOut;

	private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public SignEditLogger(SignEdit plugin) {
		this.plugin = plugin;
		this.log = plugin.getLogger();
	}

	public void logAll(String thePlayer, String theCommand, LogType theType, Level level) {
		String theMessage = thePlayer + ": /signedit " + ChatColor.stripColor(theCommand);

		if(theType.equals(LogType.PLAYERCOMMAND))
			theMessage = plugin.localization.get("playerCommand") + " " + thePlayer + ": /signedit " + theCommand;
		else if(theType.equals(LogType.SIGNCHANGE)) 
			theMessage = plugin.localization.get("signChange") + " " + thePlayer + theCommand; 

		if(Config.commandsLogFile())
			logFile("[" + dateFormat.format(new Date()) + "] " + theMessage);

		if(Config.commandsLogConsole())
			log(level, theMessage);
	}
	private void logFile(String data) {
		try {
			openFileOutput();
			fileOut.write(ChatColor.stripColor(data));
			fileOut.newLine();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void openFileOutput() {
		try	{
			logFile = new File(plugin.getDataFolder(), Config.logName());
			if(!logFile.exists()){
				logFile.createNewFile();
			}	
			fileOut = new BufferedWriter(new FileWriter(plugin.getDataFolder() + System.getProperty("file.separator") + Config.logName(), true));
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public void info(String msg) {
		log.info(ChatColor.stripColor(msg));
	}

	public void warning(String msg) {
		log.warning(ChatColor.stripColor(msg));
	}

	public void severe(String msg) {
		log.severe(ChatColor.stripColor(msg));
	}	
	
	public void log(Level level, String msg) {
		log.log(level, ChatColor.stripColor(msg));
	}
	
}