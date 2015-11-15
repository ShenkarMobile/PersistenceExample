package mobile.shenkar.com.persistenceexample.bl;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mobile.shenkar.com.persistenceexample.common.AppConst;
import mobile.shenkar.com.persistenceexample.common.Friend;
import mobile.shenkar.com.persistenceexample.common.OnDataSourceChangeListener;
import mobile.shenkar.com.persistenceexample.dal.DAO;
import mobile.shenkar.com.persistenceexample.dal.IdataAcces;

public class MainController {
	// The data model, act as a cache 
	private Context context;
	private IdataAcces dao;
	private String user;
	
	//observers list.
	private List<OnDataSourceChangeListener> dataSourceChangedListenrs = new ArrayList<OnDataSourceChangeListener>();

	public MainController(Context context) {
		this.context = context;
		dao = DAO.getInstatnce(context.getApplicationContext());
	}

	public String getUserName() {
		if (user != null)
			return user;
		SharedPreferences settings = context.getSharedPreferences(
				AppConst.SharedPrefsName, 0);
		if (settings == null)
			return "";
		user = settings.getString(AppConst.SharedPrefs_UserName, "");
		return user;
	}

	public List<Friend> getAllFriends() {
		try {
			//TODO In some cases cache the data is the best practice.
			List<Friend> fl = dao.getAllFriends();
			return fl;
		} catch (Exception e) {
			// in case of error, return empty list.
			return new ArrayList<Friend>();
		}
	}

	/*
	 * Add friend to the data source.
	 */
	public void addFriend(Friend f) {


		try {
			//add the friend to the data base and use the returned friend and add it ti the local cache.
			//the friend that returned from the DAO contain the id of the entity.
			Friend retFriend = dao.addFriend(f);
			if(retFriend == null) return;
			//update what ever it will be.
			invokeDataSourceChanged();
		} catch (Exception e) {
			Log.e("MainController",e.getMessage());
		}
	}

	/*
	 * remove friend from the data source.
	 * 
	 */
	public void removeFriend(Friend f) {
			//remove the friend from the database.
			dao.removeFriend(f);
			invokeDataSourceChanged();
	}
	public void registerOnDataSourceChanged(OnDataSourceChangeListener listener)
	{
		if(listener!=null)
			dataSourceChangedListenrs.add(listener);
	}
	public void unRegisterOnDataSourceChanged(OnDataSourceChangeListener listener)
	{
		if(listener!=null)
			dataSourceChangedListenrs.remove(listener);
	}
	public void invokeDataSourceChanged()
	{
		for (OnDataSourceChangeListener listener : dataSourceChangedListenrs) {
			listener.DataSourceChanged();
		}
	}
}
