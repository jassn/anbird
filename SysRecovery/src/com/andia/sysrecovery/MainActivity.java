package com.andia.sysrecovery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Environment;
import java.io.File;

public class MainActivity extends Activity implements OnClickListener, OnItemSelectedListener {
	
	public static final int modeFullUpdate = 0;
	public static final int modeFullUpdateAndClear = 1;
	public static final int modePartialUpdate = 2;
	
	RecoveryHandle mRH = new RecoveryHandle();
	AlertDialog alertDialog;
	String strNVersionDate = null;
	String strCVersionDate = null;
	String driveName = null;
	
	TextView txtCVName;
	TextView txtUpdateName;
	
	boolean bReadyToUpdate = false;
	boolean isNewUpdate = false;
	
	private static int iupdateMode = 0;
	
	public static final int iPowerOff = 11;
	
    @SuppressWarnings("rawtypes")
	@Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Spinner modespinner = (Spinner)findViewById(R.id.spinnerUpdate);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.mode_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modespinner.setAdapter(adapter);
       
        modespinner.setOnItemSelectedListener(this);
        
        Spinner sourcespinner = (Spinner)findViewById(R.id.updateSource);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.source_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourcespinner.setAdapter(adapter1);
       
        sourcespinner.setOnItemSelectedListener(this);
       
        Button btnStartRecovery = (Button) findViewById(R.id.btnStartRecovery);
          
        txtCVName = (TextView)findViewById(R.id.txtcurrentVersion);
        txtUpdateName = (TextView)findViewById(R.id.txtupdateVersion);
     
		btnStartRecovery.setOnClickListener(this);
		
		txtCVName.setText(getAndroidVersion());
		
		driveName = "USB";
		iupdateMode = modeFullUpdate;
    }
    
    @Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
    	Spinner spinner = (Spinner) parent;
    	String filename = null;
    	
        if(spinner.getId() == R.id.spinnerUpdate)
        {
        	iupdateMode = position;
        	
        	filename = mRH.getRecoveryFile(iupdateMode,driveName);
    		if(filename!=null)
    		{
    			txtUpdateName.setText(filename);
    			bReadyToUpdate = true;
    		}else
    		{
    			txtUpdateName.setText("update file not found");
    			bReadyToUpdate = false;
    		}
        }
        else if(spinner.getId() == R.id.updateSource)
        {
        	
        	//String[] separated;
        	
        	//iupdateMode = position;
        	if(position==0)
        	{
        		driveName = "USB";
        		Log.d("sysrecovery", "updateSource From USB Drive ");
        	}
        	if(position==1)
        	{
        		driveName = "SD";
        		Log.d("sysrecovery", "updateSource  From SD Drive");
        	}
          //do this
        	filename = mRH.getRecoveryFile(iupdateMode,driveName);
    		if(filename!=null)
    		{
    			txtUpdateName.setText(filename);
    			bReadyToUpdate = true;
    		}else
    		{
    			txtUpdateName.setText("update file not found");
    			bReadyToUpdate = false;
    		}
        }
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		driveName = "USB";
		iupdateMode = modeFullUpdate;
	}
    
    @Override
    public void onClick(View v) {
    	
    	
    	String[] separated;
    	
    	switch (v.getId()) {
		//case R.id.btnRecoveryUsb:
		//	driveName = "USB";
		//case R.id.btnRecoverySDCard:
		/*	
			
			driveName = "SD";
			filename = mRH.getRecoveryFile(driveName);
			if(filename!=null)
			{
				//separated = filename.split("\\.");
				//txtNewfname.setText(filename);
				bReadyToUpdate = true;
			}else
			{
				//txtNewfname.setText("update file not found");
				bReadyToUpdate = false;
			}
			
			break;*/
		case R.id.btnStartRecovery:

			boolean isrmCachefile = rmCachefile();

            //mark by charlie
			if(isrmCachefile)	
				Log.d("sysrecovery", "delete cache file ok");
			else
				Log.d("sysrecovery", "delete cache file error");
			
			
			if(!bReadyToUpdate)
				break;
			
			//separated = filename.split("\\.");
			/*
			isNewUpdate = false;
			
			String strCVersionTime = null;
			String strNVersionTime = null;
			
			separated = txtCVName.getText().toString().split("\\.");
			strCVersionDate = separated[2];
			strCVersionTime = separated[3];
			
			separated = txtUpdateName.getText().toString().split("\\.");
			strNVersionDate = separated[2];
			strNVersionTime = separated[3];
				
			int intCDate = Integer.parseInt(strCVersionDate);
			int intNDate = Integer.parseInt(strNVersionDate);
			int intCTime = Integer.parseInt(strCVersionTime);
			int intNTime = Integer.parseInt(strNVersionTime);
			*/
			// update image is newer than current version, run directly.
            /*
			if(intNDate>intCDate)
				isNewUpdate = true;
			
			if((intNDate==intCDate)&&(intNTime>=intCTime))
				isNewUpdate = true;
            */
			
			/*
			if(intNVersion==intCVersion)
			{
				ShowAlertDialog(1,driveName); //1, for equal and 2 for older
			}
			
			
			if(intNVersion<intCVersion)
			{
				ShowAlertDialog(2,driveName); //1, for equal and 2 for older
			}*/
			
			//if(isNewUpdate==true)
				mRH.StartRecovery(MainActivity.this,driveName,iupdateMode);
			
			break;
			
		/*case R.id.btnStartPowerOff:
			
			Log.d("sysrecovery", "btnRecoveryUsb Pressed");
			
			Intent intent = new Intent();
		    intent.setAction("com.andia.sysrecoveryservice.my_action");
		    intent.putExtra("PowerMode", iPowerOff);
		    sendBroadcast(intent);*/
		    
			//PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
			//pm.reboot("null");
				
			//break;
		}
		
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //如果使用"."、"|"、"^"等字符做分隔符时，要写成s3.split("\\^")的格式
    
    private String getAndroidVersion() {
    	
      // String release = Build.VERSION.RELEASE;
      // String codename = Build.VERSION.CODENAME;
      // int sdkVersion = Build.VERSION.SDK_INT;
      // String board = Build.BOARD;
      // String hardware = Build.HARDWARE;
      // long ltime = Build.TIME;
    	
        String incremental = Build.VERSION.INCREMENTAL;
      // String device = Build.DEVICE;

        Log.d("sysrecovery", String.format("Version:incremental %s ",incremental));
 
        //String[] separated = incremental.split("\\.");
        //String buildtime = separated[3];
        //String builddate = separated[2];
       // String andiabsp = separated[1];
	     
       // vcm30t30-ota-eng.andiabsp.1416281931
        //return "vcm30t30-eng.andiabsp."+builddate+"."+buildtime;
        return incremental;
    }

    private boolean rmCachefile(){
		boolean deleted;
		File cacheDir = Environment.getDownloadCacheDirectory();
		File recoveryFile = new File(cacheDir, "update.zip");
		deleted = recoveryFile.delete();
		return deleted;
	}
    
    private void ShowAlertDialog(final int dialogCase,final String driveName){
    	
    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
    	alertDialogBuilder.setTitle(R.string.dialog_title);
    	
    	switch (dialogCase)
    	{
    	case 1:
    		alertDialogBuilder.setMessage(R.string.dialog_message_equal);
    		break;
    	case 2:
    		alertDialogBuilder.setMessage(R.string.dialog_message_fail);
    		break;
    	default:
    
    	}
    	
        alertDialogBuilder.setPositiveButton(R.string.positive_button, 
        new DialogInterface.OnClickListener() {
  		
           @Override
           public void onClick(DialogInterface arg0, int arg1) {
              //Intent positveActivity = new Intent(getApplicationContext(),com.example.alertdialog.PositiveActivity.class);
              //startActivity(positveActivity);
        	   Log.d("sysrecovery", "positive_button Pressed");
        	   if(dialogCase==1){
        		   mRH.StartRecovery(MainActivity.this,driveName,iupdateMode);
        		   //mRH.RebootRecovery(MainActivity.this);
        	   }
           }
        });
        alertDialogBuilder.setNegativeButton(R.string.negative_button, 
        new DialogInterface.OnClickListener() {
  			
           @Override
           public void onClick(DialogInterface dialog, int which) {
              //Intent negativeActivity = new Intent(getApplicationContext(),com.example.alertdialog.NegativeActivity.class);
              //startActivity(negativeActivity);
        	   Log.d("sysrecovery", "negative_button Pressed");
  		 }
        });
  	    
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
   		
    }
}
