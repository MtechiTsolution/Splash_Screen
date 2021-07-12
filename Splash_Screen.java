package mtech.pak.portalgcuf;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

public class MainActivity extends AppCompatActivity {
AdView adView;
    private Handler mHandler = new Handler();
    private int REQUEST_CORE=11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        adView = findViewById( R.id.adView );


        final AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create( MainActivity.this );
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener( new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE)
                    result.isUpdateTypeAllowed( AppUpdateType.IMMEDIATE );
                try {
                    appUpdateManager.startUpdateFlowForResult( result, AppUpdateType.IMMEDIATE, MainActivity.this, REQUEST_CORE );
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        } );

//        AdRequest adRequest1 = new AdRequest.Builder().build();
//        adView.loadAd(adRequest1);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        mHandler.postDelayed( new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent( MainActivity.this,Main2Activity.class );
                startActivity( intent );
            }
        } ,8100);

//        mHandler.postDelayed(new Runanble() {
//            @Override
//            public void run() {
//                Intent intent=new Intent( MainActivity.this,Main2Activity.class );
////        startActivity( intent );
//            }
//        }, 4000); // 4 seconds
//        final ProgressDialog progressDialog = new ProgressDialog( MainActivity.this );
//        progressDialog.setMessage( "Please wait data is Processing....." );
//
////        After 2 Seconds i dismiss progress Dialog
//
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    Thread.sleep( 110000 );
//                    if (progressDialog.isShowing())
//                        progressDialog.dismiss();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//        Intent intent=new Intent( MainActivity.this,Main2Activity.class );
//        startActivity( intent );


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode==REQUEST_CORE){
            Toast.makeText( MainActivity.this, "Start Download", Toast.LENGTH_SHORT ).show();
            if (requestCode !=RESULT_OK){
                Log.d("mmm","Update Flow Failed"+resultCode);
            }
        }
    }
}

