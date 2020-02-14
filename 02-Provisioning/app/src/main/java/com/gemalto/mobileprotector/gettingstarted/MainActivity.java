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
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.gemalto.idp.mobile.core.ApplicationContextHolder;
import com.gemalto.idp.mobile.core.IdpCore;
import com.gemalto.idp.mobile.core.IdpException;
import com.gemalto.idp.mobile.core.passwordmanager.PasswordManagerException;
import com.gemalto.idp.mobile.core.util.SecureString;
import com.gemalto.idp.mobile.otp.OtpConfiguration;
import com.gemalto.idp.mobile.otp.oath.OathToken;
import com.gemalto.mobileprotector.gettingstarted.provisioning.ProvisioningCallback;
import com.gemalto.mobileprotector.gettingstarted.provisioning.ProvisioningFragment;
import com.gemalto.mobileprotector.gettingstarted.provisioning.ProvisioningFragmentDelegate;
import com.gemalto.mobileprotector.gettingstarted.provisioning.ProvisioningLogic;
import com.gemalto.mobileprotector.gettingstarted.util.token.TokenUtils;
import com.gemalto.mobileprotector.sdk.ProtectorConfig;

import java.util.Locale;


public class MainActivity extends AppCompatActivity implements ProvisioningFragmentDelegate {

    private final static int PERMISSION_REQUEST_CODE = 6957;

    private ProvisioningFragment mProvisioningFragment;


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

        mProvisioningFragment = (ProvisioningFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_provisioning);
        mProvisioningFragment.setDelegate(this);
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
                                              PERMISSION_REQUEST_CODE);
            return false;
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
                // this should not happen during configuration time
                throw new IllegalStateException(exception);
            }

            Toast.makeText(this, "Protector configured OK", Toast.LENGTH_LONG).show();

            // Update UI with a provisioned token if any
            try {
                updateViews(TokenUtils.getFirstToken());
            } catch (IdpException e) {
                // do nothing
            }
        }
    }


    private void updateViews(final OathToken token){
        mProvisioningFragment.updateView(token);
    }


    @Override
    public void onProvision(final String userId, final SecureString registrationCode) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.provisioning_in_progress);
        progressDialog.show();

        ProvisioningLogic
                .provision(userId, registrationCode, new ProvisioningCallback() {
                    @Override
                    public void onProvisioningSuccess(@NonNull final OathToken token) {
                        progressDialog.dismiss();

                        updateViews(token);

                        Toast.makeText(MainActivity.this, "Provisioning done OK.", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onProvisioningError(@NonNull final Exception exception) {
                        progressDialog.dismiss();

                        Toast.makeText(MainActivity.this, "Provisioning failed.\n" + exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onRemoveToken(final String tokenName) {

        try {
            if (TokenUtils.removeToken(tokenName)) {

                updateViews(null);

                Toast.makeText(MainActivity.this, "Token removed.", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Token removal failed.", Toast.LENGTH_LONG).show();
            }
        } catch (final IdpException exception) {

            final String errorMessage = String.format(Locale.getDefault(),
                                                      getString(R.string.sdk_error_placeholder),
                                                      exception.getDomain(),
                                                      exception.getCode(),
                                                      exception.getMessage());

            Toast.makeText(this, "Token removal failed.\n" + errorMessage, Toast.LENGTH_LONG).show();
        }
    }
}
