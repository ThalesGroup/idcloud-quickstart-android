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

package com.gemalto.mobileprotector.gettingstarted.otp;

import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

import com.gemalto.idp.mobile.authentication.AuthInput;
import com.gemalto.idp.mobile.authentication.mode.pin.PinAuthInput;
import com.gemalto.idp.mobile.core.IdpCore;
import com.gemalto.idp.mobile.core.IdpException;
import com.gemalto.idp.mobile.core.root.RootDetector;
import com.gemalto.idp.mobile.core.util.SecureString;
import com.gemalto.idp.mobile.otp.OtpModule;
import com.gemalto.idp.mobile.otp.oath.OathDevice;
import com.gemalto.idp.mobile.otp.oath.OathFactory;
import com.gemalto.idp.mobile.otp.oath.OathService;
import com.gemalto.idp.mobile.otp.oath.soft.SoftOathSettings;
import com.gemalto.idp.mobile.otp.oath.soft.SoftOathToken;
import com.gemalto.idp.mobile.ui.UiModule;
import com.gemalto.idp.mobile.ui.secureinput.SecureInputBuilderV2;
import com.gemalto.idp.mobile.ui.secureinput.SecureInputService;
import com.gemalto.idp.mobile.ui.secureinput.SecureInputUi;
import com.gemalto.idp.mobile.ui.secureinput.SecurePinpadListenerV2;

import com.gemalto.mobileprotector.sdk.ProtectorConfig;

/**
 * Mobile Protector SDK logic for OTP generation.
 */
class OtpLogic {

    private static DialogFragment sDialogFragment;

    /**
     * Generates an OTP.
     *
     * @param token
     *         Token to be used for OTP generation.
     * @param pin
     *         PIN.
     * @return Generated OTP and its lifespan wrapped in OtpResult class.
     * @throws IdpException
     *         If error during OTP generation occures.
     */
    static OtpResult generateOtp(@NonNull final SoftOathToken token, @NonNull final AuthInput pin)
            throws IdpException {

        // TODO: Get an instance of OathFactory from OathService

        // TODO: Create a SoftOathSettings by means of the factory

        // TODO: Set OCRA suite from the Otp configuration stored in ProtectorConfig in first lesson

        // TODO: Create a SoftOathDevice for the given token and SoftOathSettings settings

        // TODO: Use the OathDevice and PIN to generate time based OTP

        // TODO: Read the lifespan of the OTP from the OathDevice

        // TODO: Wrap the OTP and lifespan in a new instance of OtpResult

        // TODO: Wipe the OTP generated

        // TODO: Wipe the PIN input

        // TODO: Return the OtpResult created

        // TODO: remove this when you complete the implementation
        throw new IdpException(0, 0, "Implementation missing!");
    }

    /**
     * Retrieves the PIN.
     *
     * @param activity
     *         Activity on which to show the {@code DialogFragment}.
     * @param callback
     *         Callback to receive the PIN.
     */
    static void getUserPin(@NonNull final AppCompatActivity activity, @NonNull final OtpPinCallback callback) {

        // TODO: create a SecureInputBuilderV2 by means of SecureInputService

        // TODO: use buildPinpad() method to prepare an instance of SecureInputUi
        // TODO: provide a new instance of SecurePinpadListenerV2 and override its methods

            // TODO: in onFinish() method:
                // TODO: propagate PinAuthInput to onPinSuccess() method of the callback
                // TODO: wipe the instance of the builder
                // TODO: call dismiss() method for the sDialogFragment

            // TODO: in onError() method:
                // TODO: propagate the error message to the onPinError() method of the callback
                // TODO: wipe the instance of the builder
                // TODO: call dismiss() method for the sDialogFragment


        // TODO: use the instance of SecureInputUi to get and store dialogFragment to sDialogFragment

        // TODO: show the dialog fragment


        // TODO: remove this when you complete the implementation
        callback.onPinError("Implementation is missing!");
    }


}
