/*******************************************************************************
 * CVM - Computer Vision Mobile.
 * Copyright (C) 2010 CVM Luis Ciudad García
 * 
 * File: CvmMatrixTypesTest.java - This file is part of CVM.
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
import android.os.Bundle;
import android.widget.TextView;
import edu.uco.cvm.core.CvmMatrixDouble;
import edu.uco.cvm.core.CvmMatrixInt;
import edu.uco.cvm.core.CvmMatrixShort;

public class CvmMatrixTypesTest extends Activity{
	
	public final int MAX_ITERS = 1000;
	public final int MAX_PROOFS = 100;
	
	long t1,t2;
	long tDouble = 0, tInt = 0, tShort = 0;
	@Override
    public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
        setContentView(R.layout.cvmmatrixtypestest);
        
        double auxD[][] = {{1,2,3},{9,8,7},{5,4,6}};
        int auxI[][] = {{1,2,3},{9,8,7},{5,4,6}};
        short auxS[][] = {{1,2,3},{9,8,7},{5,4,6}};
        
        CvmMatrixDouble mDoubles = new CvmMatrixDouble(3,3,auxD);
        CvmMatrixInt mInt = new CvmMatrixInt(3,3,auxI);
        CvmMatrixShort mShort = new CvmMatrixShort(3,3,auxS);
        
        for(int j = 0; j < this.MAX_PROOFS; j++){
	        /**
	         * Doubles
	         */
	        t1 = System.currentTimeMillis();
	        for(int i = 0; i < this.MAX_ITERS ; i++)
	        	mDoubles.getMul(mDoubles);
	        t2 = System.currentTimeMillis();
	        tDouble += t2-t1;
	        
	        /**
	         * Integer
	         */
	        t1 = System.currentTimeMillis();
	        for(int i = 0; i < this.MAX_ITERS ; i++)
	        	mInt.getMul(mInt);
	        t2 = System.currentTimeMillis();
	        tInt += t2-t1;
	        
	        /**
	         * Short
	         */
	        t1 = System.currentTimeMillis();
	        for(int i = 0; i < this.MAX_ITERS ; i++)
	        	mShort.getMul(mShort);
	        t2 = System.currentTimeMillis();
	        tShort += t2-t1;
	        }
        
        String times = 
        	"Double: " + tDouble/this.MAX_PROOFS + " ms\n" +
        	"Integer: " + tInt/this.MAX_PROOFS + " ms\n" +
        	"Short: " + tShort/this.MAX_PROOFS + " ms\n";
        
        TextView text = (TextView) findViewById(R.id.debug);
        text.setText(times);
		}
	}
