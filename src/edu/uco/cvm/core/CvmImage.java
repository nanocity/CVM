package edu.uco.cvm.core;

import java.util.LinkedList;
import java.util.ListIterator;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;

/**
 * <p>Esta clase representa una imagen.</p>
 * 
 * <p>Una imagen posee un tamaño en función del ancho y el alto de la misma. Estos valores
 * se miden en píxeles (px). Un píxel es cada uno de los puntos de color que forman la imagen.
 * A su vez, estos puntos se diferencian por la posición que ocupan en la imagen, siendo el
 * punto (0,0) el que está en la esquina superior izquierda de la imagen. A partir de ese píxel
 * la primera coordenada crece hacia la derecha y la segunda hacia abajo (ancho, alto).</p>
 * 
 * <p>Se trata de una clase de alto nivel que abstrae al programador del manejo de los pí­xeles 
 * individuales que la componen así como de los diferentes canales en los que se pueda dividir.
 * Para ello se definen dos constantes que indican la codificacion de la imagen:
 *      <ul>
 *      	<li>GRAYSCALE: La imagen estará compuesta de un solo canal en tonos grises.</li>
 *      	<li>RGB: La imagen será tratada con 3 canales: rojo, verde y azul.</li>
 *      </ul>
 * </p>     
 * @author Luis Ciudad García.
 * @version 1.0 
 */
public class CvmImage {
	
	public static final int GRAYSCALE = 0;
	public static final int RGB = 1;
	public static final int HSV = 2;
	
	 /** Vector de canales con la información de los pí­xeles de la imagen. */
    private CvmChannel data[];
    /** Lista de transformación a aplicar sobre la imagen. */
    private LinkedList<CvmMatrixDouble> transfoList;
    /** Modo en que se representa la imagen contenida en data. */
    private int mode;                           
    
    /** Anchura de la imagen. */
    private int width;
    /** Altura de la imagen. */
    private int height;                         

    
    /**
     * Constructor de la clase CvmImage.
     * Crea una imagen a partir del Bitmap referenciado, codificando la informacion según el modo.
     * @param bitmap Imagen de partida.
     * @param mode Modo en que se representara la imagen. Los modos admitidos son RGB y GRAYSCALE.
     */
    public CvmImage(Bitmap bitmap, int mode) throws Exception{
    	
    	this.width = bitmap.getWidth();
    	this.height = bitmap.getHeight();
    	
    	this.transfoList = new LinkedList<CvmMatrixDouble>();
        this.mode = mode;
    	
    	if(this.mode == CvmImage.GRAYSCALE){
    		this.data = new CvmChannel[1];
    		
    		this.data[0] = new CvmChannel(bitmap, CvmChannel.GRAY); 
    		}
    	else if(this.mode == CvmImage.RGB){
    		this.data = new CvmChannel[3];
    		
    		this.data[0] = new CvmChannel(bitmap, CvmChannel.RED);
    		this.data[1] = new CvmChannel(bitmap, CvmChannel.GREEN);
    		this.data[2] = new CvmChannel(bitmap, CvmChannel.BLUE);
    		}
        }
    
    /**
     * Constructor de la clase CvmImage.
     * Crea una imagen a partir del Bitmap referenciado por su ubicacion, codificando 
     * la informacion según el modo.
     * 
     * @param pathName Ruta de la imagen de partida.
     * @param mode Modo en que se representara la imagen. Los modos admitidos son RGB y GRAYSCALE.
     */
    public CvmImage(String pathName, int mode) throws Exception{
    	
    	Bitmap bitmap = BitmapFactory.decodeFile(pathName);
    	
    	this.width = bitmap.getWidth();
    	this.height = bitmap.getHeight();
    	
    	this.transfoList = new LinkedList<CvmMatrixDouble>();
        this.mode = mode;
    	
    	if(this.mode == CvmImage.GRAYSCALE){
    		this.data = new CvmChannel[1];
    		
    		this.data[0] = new CvmChannel(bitmap, CvmChannel.GRAY); 
    		}
    	else if(this.mode == CvmImage.RGB){
    		this.data = new CvmChannel[3];
    		
    		this.data[0] = new CvmChannel(bitmap, CvmChannel.RED);
    		this.data[1] = new CvmChannel(bitmap, CvmChannel.GREEN);
    		this.data[2] = new CvmChannel(bitmap, CvmChannel.BLUE);
    		}
        }
    
    public CvmImage(CvmChannel channel){
    	this.width = channel.cols;
    	this.height = channel.rows;
    	
    	this.transfoList = new LinkedList<CvmMatrixDouble>();
    	this.mode = CvmImage.GRAYSCALE;
    	
    	this.data = new CvmChannel[1];
    	this.data[0] = new CvmChannel(this.width, this.height, channel.data, CvmChannel.GRAY);
    	}
    
    /**
     * Observador de la propiedad width.
     * @return Ancho de la imagen.
     */
    public int getWidth(){
    	return this.width;	
    	}

    /**
     * Observador de la propiedad height.
     * @return Alto de la imagen.
     */
    public int getHeight(){
    	return this.height;	
    	}

    
    /**
     * Convierte la imagen RGB a escala de grises.
     */
    public void convertToGray() throws Exception{
    	if(this.mode == CvmImage.RGB){
	    	int pixels[][] = new int[this.width][this.height];
	    	
	    	for(int i = 0; i < this.width; i++){
	    		for(int j = 0; j < this.height; j++){
	    			int r = data[0].getElement(i, j);
	    			int g = data[1].getElement(i, j);
	    			int b = data[2].getElement(i, j);
	    			
	    			int gray = Math.round((11*r + 16*g + 5*b) /32);
	    			
	    			pixels[i][j] = gray;
	    			}
	    		}
	    	
	    	this.data = new CvmChannel[1];
	    	this.data[0] = new CvmChannel(this.width, this.height, pixels, CvmChannel.GRAY);
    		}
        }

    /**
     * Agrega una transformacion de traslacion a la lista de transformaciones de la imagen.
     * @param Tx Numero de pixels de traslacion en el eje X.
     * @param Ty Numero de pixels de traslacion en el eje Y.
     */
    public void translate(int Tx, int Ty) throws Exception{
        CvmMatrixDouble tr = CvmMatrixDouble.getIdentity(3);

        tr.setElement(0, 2, Tx);
        tr.setElement(1, 2, Ty);

        this.transfoList.add(tr);
        }

    /**
     * Agrega una transformacion de rotacion a la lista de transformaciones de la imagen.
     * @param angle Grados en el sentido de las agujas del reloj que rotará la imagen con respecto
     * al origen de coordenadas.
     */
    public void rotate(double angle) throws Exception{
        double rad = 0;

        if(angle != 0)
            rad = (Math.PI*angle) / 180;
        else
            rad = 2*Math.PI;
        
        CvmMatrixDouble tr = CvmMatrixDouble.getIdentity(3);

        tr.setElement(0, 0,  Math.cos(rad));
        tr.setElement(0, 1, -Math.sin(rad));
        tr.setElement(1, 0,  Math.sin(rad));
        tr.setElement(1, 1,  Math.cos(rad));

        this.transfoList.add(tr);
        }

    /**
     * Agrega una transformacion de escala a la lista de transformaciones de la imagen.
     * @param Sx Factor de escala en el eje X.
     * @param Sy Factor de escala en el eje Y.
     */
    public void scale(double Sx, double Sy) throws Exception{
        CvmMatrixDouble tr = CvmMatrixDouble.getIdentity(3);

        tr.setElement(0, 0, Sx);
        tr.setElement(1, 1, Sy);

        this.transfoList.add(tr);
        }

    /**
     * IMPORTANTE!!! comprobar que la tr es de 3x3
     * Agrega una transformacion definida por el usuario a la lista de transformaciones de la imagen.
     * @param tr Transfomacion a agregar a la lista de transformaciones.
     */
    public void addTransform(CvmMatrixDouble tr){
        this.transfoList.add(tr);
        }

    /**
     * Metodo privado que calcula la matriz necesaria para aplicar todas las transformaciones contenidas
     * en la lista de transformaciones en el orden correcto.
     * @return Matriz resultado de la concatenacion de las transformaciones aplicadas hasta el momento.
     */
    private CvmMatrixDouble getTransformMatrix(){
        CvmMatrixDouble tr = CvmMatrixDouble.getIdentity(3);

        ListIterator<CvmMatrixDouble> it = this.transfoList.listIterator(this.transfoList.size());

        while(it.hasPrevious())
            try{
                tr = tr.getMul(it.previous());
                }
            catch(Exception e){}

        this.transfoList.clear();
                
        return tr;
        }

    /**
     * Aplica a la imagen todas las transformacion que se encuentran en la lista de transformaciones.
     * Una vez hecho esto, la lista de transformaciones vuelve a quedar vacia.
     */
    public void applyTransform() throws Exception{
        CvmMatrixDouble tr = this.getTransformMatrix();

        if(this.mode == CvmImage.GRAYSCALE)
        	this.data[0].applyTransform(tr);
        
        else if(this.mode == CvmImage.RGB){
        	this.data[0].applyTransform(tr);
        	this.data[1].applyTransform(tr);
        	this.data[2].applyTransform(tr);
        	}
        }

    
    /**
     * Aplica una mascara definida por el usuario a los canales de la imagen.
     * @param mask Matriz con los valores de la mascara que se desea aplicar
     */
    public void applyMask(CvmMatrixDouble mask) throws Exception{
    	if(this.mode == CvmImage.GRAYSCALE){
    		this.data[0].applyMask(mask);
    		}
    	else if(this.mode == CvmImage.RGB){
        	this.data[0].applyMask(mask);
        	this.data[1].applyMask(mask);
        	this.data[2].applyMask(mask);
        	}
    	}
    
    public void normalize(){
    	for(int i = 0; i < this.data.length; i++)
    		this.data[i].normalize();
    	}
    
    /**
     * Convierte la imagen a un Bitmap, formato nativo de Android en el que se representan las imagenes.
     * @return Equivalente en el formato Bitmap a la imagen mantenida por esta clase.
     */
    public Bitmap toBitmap(){
    	int pixels[] = new int[this.width * this.height];
    	
    	if(this.mode == CvmImage.GRAYSCALE){
    		for(int i = 0; i < this.width * this.height; i++){
    			int row = i / this.width;
    			int col = i % this.width;
    			int gray = data[0].getElement(row, col);
    			pixels[i] = Color.rgb(gray, gray, gray);
    			}	
    		}
    	else if(this.mode == CvmImage.RGB){
	    	for(int i = 0; i < this.width * this.height; i++){
    			int row = i / this.width;
    			int col = i % this.width;
    			int r = data[0].getElement(row, col);
    			int g = data[1].getElement(row, col);
    			int b = data[2].getElement(row, col);
    			
    			pixels[i] = Color.rgb(r,g,b);
    			}
    		}
    	
    	return Bitmap.createBitmap(pixels, this.width, this.height, Config.ARGB_8888);
    	}
    
    }
