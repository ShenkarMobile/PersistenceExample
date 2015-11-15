package mobile.shenkar.com.persistenceexample.bl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import mobile.shenkar.com.persistenceexample.common.AppConst;
import mobile.shenkar.com.persistenceexample.common.User;
import mobile.shenkar.com.persistenceexample.dal.DAO;
import mobile.shenkar.com.persistenceexample.dal.IdataAcces;


public class LoginController 
{
	IdataAcces dao;
	Context context;
	public LoginController(Context context) {
		dao = DAO.getInstatnce(context.getApplicationContext());
		this.context = context;
	}

	public boolean isLoggedIn() {
		SharedPreferences prefs = context.getSharedPreferences(AppConst.SharedPrefsName, Context.MODE_PRIVATE);
		if (prefs != null) {
			return prefs.getBoolean(AppConst.SharedPrefs_IsLogin, false);
		}
		return false;
	}
	
	public User getUser(String userName,String password)
	{
		return dao.getUser(userName, password);
	}
	
	public void setLogedIn(User user)
	{
		if(user!=null)
		{
			SharedPreferences prefs = context.getSharedPreferences(AppConst.SharedPrefsName, 0);
			if(prefs!=null)
			{
				Editor editor = prefs.edit();
				editor.putBoolean(AppConst.SharedPrefs_IsLogin, true);
				editor.putString(AppConst.SharedPrefs_UserName, user.getUserName());
				editor.commit();
			}
		}
	}
}
