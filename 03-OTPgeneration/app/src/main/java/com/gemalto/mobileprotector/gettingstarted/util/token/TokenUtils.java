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

package com.gemalto.mobileprotector.gettingstarted.util.token;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gemalto.idp.mobile.core.IdpException;
import com.gemalto.idp.mobile.core.IdpStorageException;
import com.gemalto.idp.mobile.otp.OtpModule;
import com.gemalto.idp.mobile.otp.oath.OathService;
import com.gemalto.idp.mobile.otp.oath.OathTokenManager;
import com.gemalto.idp.mobile.otp.oath.soft.SoftOathToken;

import java.util.Set;

/**
 * Provides convenience methods to manage tokens
 */
public class TokenUtils {

    /**
     * Retrieves an already provisioned token by its name.
     *
     * @param name Token name.
     * @return Token.
     * @throws IdpException If error occurred.
     */
    @Nullable
    public static SoftOathToken getToken(@NonNull String name) throws IdpException {
        OathTokenManager oathTokenManager = OathService.create(OtpModule.create()).getTokenManager();
        return oathTokenManager.getToken(name);
    }

    /**
     * Removes the provisioned token by its name.
     *
     * @param name Token name.
     * @return True in case of success, False in case of failure
     * @throws IdpException If error occurred.
     */
    public static boolean removeToken(@NonNull String name) throws IdpException {
        OathTokenManager oathTokenManager = OathService.create(OtpModule.create()).getTokenManager();
        return oathTokenManager.removeToken(name);
    }

    /**
     * Retrieves names of all provisioned tokens.
     *
     * @return {@code Set} of token names.
     */
    @Nullable
    public static Set<String> getTokenNames() {
        OathTokenManager oathTokenManager = OathService.create(OtpModule.create()).getTokenManager();
        try {
            return oathTokenManager.getTokenNames();
        } catch (IdpStorageException e) {
            return null;
        }
    }


    /**
     * Retrieves first available token if any
     *
     * @return A provisioned {@code SoftOathToken} token or null if none found
     * @throws IdpException Any exception
     */
    public static SoftOathToken getFirstToken() throws IdpException {
        Set<String> tokenNames = getTokenNames();

        if (tokenNames == null || tokenNames.isEmpty())
            return null;

        String firstTokenName = tokenNames.iterator().next();
        return getToken(firstTokenName);
    }
}
