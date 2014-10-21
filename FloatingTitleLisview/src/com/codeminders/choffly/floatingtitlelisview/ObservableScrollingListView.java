package com.codeminders.choffly.floatingtitlelisview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ObservableScrollingListView extends ListView {

	public interface Callback {
		public void onScrollChanged();
	}

	private Callback mCallback;

	public ObservableScrollingListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public ObservableScrollingListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ObservableScrollingListView(Context context) {
		super(context);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (mCallback != null){
			mCallback.onScrollChanged();
		}
	}

	public Callback getCallback() {
		return mCallback;
	}

	public void setCallback(Callback callback) {
		this.mCallback = callback;
	}

}
