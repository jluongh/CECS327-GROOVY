package data.constants;

public class Packet {
	// Message Types
	public static final int REQUEST = 0;
	public static final int REPLY = 1;
	
	// Request IDs
	public static final int REQUEST_ID_GETUSER = 0;
	public static final int REQUEST_ID_GETPROFILE = 1;
	public static final int REQUEST_ID_ADDSONGTOPLAYLIST = 2;
	public static final int REQUEST_ID_DELETESONGFROMPLAYLIST = 3;
	public static final int REQUEST_ID_LOADSONG = 4;
	public static final int REQUEST_ID_SEACRHBYARTIST = 5;
	public static final int REQUEST_ID_SEACRHBYALBUM = 6;
	public static final int REQUEST_ID_SEACRHBYSONG = 7;
	public static final int REQUEST_ID_CREATEPLAYLIST = 8;
	public static final int REQUEST_ID_DELETEPLAYLIST = 9;
}
