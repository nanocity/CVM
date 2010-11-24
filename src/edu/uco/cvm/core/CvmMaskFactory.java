/*******************************************************************************
 * CVM - Computer Vision Mobile.
 * Copyright (C) 2010 CVM Luis Ciudad García
 * 
 * File: CvmMaskFactory.java - This file is part of CVM.
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

/**
 * <p>Factoría de máscaras para ser utilizadas sobre las imágenes.</p>
 * 
 * <p>Se trata de una clase que contiene varios métodos estaticos que proporcionan máscaras
 * útiles en el procesamiento de imágenes.
 * </p>     
 * @author Luis Ciudad García.
 * @version 1.0 
 */
public class CvmMaskFactory {
	
    public static CvmMatrixDouble getAverageMask(int size) throws Exception{
        CvmMatrixDouble mask = new CvmMatrixDouble(size, size, 1 / (double)(size*size));

        return mask;
        }

    public static CvmMatrixDouble getGaussianMask(int size, double sigma) throws Exception{
        /** Variables auxiliares */
        double val = 0;
        double aux = 0, expo = 0;
        CvmMatrixDouble mask = new CvmMatrixDouble(size, size, 0);

        /** Calculamos la parte que no cambia en funcion de la posicion del pixel */
        aux = (1 / (2 * Math.PI * sigma * sigma));

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                /** Para cada pixel dentro de la mascara calculamos el valor de la funcion gaussiana */
                expo = -0.5 * ((Math.pow(i - Math.floor(size / 2), 2)/Math.pow(sigma,2)) + (Math.pow(j - Math.floor(size / 2), 2)/Math.pow(sigma,2)));
                val = aux * Math.pow(Math.E, expo);
                mask.setElement(i, j, val);
                }
            }

        return mask;
        }

    public static CvmMatrixDouble getHighBoostMask(int size, int A) throws Exception{
        CvmMatrixDouble mask = new CvmMatrixDouble(size, size, -1);

        /** El pixel central de la mascara lo rellenamos con el valor correspondiente */
        mask.setElement((int)Math.ceil(size / 2), (int)Math.ceil(size / 2), (size*size + A - 1));

        return mask;
        }
    
    public static CvmMatrixDouble getVSobelMask() throws Exception{
        CvmMatrixDouble mask = new CvmMatrixDouble(3, 3, 0);

        mask.setElement(0, 0, -1);
        mask.setElement(0, 2, 1);

        mask.setElement(1, 0, -2);
        mask.setElement(1, 2, 2);

        mask.setElement(2, 0, -1);
        mask.setElement(2, 2, 1);

        return mask;
        }

    public static CvmMatrixDouble getHSobelMask() throws Exception{
        CvmMatrixDouble mask = new CvmMatrixDouble(3, 3, 0);

        mask.setElement(0, 0, -1);
        mask.setElement(0, 1, -2);
        mask.setElement(0, 2, -1);

        mask.setElement(2, 0, 1);
        mask.setElement(2, 1, 2);
        mask.setElement(2, 2, 1);

        return mask;
        }
    }
