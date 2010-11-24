/*******************************************************************************
 * CVM - Computer Vision Mobile.
 * Copyright (C) 2010 CVM Luis Ciudad García
 * 
 * File: CvmMatrix.java - This file is part of CVM.
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

public abstract class CvmMatrix {
	/** Indica que el cálculo del determinante se realice mediante el algoritmo de Doolittle*/
	public final static int DOOLITTLE_METHOD = 1;	
	/** Indica que el cálculo del determinante se realice mediante el metodos de los adjuntos */
	public final static int ADJUNCT_METHOD = 2;
	
	/** Número de filas de la matriz. */
	protected final int rows; 
	/** Número de columnas de la matriz. */
	protected final int cols;
	
	public CvmMatrix(int rows, int cols){
		this.rows = rows;
		this.cols = cols;
		}
	
	/**
	 * Observador de la propiedad rows.
	 * 
	 * @return Número de filas de la matriz.
	 */
    public int getRows(){
        return this.rows;
        }

   /**
    * Observador de la propiedad cols.
    * 
    * @return Número de columnas de la matriz.
    */
    public int getCols(){
        return this.cols;
        }
    
    public abstract CvmMatrixDouble getInverse();
	}
