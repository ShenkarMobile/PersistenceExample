package mobile.shenkar.com.persistenceexample.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FriendsDBHelper extends SQLiteOpenHelper {

	// If you change the database schema, you must increment the database
	// version.
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "friends.db";

	public FriendsDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Create a table to hold the friends;
		final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE "
				+ FriendsDbContract.FriendEntry.TABLE_NAME + " (" + FriendsDbContract.FriendEntry._ID
				+ " INTEGER PRIMARY KEY," + FriendsDbContract.FriendEntry.COLUMN_FRIEND_NAME
				+ " TEXT NOT NULL, " + FriendsDbContract.FriendEntry.COLUMN_FRIEND_PHONE_NUMBER
				+ " TEXT NOT NULL  UNIQUE ON CONFLICT REPLACE)";
		db.execSQL(SQL_CREATE_LOCATION_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + FriendsDbContract.FriendEntry.TABLE_NAME);
		onCreate(db);

	}

}
