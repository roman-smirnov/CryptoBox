package roman.com.cryptobox;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;

/**
 * This activity exists to enable the user to give access permissions
 * to GPS, camera, audio,etc on devices with API level 23 and above (Marshmallow);
 * older devices just grant all these permissions automatically, and the activity loads and then skips
 */
public class PermissionsActivity extends AppCompatActivity {

    //these are the switches that pop up the dialog for each permission
    private Switch mStorageSwitch;

    // permission codes for permission request callbacks
    private static final int STORAGE_PERMISSION_CODE = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);

        // if all the permissions are already granted - load main activity
        if(checkAllPermissionsGranted()){
            goToNextActivity();
        }

        mStorageSwitch = (Switch) findViewById(R.id.storage_switch);

        //set switches on and unclickable for switches with already granted permissions
        //MUST BE CALLED BEFORE ADD SWITCH STATE CHANGE LISTENER
        setSwitchStates();

        addSwitchStateChangeListeners();


    }


    /**
     * set switches on and unclickable for switches with already granted permissions
     */
    private void setSwitchStates(){

        if(checkStoragePermission()){
            mStorageSwitch.setChecked(true);
            mStorageSwitch.setClickable(false);
        }

    }


    /**
     *
     * @return true if ACCESS_FINE_LOCATION already granted, false otherwise
     */
    public boolean checkLocationPermission(){
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
    /**
     *
     * @return true if WRITE_EXTERNAL_STORAGE already granted, false otherwise
     */
    public boolean checkStoragePermission(){
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestStoragePermission(){
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,},
                STORAGE_PERMISSION_CODE);

    }

    private void storagePermissionResponse(boolean permissionState){
        if(permissionState){
            mStorageSwitch.setChecked(true);
            mStorageSwitch.setClickable(false);

            if(checkAllPermissionsGranted()){
                goToNextActivity();
            }
            return;
        }

        mStorageSwitch.setChecked(false);
        Toast.makeText(PermissionsActivity.this, "THE APP NEEDS STORAGE PERMISSION TO WORK", Toast.LENGTH_SHORT).show();
    }



    /**
     * request permission callback, calls the relevant permissionResponse() method once a callback is received.
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case STORAGE_PERMISSION_CODE: {
                LogUtils.d("case STORAGE_PERMISSION_CODE");
                if(checkRequestGranted(grantResults)){
                    // permission was granted, yay!
                    storagePermissionResponse(true);
                } else {
                    // permission denied, boo!
                    storagePermissionResponse(false);
                }
                return;
            }
            default: {
                LogUtils.d("DEFAULT PERMISSION RESULT? WTF?");
            }
        }
    }


    /**
     * checks whether the permission was granted or not
     * @param grantResults results array
     * @return boolean granted or not
     */
    private boolean checkRequestGranted( @NonNull int[] grantResults){
        // If request is cancelled, the result arrays are empty.
        return grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * check if all relevan permissions were granted
     * @return true if all granted, false otherwise
     */
    private boolean checkAllPermissionsGranted() {
        return checkStoragePermission();
    }

    /**
     * launches the main activity
     */
    private void goToNextActivity(){
        Intent intent = new Intent(this,NotesActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * The method attaches a listener to every permission switch.
     * There are several methods to flip a switch (click,fling,etc), so we must instead listen for a state change.
     * these listeners call relevant request...Permission() methods once switch state changes.
     */
    private void addSwitchStateChangeListeners() {
        // listener for swtich fling/click/etc
        mStorageSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    requestStoragePermission();
                }
            }
        });
    }
}
