package edu.uco.cvm.test;

import org.me.applications.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import edu.uco.cvm.core.CvmMatrixDouble;
import edu.uco.cvm.core.CvmMatrixInt;

public class CvmMatrixTest extends Activity {
	
	private CvmMatrixInt matrix;
	private CvmMatrixInt copy;
	private CvmMatrixInt control;
	
	private int[][] nativeMatrix;
	private int[] nativeVector;
	
	private boolean ok = false;
	
	@Override
    public void onCreate(Bundle icicle) {
		
		super.onCreate(icicle);
        setContentView(R.layout.cvmmatrixtest);		
        
        ok = this.constructorTest();
        ok = this.dataManagementTest();
        ok = this.arithmeticTest();
        ok = this.matrixOperationsTest();
		}
	
	private boolean constructorTest(){
        try{
        	/** Constructor simple */
        	matrix = new CvmMatrixInt(0, 0);
            matrix = new CvmMatrixInt(1, 1);
            matrix = new CvmMatrixInt(2, 5);
            matrix = new CvmMatrixInt(100, 100);
        	
            /** Constructor con valor */
            matrix = new CvmMatrixInt(0, 0, 2);
            matrix = new CvmMatrixInt(0, 0, -2);
            matrix = new CvmMatrixInt(1, 0, 2);
            matrix = new CvmMatrixInt(0, 1, 2);
            
        	/** Constructor desde matriz nativa */        	
        	nativeMatrix = new int[0][0];
        	matrix = new CvmMatrixInt(0, 0, nativeMatrix);
        	
        	nativeMatrix = new int[1][1];
        	matrix = new CvmMatrixInt(1, 1, nativeMatrix);
        	
        	nativeMatrix = new int[5][2];
        	matrix = new CvmMatrixInt(5, 2, nativeMatrix);
        	
        	nativeMatrix = new int[80][80];
        	matrix = new CvmMatrixInt(80, 80, nativeMatrix);
        	
        	
        	/** Constructor desde vector */
        	nativeVector = new int[0];
        	matrix = new CvmMatrixInt(0, 0, nativeVector);
        	
        	nativeVector = new int[1];
        	matrix = new CvmMatrixInt(1, 1, nativeVector);
        	
        	nativeVector = new int[12];
        	matrix = new CvmMatrixInt(4, 3, nativeVector);
        	matrix = new CvmMatrixInt(3, 4, nativeVector);
        	matrix = new CvmMatrixInt(2, 6, nativeVector);
        	}
        catch(Exception e){
        	//Capturamos cualquier tipo de excepcion (todas las subclases)
        	Log.i("TEST_MATRIX", e.getMessage());
        	return false;
        	}
        
        /**Comprobamos que el constructor de copia genera dos objetos independientes */
        int aux[][] = {{1,2,3,4},{5,6,7,8},{9,10,11,12}};
        matrix = new CvmMatrixInt(3, 4, aux);
        copy = new CvmMatrixInt(matrix);
        
        matrix.setElement(0,0, 9999);
        
        if(matrix.equals(copy)){
        	Log.i("TEST_MATRIX", "Copy constructor error!");
        	return false;
        	}
        
        return true;
        }
	
	private boolean dataManagementTest(){
		int aux[][] = {{1,2,3,4},{5,6,7,8},{9,10,11,12}};
        matrix = new CvmMatrixInt(3, 4, aux);
		
        /** Comprobar el observador de filas */
        if(this.matrix.getRows() != 3){
        	Log.i("TEST_MATRIX", "getRows error!");
        	return false;
        	}
        
        /** Comprobar el observador de columnas */
		if(this.matrix.getCols() != 4){
        	Log.i("TEST_MATRIX", "getCols error!");
        	return false;
        	}
		
		/** Comprobar acceso y escritura de datos */
		try{
			double value = this.matrix.getElement(0, 0);
			if(value != 1){
				Log.i("TEST_MATRIX", "getElement error!");
	        	return false;
				}
			
			this.matrix.setElement(0, 0, 100);
			if(this.matrix.getElement(0, 0) != 100){
				Log.i("TEST_MATRIX", "setElement error!");
	        	return false;
				}
		
		
			/** Comprobar el observador de los datos */
			int[][] data = this.matrix.toIntMatrix();
			
			if(data.length != this.matrix.getRows()){
				Log.i("TEST_MATRIX", "getData error!");
	        	return false;
				}
			
			for(int i = 0; i < this.matrix.getRows(); i++){
				if(data[i].length != this.matrix.getCols()){
					Log.i("TEST_MATRIX", "getData error!");
		        	return false;
					}
				
				for(int j = 0; j < data[i].length; j++){
					if(data[i][j] != this.matrix.getElement(i, j)){
						Log.i("TEST_MATRIX", "getData error!");
			        	return false;
						}
					}
				
				}
			}
			catch(Exception e){
	        	//Capturamos cualquier tipo de excepcion (todas las subclases)
	        	Log.i("TEST_MATRIX", e.getMessage());
	        	return false;
	        	}
			
		return true;
		}
	
	private boolean arithmeticTest(){
		int aux[][] = {{1,2,3,4},{5,6,7,8},{9,10,11,12}};
		int res[][] = {{11,12,13,14},{15,16,17,18},{19,20,21,22}};
		
        matrix = new CvmMatrixInt(3, 4, aux);	
        control = new CvmMatrixInt(3, 4, aux);
        copy = new CvmMatrixInt(3, 4, res);
		
        
        /** Operaciones de suma, resta de un valor*/
        matrix.add(10);
        if(!matrix.equals(copy)){
        	Log.i("TEST_MATRIX", "add value error!");
        	return false;
        	}
        
        if(matrix.equals(control)){
        	Log.i("TEST_MATRIX", "add value error!");
        	return false;
        	}
        
        matrix.sub(10);
        if(matrix.equals(copy)){
        	Log.i("TEST_MATRIX", "sub value error!");
        	return false;
        	}
        
        if(!matrix.equals(control)){
        	Log.i("TEST_MATRIX", "sub value error!");
        	return false;
        	}
        
        /** Operaciones de multiplicacion y division de un valor*/
        int auxMul[][] = {{1,2,1,2},{3,4,3,4},{1,2,2,1}};
        int resMul[][] = {{2,4,2,4},{6,8,6,8},{2,4,4,2}};
        matrix = new CvmMatrixInt(3, 4, auxMul);
        control = new CvmMatrixInt(matrix);
        
        matrix.mul(1);
        if(!matrix.equals(control)){
        	Log.i("TEST_MATRIX", "mul value error!");
        	return false;
        	}
        
        matrix.div(1);
        if(!matrix.equals(control)){
        	Log.i("TEST_MATRIX", "div value error!");
        	return false;
        	}
        
        control = new CvmMatrixInt(3, 4, resMul);
        matrix.mul(2);
        if(!matrix.equals(control)){
        	Log.i("TEST_MATRIX", "mul value error!");
        	return false;
        	}
        
        control = new CvmMatrixInt(3, 4, auxMul); 
        matrix.div(2);
        if(!matrix.equals(control)){
        	Log.i("TEST_MATRIX", "div value error!");
        	return false;
        	}
        
        matrix.mul(0);
        if(!matrix.equals(new CvmMatrixInt(3,4,0))){
        	Log.i("TEST_MATRIX", "mul value error!");
        	return false;
        	}
        
        try{
        	matrix.div(0);
        	}
        catch(ArithmeticException e){
        	//Todo ok
        	}
        
        /** Operaciones aritmeticas con matrices */
        int[][] auxSumM1 = {{1,2,1,2},{3,4,3,4},{1,2,2,1}};
        int[][] auxSumM2 = {{-1,-2,-1,-2},{-3,-4,-3,-4},{-1,-2,-2,-1}};
        
        matrix = new CvmMatrixInt(3, 4, auxSumM1);
        copy = new CvmMatrixInt(3, 4, auxSumM2);
        
        control = matrix.getAdd(copy);
        matrix.add(copy);
        
        if(!matrix.equals(new CvmMatrixInt(3, 4, 0))){
        	Log.i("TEST_MATRIX", "add matrix error!");
        	return false;
        	}
        
        if(!control.equals(new CvmMatrixInt(3, 4, 0))){
        	Log.i("TEST_MATRIX", "getAdd matrix error!");
        	return false;
        	}
        
        
        int[][] auxSubM1 = {{1,2,1,2},{3,4,3,4},{1,2,2,1}};
       
        matrix = new CvmMatrixInt(3, 4, auxSubM1);
        copy = new CvmMatrixInt(3, 4, auxSubM1);
        
        control = matrix.getSub(copy);
        matrix.sub(copy);
        
        if(!matrix.equals(new CvmMatrixInt(3, 4, 0))){
        	Log.i("TEST_MATRIX", "sub matrix error!");
        	return false;
        	}
        
        if(!control.equals(new CvmMatrixInt(3, 4, 0))){
        	Log.i("TEST_MATRIX", "getSub matrix error!");
        	return false;
        	}
        
        int[][] auxMulM1 = {{1,2,1,2},{3,4,3,4},{1,2,2,1}};
        int[][] auxMulM2 = {{-1,-2,-1},{-3,-4,-3},{-1,-2,-2},{4,2,0}};
        int[][] resMulM = {{0,-8,-9},{-2,-20,-21},{-5,-12,-11}};
        
        matrix = new CvmMatrixInt(3, 4, auxMulM1);
        copy = new CvmMatrixInt(4, 3, auxMulM2);
        control = new CvmMatrixInt(3, 3, resMulM);
        
        try{
        	copy = matrix.getMul(copy);
        	}
        catch(Exception e){
        	//Capturamos cualquier tipo de excepcion (todas las subclases)
        	Log.i("TEST_MATRIX", e.getMessage());
        	return false;
        	}
        
        if(!copy.equals(control)){
        	Log.i("TEST_MATRIX", "getMul matrix error!");
        	return false;
        	}
        
		return true;
		}
	
	private boolean matrixOperationsTest(){
		int[][] identity = {{1,0,0,0},{0,1,0,0},{0,0,1,0},{0,0,0,1}};
		int[][] aux = {{1,2,3},{4,5,6},{7,8,9}};
		int[][] auxT = {{1,4,7},{2,5,8},{3,6,9}};
		
		control = new CvmMatrixInt(4, 4, identity);
		
		matrix = CvmMatrixInt.getIdentity(4);
		if(!matrix.equals(control)){
        	Log.i("TEST_MATRIX", "getIdentity error!");
        	return false;
        	}
		
		matrix = new CvmMatrixInt(3,3,aux);
		control = new CvmMatrixInt(3,3,auxT);
		
		matrix = matrix.getTranspose();
		if(!matrix.equals(control)){
        	Log.i("TEST_MATRIX", "getTranspose error!");
        	return false;
        	}
		
		int[][] auxDet = {{1,2,4},{4,5,6},{7,8,9}};
		matrix = new CvmMatrixInt(3,3,auxDet);
		double detD = matrix.getDeterminant(CvmMatrixInt.DOOLITTLE_METHOD);
		Log.i("TEST_MATRIX", "Determinante (~ -3): "+detD);
		
		double detA = matrix.getDeterminant(CvmMatrixInt.ADJUNCT_METHOD);
		Log.i("TEST_MATRIX", "Determinante (~ -3): "+detA);
		
		CvmMatrixDouble controlD = matrix.getInverse();
		Log.i("TEST_MATRIX","Inversa {{1,-4.66,2.66},{-2,6.33,-3.33},{1,-2,1}}:");
		Log.i("TEST_MATRIX",controlD.toString());
		
		controlD = matrix.getAdjunctElementsMatrix();
		if(Math.round(controlD.getElement(1, 1)) != -19.0){
        	Log.i("TEST_MATRIX", "getAdjunctsElementsMatrix error!");
        	return false;
        	}
		
		if(Math.round(matrix.getAdjunctElement(1, 1)) != -19.0){
        	Log.i("TEST_MATRIX", "getAdjunctElement error!");
        	return false;
        	}
		
		int[][] auxAM = {{1,4},{7,9}};
		copy = new CvmMatrixInt(2,2, auxAM);
		control = matrix.getAdjunctMatrix(1, 1);
		
		if(! copy.equals(control)){
        	Log.i("TEST_MATRIX", "getAdjunctMatrix error!");
        	return false;
        	}
		
		
		/*** PRUEBA ***/
		int[][] auxIden = {{40}, {50}, {1}};
		copy = new CvmMatrixInt(3,1, auxIden);
		control = CvmMatrixInt.getIdentity(3);
		
		Log.i("TEST_MATRIX", control.getMul(copy).toString());
		return true;
		}
	}
