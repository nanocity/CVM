/*******************************************************************************
 * CVM - Computer Vision Mobile.
 * Copyright (C) 2010 CVM Luis Ciudad García
 * 
 * File: CvmColorSpaceTest.java - This file is part of CVM.
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
package edu.uco.cvm.test;

import org.me.applications.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import edu.uco.cvm.core.CvmImage;

public class CvmColorSpaceTest extends Activity{

	private CvmImage mImage;
	
	private ImageView originalIV;
	private ImageView channel1IV;
	private ImageView channel2IV;
	private ImageView channel3IV;
	
	@Override
    public void onCreate(Bundle icicle) {
		
		super.onCreate(icicle);
        setContentView(R.layout.cvmcolorspacetest);		
        
        originalIV = (ImageView) findViewById(R.id.originalIV);
    	channel1IV = (ImageView) findViewById(R.id.channel1);
    	channel2IV = (ImageView) findViewById(R.id.channel2);
    	channel3IV = (ImageView) findViewById(R.id.channel3);
        
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fire);        
		mImage = new CvmImage(mBitmap, CvmImage.RGB);
		mImage.changeColorSpace(CvmImage.HSV);
		
		originalIV.setImageBitmap(mBitmap);
		
		channel1IV.setImageBitmap(mImage.getChannel(0).toBitmap());
		channel2IV.setImageBitmap(mImage.getChannel(1).toBitmap());
		channel3IV.setImageBitmap(mImage.getChannel(2).toBitmap());
		}
	}
