package mobile.shenkar.com.persistenceexample.dal;

import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import mobile.shenkar.com.persistenceexample.common.Friend;
import mobile.shenkar.com.persistenceexample.common.User;

public class DAO implements IdataAcces {
	private static DAO instace;
	private Context context;
	private FriendsDBHelper dbHelper;
	private String[] friendsColumns = { FriendsDbContract.FriendEntry._ID,
			FriendsDbContract.FriendEntry.COLUMN_FRIEND_NAME,
			FriendsDbContract.FriendEntry.COLUMN_FRIEND_PHONE_NUMBER, };

	private DAO(Context context) {
		this.context = context;
		dbHelper = new FriendsDBHelper(this.context);
	}

	public static DAO getInstatnce(Context context) {
		if (instace == null)
			instace = new DAO(context);
		return instace;
	}

	@Override
	public Friend addFriend(Friend friend) {
		SQLiteDatabase database = null;
		try {
			database = dbHelper.getReadableDatabase();
			if (friend == null)
				return null;
			//build the content values.
			ContentValues values = new ContentValues();
			values.put(FriendsDbContract.FriendEntry.COLUMN_FRIEND_NAME, friend.getFriendName());
			values.put(FriendsDbContract.FriendEntry.COLUMN_FRIEND_PHONE_NUMBER, friend.getPhoneNumber());

			//do the insert.
			long insertId = database.insert(FriendsDbContract.FriendEntry.TABLE_NAME, null, values);

			//get the entity from the data base - extra validation, entity was insert properly.
			Cursor cursor = database.query(FriendsDbContract.FriendEntry.TABLE_NAME, friendsColumns,
					FriendsDbContract.FriendEntry._ID + " = " + insertId, null, null, null, null);
			cursor.moveToFirst();
			//create the friend object from the cursor.
			Friend newFriend = cursorToFriend(cursor);
			cursor.close();
			return newFriend;
		}finally {
			if (database != null)
				database.close();
		}

	}

	/*
	 * Create friend object from the cursor.
	 */
	private Friend cursorToFriend(Cursor cursor) {
			Friend f = new Friend();
			f.setId(cursor.getInt(cursor.getColumnIndex(FriendsDbContract.FriendEntry._ID)));
			f.setFriendName(cursor.getString(cursor
					.getColumnIndex(FriendsDbContract.FriendEntry.COLUMN_FRIEND_NAME)));
			f.setPhoneNumber(cursor.getString(cursor
					.getColumnIndex(FriendsDbContract.FriendEntry.COLUMN_FRIEND_PHONE_NUMBER)));
			return f;
	}

	@Override
	public void removeFriend(Friend friend) {
		SQLiteDatabase database = null;
		try {
            database = dbHelper.getReadableDatabase();
			long id = friend.getId();
			database.delete(FriendsDbContract.FriendEntry.TABLE_NAME, FriendsDbContract.FriendEntry._ID + " = " + id,
					null);
		}finally {
			if(database != null){
				database.close();
			}
		}
	}

	@Override
	public List<Friend> getAllFriends() {
		SQLiteDatabase database = null;
		try {
            database = dbHelper.getReadableDatabase();
			List<Friend> friends = new ArrayList<Friend>();

			Cursor cursor = database.query(FriendsDbContract.FriendEntry.TABLE_NAME, friendsColumns,
					null, null, null, null, null);

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Friend f = cursorToFriend(cursor);
				friends.add(f);
				cursor.moveToNext();
			}
			// make sure to close the cursor
			cursor.close();
			return friends;
		} finally {
			if (database != null) {
				database.close();
			}
		}
	}

	@Override
	public User getUser(String userName, String password) {
		// Check in the storage (In the cloud, the database etc..)
		// if exists, return the user object,otherwise
		// return null.

		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		user.setMail("Cadan85@gmail.com");
		return user;
	}

}
