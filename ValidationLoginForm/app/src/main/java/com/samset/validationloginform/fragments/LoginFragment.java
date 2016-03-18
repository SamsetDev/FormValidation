package com.samset.validationloginform.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.samset.validationloginform.R;
import com.samset.validationloginform.listener.OnCustomclickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private View view;
    Button login;
    private TextView dontHave;
    private TextInputLayout ti_email,ti_password;
    private EditText et_email,et_password;

    private OnCustomclickListener listener;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_login, container, false);
        dontHave= (TextView) view.findViewById(R.id.dontaccount);
        ti_email= (TextInputLayout) view.findViewById(R.id.text_input_email);
        ti_password= (TextInputLayout) view.findViewById(R.id.text_input_password);

        et_email= (EditText) view.findViewById(R.id.input_email);
        et_password= (EditText) view.findViewById(R.id.input_password);

        login= (Button) view.findViewById(R.id.btn_login);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        et_email.addTextChangedListener(new MyTextWatcher(et_email));
        et_password.addTextChangedListener(new MyTextWatcher(et_password));

        dontHave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             listener.OnClick("registration");
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener= (OnCustomclickListener) activity;
            //mOnFragment1ClickedListener = (OnFragment1ClickedListener) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    private void Login() {

        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        Toast.makeText(getActivity(), "sucessfully login!", Toast.LENGTH_SHORT).show();
    }
    private boolean validateEmail() {
        String email = et_email.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            ti_email.setError(getString(R.string.err_email));
            requestFocus(et_email);
            return false;
        } else {
            ti_email.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (et_password.getText().toString().trim().isEmpty()) {
            ti_password.setError(getString(R.string.err_password));
            requestFocus(et_password);
            return false;
        } else {
            ti_password.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i0, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i0, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }
}
