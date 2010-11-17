package edu.uco.cvm.test;

import org.me.applications.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import edu.uco.cvm.core.CvmImage;

public class CvmColorSpaceTest extends Activity{

	private CvmImage mImage;
	
	private ImageView originalIV;
	private ImageView channel1IV;
	private ImageView channel2IV;
	private ImageView channel3IV;
	
	@Override
    public void onCreate(Bundle icicle) {
		
		super.onCreate(icicle);
        setContentView(R.layout.cvmcolorspacetest);		
        
        originalIV = (ImageView) findViewById(R.id.originalIV);
    	channel1IV = (ImageView) findViewById(R.id.channel1);
    	channel2IV = (ImageView) findViewById(R.id.channel2);
    	channel3IV = (ImageView) findViewById(R.id.channel3);
        
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fire);        
		mImage = new CvmImage(mBitmap, CvmImage.RGB);
		mImage.changeColorSpace(CvmImage.HSV);
		
		originalIV.setImageBitmap(mBitmap);
		
		channel1IV.setImageBitmap(mImage.getChannel(0).toBitmap());
		channel2IV.setImageBitmap(mImage.getChannel(1).toBitmap());
		channel3IV.setImageBitmap(mImage.getChannel(2).toBitmap());
		}
	}
