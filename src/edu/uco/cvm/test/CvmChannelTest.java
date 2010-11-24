/*******************************************************************************
 * CVM - Computer Vision Mobile.
 * Copyright (C) 2010 CVM Luis Ciudad García
 * 
 * File: CvmChannelTest.java - This file is part of CVM.
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
import edu.uco.cvm.core.CvmChannel;
import edu.uco.cvm.core.CvmMaskFactory;

public class CvmChannelTest extends Activity {
	
	private ImageView original;
	private ImageView red;
	private ImageView green;
	private ImageView blue;
	
	@Override
    public void onCreate(Bundle icicle) {
		
		super.onCreate(icicle);
        setContentView(R.layout.cvmchanneltest);	
		
        ImageView original = (ImageView) findViewById(R.id.channelTestOriginal);
    	ImageView red = (ImageView) findViewById(R.id.channelTestR);
    	ImageView green = (ImageView) findViewById(R.id.channelTestG);
    	ImageView blue = (ImageView) findViewById(R.id.channelTestB);
        
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_cat);
        original.setImageBitmap(mBitmap);
        
        CvmChannel c1 = new CvmChannel(mBitmap, CvmChannel.GRAY);
        
        red.setImageBitmap(c1.toBitmap());
        c1.applyThreshold(100, 255, CvmChannel.THRES_OTSU);
        green.setImageBitmap(c1.toBitmap());
        c1.updateHistogram();
        blue.setImageBitmap(c1.getHistogramImage());     
		}
	
	}
