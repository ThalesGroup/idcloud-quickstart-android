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


/**
 * Wrapper class for OTP generation result that includes also the OTP lifespan
 */
class OtpResult {

    /**
     * Flag indicating that the result is actually invalid
     */
    static final int OTP_GENERATION_FAILED = -1;

    private final String otp;
    private final int lifeSpan;

    /**
     * Constructor setting both OTP and lifespan
     *
     * @param otp      OTP value
     * @param lifeSpan OTP lifespan in seconds
     */
    OtpResult(String otp, int lifeSpan) {
        this.otp = otp;
        this.lifeSpan = lifeSpan;
    }

    /**
     * Provides OTP value
     *
     * @return String value of the OTP
     */
    String getOtp() {
        return otp;
    }


    /**
     * Provides OTP lifespan
     *
     * @return OTP lifespan in seconds
     */
    int getLifeSpan() {
        return lifeSpan;
    }

    /**
     * Provides indication if the result is actually valid
     *
     * @return True if the OTP is valid or False otherwise
     */
    boolean isValid() {
        return (lifeSpan != OTP_GENERATION_FAILED && otp != null);
    }

}
