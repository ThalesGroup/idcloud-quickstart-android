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

package com.gemalto.mobileprotector.sdk;


import androidx.annotation.NonNull;

import com.gemalto.idp.mobile.core.IdpCore;
import com.gemalto.idp.mobile.core.net.TlsConfiguration;
import com.gemalto.idp.mobile.core.util.SecureString;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Configuration values needed for Mobile Protector SDK setup.
 */
public class ProtectorConfig {

    /**
     * Domain.
     */
    public static final String DOMAIN = "idcloudpartners";

    /**
     * Custom finger print data.
     */
    public static final byte[] CUSTOM_FINGERPRINT_DATA;

    static {
        CUSTOM_FINGERPRINT_DATA = "CUSTOM_FINGERPRINT_DATA".getBytes(
                StandardCharsets.UTF_8);
    }

    /**
     * Activation code is used to enable individual supported features
     */
    private static final byte[] ACTIVATION_CODE = { (byte) 0x01, (byte) 0x47, (byte) 0x54, (byte) 0x4f, (byte) 0x52,
            (byte) 0x6e, (byte) 0x44, (byte) 0x30, (byte) 0x31, (byte) 0xff,
            (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x42, (byte) 0x92,
            (byte) 0xbb, (byte) 0x8d, (byte) 0xae, (byte) 0x24, (byte) 0xfb,
            (byte) 0x2b, (byte) 0xd4, (byte) 0xe6, (byte) 0xfa, (byte) 0xc9,
            (byte) 0xad, (byte) 0x26, (byte) 0xef, (byte) 0xbe, (byte) 0x7e,
            (byte) 0x54, (byte) 0x26, (byte) 0x2c, (byte) 0x91, (byte) 0xa2,
            (byte) 0x0c, (byte) 0xf4, (byte) 0xcb, (byte) 0x1e, (byte) 0x90,
            (byte) 0xea, (byte) 0x87, (byte) 0x95, (byte) 0xeb, (byte) 0x5d,
            (byte) 0x40, (byte) 0x9d, (byte) 0x4e, (byte) 0xd1, (byte) 0x11,
            (byte) 0x1b, (byte) 0x24, (byte) 0xcd, (byte) 0x4c, (byte) 0x6f,
            (byte) 0xc9, (byte) 0x72, (byte) 0x79, (byte) 0x09, (byte) 0xf1,
            (byte) 0xda, (byte) 0xeb, (byte) 0x15, (byte) 0x3c, (byte) 0xff,
            (byte) 0xae, (byte) 0x9a, (byte) 0x49, (byte) 0x9d, (byte) 0x92,
            (byte) 0xc0, (byte) 0x62, (byte) 0xb0, (byte) 0x7a, (byte) 0xc1,
            (byte) 0xe1, (byte) 0xf0 };

    /**
     * Retrieves the activation code.
     *
     * @return Activation code.
     */
    @NonNull
    public static byte[] getActivationCode() {
        return ACTIVATION_CODE.clone();
    }


    /**
     * Values needed for Token provisioning.
     */
    public static class Provisioning {

        /**
         * The URL of the Enrollment API endpoint, e.g: https://api/provisioning/pp
         */

        private static final String PROVISIONING_URL
                = "https://provisioner-eps-demo.rnd.gemaltodigitalbankingidcloud.com/provisioner/domains/idcloudpartners/provision";

        /**
         * Identifier for the EPS server’s public RSA key.
         */
        private static final String RSA_KEY_ID = "B9FE8D1DC947F143E99DBB4638D850D9EFCC985D";

        /**
         * The RSA modulus of the EPS public key (on provisioning protocol level, not transport level).
         */
        private static final byte[] RSA_KEY_MODULUS = {
                (byte) 0x00, (byte) 0xb6, (byte) 0x33, (byte) 0x7a, (byte) 0x8c, (byte) 0xc8, (byte) 0xff,
                (byte) 0x77, (byte) 0x17, (byte) 0x30, (byte) 0x69, (byte) 0xc2, (byte) 0x1e, (byte) 0xd9,
                (byte) 0xce, (byte) 0x5b, (byte) 0x83, (byte) 0xd4, (byte) 0xb9, (byte) 0x6d, (byte) 0xb4,
                (byte) 0x0c, (byte) 0x39, (byte) 0x2c, (byte) 0x29, (byte) 0xd7, (byte) 0x7c, (byte) 0x6f,
                (byte) 0x3e, (byte) 0xca, (byte) 0x47, (byte) 0x28, (byte) 0xd7, (byte) 0xf1, (byte) 0x1e,
                (byte) 0x2f, (byte) 0x25, (byte) 0x7e, (byte) 0x1f, (byte) 0xab, (byte) 0x5e, (byte) 0xe5,
                (byte) 0x95, (byte) 0x12, (byte) 0x51, (byte) 0x42, (byte) 0x88, (byte) 0x60, (byte) 0x34,
                (byte) 0x5c, (byte) 0xf2, (byte) 0x6e, (byte) 0x71, (byte) 0x4f, (byte) 0x36, (byte) 0x33,
                (byte) 0x78, (byte) 0xbf, (byte) 0x69, (byte) 0x3f, (byte) 0xe4, (byte) 0x01, (byte) 0xb1,
                (byte) 0xfb, (byte) 0x66, (byte) 0xce, (byte) 0xaa, (byte) 0x84, (byte) 0xf6, (byte) 0xb9,
                (byte) 0x47, (byte) 0xd1, (byte) 0x23, (byte) 0x29, (byte) 0xa9, (byte) 0x30, (byte) 0xe4,
                (byte) 0x7c, (byte) 0xed, (byte) 0xae, (byte) 0x9d, (byte) 0x73, (byte) 0xdc, (byte) 0x0f,
                (byte) 0x07, (byte) 0x46, (byte) 0x05, (byte) 0xea, (byte) 0xb2, (byte) 0xae, (byte) 0x34,
                (byte) 0x2f, (byte) 0xb2, (byte) 0x86, (byte) 0x3b, (byte) 0x7e, (byte) 0xdf, (byte) 0x43,
                (byte) 0x59, (byte) 0xd3, (byte) 0x52, (byte) 0x86, (byte) 0xff, (byte) 0xc5, (byte) 0x38,
                (byte) 0xac, (byte) 0x0a, (byte) 0x6c, (byte) 0x54, (byte) 0x2f, (byte) 0x83, (byte) 0x75,
                (byte) 0x33, (byte) 0x76, (byte) 0x67, (byte) 0x5e, (byte) 0xca, (byte) 0xb4, (byte) 0xd5,
                (byte) 0x72, (byte) 0xab, (byte) 0xfe, (byte) 0xe9, (byte) 0xef, (byte) 0x1f, (byte) 0x87,
                (byte) 0x4c, (byte) 0x0a, (byte) 0xd4, (byte) 0x39, (byte) 0x76, (byte) 0x89, (byte) 0xa0,
                (byte) 0x64, (byte) 0x5d, (byte) 0xc6, (byte) 0x8c, (byte) 0x71, (byte) 0x58, (byte) 0xf7,
                (byte) 0x14, (byte) 0x85, (byte) 0x3a, (byte) 0xb3, (byte) 0x28, (byte) 0x4d, (byte) 0xae,
                (byte) 0xc5, (byte) 0x5d, (byte) 0x20, (byte) 0x30, (byte) 0x12, (byte) 0x6b, (byte) 0xa2,
                (byte) 0x69, (byte) 0x5f, (byte) 0x72, (byte) 0x34, (byte) 0x8a, (byte) 0xed, (byte) 0x6e,
                (byte) 0x67, (byte) 0x82, (byte) 0x3c, (byte) 0x1c, (byte) 0x1d, (byte) 0xfa, (byte) 0x8d,
                (byte) 0xc4, (byte) 0xc5, (byte) 0x74, (byte) 0x61, (byte) 0xaa, (byte) 0x7b, (byte) 0x77,
                (byte) 0x87, (byte) 0x68, (byte) 0xc6, (byte) 0x00, (byte) 0xd3, (byte) 0x2f, (byte) 0xaf,
                (byte) 0x48, (byte) 0xe1, (byte) 0xcd, (byte) 0x0a, (byte) 0x6d, (byte) 0xb3, (byte) 0xfd,
                (byte) 0xb9, (byte) 0x43, (byte) 0x1f, (byte) 0xe2, (byte) 0xaa, (byte) 0x18, (byte) 0xd4,
                (byte) 0x8b, (byte) 0x04, (byte) 0x0a, (byte) 0xdd, (byte) 0xa4, (byte) 0xda, (byte) 0x1b,
                (byte) 0x89, (byte) 0xc0, (byte) 0x67, (byte) 0x9c, (byte) 0x1c, (byte) 0x98, (byte) 0xb9,
                (byte) 0x27, (byte) 0x86, (byte) 0x2f, (byte) 0x3b, (byte) 0x1f, (byte) 0xe2, (byte) 0x27,
                (byte) 0xee, (byte) 0x8c, (byte) 0x27, (byte) 0xae, (byte) 0xba, (byte) 0x9e, (byte) 0x42,
                (byte) 0x92, (byte) 0xa0, (byte) 0x88, (byte) 0xdb, (byte) 0x7b, (byte) 0xf5, (byte) 0x39,
                (byte) 0xb8, (byte) 0x78, (byte) 0x57, (byte) 0x05, (byte) 0x80, (byte) 0x1c, (byte) 0x95,
                (byte) 0xb1, (byte) 0xaa, (byte) 0xb2, (byte) 0xa5, (byte) 0xe9, (byte) 0x8e, (byte) 0x6a,
                (byte) 0x6f, (byte) 0xa5, (byte) 0x4d, (byte) 0x9b, (byte) 0x70, (byte) 0xc0, (byte) 0xb0,
                (byte) 0xc8, (byte) 0x49, (byte) 0x93, (byte) 0xa4, (byte) 0x88, (byte) 0x69, (byte) 0x70,
                (byte) 0xd7, (byte) 0xf8, (byte) 0x24, (byte) 0x36, (byte) 0x30, (byte) 0x6c, (byte) 0xab,
                (byte) 0x3a, (byte) 0x86, (byte) 0x26, (byte) 0x90, (byte) 0xd3, (byte) 0x7a, (byte) 0xfe,
                (byte) 0x7a, (byte) 0xa8, (byte) 0x77, (byte) 0xbf, (byte) 0xf3, (byte) 0xef, (byte) 0x3c,
                (byte) 0x86, (byte) 0x6a, (byte) 0xce, (byte) 0xd3, (byte) 0x4c, (byte) 0x11, (byte) 0x92,
                (byte) 0x6a, (byte) 0x49, (byte) 0x06, (byte) 0x83, (byte) 0x4e, (byte) 0x4b, (byte) 0xcf,
                (byte) 0x2f, (byte) 0xb1, (byte) 0x98, (byte) 0x13, (byte) 0x3e, (byte) 0x57, (byte) 0x3e,
                (byte) 0xd6, (byte) 0x86, (byte) 0x04, (byte) 0x61, (byte) 0xaa, (byte) 0x0a, (byte) 0x48,
                (byte) 0x74, (byte) 0x83, (byte) 0x2d, (byte) 0x51, (byte) 0x78, (byte) 0xf6, (byte) 0x4a,
                (byte) 0xe5, (byte) 0xbf, (byte) 0xe4, (byte) 0xf2, (byte) 0x14, (byte) 0x19, (byte) 0xde,
                (byte) 0xb7, (byte) 0x42, (byte) 0x77, (byte) 0xb7, (byte) 0x2c, (byte) 0x9f, (byte) 0x3f,
                (byte) 0x54, (byte) 0xcc, (byte) 0xfb, (byte) 0x56, (byte) 0xe1, (byte) 0x52, (byte) 0xe9,
                (byte) 0xdb, (byte) 0xde, (byte) 0xfd, (byte) 0xdb, (byte) 0xa2, (byte) 0xec, (byte) 0x35,
                (byte) 0x2a, (byte) 0xde, (byte) 0xbf, (byte) 0xbe, (byte) 0xe4, (byte) 0x59, (byte) 0xeb,
                (byte) 0x8a, (byte) 0xdb, (byte) 0x04, (byte) 0xf4, (byte) 0xc8, (byte) 0xdc, (byte) 0x63,
                (byte) 0x06, (byte) 0xc1, (byte) 0x0a, (byte) 0xf3, (byte) 0xb1, (byte) 0x16, (byte) 0x61,
                (byte) 0xb8, (byte) 0xee, (byte) 0x07, (byte) 0x65, (byte) 0x25, (byte) 0x1d, (byte) 0xa8,
                (byte) 0xea, (byte) 0xc2, (byte) 0xf7, (byte) 0xca, (byte) 0xb3, (byte) 0x75, (byte) 0x0b,
                (byte) 0x16, (byte) 0x90, (byte) 0xef, (byte) 0x60, (byte) 0xa0, (byte) 0x20, (byte) 0x73
        };

        /**
         * The RSA exponent of the EPS public key (on provisioning protocol level, not transport level).
         */
        private static final byte[] RSA_KEY_EXPONENT = { (byte) 0x01, (byte) 0x00, (byte) 0x01 };

        /**
         * Retrieves the provisioning URL.
         *
         * @return Provisioning URL.
         */
        public static URL getProvisioningUrl() throws MalformedURLException {
            return new URL(PROVISIONING_URL);
        }

        /**
         * Retrieves the RSA key modulus.
         *
         * @return RSA key modulus.
         */
        public static byte[] getRsaKeyModulus() {
            return RSA_KEY_MODULUS.clone();
        }

        /**
         * Retrieves the RSA key exponent.
         *
         * @return RSA key exponent.
         */
        public static byte[] getRsaKeyExponent() {
            return RSA_KEY_EXPONENT.clone();
        }

        /**
         * This method potentially allows to weaken TLS configuration for debug purposes.
         * It’s strongly discouraged to modify it in release except changing the timeout period.
         *
         * @return TLS configuration.
         */
        public static TlsConfiguration getTlsConfiguration() {

            // Default TLS configuration with 10s timeout
            return new TlsConfiguration(10000);

            // Example of weakening the TLS configuration
//            return new TlsConfiguration(TlsConfiguration.Permit.SELF_SIGNED_CERTIFICATES,
//                    TlsConfiguration.Permit.HOSTNAME_MISMATCH,
//                    TlsConfiguration.Permit.INSECURE_CONNECTIONS);
        }

        /**
         * Retrieves the RSA key ID.
         *
         * @return RSA key ID.
         */
        public static String getRsaKeyId() {
            return RSA_KEY_ID;
        }
    }


    public static class Otp {

        /**
         * Definition of OCRA algorithms used for OTP generation
         */
        private static final String OCRA_SUITE = "OCRA-1:HOTP-SHA256-8:QH64-T30S";


        /**
         * Suite will set all relevant OCRA settings accordingly.
         *
         * @return OCRA suite.
         */
        public static SecureString getOcraSuite() {
            return IdpCore.getInstance().getSecureContainerFactory().fromString(OCRA_SUITE);
        }
    }

    //region SecureLog configuration

    /**
     * Retrieve the public key's modulus for SecureLog configuration
     */
    public static final byte[] CFG_SLOG_MODULUS = new byte[]{
            (byte) 0x00, (byte) 0xd4, (byte) 0x6d, (byte) 0x5c, (byte) 0x06, (byte) 0x35, (byte) 0xb0,
            (byte) 0x52, (byte) 0x2f, (byte) 0x3e, (byte) 0xf4, (byte) 0x14, (byte) 0xd8, (byte) 0x3d,
            (byte) 0xf2, (byte) 0xd7, (byte) 0xf5, (byte) 0x1b, (byte) 0x54, (byte) 0x7e, (byte) 0x01,
            (byte) 0x0b, (byte) 0x1c, (byte) 0x23, (byte) 0x60, (byte) 0x04, (byte) 0xde, (byte) 0x4c,
            (byte) 0x67, (byte) 0x3e, (byte) 0xf8, (byte) 0x3b, (byte) 0x2b, (byte) 0xdd, (byte) 0xfa,
            (byte) 0x50, (byte) 0x87, (byte) 0xe7, (byte) 0xb3, (byte) 0x03, (byte) 0x22, (byte) 0x93,
            (byte) 0x87, (byte) 0xdd, (byte) 0xaf, (byte) 0x0a, (byte) 0xdd, (byte) 0xf9, (byte) 0xee,
            (byte) 0x8b, (byte) 0x60, (byte) 0x45, (byte) 0x1a, (byte) 0x6b, (byte) 0xf9, (byte) 0x49,
            (byte) 0xfd, (byte) 0x64, (byte) 0x0f, (byte) 0xbd, (byte) 0xe1, (byte) 0x85, (byte) 0x7e,
            (byte) 0x40, (byte) 0xe1, (byte) 0x52, (byte) 0x10, (byte) 0xec, (byte) 0xae, (byte) 0x93,
            (byte) 0xfd, (byte) 0x61, (byte) 0xb7, (byte) 0xfc, (byte) 0xdb, (byte) 0x5f, (byte) 0x60,
            (byte) 0xa0, (byte) 0xbf, (byte) 0x10, (byte) 0x94, (byte) 0x76, (byte) 0x15, (byte) 0x8c,
            (byte) 0x9b, (byte) 0x7c, (byte) 0xcd, (byte) 0xd7, (byte) 0xa7, (byte) 0xa5, (byte) 0x29,
            (byte) 0x1f, (byte) 0x31, (byte) 0x9a, (byte) 0xd0, (byte) 0x2e, (byte) 0xa2, (byte) 0x4f,
            (byte) 0x26, (byte) 0xe9, (byte) 0x14, (byte) 0x98, (byte) 0x99, (byte) 0xa6, (byte) 0x12,
            (byte) 0x1c, (byte) 0xb5, (byte) 0xac, (byte) 0x19, (byte) 0x99, (byte) 0xae, (byte) 0x23,
            (byte) 0xc8, (byte) 0x75, (byte) 0xea, (byte) 0xc0, (byte) 0xe0, (byte) 0x10, (byte) 0x31,
            (byte) 0x02, (byte) 0xf1, (byte) 0x4a, (byte) 0x97, (byte) 0xa5, (byte) 0xe2, (byte) 0xb0,
            (byte) 0xfd, (byte) 0x06, (byte) 0x70, (byte) 0xd2, (byte) 0xa5, (byte) 0x5a, (byte) 0xed,
            (byte) 0xe2, (byte) 0x9e, (byte) 0xea, (byte) 0x6f, (byte) 0x05, (byte) 0x06, (byte) 0x64,
            (byte) 0xa0, (byte) 0xf3, (byte) 0x5d, (byte) 0xba, (byte) 0x48, (byte) 0x4b, (byte) 0x18,
            (byte) 0xd1, (byte) 0x7b, (byte) 0xef, (byte) 0x48, (byte) 0x22, (byte) 0x8f, (byte) 0xdb,
            (byte) 0x5c, (byte) 0x07, (byte) 0xf0, (byte) 0x96, (byte) 0xfe, (byte) 0xfb, (byte) 0xac,
            (byte) 0xf1, (byte) 0xb0, (byte) 0x13, (byte) 0x0d, (byte) 0x3f, (byte) 0xe0, (byte) 0x8e,
            (byte) 0x81, (byte) 0xae, (byte) 0x73, (byte) 0xef, (byte) 0x5c, (byte) 0xd4, (byte) 0x11,
            (byte) 0x37, (byte) 0x85, (byte) 0x80, (byte) 0x9f, (byte) 0xdc, (byte) 0x19, (byte) 0x05,
            (byte) 0x49, (byte) 0xde, (byte) 0x34, (byte) 0xfe, (byte) 0x20, (byte) 0x54, (byte) 0x2d,
            (byte) 0xe6, (byte) 0xcc, (byte) 0x33, (byte) 0x19, (byte) 0x82, (byte) 0x0c, (byte) 0xc5,
            (byte) 0x9e, (byte) 0x42, (byte) 0xbe, (byte) 0x27, (byte) 0xf2, (byte) 0x7b, (byte) 0xaa,
            (byte) 0xfc, (byte) 0x7f, (byte) 0x11, (byte) 0x43, (byte) 0x83, (byte) 0x8c, (byte) 0xde,
            (byte) 0x71, (byte) 0xdd, (byte) 0x8b, (byte) 0xd5, (byte) 0x08, (byte) 0xb7, (byte) 0xcc,
            (byte) 0xc5, (byte) 0x0a, (byte) 0xf9, (byte) 0x91, (byte) 0xdc, (byte) 0x78, (byte) 0x68,
            (byte) 0x12, (byte) 0x64, (byte) 0x9d, (byte) 0x35, (byte) 0x89, (byte) 0x1e, (byte) 0xcc,
            (byte) 0x23, (byte) 0x7a, (byte) 0x11, (byte) 0x21, (byte) 0x77, (byte) 0x2a, (byte) 0xc4,
            (byte) 0xad, (byte) 0xc4, (byte) 0x2f, (byte) 0xcf, (byte) 0xec, (byte) 0x21, (byte) 0x50,
            (byte) 0x9e, (byte) 0x32, (byte) 0xf9, (byte) 0xa3, (byte) 0x2a, (byte) 0x27, (byte) 0x33,
            (byte) 0x27, (byte) 0x4d, (byte) 0x24, (byte) 0x78, (byte) 0x59
    };

    /**
     * Retrieve the public key's exponent for SecureLog configuration
     */
    public static final byte[] CFG_SLOG_EXPONENT = new byte[]{
            (byte) 0x01, (byte) 0x00, (byte) 0x01
    };
    //endregion
}
