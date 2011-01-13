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

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * <p>Esta clase representa la cantidad de veces que aparece un determinado valor en una imagen.</p>
 * 
 * <p>Dichos valores pueden ser cantidad de luminosidad de pixels, modulo o angulo de un gradiente, etc.
 * En general cualquier valor entero.</p>
 * 
 * @author Luis Ciudad García.
 * @version 1.0
 *
 */
public class CvmHistogram extends TreeMap<Integer, Integer>{	
	
	/** Clave correspondiente al valor que más ocurrencias tiene dentro del histograma. */
	Integer maxOcurrKey;
	
	/**
	 * Constructor vacío.
	 * Crea un histograma sin ninguna clave y ningún valor asignado.
	 */
	public CvmHistogram(){
		super();
		
		this.maxOcurrKey = null;
		}
	
	/**
	 * Crea un histograma del canal pasado como parametro.
	 * 
	 * @param channel Canal usado como base en la creación del histograma. 
	 */
	public CvmHistogram(CvmChannel channel){
		super();
		
		for(int i = 0; i < channel.data.length; i++)
			this.increment(channel.data[i]);
		}
	
	/**
	 * Incrementa en una unidad la clave correspondiente. No es necesario crear explicitamente
	 * la clave que se quiere incrementar ya que si esta no existe la crea este método.
	 * 
	 * @param key Clave cuyo valor se incrementará en una unidad
	 * 
	 * @return Si la clave ya existía devuelve el valor anterior al incremento, en caso
	 * contrario la función devuelve null.
	 */
	public Integer increment(int key){
		if(this.containsKey(key)){
			Integer value = this.get(key);
			this.put(key, value + 1);
			
			if(this.get(this.maxOcurrKey) < value + 1)
				this.maxOcurrKey = key;
			
			return value;
			}
		else{
			this.put(key, 1);
			
			if(this.maxOcurrKey == null || this.get(this.maxOcurrKey) < 1)
				this.maxOcurrKey = key;
			
			return null;
			}
		}
	
	/**
	 * Recupera el valor asociado a una clave.
	 * 
	 * @param key Clave asociada al valor que se devuelve.
	 * 
	 * @return Se devuelve el valor de la clave que se especifica en el parámetro, si
	 * la clave no existe en el histograma se devuelve el valor cero.
	 */
	public Integer get(Integer key){
		Integer value = super.get(key);
		
		return (value == null) ? 0 : value; 	
		}
	
	/**
	 * Asigna a una clave un valor concreto. No es necesario que la clave exista con 
	 * anterioridad ya que si esta no existe en el histograma será creada.
	 * 
	 * @param key Clave a la que asignar el valor.
	 * @param value Valor que se asociará con la clave.
	 * 
	 * @return Si la clave ya existía devuelve el valor anterior al incremento, en caso
	 * contrario la función devuelve null.
	 * 
	 * @exception ClassCastException
	 * @exception NullPointerException
	 */
	public Integer put(Integer key, Integer value) throws ClassCastException, NullPointerException {
		Integer res = super.put(key, value);
		
		if(this.maxOcurrKey == null || this.get(this.maxOcurrKey) < value)
			this.maxOcurrKey = key;
		
		return res;
		}
	
	/**
	 * Sustituye el mapeo del histograma por el que se pasa como paramétro.
	 * 
	 * @param map Mapa que sustituirá al mapeo actual del histograma.
	 * 
	 * @exception ClassCastException
	 * @exception NullPointerException
	 */
	public void putAll(Map map) throws ClassCastException, NullPointerException {
		super.putAll(map);
		
		Set<Integer> keys = this.keySet();
		Iterator<Integer> it = keys.iterator();
		
		this.maxOcurrKey = it.next();
		
		while(it.hasNext()){
			Integer key = it.next();
			if(this.get(this.maxOcurrKey) < this.get(key))
				this.maxOcurrKey = key;
			}
		}
	
	/**
	 * <p>Obtiene una imagen que representa el histograma del canal como un gráfico de barras.</p>
	 * <p>En cada columna se representan la cantidad de pixels que toman el valor de dicha columna 
	 * en el canal de izquierda a derecha comenzando en el mínimo hasta el máximo. La altura de la barra es 
	 * proporcional al alto de la imagen, lo que quiere decir que el valor de luminosidad que tengan
	 * más pixels siempre llegará a lo mas alto de la imagen y los demás valores serán relativos a él.</p>
	 * <p>Para que no se produzca una pérdidad de información, es necesario que el ancho de la imagen
	 * sea suficiente para representar cada valor en al menos un pixel, esto quiere decir que el ancho
	 * de la imagen debe ser al menos igual al rango de valores que se desea representar.</p>
	 * 
	 * @param min Valor mínimo del histograma que se representa en la imagen.
	 * @param max Valor máximo del histograma que se representa en la imagen.
	 * @param width Ancho de la imagen.
	 * @param height Alto de la imagen.
	 * 
	 * @return Imagen que representa el histograma del canal. 
	 */
	public CvmChannel getImage(int min, int max, int width, int height){
		CvmMatrixInt matrix = new CvmMatrixInt(width, height, 127);
		int range = max - min + 1;
		
		double factorW = range / (double) width;
		double factorH = this.get(this.maxOcurrKey) / (double)height;
		
		if(width < range){ // o bien factorW < 1.0
			return null; //No se pueden representar algunos valores y no se puede asegurar que el valor que no se muestre no sea representativo
			}
		
		for(int i = 0; i < width; i++){
			Integer key = (int)(Math.round(i * factorW) + min);
			for(int j = height - (int)(this.get(key) / factorH); j < height; j++)
				matrix.setElement(i, j, 255);
			}
		
		CvmChannel channel = new CvmChannel(matrix.getTranspose());
		
		return channel;
		}
	}
