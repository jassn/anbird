package com.andia.sysrecovery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import android.os.RecoverySystem;
import android.util.Log;


public class RecoveryHandle {
	
	boolean copyRet = false; 
	String updateName = null;
	public static final int modeFullUpdate = 0;
	public static final int modeFullUpdateAndClear = 1;
	public static final int modePartialUpdate = 2;

	//public static final String mount_Filename = "/mnt/media_rw/59E2-C2BB";
	public static final String mount_Filename = "/mnt/media_rw/8A13-A472";
	
	/*
	Environment.getDataDirectory() = /data
	Environment.getDownloadCacheDirectory() = /cache
	Environment.getExternalStorageDirectory() = /mnt/sdcard
	Environment.getRootDirectory() = /system
	context.getCacheDir() = /data/data/com.mt.mtpp/cache
	context.getExternalCacheDir() = /mnt/sdcard/Android/data/com.mt.mtpp/cache
	context.getFilesDir() = /data/data/com.mt.mtpp/files
	*/

	public String getRecoveryFile(int updatemode,String strdir) {
		
		File fileDir = null;
		File recoveryfile = null;
		String modeType = null;
		
		if((updatemode==modeFullUpdate)||(updatemode==modeFullUpdateAndClear))
			modeType = "sabresd_6dq-ota";
		
		if(updatemode==modePartialUpdate)
			modeType = "sabresd_6dq-incre";
		
		
		if(strdir.equalsIgnoreCase("USB"))
		{
			fileDir = new File(mount_Filename);
		}
		
		if(strdir.equalsIgnoreCase("SD"))
		{
			fileDir = new File("/mnt/sdcard2");
		}

		File[] files=fileDir.listFiles();

		if(files==null)
			return null;

		for(int i=0; i<files.length; i++)
		{
			File file = files[i];
			String filePath = file.getName();
			//filePath = "vcm30t30-20141111.andiabsp.zip";
			if(filePath.endsWith(".zip")&&filePath.startsWith(modeType,0)) // Condition to check vcm30t30-xxxxx-xxxxx.zip file 
			{
				recoveryfile = file;
				break;
			}
		      
		}
		//recoveryfile = new File("vcm30t30-20141111.andiabsp.zip");
		if(recoveryfile == null)
			return null;

		updateName = recoveryfile.getName();
		return recoveryfile.getName();	
	} // END of getRecoveryFile
	
	public void StartRecovery(final Context mycontext,final String strdir,final int updatemode) {
		//step1: copy recovery zip file to /cache 
		//Log.d("sysrecovery", "charlie StartRecovery start");
		
		//copyRecoveryfile(mycontext,strdir);
		
		final ProgressDialog ringProgressDialog = ProgressDialog.show(mycontext,"Please wait","Copy RecoveryImage...",true);
		ringProgressDialog.setCancelable(true);

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// Here you should write your time consuming task...
					// Let the progress ring for 10 seconds...
					//Log.d("sysrecovery", "copy recovery zip file to /cache");
					copyRecoveryfile(mycontext,strdir);
					
				} catch (Exception ioe) {
					ioe.printStackTrace();
					Log.d("sysrecovery", "StartRecovery Exception");
				}
				ringProgressDialog.dismiss();
				//when dissmiss dialog and then reset system
				RebootnRecovery(mycontext,updatemode);
			}
		}).start();
		
	} // END of StartRecovery
	
	public void RebootRecovery(Context mycontext,int updatemode) {
		RebootnRecovery(mycontext,updatemode);
	}
	
	/*copy ota_update zip file from usb drive to system cache folder*/
	private void copyRecoveryfile(Context mycontext,String strdir) {
		
		Log.d("sysrecovery", "copyRecoveryfile start");
		File fileDir = null;
		
		if(strdir.equalsIgnoreCase("USB"))
		{
			fileDir = new File(mount_Filename);
		}
		
		if(strdir.equalsIgnoreCase("SD"))
		{
			fileDir = new File("/mnt/sdcard2");
		}
		
		File cacheDir = Environment.getDownloadCacheDirectory();
		//File cacheDir = new File("/cache");

	
		File recoveryFilein = new File(fileDir, updateName);
		File recoveryFileout = new File(cacheDir, "update.zip");
		
		try{ 
			copyRet = copyFile(recoveryFilein,recoveryFileout);
			Log.d("sysrecovery", "Environment = OK ");
				
		}catch(IOException ioe){
		    // Handle Exception
			Log.d("sysrecovery", " copyRecoveryfile exception");
			ioe.printStackTrace();
		}
		
		if(copyRet)
			Log.d("sysrecovery", "value = OK ");	
		else
			Log.d("sysrecovery", "value = false" );
		
		Log.d("sysrecovery", "copyRecoveryfile exit");
	}
	
	
	/*copy last log zip file to storage drive*/
	public StringBuilder getLast_logfile(String strdir) {
		Log.d("sysrecovery", "getLast_logfile exit");
		File fileDir = null;
		
		
		File cacheDir = Environment.getDownloadCacheDirectory();
		//File cacheDir = new File("/cache");

		File recoverydir = new File(cacheDir, "recovery");
		File recoveryFileout = new File(fileDir, "last_log");
		File RECOVERY_DIR = new File("/cache/recovery");
		//Log.d("sysrecovery", String.format("recoveryFilein %s ",recoveryFilein.getPath()));
		//Log.d("sysrecovery", String.format("recoveryFileout %s ",recoveryFileout.getPath()));
		//File[] files=RECOVERY_DIR.listFiles();

		String[] names = RECOVERY_DIR.list();

		for (int i = 0; names != null && i < names.length; i++) {
          if (names[i].startsWith("last_",0))
          {
          	//Read text from file 
            StringBuilder text = new StringBuilder();  

            try {  
                BufferedReader br = new BufferedReader(new FileReader(names[i]));  
                String line;  

                while ((line = br.readLine()) != null) {  
                    text.append(line);  
                    text.append('\n');  
                }  
            }  
            catch (IOException ioe) {  
                //You'll need to add proper error handling here  
                ioe.printStackTrace();
                return null; 
            }
            return text;
          }
            //{
            //Log.d("getLast_logfile", String.format("recoveryDir %s ",names[i]));
            //}
           // File f = new File(RECOVERY_DIR, names[i]);
            
        }

		Log.d("sysrecovery", "getLast_logfile exit");
		return null;
		}
       
	
	/*call RecoverySystem installpackage to reboot device and enter recovery mode*/
	private void RebootnRecovery(Context mycontext,int updatemode) {
		
		File cacheDir = Environment.getDownloadCacheDirectory();
		//File cacheDir = new File("/cache");
		File recoveryFile = new File(cacheDir, "update.zip");
		
		try{ 
			if(updatemode == modeFullUpdateAndClear)
				recoveryAndclear(mycontext,recoveryFile);
			else
				RecoverySystem.installPackage(mycontext, recoveryFile);
			Log.d("sysrecovery", "RecoverySystem_installPackage");
				
		}catch(IOException ioe){
		    // Handle Exception
			Log.d("sysrecovery", "value = false" );
			ioe.printStackTrace();
		}
		Log.d("sysrecovery", "RecoverySystem_installPackage OK!!!");

	}
	
	

	private boolean copyFile(File src,File dst) throws IOException{
        if(src.getAbsolutePath().toString().equals(dst.getAbsolutePath().toString())){

            return false;

        }else{
            InputStream is=new FileInputStream(src);
            OutputStream os=new FileOutputStream(dst);
            
            byte[] buff=new byte[1024];
            int len;
            int sumlen = 0;
            while((len=is.read(buff))>0){
                os.write(buff,0,len);
                sumlen = sumlen+len;
                if((sumlen%(2*1024*1024)==0))
                	Log.d("sysrecovery", String.format("write %d bytes to cache",sumlen));
            }
            is.close();
            os.close();
        }
        return true;
    }
	
	//get ro.product.version for log filename
	
    public void recoveryAndclear(Context context,File recoveryFile) {
             
        try {
            //Class<?> cls = Class.forName("android.os.RecoverySystem");
            Method method = RecoverySystem.class.getMethod("installPackageWithWipeData", new Class[]{ Context.class, File.class });
            Log.d("sysrecovery", "recoveryAndclear method = " + method.toString());
            method.invoke(null, new Object[]{ context, recoveryFile });
            //System.out.println("method = " + method.toString());
            //Object obj = method.invoke(null, prop);
           
            } catch (Exception e) {
               Log.e("sysrecovery", "recoveryAndclear" , e);
            }
        }	
}

