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

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gemalto.idp.mobile.authentication.mode.pin.PinAuthInput;
import com.gemalto.idp.mobile.core.IdpException;
import com.gemalto.idp.mobile.otp.oath.OathToken;
import com.gemalto.idp.mobile.otp.oath.soft.SoftOathToken;
import com.gemalto.mobileprotector.gettingstarted.R;
import com.gemalto.mobileprotector.gettingstarted.util.token.TokenAwareView;
import com.gemalto.mobileprotector.gettingstarted.util.token.TokenUtils;

import java.util.Locale;

/**
 * Provisioning fragment.
 */
public class OtpFragment extends Fragment implements TokenAwareView {

    private Button mBtnGenerate;
    private TextView mOtpTextView;
    private TextView mOtpLifeSpanView;

    private CountDownTimer mLifeSpanCountDown;


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_otp, container, false);

        mOtpTextView = view.findViewById(R.id.tv_otp_id);
        mOtpLifeSpanView = view.findViewById(R.id.tv_otp_lifespan);

        mBtnGenerate = view.findViewById(R.id.btn_generate_otp);
        mBtnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() == null) return;

                OtpLogic.getUserPin((AppCompatActivity) getActivity(), new OtpPinCallback() {
                    @Override
                    public void onPinSuccess(@NonNull PinAuthInput pin) {
                        OtpResult otpResult = getOtp(pin);

                        if (otpResult.isValid()) {
                            mOtpTextView.setText(otpResult.getOtp());

                            mLifeSpanCountDown = new CountDownTimer(otpResult.getLifeSpan() * 1000, 1000) {
                                @Override
                                public void onTick(long millisRemaining) {
                                    String lifeSpanText = String.format(Locale.getDefault(),
                                            getString(R.string.otp_validity_placeholder),
                                            millisRemaining / 1000 + 1);

                                    mOtpLifeSpanView.setText(lifeSpanText);
                                }

                                @Override
                                public void onFinish() {
                                    mOtpLifeSpanView.setText(R.string.otp_expired);
                                }
                            }.start();
                        } else {
                            mOtpTextView.setText("");
                            mOtpLifeSpanView.setText("");
                        }
                    }

                    @Override
                    public void onPinError(@NonNull String errorMessage) {
                        Toast.makeText(getActivity(), "Error getting user PIN: \n" + errorMessage, Toast.LENGTH_LONG)
                                .show();

                        mOtpTextView.setText("");
                        mOtpLifeSpanView.setText("");
                    }
                });
            }
        });

        return view;
    }


    /**
     * Generates an OTP.
     *
     * @param pin User authentication.
     * @return Generated OTP and its lifespan wrapped in OtpResult class.
     */
    private OtpResult getOtp(@NonNull PinAuthInput pin) {

        try {
            SoftOathToken token = TokenUtils.getFirstToken();
            if (token == null)
                throw new IllegalStateException("This should not be reached as the UI should have been disabled when token is not available.");

            return OtpLogic.generateOtp(token, pin);

        } catch (IdpException exception) {
            String errorMessage = String.format(Locale.getDefault(),
                    getString(R.string.sdk_error_placeholder),
                    exception.getDomain(),
                    exception.getCode(),
                    exception.getMessage());

            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();

            return new OtpResult(null, OtpResult.OTP_GENERATION_FAILED);
        }
    }

    @Override
    public void updateView(OathToken token) {

        if (token == null) {
            mBtnGenerate.setEnabled(false);

            if (mLifeSpanCountDown != null) {
                mLifeSpanCountDown.cancel();
                mLifeSpanCountDown = null;
            }

            mOtpTextView.setText("");
            mOtpLifeSpanView.setText("");
        } else {
            mBtnGenerate.setEnabled(true);
        }
    }
}
