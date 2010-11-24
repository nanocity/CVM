/*******************************************************************************
 * CVM - Computer Vision Mobile.
 * Copyright (C) 2010 CVM Luis Ciudad García
 * 
 * File: CvmHistogram.java - This file is part of CVM.
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
package edu.uco.cvm.core;


public class CvmHistogram {	
	private int maxValue;
	private int[] data;
	
	public CvmHistogram(){
		this.data = new int[256];
		this.maxValue = 0;
		}
	
	/*public CvmHistogram(K[] data){
		super();
		
		this.maxValue = 0;
		
		for(int i = 0; i < data.length; i++)
			this.inc(data[i]);	
		}*/
	
	public void inc(int value){
		// cuidado con value fuera de rango
		this.data[value] += 1;

		if(this.data[value] > this.maxValue)
			this.maxValue = this.data[value];
		}

	public int get(int value){
		return this.data[value];
		}
	
	public void reset(){
		for(int i = 0; i < 256; i++)
			this.data[i] = 0;
		
		this.maxValue = 0;
		}
	
	/**
	 * El ancho debe ser al menos 256, si no la distorsion hace que no se pudan mostrar todos los valores
	 * y es un follon.
	 * 
	 * @param width
	 * @param height
	 * @return
	 */
	
	public CvmChannel getImage(){
		CvmMatrixInt matrix = new CvmMatrixInt(256, 256, 128);
		
		for(int i = 0; i < 256; i++){
			int amount = this.data[i];
			
			double factor = (double)amount / (double)this.maxValue;
			
			amount = (int)(factor * 256);
			
			for(int j = 256 - amount; j < 256; j++){
				matrix.setElement(i, j, 255);
				}
			}
		
		CvmChannel channel = new CvmChannel(matrix.getTranspose());
		
		return channel;
		}
	}
