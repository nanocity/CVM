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
