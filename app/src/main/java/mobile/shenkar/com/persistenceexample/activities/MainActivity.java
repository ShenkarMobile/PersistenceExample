package mobile.shenkar.com.persistenceexample.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import mobile.shenkar.com.persistenceexample.R;
import mobile.shenkar.com.persistenceexample.bl.FriendListBaseAdapter;
import mobile.shenkar.com.persistenceexample.bl.MainController;
import mobile.shenkar.com.persistenceexample.common.AppConst;
import mobile.shenkar.com.persistenceexample.common.Friend;
import mobile.shenkar.com.persistenceexample.common.OnDataSourceChangeListener;

public class MainActivity extends Activity implements
		OnDataSourceChangeListener {
	static final int GET_FREIND_REQUEST = 1;

	private MainController controller;
	private FriendListBaseAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// create the controller.
		controller = new MainController(this);
		// register for OnDataSourceChangedListener event.
		controller.registerOnDataSourceChanged(this);

		TextView tv = (TextView) findViewById(R.id.textViewUserName);
		tv.setText(controller.getUserName());
		ListView lv = (ListView) findViewById(R.id.listViewFriends);
		//handle the list view 
		if (lv != null) {
			//create the adapter and get the data from the controller.
			adapter = new FriendListBaseAdapter(this,
					controller.getAllFriends());
			lv.setAdapter(adapter);
			//register for Long item click listener.
			//When the user will do a long press on item in the list, the item will be removed.
			lv.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
						View view, int position, long id) {
					Friend f = (Friend) adapter.getItem(position);
					controller.removeFriend(f);
					return true;
				}
			});
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == GET_FREIND_REQUEST) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				String freindName = extras.getString(AppConst.ExtrasFriendName);
				String friendPhone = extras
						.getString(AppConst.ExtrasFriendPhone);
				Friend f = new Friend();
				f.setFriendName(freindName);
				f.setPhoneNumber(friendPhone);
				controller.addFriend(f);
			}

		}
	}

	public void addClicked(View v) {
		Intent i = new Intent(this, AddFriendActivity.class);
		startActivityForResult(i, GET_FREIND_REQUEST);

	}

	@Override
	public void DataSourceChanged() {
		if (adapter != null) {
			adapter.UpdateDataSource(controller.getAllFriends());
			adapter.notifyDataSetChanged();
		}

	}
}
