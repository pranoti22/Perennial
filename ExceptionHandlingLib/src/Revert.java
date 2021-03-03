package perennialbank.excpt;

import java.util.Map;

public class Revert implements Actions {

	@Override
	public String performAction(Map<String, String> act) {
		// TODO Auto-generated method stub
		//System.out.println("mail done!");
		return "rollback done!";

	}

}
