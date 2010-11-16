package edu.uco.cvm.core;

import java.util.Arrays;

import edu.uco.cvm.exceptions.CvmIncompatibleMatrixSizeException;
import edu.uco.cvm.exceptions.CvmSingularMatrixException;

/**
 * <p>Esta clase representa un conjunto de valores ordenados en forma de matriz, los cuales
 * pueden ser accedidos a partir de la posición que ocupan.</p>
 * 
 * <p>En una representación visual, el primer elemento será el que ocupa la posicion (0,0) y
 * estarí­a situado en la esquina superior izquierda de la matriz. A partir de ese punto la
 * primera coordenada crece hacia abajo, y la segunda hacia la derecha. (filas, columnas)</p>
 * 
 * <p>Una vez creado un objeto de este tipo su tamaño permanece inalterado sin importar qué
 * operación se realize sobre él. En caso de ejecutarse una acción que modificase el tamaño
 * de la matriz, el resultado de será retornado como un objeto nuevo permaneciendo el original 
 * sin ningún cambio.</p>
 * 
 * @author Luis Ciudad García.
 * @version 1.0
 */

public class CvmMatrixInt extends CvmMatrix{
	/** Datos contenidos por la matriz. */
	protected int[] data;
    
	/** Factor de escala de la matriz */
	public double scaleFactor = 1;
	
	/** 
	 * Crea una matriz de las dimensiones correspondientes a los parametros e inicializa los 
	 * datos al valor por defecto.
	 * 
	 * @param rows Número de filas de la matriz.
	 * @param cols Número de columnas de la matriz.
	 * 
	 * @exception NegativeArraySizeException Se lanza si se intenta crear una matriz de
	 * dimensiones negativas.
	 */
	public CvmMatrixInt(int rows, int cols) throws NegativeArraySizeException{
		super(rows, cols);
		
		this.data = new int[rows*cols];
		}

	/**
	 * Copia la matriz nativa de Java a una matriz CVM con las dimensiones indicadas y
	 * los valores proporcionados.
	 * 
	 * @param rows Número de filas de la matriz.
	 * @param cols Número de columnas de la matriz.
	 * @param value Matriz nativa con el valor que tomará cada uno de los elementos.
	 * 
	 * @exception NegativeArraySizeException Se lanza si se intenta crear una matriz de
	 * dimensiones negativas.
	 * @exception ArrayIndexOutOfBoundsException Se lanza si el tamaño de la matriz nativa no coincide con la que
	 * se quiere crear.
	 */
     public CvmMatrixInt (int rows, int cols, int[][] values) throws NegativeArraySizeException, ArrayIndexOutOfBoundsException {    	 
    	 super(rows, cols);

         this.data = new int[this.rows*this.cols];

         if(values.length != this.rows)
    		 throw new ArrayIndexOutOfBoundsException(values.length);
         
         for(int r = 0; r < this.rows; r++){
        	 if(values[r].length != cols)
        		 throw new ArrayIndexOutOfBoundsException(values[r].length);
        	 
        	 for(int c = 0; c < this.cols; c++)
        		 this.data[r * this.cols + c] = values[r][c];
         	}
         }
     
	/**
	 * Crea una matriz a partir de un vector de elementos, los cuales se consideran
	 * ordenados por filas.
	 * 
	 * @param rows Número de filas de la matriz.
	 * @param cols Número de columnas de la matriz.
	 * @param value Valor que tomará cada uno de los elementos.
	 * 
	 * @exception NegativeArraySizeException Se lanza si se intenta crear una matriz de
	 * dimensiones negativas.
	 * @exception ArrayIndexOutOfBoundsException Se lanza si el numero de elementos del vector no coincide con
	 * el numero de elementos de la matriz que se quiere crear.
	 */
	public CvmMatrixInt(int rows, int cols, int[] values) throws NegativeArraySizeException, ArrayIndexOutOfBoundsException {    	 
		super(rows, cols);
		
		if(values.length != this.rows*this.cols)
			throw new ArrayIndexOutOfBoundsException(values.length);
				
		this.data = values.clone();
		}
    
	/**
	 * Crea una matriz de las dimensiones correspondientes cuyos elementos toman
	 * el valor pasado como parámetro.
	 * 
	 * @param rows Número de filas de la matriz.
	 * @param cols Número de columnas de la matriz.
	 * @param value Valor que tomarán todos los elementos de la matriz creada.
	 * 
	 * @exception NegativeArraySizeException Se lanza si se intenta crear una matriz de
	 * dimensiones negativas.
	 */
    public CvmMatrixInt(int rows, int cols, int value) throws NegativeArraySizeException{
        super(rows, cols);

        this.data = new int[rows * cols];

        Arrays.fill(this.data, value);
        }

   /**
    * <p>Constructor de copia de la clase CvmMatrix.</p>
    * 
    * <p>Crea una matriz realizando una copia exacta de la que se pasa por parámetro
    * manteniendo dos referencias distintas e independientes.</p>
    * 
    * @param matrix Matriz original que se copiará en la nueva.
    */
    public CvmMatrixInt(CvmMatrixInt matrix){
        super(matrix.rows, matrix.cols);

        this.data = matrix.data.clone();
        }

   /**
    * Observador de la propiedad data.
    * 
    * @return Los datos mantenidos por la clase en forma de matriz nativa.
    */
    public int[][] toIntMatrix(){
        int[][] datacopy = new int[this.rows][this.cols];

        for(int r = 0; r < this.rows; r++)
        	for(int c = 0; c < this.cols; c++)
        		datacopy[r][c] = this.data[r * this.cols + c];

        return datacopy;
        }


   /**
    * Devuelve el valor del elemento de la matriz que indican los parámetros.
    * 
    * @param row Fila del elemento al que se intenta acceder.
    * @param col Columna del elemento al que se intenta acceder.
    * 
    * @return Valor del elemento apuntado por los parámetros.
    * 
    * @exception ArrayIndexOutOfBoundsException Se produce si los parámetros de filas y columnas 
    * no están dentro de los límites de la matriz.
    */
    public int getElement (int row, int col) throws ArrayIndexOutOfBoundsException {
    	if(row < 0 || row >= this.rows)
    		throw new ArrayIndexOutOfBoundsException(row);
    	if(col < 0 || col >= this.cols)
    		throw new ArrayIndexOutOfBoundsException(col);
    	
    	return this.data[row * this.cols + col];
        }

   /**
    * Guarda el valor deseado en el elemento de la matriz que indican los parámetros.
    * 
    * @param row Fila del elemento al que se intenta acceder.
    * @param col Columna del elemento al que se intenta acceder.
    * @param value Valor que se quiere guardar en la posición indicada.
    * 
    * @exception ArrayIndexOutOfBoundsException Se produce si los parámetros de filas y columnas
    * no están dentro de los límites de la matriz.
    */
    public void setElement(int row, int col, int value) throws ArrayIndexOutOfBoundsException {
    	if(row < 0 || row >= this.rows)
    		throw new ArrayIndexOutOfBoundsException(row);
    	if(col < 0 || col >= this.cols)
    		throw new ArrayIndexOutOfBoundsException(col);
    	
        this.data[row * this.cols + col] = value;
        }

   /**
    * Suma elemento a elemento los valores de la matriz original con los de la
    * matriz que se pasa como parámetro.
    * 
    * @param matrix Matriz con los elementos a sumar a la matriz original.
    * 
    * @exception CvmIcompatibleMatrixSizeException Se produce si las matrices no son 
    * del mismo tamaño.
    */
    public void add(CvmMatrixInt matrix) throws CvmIncompatibleMatrixSizeException {
        if(this.rows != matrix.rows || this.cols != matrix.cols){
            throw new CvmIncompatibleMatrixSizeException(matrix.rows, matrix.cols);
            }

        for(int i = 0; i < this.data.length; i++)
        	this.data[i] += matrix.data[i];
        }

   /**
    * Resta elemento a elemento los valores de la matriz original con los de la
    * matriz que se pasa como parámetro.
    * 
    * @param matrix Matriz con los elementos a restar de la matriz original.
    * 
    * @exception CvmIcompatibleMatrixSizeException Se produce si las matrices no son 
    * del mismo tamaño.
    */
    public void sub(CvmMatrixInt matrix) throws CvmIncompatibleMatrixSizeException {
    	if(this.rows != matrix.rows || this.cols != matrix.cols){
            throw new CvmIncompatibleMatrixSizeException(matrix.rows, matrix.cols);
            }

        for(int i = 0; i < this.data.length; i++)
        	this.data[i] -= matrix.data[i];
        }

	/**
	 * Suma un valor a todos los elementos de la matriz original.
	 * 
	 * @param value Valor a sumar a todos los elementos de la matriz original.
	 */
	 public void add(int value) {
	 	for(int i = 0; i < this.data.length; i++)
	     	this.data[i] += value;
	     }
    
	 /**
	  * Resta un valor a todos los elementos de la matriz original.
	  * 
	  * @param value Valor a restar a todos los elementos de la matriz original.
	  */
	 public void sub(int value) {
		 for(int i = 0; i < this.data.length; i++)
			 this.data[i] -= value;
	 	}

   /**
    * Multiplica todos los elementos de la matriz original por el valor indicado como parámetro.
    * 
    * @param factor Valor a multiplicar por cada uno de los elementos de la matriz original.
    */
    public void mul(int factor){
    	for(int i = 0; i < this.data.length; i++)
        	this.data[i] *= factor;
        }

   /**
    * Divide todos los elementos de la matriz original por el valor indicado como parámetro.
    * 
    * @param factor Valor por el que dividir cada uno de los elementos de la matriz original.
    * 
    * @exception ArithmeticException Se lanza si se intenta una division entre cero.
    */
    public void div(int factor) throws ArithmeticException{
    	if(factor == 0)
    		throw new ArithmeticException();
    	
    	double aux = 1/factor;
    	
    	for(int i = 0; i < this.data.length; i++)
        	this.data[i] = (int)(this.data[i] * aux);
        }

   /**
    * Suma elemento a elemento los valores de la matriz original con los de la
    * matriz que se pasa como parámetro y devuelve el resultado en una nueva instancia.
    * 
    * @param matrix Matriz con los elementos a sumar a la matriz original.
    * 
    * @return Nueva matriz con el resultado de la operación de suma de las dos matrices.
    * 
    * @exception CvmIncompatibleMatrixSizeException Se produce si las matrices no son 
    * del mismo tamaño.
    */
    public CvmMatrixInt getAdd(CvmMatrixInt matrix) throws CvmIncompatibleMatrixSizeException {
        CvmMatrixInt copy = new CvmMatrixInt(this);

        copy.add(matrix);

        return copy;
        }

   /**
    * Suma un valor a todos los elementos de la matriz original y devuelve el
    * resultado en una nueva matriz.
    * 
    * @param value Valor a sumar a todos los elementos de la matriz original.
    * 
    * @return Nueva matriz con el resultado de la operación de suma de la matriz
    * original y el valor que se pasa como parámetro.
    */
    public CvmMatrixInt getAdd(int value){
        CvmMatrixInt copy = new CvmMatrixInt(this);

        copy.add(value);

        return copy;
        }

   /**
    * Resta elemento a elemento los valores de la matriz original con los de la
    * matriz que se pasa como parámetro y devuelve el resultado en una nueva instancia.
    * 
    * @param matrix Matriz con los elementos a restar a la matriz original.
    * 
    * @return Nueva matriz con el resultado de la operación de resta de las dos matrices.
    * 
    * @exception CvmIncompatibleMatrixSizeException Se produce si las matrices no son 
    * del mismo tamaño.
    */
    public CvmMatrixInt getSub(CvmMatrixInt matrix) throws CvmIncompatibleMatrixSizeException {
        CvmMatrixInt copy = new CvmMatrixInt(this);

        copy.sub(matrix);

        return copy;
        }

   /**
    * Resta un valor a todos los elementos de la matriz original y devuelve el
    * resultado en una nueva matriz.
    * 
    * @param value Valor a restar a todos los elementos de la matriz original.
    * 
    * @return Nueva matriz con el resultado de la operación de resta de la matriz
    * original y el valor que se pasa como parámetro.
    */
    public CvmMatrixInt getSub(int value) {
        CvmMatrixInt copy = new CvmMatrixInt(this);

        copy.sub(value);

        return copy;
        }


   /**
    * Realiza la multiplicación de dos matrices, siempre que sea posible, y devuelve
    * el resultado en una nueva matriz.
    * 
    * @param matrix Matriz que se multiplicará por la derecha de la matriz original.
    * 
    * @return Nueva matriz con el resultado de la operación de multiplicación de
    * ambas matrices.
    * 
    * @exception CvmIcompatibleMatrixSizeException La matrices no pueden ser multiplicadas.
    * La matriz original debe tener el mismo número de columnas que filas tenga la
    * matriz que se pasa por parámetro.
    */
    public CvmMatrixInt getMul(CvmMatrixInt matrix) throws CvmIncompatibleMatrixSizeException {
        if(this.cols != matrix.rows){
            throw new CvmIncompatibleMatrixSizeException(matrix.rows, matrix.cols);
            }

        CvmMatrixInt result = new CvmMatrixInt(this.rows, matrix.cols);

        for(int r = 0; r < result.rows; r++){
            for(int c = 0; c < result.cols; c++){
                for(int k = 0; k < this.cols; k++) {
                    result.data[r * result.cols + c] += this.data[r * this.cols + k] * matrix.data[k * matrix.cols + c];
                    }
                }
            }

        return result;
        }


   /**
    * Multiplica por un valor todos los elementos de la matriz original y devuelve el
    * resultado en una nueva matriz.
    * 
    * @param factor Valor por el que se multiplican todos los elementos de la matriz original.
    * 
    * @return Nueva matriz con el resultado de la operación de multiplicación de la matriz
    * original y el valor que se pasa como parámetro.
    */
    public CvmMatrixInt getMul(int factor){
        CvmMatrixInt copy = new CvmMatrixInt(this);

        copy.mul(factor);

        return copy;
        }

   /**
    * Divide por un valor todos los elementos de la matriz original y devuelve el
    * resultado en una nueva matriz.
    * 
    * @param factor Valor por el que se dividen todos los elementos de la matriz original.
    * 
    * @return Nueva matriz con el resultado de la operación de división de la matriz
    * original y el valor que se pasa como parámetro.
    */
    public CvmMatrixInt getDiv(int factor){
        CvmMatrixInt copy = new CvmMatrixInt(this);

        copy.div(factor);

        return copy;
        }
    
   /**
    * Intercambia los elementos de filas y columnas de manera que se devuelve la
    * matriz traspuesta de la matriz original.
    * 
    * @return Nueva matriz de tamaño columnas por filas, que contiene los elementos
    * de la matriz original pero cambiando filas por columnas.
    */
    public CvmMatrixInt getTranspose(){
        CvmMatrixInt copy = new CvmMatrixInt(this.cols, this.rows);

        for(int r = 0; r < copy.rows; r++)
            for(int c = 0; c < copy.cols; c++)
                copy.setElement(r, c, this.getElement(c, r));

        return copy;
        }

    /**
     * Crea una matriz cuadrada del orden seleccionado cuya diagonal principal está
     * formada por el elemento 1.
     * 
     * @param order Orden de la matriz (Número de filas y columnas).
     * 
     * @return Matriz identidad del orden especificado.
     * 
     * @exception NegativeArraySizeException Se lanza si el orden indicado es negativo.
     */
    public static CvmMatrixInt getIdentity(int order) throws NegativeArraySizeException{
        CvmMatrixInt copy = new CvmMatrixInt(order, order);

        for(int i = 0; i < order; i++){
            copy.data[i * copy.cols + i] = 1;
            }

        return copy;
        }
    
    /**
     * Calcula el determinante de la matriz, esta debe ser cuadrada.
     * El método usado para matrices de orden superior a 2 es el de Dolittle.
     * Si la matriz es singular no se puede obtener el determinante y se lanza
     * una excepción.
     * 
     * @return El determinante de la matriz.
     * 
     * @exception CvmIncompatibleMatrixSizeException La matriz no es cuadrada.
     * @exception CvmSingularMatrixException Se lanza si el determinante no puede ser calculado
     * mediante el algoritmo de Doolittle.
     */    
    public double getDeterminant(int method) throws CvmIncompatibleMatrixSizeException, CvmSingularMatrixException {
        if(this.cols != this.rows){
            throw new CvmIncompatibleMatrixSizeException(this.rows, this.cols);
            }

        double det = 0;

        switch(this.cols){
        case 0:
        	det = 0;
        	break;
        case 1:
            det = this.data[0];
            break;
        case 2:
        	det = this.data[0] * this.data[3] - this.data[1] * this.data[2];
        	break;
        case 3:
        	det =   this.data[0] * this.data[4] * this.data[8] +
		        	this.data[3] * this.data[7] * this.data[2] +
		        	this.data[1] * this.data[5] * this.data[6] -
		        	this.data[2] * this.data[4] * this.data[6] -
		        	this.data[3] * this.data[1] * this.data[8] -
		        	this.data[7] * this.data[5] * this.data[0];
        	break;
        default:
        	if(method == CvmMatrixInt.DOOLITTLE_METHOD){
	        	CvmMatrixInt P = CvmMatrixInt.getIdentity(this.rows);
	        	CvmMatrixInt L = new CvmMatrixInt(this.rows, this.cols, 0);
	        	CvmMatrixInt U = new CvmMatrixInt(this);
	        	
	        	int pivotRow = 0;
	        	double maxValue = 0;
	        	
	        	for(int i = 0; i < this.rows; i++){
	        		
	        		pivotRow = i;
	        		
	        		for(int j = i; j < this.rows; j++){
	        			
	        			if(maxValue < this.data[j * this.cols + i]){
	        				maxValue = this.data[j * this.cols + i];
	        				pivotRow = j;
	        				}
	        			}
	        		
	        		if(maxValue == 0)
	        			throw new CvmSingularMatrixException(); //No se puede factorizar
	        		
	        		else if(pivotRow != i){
	        			for(int j = 0; j < this.cols; j++){
	        				int aux = P.data[i * this.cols + j];
	        				P.data[i * this.cols + j] = P.data[pivotRow * this.cols + j];
	        				P.data[pivotRow * this.cols + j] = aux;
	        				
	        				aux = L.data[i * this.cols + j];
	        				L.data[i * this.cols + j] = L.data[pivotRow * this.cols + j];
	        				L.data[pivotRow * this.cols + j] = aux;
	        				
	        				aux = U.data[i * this.cols + j];
	        				U.data[i * this.cols + j] = U.data[pivotRow * this.cols + j];
	        				U.data[pivotRow * this.cols + j] = aux;
	        				}
	        			}
	        		
	        		/** Hacer ceros por debajo de la diagonal */
	        		for(int j = i+1; j < this.rows; j++){
	        			double factor = U.data[j * this.cols + i] / U.data[i * this.cols + i];
	        			for(int k = i; k < this.cols; k++){
	        				U.data[j * this.cols + k] -= factor * U.data[i * this.cols + k];
	        				}
	        			
	        			/** No es necesaria esta instruccion, solo informativa */
	        			L.data[j * this.cols + i] = (int)-factor;
	        			}
	        		}
	        	
	        	det = 1;
	            for(int i = 0; i < this.cols; i++)
	            	det *= U.data[i * this.cols + i];
	        	}
        	else if(method == CvmMatrixInt.ADJUNCT_METHOD){
        		det = 0;
        		for(int i = 0; i < this.data.length; i++){
        			det += this.data[i] * this.getAdjunctElement(i / this.cols, i % this.cols); 
        			}
        		}
            
            break;
        	}
        
        return det;
        }

    /**
     * Recupera la matriz adjunta a un elemento, es decir, devuelve una matriz con los mismos
     * elementos que la original en la que se han suprimido los elementos de la fila y la columna
     * indicados.
     * 
     * @param row Fila del elemento. Esta fila será eliminada en la matriz resultante.
     * @param col Columna del elemento. Esta columna será eliminada en la matriz resultante.
     * 
     * @return Matriz adjunta al elemento seleccionado.
     * 
     * @exception ArrayIndexOutOfBoundsException Se produce si los parámetros de filas y columnas
     * no están dentro de los límites de la matriz.
     */
    public CvmMatrixInt getAdjunctMatrix(int row, int col) throws ArrayIndexOutOfBoundsException {
    	if(row < 0 || row >= this.rows)
    		throw new ArrayIndexOutOfBoundsException(row);
    	if(col < 0 || col >= this.cols)
    		throw new ArrayIndexOutOfBoundsException(col);
    	
    	if(this.rows <= 1 || this.cols <= 1)
    		return new CvmMatrixInt(this.rows, this.cols);
    	
    	CvmMatrixInt copy = new CvmMatrixInt(this.rows - 1, this.cols - 1);
    	
    	int k = 0;
    	for(int i = 0; i < this.data.length; i++){
    		if(!(i % this.cols == col || i / this.cols == row)){
				copy.data[k] = this.data[i];
				k++;
    			}
    		}
    	
    	return copy;
    	} 
    
    /**
     * Calcula el adjunto a un elemento. Esto significa que calcula el determinante de la
     * matriz adjunta al elemento seleccionado. Esta operación solo es válida sobre matrices
     * cuadradas.
     * 
     * @param row Fila del elemento.
     * @param col Columna del elemento.
     * 
     * @return Valor del adjunto de la matriz que ocupa la posición indicada.
     * 
     * @exception ArrayIndexOutOfBoundsException Se produce si los parámetros de filas y columnas
     * no están dentro de los límites de la matriz.
     * @exception CvmIncompatibleMatrixSizeException La matriz debe ser cuadrada.
     */
    public double getAdjunctElement(int row, int col) throws ArrayIndexOutOfBoundsException, CvmIncompatibleMatrixSizeException{
    	if(this.cols != this.rows)
            throw new CvmIncompatibleMatrixSizeException(this.rows, this.cols);
    	
    	double det = 0;
        
    	CvmMatrixInt adjunctMatrix = this.getAdjunctMatrix(row, col);
    	
        try{
        	det = adjunctMatrix.getDeterminant(CvmMatrixInt.DOOLITTLE_METHOD);
        	}
        catch(CvmSingularMatrixException e){
        	det = adjunctMatrix.getDeterminant(CvmMatrixInt.ADJUNCT_METHOD);
        	}
    	
    	return Math.pow(-1, row+col) * det;
    	}
    
    
    /**
     * Calcula la matriz de adjuntos. La matriz debe ser cuadrada.
     * 
     * @return Una nueva matriz cuyos elementos son los adjuntos de cada uno de los elementos 
     * de la matriz original.
     * 
     * @exception CvmIncompatibleMatrixSizeException La matriz no es cuadrada.
     */ 
    public CvmMatrixDouble getAdjunctElementsMatrix() throws CvmIncompatibleMatrixSizeException {
    	if(this.cols != this.rows)
            throw new CvmIncompatibleMatrixSizeException(this.rows, this.cols);
    	
    	if(this.rows <= 1 || this.cols <= 1)
    		return new CvmMatrixDouble(this.cols, this.rows);
    	
        CvmMatrixDouble copy = new CvmMatrixDouble(this.rows, this.cols);
        
        for(int i = 0; i < copy.data.length; i++)
        	copy.data[i] = this.getAdjunctElement(i / copy.cols, i % copy.cols);

        return copy;
        }
    
    /**
     * Calcula la inversa de una matriz si es posible.
     * 
     * @return La matriz inversa.
     * 
     * @exception CvmSingularMatrixException No es posible calcular la inversa de la matriz.
     * @exception CvmIncompatibleMatrixSizeException La matriz no es cuadrada.
     */ 
    public CvmMatrixDouble getInverse() throws CvmSingularMatrixException, CvmIncompatibleMatrixSizeException{
        double det = 0;
        
        try{
        	det = this.getDeterminant(CvmMatrixInt.DOOLITTLE_METHOD);
        	}
        catch(Exception e){
        	det = this.getDeterminant(CvmMatrixInt.ADJUNCT_METHOD);
        	}
        
        if(det == 0)
        	throw new CvmSingularMatrixException();
        
    	CvmMatrixDouble copy = this.getAdjunctElementsMatrix().getTranspose();
        copy.div(det);

        return copy;
        }

    /**
     * Devuelve los datos contenido en la matriz en un Array de tipo entero.
     * 
     * @return Array de enteros que contiene los valores de la matriz ordenados por filas.
     */
    public int[] toArray(){
    	int[] arr = new int[this.cols * this.rows];
    	
    	for(int i = 0; i < this.data.length; i++)
    		arr[i] = this.data[i];
    	
    	return arr;
    	}
    
   /**
    * Representa mediante una cadena de texto los distintos valores que contiene la matriz.
    * 
    * @return Cadena de texto formateada con lo valores de todos los elementos de
    * la matriz organizados por filas y columnas.
    */
    @Override
    public String toString(){
        String str = new String("");

        for(int r = 0; r < this.rows; r++){
            for(int c = 0; c < this.cols; c++){
                double f = this.data[r * this.cols + c];
                str += f + " ";
                }
            str += "\n";
            }

        return str;
        }
    
    /**
     * Compara las dimensiones y los valores contenido en dos matrices para determinar si estas
     * son iguales.
     * 
     * @param matrix Matriz a comparar con este objeto.
     * 
     * @return Si las dos matrices tienen las mismas dimensiones y almacenan los mismos valores
     * en las mismas posiciones la funcion devuelve true, en caso contrario devuelve false.
     */ 
    public boolean equals(CvmMatrixInt matrix){
    	if(this.rows != matrix.rows || this.cols != matrix.cols)
    		return false;
    	
    	for(int i = 0; i < this.data.length; i++)
    		if(this.data[i] != matrix.data[i])
    			return false;
    	
    	return true;
    	}
    }
