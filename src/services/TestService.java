package services;

public class TestService {
	public static void main(String[] args) {
		HashService hs = new HashService();
		String payload = "iwjci238rufnc8ue vfrn47yr efhncry rh78nvh vfh fn7rn9fhnqwdh 8dhn9cydh uidhhd uhcihuewuhieuihnen qheuhwn";
		
		System.out.println(hs.sha1(payload));
	}
}
