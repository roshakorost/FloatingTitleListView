package com.codeminders.choffly.floatingtitlelisview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

public class FloatingWindowExample extends Activity {
	private static final int TEST_DATA_ROWS_COUNT = 50;

	FloatHeaderListView mFloatHeaderListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//example1(); remove // to see example1
		example2();
		
		ArrayAdapter<String> testDataAdapter = new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_1,
				generateTestData(TEST_DATA_ROWS_COUNT));
		
		mFloatHeaderListView.setAdapter(testDataAdapter);
	}
	
	
	///////////////////////////////////////////////////
	/// example of how you can set headers from code //
	///////////////////////////////////////////////////
	private void example1(){
		setContentView(R.layout.test_1);
		
		mFloatHeaderListView = (FloatHeaderListView)findViewById(R.id.root);
		
		View floatingHeader = getLayoutInflater().inflate(
				R.layout.test_floating_header, mFloatHeaderListView, false);

		mFloatHeaderListView.setHeaderFloatingView(floatingHeader);
		
		
		View fixedHeader = getLayoutInflater().inflate(
				R.layout.test_fixed_header, mFloatHeaderListView, false);
		
		mFloatHeaderListView.setHeaderFixedView(fixedHeader);
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////
	//           Look R.layout.test_2 how you can set headers from xml             ////
	/// YOU SHOUD USER ID FOR VIEWS @+id/fixed_header AND android:id="@+id/floating_header"
	private void example2(){
		setContentView(R.layout.test_2);
		mFloatHeaderListView = (FloatHeaderListView)findViewById(R.id.root);
		
	}


	private String[] generateTestData(int count) {
		String[] result = new String[count];

		for (int i = 0; i < count; i++) {
			result[i] = String.valueOf(i);
		}

		return result;
	}

}
