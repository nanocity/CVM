/*******************************************************************************
 * CVM - Computer Vision Mobile.
 * Copyright (C) 2010 CVM Luis Ciudad García
 * 
 * File: MainActivity.java - This file is part of CVM.
 * 
 * CVM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * CVM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with CVM.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package org.me.applications;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import edu.uco.cvm.test.CvmChannelTest;
import edu.uco.cvm.test.CvmColorSpaceTest;
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
	        case R.id.test_colorspace:
	        	newActivity = new Intent(this, CvmColorSpaceTest.class);
	        	this.startActivity(newActivity);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	        }
    	}
    }
