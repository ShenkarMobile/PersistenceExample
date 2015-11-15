package mobile.shenkar.com.persistenceexample.dal;

import android.provider.BaseColumns;

/**
 * Defines table and column names for the friends database.
 */
public class FriendsDbContract
{
    /* Inner class that defines the table contents of the friends */
	 public static final class FriendEntry implements BaseColumns {

	        // Table name
	        public static final String TABLE_NAME = "friends";

	        public static final String COLUMN_FRIEND_NAME = "friend_name";

	        public static final String COLUMN_FRIEND_PHONE_NUMBER = "friend_phone_number";

	    }


}
