package perennialbank.excpt;

import java.util.Map;

public class ShutDown implements Actions {

	@Override
	public String performAction(Map<String, String> act) {
		System.out.println("shuting down....");
		System.exit(1);
		return null;
	}

}
