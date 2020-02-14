/*
 * MIT License
 *
 * Copyright (c) 2020 Thales DIS
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * IMPORTANT: This source code is intended to serve training information purposes only.
 *            Please make sure to review our IdCloud documentation, including security guidelines.
 */

package com.gemalto.mobileprotector.gettingstarted;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gemalto.idp.mobile.core.ApplicationContextHolder;
import com.gemalto.idp.mobile.core.IdpCore;
import com.gemalto.idp.mobile.core.passwordmanager.PasswordManagerException;
import com.gemalto.idp.mobile.otp.OtpConfiguration;
import com.gemalto.mobileprotector.sdk.ProtectorConfig;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApplicationContextHolder.setContext(this);

        final TextView versionTextView = findViewById(R.id.tv_protector_version);

        final String formattedVersion = String.format(Locale.getDefault(),
                                                     getString(R.string.sdk_version_placeholder),
                                                     IdpCore.getVersion());
        versionTextView.setText(formattedVersion);

        findViewById(R.id.root_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                updateStatus();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        final boolean doesHavePermissions = checkAndRequestPermissions();

        if (doesHavePermissions) {
            configureProtector();
        }

    }


    /**
     * Requests needed runtime permissions.
     *
     * @return {@code True} if application already has all needed permissions, {@code false} if permissions need to be
     * requested.
     */
    protected boolean checkAndRequestPermissions() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        final int permissionState = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionState == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            ActivityCompat.requestPermissions(this,
                                              new String[]{Manifest.permission.READ_PHONE_STATE},
                                              6497);    // just a random number used as a request code
            return false;
        }
    }


    private void updateStatus(){

        Toast.makeText(this, "Refreshing configuration status ...", Toast.LENGTH_SHORT).show();

        if(IdpCore.isConfigured()){
            final TextView statusTextView = findViewById(R.id.tv_configuration_status);
            statusTextView.setText(getString(R.string.configured));
        }
    }


    private void configureProtector(){
        if (!IdpCore.isConfigured()) {

            final OtpConfiguration otpConfiguration = new OtpConfiguration.Builder()
                    .setRootPolicy(OtpConfiguration.TokenRootPolicy.IGNORE)
                    .build();

            IdpCore.configure(ProtectorConfig.getActivationCode(), otpConfiguration);

            // Login to PasswordManager without a password so that the SDK’s persistent data
            // can be accessed during provisioning.
            try {
                    IdpCore.getInstance().getPasswordManager().login();
            } catch (final PasswordManagerException exception) {
                // this should not happen.
                throw new IllegalStateException(exception);
            }
        }
    }


}
