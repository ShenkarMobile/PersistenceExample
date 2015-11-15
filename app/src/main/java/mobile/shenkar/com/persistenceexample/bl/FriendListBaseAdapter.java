package mobile.shenkar.com.persistenceexample.bl;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import mobile.shenkar.com.persistenceexample.R;
import mobile.shenkar.com.persistenceexample.common.Friend;


public class FriendListBaseAdapter extends BaseAdapter
{
	public FriendListBaseAdapter(Context context, List<Friend> items) {
		this.items = items;
		this.context = context;
	}

	static class ViewHolder {
		TextView tv_friendName;
		TextView tv_friendPhone;
	}

	private Context context;
	private List<Friend> items;

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		if (this.items != null && items.size() > position)
			return this.items.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void UpdateDataSource(List<Friend> items)
	{
		if(items ==null) return; //TODO Decide how to deal with it (Maybe an exception??)
		this.items= items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.friend_list_item, null);
			holder = new ViewHolder();
			holder.tv_friendName = (TextView) convertView
					.findViewById(R.id.textViewFriendName);
			holder.tv_friendPhone = (TextView) convertView
					.findViewById(R.id.textViewFriendPhone);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_friendName.setText(items.get(position).getFriendName());
		holder.tv_friendPhone.setText(items.get(position).getPhoneNumber());
		return convertView;
	}


}
