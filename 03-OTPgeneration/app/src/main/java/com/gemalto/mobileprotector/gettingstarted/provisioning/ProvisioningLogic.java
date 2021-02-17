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

package com.gemalto.mobileprotector.gettingstarted.provisioning;

import android.support.annotation.NonNull;

import com.gemalto.idp.mobile.core.IdpException;
import com.gemalto.idp.mobile.core.devicefingerprint.DeviceFingerprintSource;
import com.gemalto.idp.mobile.core.util.SecureString;
import com.gemalto.idp.mobile.otp.OtpModule;
import com.gemalto.idp.mobile.otp.Token;
import com.gemalto.idp.mobile.otp.TokenManager;
import com.gemalto.idp.mobile.otp.devicefingerprint.DeviceFingerprintTokenPolicy;
import com.gemalto.idp.mobile.otp.oath.OathService;
import com.gemalto.idp.mobile.otp.oath.OathToken;
import com.gemalto.idp.mobile.otp.oath.OathTokenManager;
import com.gemalto.idp.mobile.otp.provisioning.EpsConfigurationBuilder;
import com.gemalto.idp.mobile.otp.provisioning.MobileProvisioningProtocol;
import com.gemalto.idp.mobile.otp.provisioning.ProvisioningConfiguration;
import com.gemalto.mobileprotector.sdk.ProtectorConfig;

import java.net.MalformedURLException;
import java.util.Map;

/**
 * Logic for token provisioning using Mobile Protector SDK.
 */
public class ProvisioningLogic {

    /**
     * Provisions asynchronously a new token.
     *
     * @param userId           User id.
     * @param registrationCode Registration code.
     * @param callback         Callback back to the application - called on Main UI Thread.
     */
    public static void provision(
            @NonNull String userId,
            @NonNull SecureString registrationCode,
            @NonNull final ProvisioningCallback callback
    ) {
        try {
            OathTokenManager oathTokenManager = OathService.create(OtpModule.create()).getTokenManager();

            ProvisioningConfiguration provisioningConfiguration = new EpsConfigurationBuilder(registrationCode,
                    ProtectorConfig.Provisioning.getProvisioningUrl(),
                    ProtectorConfig.DOMAIN,
                    MobileProvisioningProtocol.PROVISIONING_PROTOCOL_V5,
                    ProtectorConfig.Provisioning.getRsaKeyId(),
                    ProtectorConfig.Provisioning.getRsaKeyExponent(),
                    ProtectorConfig.Provisioning.getRsaKeyModulus())
                    .setTlsConfiguration(ProtectorConfig.Provisioning.getTlsConfiguration())
                    .build();

            DeviceFingerprintSource
                    deviceFingerprintSource = new DeviceFingerprintSource(ProtectorConfig.CUSTOM_FINGERPRINT_DATA,
                    DeviceFingerprintSource.Type.SOFT);
            DeviceFingerprintTokenPolicy
                    deviceFingerprintTokenPolicy = new DeviceFingerprintTokenPolicy(true,
                    deviceFingerprintSource);

            oathTokenManager.createToken(userId,
                    provisioningConfiguration,
                    deviceFingerprintTokenPolicy,
                    new TokenManager.TokenCreationCallback() {
                        @Override
                        public void onSuccess(
                                Token token,
                                Map<String, String> map
                        ) {
                            callback.onProvisioningSuccess((OathToken) token);
                        }

                        @Override
                        public void onError(IdpException e) {
                            callback.onProvisioningError(e);
                        }
                    });
        } catch (MalformedURLException e) {
            callback.onProvisioningError(e);
        } finally {
            registrationCode.wipe();
        }
    }
}
