package mobile.shenkar.com.persistenceexample.dal;

import java.util.List;

import android.database.SQLException;

import mobile.shenkar.com.persistenceexample.common.Friend;
import mobile.shenkar.com.persistenceexample.common.User;


public interface IdataAcces 
{
	User getUser(String userName, String password);

	List<Friend> getAllFriends();

	void removeFriend(Friend friend);

	Friend addFriend(Friend friend);

}
