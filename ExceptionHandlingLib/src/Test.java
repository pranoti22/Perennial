package perennialbank.excpt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class Test {
	void display()
	{
		try {
			throw new SQLException();
		}catch( SQLException e) {
			new ExceptionHandler().handleException("Try","Module 2",e);
		}
		try {
			throw new IOException();
		}catch( IOException e) {
			new ExceptionHandler().handleException("Try","Module 1",e);
		}
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Test().display();

	}

}
