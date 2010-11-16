package edu.uco.cvm.exceptions;

public class CvmIncompatibleMatrixSizeException extends RuntimeException{
	static final long serialVersionUID = 1L;
		
	public CvmIncompatibleMatrixSizeException(int rows, int cols){
		super("(" + rows + "," + cols + ")");
		}
	}
