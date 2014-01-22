package fr.esgi.esgi_todo;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TaskCustomAdapter extends BaseAdapter {

	private static ArrayList<Task> TaskList;
	private LayoutInflater mInflater;

	public TaskCustomAdapter(Context context, ArrayList<Task> tasks) {
		TaskList = tasks;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return TaskList.size();
	}

	@Override
	public Object getItem(int position) {
		return TaskList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater
					.inflate(R.layout.custom_task_row_view, null);
			holder = new ViewHolder();
			holder.txtTitle = (TextView) convertView
					.findViewById(R.id.title_cv);
			holder.txtIniDate = (TextView) convertView
					.findViewById(R.id.initial_date_cv);
			holder.txtIniHour = (TextView) convertView
					.findViewById(R.id.initial_hour_cv);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtTitle.setText(TaskList.get(position).getTitle());
		holder.txtIniDate.setText(TaskList.get(position).getInitialDate());
		holder.txtIniHour.setText(TaskList.get(position).getInitialHour());

		return convertView;
	}

	static class ViewHolder {
		public TextView txtIniHour;
		public TextView txtIniDate;
		public TextView txtTitle;
	}
}
