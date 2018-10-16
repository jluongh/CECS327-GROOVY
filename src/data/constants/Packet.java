package data.constants;

public class Packet {
	// Message Types
	public static final int REQUEST = 0;
	public static final int REPLY = 1;
	public static final int ACKNOWLEDGEMENT = 2;
	
	// Request IDs
	public static final int REQUEST_ID_GETUSER = 0;
	public static final int REQUEST_ID_GETPROFILE = 1; // done
	public static final int REQUEST_ID_ADDSONGTOPLAYLIST = 2; // done
	public static final int REQUEST_ID_DELETESONGFROMPLAYLIST = 3; // done
	public static final int REQUEST_ID_LOADSONG = 4; // done
	public static final int REQUEST_ID_SEARCHBYARTIST = 5; // done
	public static final int REQUEST_ID_SEARCHBYALBUM = 6; // done
	public static final int REQUEST_ID_SEARCHBYSONG = 7; // done
	public static final int REQUEST_ID_CREATEPLAYLIST = 8; // done
	public static final int REQUEST_ID_DELETEPLAYLIST = 9; // done
	public static final int REQUEST_ID_BYTECOUNT = 10; // done
	public static final int REQUEST_ID_GETARTISTBYSONGID = 11;
	public static final int REQUEST_ID_GETALBUMBYSONGID = 12;

	// Byte Size
	public static final int BYTESIZE = 10000;

	// CRUD Success
	public static final int FAIL = 0;
	public static final int SUCCESS = 1;
	
	// Requests with Acknowledgement
	public static final int[] RRA = { REQUEST_ID_ADDSONGTOPLAYLIST, 
			REQUEST_ID_DELETESONGFROMPLAYLIST, 
			REQUEST_ID_CREATEPLAYLIST, 
			REQUEST_ID_DELETEPLAYLIST };
	
}
