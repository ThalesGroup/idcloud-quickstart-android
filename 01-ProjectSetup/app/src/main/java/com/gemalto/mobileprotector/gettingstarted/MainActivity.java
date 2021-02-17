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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gemalto.idp.mobile.core.ApplicationContextHolder;
import com.gemalto.idp.mobile.core.IdpCore;
import com.gemalto.idp.mobile.core.passwordmanager.PasswordManagerException;
import com.gemalto.idp.mobile.otp.OtpConfiguration;
import com.gemalto.mobileprotector.sdk.ProtectorConfig;
import com.thalesgroup.gemalto.securelog.SecureLogConfig;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApplicationContextHolder.setContext(this);

        TextView versionTextView = findViewById(R.id.tv_protector_version);

        String formattedVersion = String.format(Locale.getDefault(),
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

        configureProtector();
    }

    private void updateStatus() {

        Toast.makeText(getApplicationContext(), "Refreshing configuration status ...", Toast.LENGTH_SHORT)
                .show();

        if (IdpCore.isConfigured()) {
            TextView statusTextView = findViewById(R.id.tv_configuration_status);
            statusTextView.setText(getString(R.string.configured));
        }
    }

    private void configureProtector() {
        if (!IdpCore.isConfigured()) {

            IdpCore.configureSecureLog(new SecureLogConfig.Builder(ApplicationContextHolder.getContext())
                    .publicKey(ProtectorConfig.CFG_SLOG_MODULUS, ProtectorConfig.CFG_SLOG_EXPONENT)
                    .build());

            OtpConfiguration otpConfiguration = new OtpConfiguration.Builder()
                    .setRootPolicy(OtpConfiguration.TokenRootPolicy.IGNORE)
                    .build();

            IdpCore.configure(ProtectorConfig.getActivationCode(), otpConfiguration);

            // Login to PasswordManager without a password so that the SDK’s persistent data
            // can be accessed during provisioning.
            try {
                IdpCore.getInstance().getPasswordManager().login();
            } catch (PasswordManagerException exception) {
                // this should not happen.
                throw new IllegalStateException(exception);
            }
        }
    }
}
