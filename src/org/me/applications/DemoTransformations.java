/*******************************************************************************
 * CVM - Computer Vision Mobile.
 * Copyright (C) 2010 CVM Luis Ciudad Garc�a
 * 
 * File: DemoTransformations.java - This file is part of CVM.
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

import org.me.applications.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import edu.uco.cvm.core.CvmImage;

public class DemoTransformations extends Activity {
	
	@Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.demotransformations);
        
        final Button button = (Button) findViewById(R.id.ButtonAccept);
        final ImageView imageTransfo = (ImageView) findViewById(R.id.ImageTransfo);
        final ImageView imageOriginal = (ImageView) findViewById(R.id.ImageOriginal);
        final TextView debugText = (TextView) findViewById(R.id.debug);
        final Spinner imagesCombo = (Spinner) findViewById(R.id.ImagesCombo);
        
        ArrayAdapter adapterImages = ArrayAdapter.createFromResource(this, R.array.images, android.R.layout.simple_spinner_item);
        	adapterImages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        imagesCombo.setAdapter(adapterImages);     
        imagesCombo.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
        	public void onItemSelected(AdapterView parent, View v, int position, long id) {
        		}

        	public void onNothingSelected(AdapterView parent){
        		}  
        	}
        );
        
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	Bitmap mBitmap;
        		
        		switch(imagesCombo.getSelectedItemPosition()){
        		case 0:
        			mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_cat);
        			break;
        		case 1:
        			mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_casco);
        			break;
        		case 2:
        			mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_bn);
        			break;
        		default:
        			mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_bn);
        			}
        		
        		imageOriginal.setImageBitmap(mBitmap);
	            
            	float scaleX = 0.0f, scaleY = 0.0f, transX = 0.0f, transY = 0.0f, rotation = 0.0f;
            	
            	EditText widget = (EditText) findViewById(R.id.EditScaleX);
            	scaleX = Float.parseFloat(widget.getText().toString());
            	
            	widget = (EditText) findViewById(R.id.EditScaleY);
            	scaleY = Float.parseFloat(widget.getText().toString());
            	
            	widget = (EditText) findViewById(R.id.EditTransX);
            	transX = Float.parseFloat(widget.getText().toString());
            	
            	widget = (EditText) findViewById(R.id.EditTransY);
            	transY = Float.parseFloat(widget.getText().toString());

            	widget = (EditText) findViewById(R.id.EditRotation);
            	rotation = Float.parseFloat(widget.getText().toString());
            	
            	try{
            		long t1 = System.currentTimeMillis();
            		
	            	CvmImage mImage = new CvmImage(mBitmap, CvmImage.GRAYSCALE);	            	
	            	
	            	if(rotation != 0)
	            		mImage.rotate(rotation);
	            	if(scaleX != 0 && scaleY != 0)
	            		mImage.scale(scaleX, scaleY);
	            	if(transX != 0 && transY != 0)
	            		mImage.translate((int)transX, (int)transY);
	            	
	                mImage.applyTransform();
	                
	                imageTransfo.setImageBitmap(mImage.toBitmap());
	                
	                long t2 = System.currentTimeMillis();
	                
	                debugText.setText("Total time: " + (t2-t1) + "ms");
            		}
            	catch(Exception e){}
	            }
	        });
        
		}
	
	}
