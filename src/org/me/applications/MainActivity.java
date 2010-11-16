package org.me.applications;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import edu.uco.cvm.test.CvmChannelTest;
import edu.uco.cvm.test.CvmMatrixTest;
import edu.uco.cvm.test.CvmMatrixTypesTest;

public class MainActivity extends Activity { 
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main);
        }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu, menu);
        return true;
    	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent newActivity;
    	
    	switch (item.getItemId()) {
	        case R.id.demo_filters:
	        	newActivity = new Intent(this, DemoFilters.class);
	        	this.startActivity(newActivity);
	        	return true;
	        case R.id.demo_transfo:
	        	newActivity = new Intent(this, DemoTransformations.class);
	        	this.startActivity(newActivity);
	        	return true;
	        case R.id.test_matrix:
	        	newActivity = new Intent(this, CvmMatrixTest.class);
	        	this.startActivity(newActivity);
	        	return true;
	        case R.id.test_channel:
	        	newActivity = new Intent(this, CvmChannelTest.class);
	        	this.startActivity(newActivity);
	        	return true;
	        case R.id.test_matrixtypes:
	        	newActivity = new Intent(this, CvmMatrixTypesTest.class);
	        	this.startActivity(newActivity);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	        }
    	}
    }