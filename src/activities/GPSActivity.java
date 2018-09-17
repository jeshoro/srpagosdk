package sr.pago.sdk.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import sr.pago.sdk.SrPagoLocationManager;
import sr.pago.sdk.definitions.Definitions;

/**
 * Created by Rodolfo on 24/09/2015 for SrPagoSDK.
 * Sr. Pago All rights reserved.
 */
public abstract class GPSActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{

    protected GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double latitude;
    private double longitude;
    private SrPagoLocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //buildGoogleApiClient();
    }

    @Override
    public void onConnected(Bundle bundle) {
        final Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
        }
        if(mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
        else{
            getLocationWithGPS();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        getLocationWithGPS();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        getLocationWithGPS();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
    }

    public Boolean statusGps() {
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
       if ((mGoogleApiClient == null || !mGoogleApiClient.isConnected()) && !locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            dialogo1.setTitle(Definitions.SR_PAGO());
            dialogo1.setMessage(Definitions.GPS_DISACTIVATED());
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton(Definitions.CONFIRM(), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                }
            });
            dialogo1.show();
           return false;
        }
        return  true;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            initGPS();
        }
        /*statusGps();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected())
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            */

    }

    public void initGPS() {
        buildGoogleApiClient();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*if (mGoogleApiClient != null)
            mGoogleApiClient.connect();*/
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void reinitGps(){
        buildGoogleApiClient();
    }

    protected void startLocationUpdates(){
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000)
                .setFastestInterval(2000);

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    private void getLocationWithGPS(){
        locationManager = new SrPagoLocationManager(this);
        latitude = locationManager.getLatitude();
        longitude = locationManager.getLongitude();
    }
}
