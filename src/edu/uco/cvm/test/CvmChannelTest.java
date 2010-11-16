package edu.uco.cvm.test;

import org.me.applications.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import edu.uco.cvm.core.CvmChannel;
import edu.uco.cvm.core.CvmMaskFactory;

public class CvmChannelTest extends Activity {
	
	private ImageView original;
	private ImageView red;
	private ImageView green;
	private ImageView blue;
	
	@Override
    public void onCreate(Bundle icicle) {
		
		super.onCreate(icicle);
        setContentView(R.layout.cvmchanneltest);	
		
        ImageView original = (ImageView) findViewById(R.id.channelTestOriginal);
    	ImageView red = (ImageView) findViewById(R.id.channelTestR);
    	ImageView green = (ImageView) findViewById(R.id.channelTestG);
    	ImageView blue = (ImageView) findViewById(R.id.channelTestB);
        
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_cat);
        original.setImageBitmap(mBitmap);
        
        CvmChannel c1 = new CvmChannel(mBitmap, CvmChannel.GRAY);
        
        red.setImageBitmap(c1.toBitmap());
        c1.applyThreshold(100, 255, CvmChannel.THRES_OTSU);
        green.setImageBitmap(c1.toBitmap());
        c1.updateHistogram();
        blue.setImageBitmap(c1.getHistogramImage());     
		}
	
	}
