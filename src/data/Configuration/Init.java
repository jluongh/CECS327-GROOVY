package data.Configuration;

public class Init {
	public static void main(String[] args) {
		Loader ldr = new Loader();
		ldr.loadStore();
		
		Seeder sdr = new Seeder();
		sdr.seedStore();
	}
}
