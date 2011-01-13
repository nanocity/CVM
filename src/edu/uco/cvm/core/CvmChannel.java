/*******************************************************************************
 * CVM - Computer Vision Mobile.
 * Copyright (C) 2010 CVM Luis Ciudad García
 * 
 * File: CvmChannel.java - This file is part of CVM.
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

import java.util.Hashtable;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.util.Log;
import edu.uco.cvm.exceptions.CvmIncompatibleMatrixSizeException;

/**
 * <p>Esta clase representa cada uno de los canales de color en los que se divide una imagen.</p>
 * 
 * <p>Un canal es una matriz de valores ordenados por filas y columnas (manteniendo la misma 
 * distribución que los píxeles de la imagen) en la que cada elemento representa el valor 
 * con el que ese canal participa en la formación de la imagen final.</p>
 * 
 * <p>Dependiendo de la codificación utilizada, dicha imagen puede contener los canales rojo, 
 * verde y azul (RGB); cian, magenta y amarillo (CMY) o cualquier otra combinación siempre 
 * que el elemento que dibuje la imagen sea capaz de descodificarlo. Puede ocurrir que una 
 * imagen solo posea un canal, como en el caso de los imágenes en escala de grises.</p>
 * 
 * @author Luis Ciudad García.
 * @version 1.0
 */

public class CvmChannel extends CvmMatrixInt {
	
	public static final int GRAY = 0;

	public static final int RED = 1;
	public static final int GREEN = 2;
	public static final int BLUE = 3;
	public static final int ALPHA = 4;
	
	public static final int HUE = 5;
	public static final int SATURATION = 6;
	public static final int VALUE = 7;
	
	public static final int THRES_BINARY = 0;
	public static final int THRES_BINARY_INV = 1;
	public static final int THRES_TRUNC = 2;
	public static final int THRES_TO_ZERO = 3;
	public static final int THRES_TO_ZERO_INV = 4;
	public static final int THRES_OTSU = 5;
	
	private final int channel;
	
	/**
	 * Crea un canal a partir de un objeto Bitmap. Debe indicarse que canal del Bitmap se quiere
	 * cargar.
	 * 
	 * @param bm Objeto de la clase Bitmap que contiene la información de una imagen.
	 * @param channel Canal que se quiere guardar.
	 * 
	 * @exception NegativeArraySizeException Se lanza en caso de que las dimensiones del Bitmap 
	 * sean negativas.
	 */
	public CvmChannel(Bitmap bm, int channel) throws NegativeArraySizeException{
		super(bm.getHeight(), bm.getWidth());
		
		this.channel = channel;
		
        int pixels [] = new int[this.cols * this.rows];
        bm.getPixels(pixels, 0, this.cols, 0, 0, this.cols, this.rows);
        
		if(channel == CvmChannel.RED){
			for(int i = 0; i < this.data.length; i++)
				this.data[i] = (pixels[i] >> 16) & 0xff;
			}
		else if(channel == CvmChannel.GREEN){
			for(int i = 0; i < this.data.length; i++)
				this.data[i] = (pixels[i] >> 8) & 0xff;
			}
		else if(channel == CvmChannel.BLUE){
			for(int i = 0; i < this.data.length; i++)
				this.data[i] = (pixels[i]) & 0xff;
			}
		else if(channel == CvmChannel.GRAY){
			for(int i = 0; i < this.data.length; i++){
				int color = pixels[i];
				
				int r = (color >> 16) & 0xff;
	            int g = (color >> 8) & 0xff;
	            int b = color & 0xff;
	            
	            this.data[i] = Math.round((11*r + 16*g + 5*b) /32);
				}
			}
		}
	
	public CvmChannel(CvmMatrixInt matrix){
		super(matrix);
		
		this.channel = CvmChannel.GRAY;
		}
	
	/**
	 * Crea un canal a partir de los valores contenidos en una matriz nativa de Java.
	 * Las dimensiones de la matriz deben coincidir con las del canal.
	 * 
	 * @param width Anchura del canalen pixels.
	 * @param height Altura del canal en pixels.
	 * @param values Conjunto de valores que tomarán los pixeles del canal.
	 * 
	 * @throws NegativeArraySizeException Se lanza si las dimensiones del canal son negativas.
	 * @throws ArrayIndexOutOfBoundsException Se lanza si las dimensiones de la matriz no coindicen
	 * con las del canal.
	 */
	public CvmChannel(int width, int height, int values[][], int channel) throws NegativeArraySizeException, ArrayIndexOutOfBoundsException {
		super(height, width, values);
		
		this.channel = channel;
		}
	
	/**
	 * Crea un canal a partir de los valores contenidos en un vector nativo de Java.
	 * Las dimensiones de la matriz deben coincidir con las del canal.
	 * 
	 * @param width Anchura del canalen pixels.
	 * @param height Altura del canal en pixels.
	 * @param values Conjunto de valores que tomarán los pixeles del canal.
	 * 
	 * @throws NegativeArraySizeException Se lanza si las dimensiones del canal son negativas.
	 * @throws ArrayIndexOutOfBoundsException Se lanza si las dimensiones de la matriz no coindicen
	 * con las del canal.
	 */
	public CvmChannel(int width, int height, int values[], int channel) throws NegativeArraySizeException, ArrayIndexOutOfBoundsException {
		super(height, width, values);
		
		this.channel = channel;
		}
	
	/**
	 * Aplica una transformación geométrica sobre cada uno de los pixels que forman el canal.
	 * Dicha transformación se representa en forma de matriz cuadrada de tamaño 3x3.
	 * 
	 * @param tr Matrix de transformación a aplicar sobre los pixels del canal.
	 * 
	 * @throws Exception  !!!Controlar que se calcula bien la inversa de la matriz.
	 * @throws CvmIncompatibleMatrixSizeException Se lanza si la matriz de transformación ni tiene
	 * unas dimensiones de 3x3.
	 */
	public void applyTransform(CvmMatrixDouble tr) throws Exception, CvmIncompatibleMatrixSizeException{
		if(tr.cols != 3 || tr.rows != 3)
			throw new CvmIncompatibleMatrixSizeException(tr.rows, tr.cols);
		
        int[] outdata = new int[this.rows * this.cols];
		
		CvmMatrixInt Pp = new CvmMatrixInt(3,1);
        CvmMatrixInt Ti = tr.getInverse().scaledMatrixInt();
        double scaleFactorInv = 1 / Ti.scaleFactor;
        
        for(int i = 0; i < this.data.length; i++){
        	/** Para cada píxel incializamos Pp con las coordenadas del punto de la imagen resultante que queremos calcular */
        	Pp.data[0] = i / this.cols;
        	Pp.data[1] = i % this.cols;
        	Pp.data[2] = 1;
        	
        	/**
             * Aplicamos la fórmula para saber que píxel de la imagen original corresponde a cada uno de los píxeles de la imagen resultante que estamos recorriendo con el bucle
             */
            CvmMatrixInt P = Ti.getMul(Pp);
            
            /**
             * Comprobamos que el pixel que va en la posicion actual corresponda con un pixel de la imagen original.
             * Si es pixel esta en la imagen original lo copiamos a la de destino y en caso contrario ponemos un pixel negro.
             */
            int x = (int)(P.data[0] * scaleFactorInv);
            int y = (int)(P.data[1] * scaleFactorInv);
            
            if(x < 0 || x >= this.rows || y < 0 || y >= this.cols){
                outdata[i] = 255;
                }
            else{
            	outdata[i] = this.data[x * this.cols + y];
                }
        	}
        
        this.data = outdata;
        }
	
	/**
	 * ¡¡¡¡PROBAR VALORES NEGATIVOS DE X E Y!!!!
	 * 
	 * <p>Obtiene la región cuadrada del canal cuyo centro se indicada en los parámetros como un nuevo 
	 * objeto de tipo canal. Si algún elemento de la nueva región corresponde a un pixel fuera de la 
	 * imagen es devuelto con el valor 0.</p>
	 * <p>Si se indica un tamaño de region par, esta se desplazará hacia abajo y hacia la derecha para
	 * las dimensiones que se indican</p> 
	 * 
	 * @param x Columna del punto central de la región.
	 * @param y Fila del punto central de la reguión.
	 * @param size Tamaño del lado de la región a recuperar.
	 * 
	 * @return Nuevo canal de dimensiones cuadradas ([size, size]) que contiene la región del
	 * canal actual que indican los parámetros.
	 * 
	 * @throws NegativeArraySizeException Se lanza si el tamaño de la región es negativo.
	 */
	public CvmMatrixInt getCenterSquare(int x, int y, int size) throws NegativeArraySizeException{
        CvmMatrixInt rect = new CvmMatrixInt(size, size);

        int offset = (size % 2 == 0) ? (size / 2) - 1 : (size / 2);

        for(int i = x - offset; i < x - offset + size; i++){
            for(int j = y - offset; j < y - offset + size; j++){
                int value = 0;
                
                if(i >= 0 && i < this.rows && j >= 0 && j < this.cols){
                    value = this.data[i * this.cols + j];
                    }

                rect.setElement(i - (x - offset), j - (y - offset), value);
                }
            }
        
        return rect.getTranspose();
        }
	
	/**
	 * <p>Aplica una máscara de convolución sobre todos los pixels del canal. Esto quiere decir que el valor
	 * de cada pixel es el resultado de sumar el producto de cada elemento de la máscara con el pixel de
	 * el canal que corresponda.</p> 
	 * <p>Esta máscara se mueve desde el primer al último pixel, por lo que en algunos casos, la máscara
	 * caerá fuera de los márgenes del canal. En este caso los elementos de la máscara son multiplicados
	 * por cero.</p>
	 * 
	 * @param mask Máscara de convolución que se aplica. Debe ser cuadrada.
	 * 
	 * @exception CvmIncompatibleMatrixSizeException Se lanza si la máscara no es cuadrada.
	 */
	public void applyMask(CvmMatrixDouble mask) throws CvmIncompatibleMatrixSizeException, Exception{
		if(mask.cols != mask.rows)
			throw new CvmIncompatibleMatrixSizeException(mask.rows, mask.cols);
		
		int[] outdata = new int[this.data.length];

		CvmMatrixInt auxMask = mask.scaledMatrixInt();
		double scaleFactorInv = 1 / auxMask.scaleFactor;

		for(int i = 0; i < this.data.length; i++){
			CvmMatrixInt channelValues = this.getCenterSquare(i / this.cols, i % this.cols, mask.getCols());
            int pixelValue = 0;

            for(int k = 0; k < mask.data.length; k++){
                pixelValue += channelValues.data[k] * auxMask.data[k];
                }
            
            int px = (int)(pixelValue * scaleFactorInv);
            
            /*if(px > 255) px = 255;
            else if(px < 0) px = 0;*/
                       
            outdata[i] = px;
            }
		
        this.data = outdata;
        }
	
	public void applyThreshold(int threshold, int max, int method){
		int min = 0;
		
		switch(method){
			default:
			case CvmChannel.THRES_BINARY:
				for(int i = 0; i < this.data.length; i++)
					this.data[i] = (this.data[i] > threshold) ? max : min;
				break;
			case CvmChannel.THRES_BINARY_INV:
				for(int i = 0; i < this.data.length; i++)
					this.data[i] = (this.data[i] > threshold) ? min : max;
				break;
			case CvmChannel.THRES_TRUNC:
				for(int i = 0; i < this.data.length; i++)
					this.data[i] = (this.data[i] > threshold) ? threshold : this.data[i];
				break;
			case CvmChannel.THRES_TO_ZERO:
				for(int i = 0; i < this.data.length; i++)
					this.data[i] = (this.data[i] > threshold) ? threshold : min;
				break;
			case CvmChannel.THRES_TO_ZERO_INV:
				for(int i = 0; i < this.data.length; i++)
					this.data[i] = (this.data[i] > threshold) ? threshold : max;
				break;
			case CvmChannel.THRES_OTSU:
				threshold = this.calculateOtsuThreshold();
				for(int i = 0; i < this.data.length; i++)
					this.data[i] = (this.data[i] > threshold) ? max : min;
				break;
			}
		}
	
	private int calculateOtsuThreshold(){
		int thres = 0, numPixels = 0;
		double wB = 0, wF = 1;
		double WCV, minWCV = 0;
		
		//this.updateHistogram();
		
		CvmHistogram hist = new CvmHistogram(this);
		
		for(int i = 0; i < 256; i++){
			numPixels += hist.get(i);
			if(numPixels == 0)
				break;
			
			wB = numPixels / (double)this.data.length;
			wF = 1 - wB;
			
			float meanB = 0;
			for(int j = 0; j <= i; j++)
				meanB += j * hist.get(j);
			meanB /= numPixels;
			float varianceB = 0;
			for(int j = 0; j <= i; j++)
				varianceB += (j - meanB) * (j - meanB) * hist.get(j);
			varianceB /= numPixels;
			
			float meanF = 0;
			for(int j = i+1; j < 256; j++)
				meanF += j * hist.get(j);
			meanF /= this.data.length - numPixels;
			float varianceF = 0;
			for(int j = i+1; j < 256; j++)
				varianceF += (j - meanF) * (j - meanF) * hist.get(j);
			varianceF /= this.data.length - numPixels;
			
			WCV = varianceB*wB + varianceF*wF;
			
			if(i == 0 || WCV < minWCV){
				thres = i;
				minWCV = WCV;
				}
			}
		
		return thres;
		}
	
	public void normalize(){
		int min = 0, max = 0;
		
		min = max = this.data[0];
		
		for(int i = 1; i < this.data.length; i++){
			if(this.data[i] < min)
				min = this.data[i];
			if(this.data[i] > max)
				max = this.data[i];
			}
		
		for(int i = 0; i < this.data.length; i++){
			this.data[i] = (int)((double)((this.data[i] - min) / (double)(max - min)) * 255);
			}
		}
	
	public void module(CvmChannel channel){
		if(this.cols != channel.cols || this.rows != channel.rows)
			return; //Excepcion!!!
		
		for(int i = 0; i < this.data.length; i++)
			this.data[i] = (int)Math.sqrt(this.data[i] * this.data[i] + channel.data[i] * channel.data[i]);
		}
	
	public CvmHistogram getValuesHistogram(){
		CvmHistogram hist = new CvmHistogram(this);
		
		return hist;
		}

	public CvmHistogram getGradientHistogram(){
		CvmHistogram hist = new CvmHistogram();		
		
		CvmChannel hor = new CvmChannel(this);
		CvmChannel ver = new CvmChannel(this);
		
		try{
			hor.applyMask(CvmMaskFactory.getHSobelMask());
			ver.applyMask(CvmMaskFactory.getVSobelMask());
			}
		catch(Exception e){}
		
		for(int i = 0; i < hor.data.length; i++){
			int angle = 0;
			try{
				angle = ((int) Math.toDegrees(Math.atan(ver.data[i] / (double)hor.data[i])));
				if(angle < 0)
					angle = 180 - Math.abs(angle);
				else
					angle = angle % 180;
				}
			catch(ArithmeticException e){
				angle = -1;
				}
			
			hist.increment(angle);
			}
		
		return hist;
		}
	
	public CvmChannel cannyEdgeDetector(){
		CvmChannel copy = new CvmChannel(this);
		
		CvmChannel channelA = new CvmChannel(copy);
		CvmChannel channelB = new CvmChannel(copy);		
		try{
			//Aplicacmos una mascara Gaussiana
			copy.applyMask(CvmMaskFactory.getGaussianMask(5, 1.4));
			
			
			//Pasamos los filtros de sobel
			channelA.applyMask(CvmMaskFactory.getHSobelMask());
			channelB.applyMask(CvmMaskFactory.getVSobelMask());
			
			//Calculamos el valor del modulo y el del angulo
			copy = new CvmChannel(channelA);
			copy.module(channelB);
			
			for(int i = 0; i < channelA.data.length; i++){
				int angle = 0;
				try{
					angle = ((int) Math.toDegrees(Math.atan(channelB.data[i] / (double)channelA.data[i])));
					}
				catch(ArithmeticException e){
					angle = -1;
					}
				
				if(angle < 0)
					angle = 180 - Math.abs(angle);
				else
					angle = angle % 180;
				
				channelA.data[i] = angle;
				}
			
			//Ahora en funcion de la direccion de angulo "adelgazamos la imagen"
			for(int i = 0; i < channelA.data.length; i++){
				int value = copy.data[i];
				int angle = channelA.data[i];
				int n1 = 0, n2 = 0;
				
				try{
					if(angle >= 0 && angle < 45){
						n1 = copy.data[i-1];
						n2 = copy.data[i+1];
						}
					else if(angle >= 45 && angle < 90){
						n1 = copy.data[i - copy.cols - 1];
						n2 = copy.data[i + copy.cols - 1];
						}
					else if(angle >= 90 && angle < 135){
						n1 = copy.data[i - copy.cols];
						n2 = copy.data[i + copy.cols];
						}
					else if(angle >= 135 && angle < 180){
						n1 = copy.data[i - copy.cols + 1];
						n2 = copy.data[i + copy.cols + 1];
						}
					}
				catch(ArrayIndexOutOfBoundsException e){
					n1 = 0;
					n2 = 0;
					}
				
				if(value < n1 || value < n2)
					channelB.data[i] = 0;
				}
			}
		catch(Exception e){}
		
		return channelB;
		}
	
	/**
     * Convierte el canal a un Bitmap, formato nativo de Android en el que se representan las imagenes.
     * 
     * @return Equivalente en el formato Bitmap al canal mantenida por esta clase.
     */
    public Bitmap toBitmap(){
    	this.normalize();
    	int pixels[] = this.toArray();
    	
    	for(int i = 0; i < pixels.length; i++){    		
    		pixels[i] = Color.rgb(pixels[i],pixels[i],pixels[i]);
    		}
    	    	
    	return Bitmap.createBitmap(pixels, this.cols, this.rows, Config.ARGB_8888);
    	}
    
    /**
     * Convierte el canal a un Bitmap, formato nativo de Android en el que se representan las imagenes.
     * 
     * @return Equivalente en el formato Bitmap al canal mantenida por esta clase.
     */
    public Bitmap toBitmap(boolean normalize){
    	if(normalize == true)
    		this.normalize();
    	
    	//Sino, se deja que los valores se pasen

    	int pixels[] = this.toArray();
    	
    	for(int i = 0; i < pixels.length; i++){    		
    		pixels[i] = Color.rgb(pixels[i],pixels[i],pixels[i]);
    		}
    	    	
    	return Bitmap.createBitmap(pixels, this.cols, this.rows, Config.ARGB_8888);
    	}
	}
