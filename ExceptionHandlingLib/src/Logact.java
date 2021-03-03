package perennialbank.excpt;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logact implements Actions {

	@Override
	public String performAction(Map<String, String> act) {
		String s="logFilePath";
		try {
			File f=new File(act.get(s));
			if(!f.exists())
			{
				f.createNewFile();
			}
			FileHandler fh=new FileHandler(act.get(s),true);
			Logger log=Logger.getLogger("Exception");
			log.addHandler(fh);
			SimpleFormatter format=new SimpleFormatter();
			fh.setFormatter(format);
			log.info("An error occured");
			
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Log created";
		//if()

	}

}
/*FileHandler fh=new FileHandler("",true);
Logger log=Logger.getLogger("ExceptionLogs");
SimpleFormatter formatter = new SimpleFormatter();  
fh.setFormatter(formatter);
log.addHandler(fh);
log.info(act.get(s));*/
