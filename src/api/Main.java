package api;

import java.util.List;

import com.google.gson.Gson;
import data.Models.*;

public class Main {

	public static void main(String[] args) {

//		System.out.print("Groovy Tunes");
		Account account = new Account("Jen", "1234");
		Account account1 = new Account("Caitlin", "1234");
				
		Gson gson = new Gson();
		
		System.out.print(gson.toJson(account));
	}

}
