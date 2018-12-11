package data.constants;

public class Files {

	/* ---------- Physical Files Data ---------- */
	public final static String ROOT = "./src/data/";
	public final static String MDF = "./src/data/metadata.json";

	public final static String[] ARTISTS = new String[] { "./src/data/artist1.txt", "./src/data/artist2.txt",
			"./src/data/artist3.txt" };
	public final static String[] ALBUMS = new String[] { "./src/data/album1.txt", "./src/data/album2.txt",
			"./src/data/album3.txt" };
	public final static String[] SONGS = new String[] { "./src/data/song1.txt", "./src/data/song2.txt",
			"./src/data/song3.txt" };
	public final static String[] USERS = new String[] { "./src/data/users1.txt", "./src/data/users2.txt",
			"./src/data/users3.txt" };

	public final static String[] FILES = new String[] { "./src/data/album1.txt", "./src/data/album2.txt",
			"./src/data/album3.txt", "./src/data/artist1.txt", "./src/data/artist2.txt", "./src/data/artist3.txt",
			"./src/data/song1.txt", "./src/data/song2.txt", "./src/data/song3.txt" };

	/* ---------- Logical Files Data ---------- */
	public final static String ARTIST_IDX = "artist.idx";
	public final static String ALBUM_IDX = "album.idx";
	public final static String SONG_IDX = "song.idx";
	public final static String USERS_IDX = "users.idx";

	/* ---------- Song Information Index ---------- */
	public final static int[] TITLE = { 0, 2, 3 };
	public final static int[] SONGID = { 1, 3, 4 };
	public final static int[] ARTIST = { 2, 4, 0 };
	public final static int[] ALBUM = { 3, 0, 1 };
	public final static int[] DURATION = { 4, 1, 2 };

	/* ---------- Search Type --------- */
	public final static int SONGTYPE = 0;
	public final static int ALBUMTYPE = 1;
	public final static int ARTISTTYPE = 2;

	/* ---------- Misc. ---------- */
	public final static char[] limits = { 'a', 'f', 'g', 'm', 'n', 'z' };

}
