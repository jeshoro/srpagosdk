package sr.pago.sdk;

import android.app.Service;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import sr.pago.sdk.utils.Logger;


/**
 * Created by Rodolfo on 22/03/2017 for PixzelleSAS.
 * Pixzelle Studio S. de R.L. All rights reserved.
 */

public class LocationUpdateService extends Service implements GoogleApiClient.ConnectionCallbacks,
        LocationListener,
        GoogleApiClient.OnConnectionFailedListener {
    protected GoogleApiClient mGoogleApiClient;

    protected LocationRequest mLocationRequest;
    public static Location lastLocation;
    public static int status = -1;

    protected LocationSettingsRequest mLocationSettingsRequest;
    private boolean hasPlayServices;

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 3000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {
            hasPlayServices = true;
            buildGoogleApiClient();
            createLocationRequest();
            buildLocationSettingsRequest();
        } else {
            hasPlayServices = true;
//            hasPlayServices = false;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if (hasPlayServices) {
            if (mGoogleApiClient != null) {
                mGoogleApiClient.connect();
            }
        } else {
            startLocationUpdates();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }

    protected synchronized void buildGoogleApiClient() {
        Logger.logWarning("LocationUpdateService", "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (mGoogleApiClient != null) {
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            startLocationUpdates();
        } else {
            buildGoogleApiClient();
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Logger.logWarning("LocationUpdateService", "onConnectionFailed -> " + connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {
        Logger.logWarning("LocationUpdateService", "onLocationChanged");
        if (location != null) {
            Logger.logWarning("LocationUpdateService", "Changed lat: " + location.getLatitude() + " - long: " + location.getLongitude());
            lastLocation = location;
        }
    }

    protected void startLocationUpdates() {

        if (hasPlayServices) {
            LocationServices.SettingsApi.checkLocationSettings(
                    mGoogleApiClient,
                    mLocationSettingsRequest
            ).setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult locationSettingsResult) {
                    final Status status = locationSettingsResult.getStatus();
                    LocationUpdateService.status = status.getStatusCode();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            Logger.logDebug("LocationUpdateService", "All location settings are satisfied.");
                            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, LocationUpdateService.this);
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, LocationUpdateService.this);
                            Logger.logDebug("LocationUpdateService", "Upgrade.");
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            String errorMessage = "Location settings are inadequate, and cannot be " +
                                    "fixed here. Fix in Settings.";
                            Logger.logDebug("LocationUpdateService", errorMessage);
                            break;
                        default:
                            Logger.logDebug("LocationUpdateService", "Error: " + locationSettingsResult);
                            break;
                    }
                }
            });
        } else {
            LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            List<String> providers = locationManager.getAllProviders();

            String provider= providers.get(0);
//            for (String provider : providers) {
                Logger.logWarning("LocationUpdateService", "provider -> " + provider);
                if (locationManager.isProviderEnabled(provider)) {
                    locationManager.requestLocationUpdates(provider, FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS, 10f, new android.location.LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            Logger.logWarning("LocationUpdateService", "onLocationChanged -> "+location.toString());
                            onLocationChanged(location);
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {
                            Logger.logWarning("LocationUpdateService", "onStatusChanged");
                        }

                        @Override
                        public void onProviderEnabled(String provider) {
                            Logger.logWarning("LocationUpdateService", "onProviderEnabled");
                        }

                        @Override
                        public void onProviderDisabled(String provider) {
                            Logger.logWarning("LocationUpdateService", "onProviderDisabled");
                        }
                    });
//                    break;
                } else
                    Logger.logWarning("LocationUpdateService", "provider is off");
//            }
        }
    }

    protected void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        if (hasPlayServices) {
            try {
                LocationServices.FusedLocationApi.removeLocationUpdates(
                        mGoogleApiClient,
                        this
                ).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                    }
                });
                status = -1;
            } catch (Exception ex) {

            }
        } else {

        }
    }
}
