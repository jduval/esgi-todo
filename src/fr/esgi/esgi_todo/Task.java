package fr.esgi.esgi_todo;

import android.widget.EditText;

public class Task {

	long _id;
	String initial_date;
	String initial_hour;
	String recall_date;
	String recall_hour;
	String priority;
	String category;
	String titleTask;
	String contentTask;

	public Task() {}
	public Task(long id, String initial_date, String initial_hour) {
		this._id = id;
		this.initial_date = initial_date;
	}

	public Task(long id, String initial_date, String initial_hour, String recall_date, String recall_hour) {
		this._id = id;
		this.initial_date = initial_date;
	}

	public Task(String initial_date, String initial_hour, String recall_date, String recall_hour) {
		this.initial_date = initial_date;
	}

	public Task(String initial_date, String initial_hour, String recall_date, String recall_hour, String priority, String category, String titleTask, String contentTask) {
		this.initial_date = initial_date;
		this.initial_hour = initial_hour;
		this.recall_date = recall_date;
		this.recall_hour = recall_hour;
		this.priority = priority;
		this.category = category;
		this.titleTask = titleTask;
		this.contentTask = contentTask;
	}

	public long getId() {
		return this._id;
	}

	public void setId(long id) {
		this._id = id;
	}

	public String getInitialDate() {
		return this.initial_date;
	}

	public void setInitialDate(String initial_date) {
		this.initial_date = initial_date;
	}

	public String getInitialHour() {
		return this.initial_hour;
	}

	public void setInitialHour(String initial_hour) {
		this.initial_date = initial_hour;
	}

	public String getRecallDate() {
		return this.recall_date;
	}

	public void setRecallDate(String recall_date) {
		this.initial_date = recall_date;
	}

	public String getRecallHour() {
		return this.recall_hour;
	}

	public void setRecallHour(String recall_hour) {
		this.initial_date = recall_hour;
	}

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return this.titleTask;
	}

	public void setTitle(String titleTask) {
		this.titleTask = titleTask;
	}

	public String getContent() {
		return this.contentTask;
	}

	public void setContent(String contentTask) {
		this.contentTask = contentTask;
	}
}
