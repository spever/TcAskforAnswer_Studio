package com.tuanche.api.widget.gallery.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class LaShouImageParcel implements Parcelable {

	private String url;
	private String description;

	public LaShouImageParcel() {

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LaShouImageParcel(Parcel source) {
		this.url = source.readString();
		this.description = source.readString();
	}

	public static final Parcelable.Creator<LaShouImageParcel> CREATOR = new Creator<LaShouImageParcel>() {

		@Override
		public LaShouImageParcel[] newArray(int size) {
			return new LaShouImageParcel[size];
		}

		@Override
		public LaShouImageParcel createFromParcel(Parcel source) {
			return new LaShouImageParcel(source);
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(url);
		dest.writeString(description);
	}

}
