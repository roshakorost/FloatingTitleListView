package com.codeminders.choffly.floatingtitlelisview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListAdapter;

import com.codeminders.choffly.floatingtitlelisview.ObservableScrollingListView.Callback;

public class FloatHeaderListView extends FrameLayout {
	private ObservableScrollingListView mListView;
	private FrameLayout mFixedHeaderContainer;
	private View mFloatingView;

	private boolean mIsRidFloatingHeader;
	private boolean mIsRidFixedHeader;

	private int mIdFloatingHeader;
	private int mIdFixedHeader;

	private static final int LIST_VIEW_ID = android.R.id.list;

	public FloatHeaderListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public FloatHeaderListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public FloatHeaderListView(Context context) {
		super(context);
		init();
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mListView = (ObservableScrollingListView) inflater.inflate(
				R.layout.floating_header_list_view, this, false);
		mListView.setId(LIST_VIEW_ID);

		mFixedHeaderContainer = (FrameLayout) inflater.inflate(
				R.layout.header_item, mListView, false);
		mListView.addHeaderView(mFixedHeaderContainer);
		mListView.setCallback(new Callback() {
			@Override
			public void onScrollChanged() {
				updateFlaotViewPosition();
			}
		});

		super.addView(mListView);

		reflectIdsValues();
	}

	public void setAdapter(ListAdapter adapter) {
		mListView.setAdapter(adapter);
	}

	public void setHeaderFloatingView(View child) {
		setHeaderFloatingView(child, child.getLayoutParams());
	}
	
	public void setHeaderFloatingView(View child,
			android.view.ViewGroup.LayoutParams params) {
		if (mFloatingView != null) {
			throw new UnsupportedOperationException(
					"Floating header was added allready");
		}

		mFloatingView = child;
		super.addView(child, -1, params);
	}
	
	public void setHeaderFixedView(View child, android.view.ViewGroup.LayoutParams params){
		if (mFixedHeaderContainer.getChildCount() > 0) {
			throw new UnsupportedOperationException(
					"Fixed header was added allready");
		}

		mFixedHeaderContainer.addView(child, -1, params);
		return;
	}
	
	public void setHeaderFixedView(View child){
		setHeaderFixedView(child, child.getLayoutParams());
	}

	@Override
	public void addView(View child, int index,
			android.view.ViewGroup.LayoutParams params) {

		int id = child.getId();

		if (id == LIST_VIEW_ID) {
			super.addView(child, index, params);
			return;
		}

		if (mIsRidFixedHeader && mIdFixedHeader == id) {

			if (mFixedHeaderContainer.getChildCount() > 0) {
				throw new UnsupportedOperationException(
						"Fixed header was added allready");
			}

			mFixedHeaderContainer.addView(child, index, params);
			return;
		}

		if (mIsRidFloatingHeader && mIdFloatingHeader == id) {

			if (mFloatingView != null) {
				throw new UnsupportedOperationException(
						"Floating header was added allready");
			}

			mFloatingView = child;
			super.addView(child, index, params);
			return;
		}

		throw new UnsupportedOperationException(
				"Use Views with Id R.id.fixed_header for Fixed header view or Id R.id.floating_header for Floating header");

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		recomputeHeadersSizeAndPositionMetrics();
	}

	private void reflectIdsValues() {
		try {
			mIdFloatingHeader = R.id.class.getDeclaredField("floating_header")
					.getInt(null);
			mIsRidFloatingHeader = true;
		} catch (Exception ex) {
			mIsRidFloatingHeader = false;
		}

		try {
			mIdFixedHeader = R.id.class.getDeclaredField("fixed_header")
					.getInt(null);
			mIsRidFixedHeader = true;
		} catch (Exception ex) {
			mIsRidFixedHeader = false;
		}
	}

	private void updateFlaotViewPosition() {
		if (mFloatingView != null) {
			float position = mFixedHeaderContainer.getY()
					+ mFixedHeaderContainer.getHeight()
					- mFloatingView.getHeight();
			if (position < 0) {
				position = 0;
			}

			mFloatingView.setTranslationY(position);
		}
	}

	private void recomputeHeadersSizeAndPositionMetrics() {
		if (mFloatingView != null) {
			int bottom = mFloatingView.getHeight();
			mFixedHeaderContainer.setPadding(0, 0, 0, bottom);
			mFloatingView.setTranslationY(mFixedHeaderContainer.getHeight());
		}
	}

}
