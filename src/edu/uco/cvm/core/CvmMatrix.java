package edu.uco.cvm.core;

public abstract class CvmMatrix {
	/** Indica que el c�lculo del determinante se realice mediante el algoritmo de Doolittle*/
	public final static int DOOLITTLE_METHOD = 1;	
	/** Indica que el c�lculo del determinante se realice mediante el metodos de los adjuntos */
	public final static int ADJUNCT_METHOD = 2;
	
	/** N�mero de filas de la matriz. */
	protected final int rows; 
	/** N�mero de columnas de la matriz. */
	protected final int cols;
	
	public CvmMatrix(int rows, int cols){
		this.rows = rows;
		this.cols = cols;
		}
	
	/**
	 * Observador de la propiedad rows.
	 * 
	 * @return N�mero de filas de la matriz.
	 */
    public int getRows(){
        return this.rows;
        }

   /**
    * Observador de la propiedad cols.
    * 
    * @return N�mero de columnas de la matriz.
    */
    public int getCols(){
        return this.cols;
        }
    
    public abstract CvmMatrixDouble getInverse();
	}
