package org.me.applications;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import edu.uco.cvm.core.CvmChannel;
import edu.uco.cvm.core.CvmImage;
import edu.uco.cvm.core.CvmMaskFactory;

public class DemoFilters extends Activity {
	
	private TextView debugText;
	private ImageView imageTransfo;
	private ImageView imageOriginal;
	
	private EditText param1;
	private EditText param2;
	
	private Spinner filtersCombo;
	private Spinner imagesCombo;
	
	@Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.demofilters);
        
        debugText = (TextView) findViewById(R.id.FiltersDebug);
        imageTransfo = (ImageView) findViewById(R.id.FiltersImageTransfo);
        imageOriginal = (ImageView) findViewById(R.id.FiltersImageOriginal);
        
        param1 = new EditText(this);
        	param1.setWidth(220);
        param2 = new EditText(this);
        	param2.setWidth(220);
        
        Button acceptButton = (Button) findViewById(R.id.FiltersAcceptButton);
        	acceptButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	applyFilter();
                	}
        		}
        	);
        
    	imagesCombo = (Spinner) findViewById(R.id.ImagesCombo);
        ArrayAdapter adapterImages = ArrayAdapter.createFromResource(this, R.array.images, android.R.layout.simple_spinner_item);
        	adapterImages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        imagesCombo.setAdapter(adapterImages);     
        
        imagesCombo.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
        	public void onItemSelected(AdapterView parent, View v, int position, long id) {
        		}

        	public void onNothingSelected(AdapterView parent){
        		}  
        	}
        );	
        	
        filtersCombo = (Spinner) findViewById(R.id.FiltersCombo);
        ArrayAdapter adapterFilters = ArrayAdapter.createFromResource(this, R.array.filters, android.R.layout.simple_spinner_item);
        	adapterFilters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        filtersCombo.setAdapter(adapterFilters);     
        
        filtersCombo.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
        	public void onItemSelected(AdapterView parent, View v, int position, long id) {
        		buildParamsLayout(position);
        		}

        	public void onNothingSelected(AdapterView parent){
        		}  
        	}
        );
        
		}
	
	public void buildParamsLayout(int position){
		TableRow tr1 = (TableRow) findViewById(R.id.FiltersRowParam1);
		TableRow tr2 = (TableRow) findViewById(R.id.FiltersRowParam2);
		
		tr1.removeAllViews();
		tr2.removeAllViews();		
		
		switch(position){
			case 0:
				TextView avgSizeLabel = new TextView(this);
					avgSizeLabel.setText("Mask size:");				
				
				tr1.addView(avgSizeLabel);
				
				tr1.addView(param1);
				break;
		
			case 1:
				TextView boostSizeLabel = new TextView(this);
					boostSizeLabel.setText("Mask size:");
				TextView boostSigmaLabel = new TextView(this);
					boostSigmaLabel.setText("Param A:");				
				
				tr1.addView(boostSizeLabel);
				tr2.addView(boostSigmaLabel);
				
				tr1.addView(param1);
				tr2.addView(param2);
				break;
			case 2:
				TextView gaussSizeLabel = new TextView(this);
					gaussSizeLabel.setText("Mask size:");
				TextView gaussSigmaLabel = new TextView(this);
					gaussSigmaLabel.setText("Sigma:");				
				
				tr1.addView(gaussSizeLabel);
				tr2.addView(gaussSigmaLabel);
				
				tr1.addView(param1);
				tr2.addView(param2);
				break;
			case 3:
				break;
			}
		}
	
	public void applyFilter(){		
		Bitmap mBitmap;
		
		switch(imagesCombo.getSelectedItemPosition()){
		case 0:
			mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_cat);
			break;
		case 1:
			mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_casco);
			break;
		case 2:
			mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_bn);
			break;
		default:
			mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_bn);
			}
    	
		imageOriginal.setImageBitmap(mBitmap);
		
    	try{
    		int pos = filtersCombo.getSelectedItemPosition();
    		int size;
    		long t1 = System.currentTimeMillis();
    		CvmImage mImage = new CvmImage(mBitmap, CvmImage.GRAYSCALE);

    		switch(pos){
	    		case 0:
	    			size = Integer.parseInt(param1.getText().toString());
	    			mImage.applyMask(CvmMaskFactory.getAverageMask(size));
	    			break;
	    		case 1:
	    			size = Integer.parseInt(param1.getText().toString());
	    			int A = Integer.parseInt(param2.getText().toString());
	    			mImage.applyMask(CvmMaskFactory.getHighBoostMask(size, A));
	    			mImage.normalize();
	    			break;
	    		case 2:
	    			size = Integer.parseInt(param1.getText().toString());
	    			double sigma = Double.parseDouble(param2.getText().toString());
	    			mImage.applyMask(CvmMaskFactory.getGaussianMask(size, sigma));
	    			break;
	    		case 3:
	    			CvmChannel vertical = new CvmChannel(mBitmap, CvmChannel.GRAY);
	    			CvmChannel horizontal = new CvmChannel(mBitmap, CvmChannel.GRAY);
	    			vertical.applyMask(CvmMaskFactory.getVSobelMask());
	    			horizontal.applyMask(CvmMaskFactory.getHSobelMask());
	    			
	    			vertical.module(horizontal);
	    			vertical.normalize();
	    			mImage = new CvmImage(vertical);
	    			break;
    			}

            imageTransfo.setImageBitmap(mImage.toBitmap());
            long t2 = System.currentTimeMillis();
            
            debugText.setText("Total time: " + (t2-t1) + "ms");
    		}
    	catch(Exception e){
    		Log.i("CVM", "Excepcion!!!!" + e.getMessage());
    		}
		}
	
	}

