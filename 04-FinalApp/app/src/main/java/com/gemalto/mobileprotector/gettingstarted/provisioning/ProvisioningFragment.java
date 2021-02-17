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

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gemalto.idp.mobile.core.IdpCore;
import com.gemalto.idp.mobile.core.util.SecureString;
import com.gemalto.idp.mobile.otp.oath.OathToken;
import com.gemalto.mobileprotector.gettingstarted.R;
import com.gemalto.mobileprotector.gettingstarted.util.token.TokenAwareView;

/**
 * Provisioning fragment.
 */
public class ProvisioningFragment extends Fragment implements TokenAwareView {
    private ProvisioningFragmentDelegate mDelegate;

    private EditText mUserId;
    private EditText mRegCode;

    private Button mProvision;
    private Button mRemoveToken;

    public void setDelegate(final ProvisioningFragmentDelegate delegate) {
        mDelegate = delegate;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_provisioning, container, false);

        mUserId = view.findViewById(R.id.et_user_id);
        mRegCode = view.findViewById(R.id.et_reg_code);

        mProvision = view.findViewById(R.id.btn_provision);
        mProvision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userId = mUserId.getText().toString();
                String regCode = mRegCode.getText().toString();

                if (userId.isEmpty() || regCode.isEmpty()) {
                    Toast.makeText(getActivity(), "User ID and Registration Code cannot be empty.", Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                SecureString regCodeSecureString = IdpCore.getInstance().getSecureContainerFactory()
                        .fromString(regCode);

                if (mDelegate != null) mDelegate.onProvision(userId, regCodeSecureString);
            }
        });

        mRemoveToken = view.findViewById(R.id.btn_remove_token);
        mRemoveToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getContext() == null) return;

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure to remove the provisioned token?");
                builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        if (mDelegate != null)
                            mDelegate.onRemoveToken(mUserId.getText().toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        return view;
    }

    @Override
    public void updateView(OathToken token) {

        boolean isTokenProvisioned = token != null;

        if (isTokenProvisioned) {
            mUserId.setText(token.getName());
            mRegCode.setText("------");
        } else {
            mUserId.setText("");
            mRegCode.setText("");
        }

        mUserId.setEnabled(!isTokenProvisioned);
        mRegCode.setEnabled(!isTokenProvisioned);

        mProvision.setEnabled(!isTokenProvisioned);
        mRemoveToken.setEnabled(isTokenProvisioned);
    }
}
