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
public class RegistratinFragment extends Fragment implements View.OnClickListener{

    private View view;
    private Button signup;
    private TextView haveaccount;
    private OnCustomclickListener listener;
    private TextInputLayout ti_fname,ti_lname,ti_email,ti_password,ti_cpassword;
    private EditText et_fname,et_lname,et_email,et_password,et_cpassword;
    public RegistratinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view=inflater.inflate(R.layout.fragment_registratin, container, false);
            setView(view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        et_fname.addTextChangedListener(new MyTextWatcher(et_fname));
        et_lname.addTextChangedListener(new MyTextWatcher(et_lname));
        et_email.addTextChangedListener(new MyTextWatcher(et_email));
        et_password.addTextChangedListener(new MyTextWatcher(et_password));
        et_cpassword.addTextChangedListener(new MyTextWatcher(et_cpassword));
        haveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnClick("login");
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
    private void setView(View view)
    {
        haveaccount= (TextView) view.findViewById(R.id.hvaccount);
        ti_fname= (TextInputLayout) view.findViewById(R.id.text_input_fname);
        ti_lname= (TextInputLayout) view.findViewById(R.id.text_input_lname);
        ti_email= (TextInputLayout) view.findViewById(R.id.text_input_email);
        ti_password= (TextInputLayout) view.findViewById(R.id.text_input_password);
        ti_cpassword= (TextInputLayout) view.findViewById(R.id.text_input_cpassword);

        et_fname= (EditText) view.findViewById(R.id.input_fname);
        et_lname= (EditText) view.findViewById(R.id.input_lname);
        et_email= (EditText) view.findViewById(R.id.input_email);
        et_password= (EditText) view.findViewById(R.id.input_password);
        et_cpassword= (EditText) view.findViewById(R.id.input_cpassword);

        signup= (Button) view.findViewById(R.id.btn_signup);
        signup.setOnClickListener(this);
    }

    private boolean validateFirstName() {
        if (et_fname.getText().toString().trim().isEmpty()) {
            ti_fname.setError(getString(R.string.err_fname));
            requestFocus(et_fname);
            return false;
        } else {
            ti_fname.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateLastName() {
        if (et_lname.getText().toString().trim().isEmpty()) {
            ti_lname.setError(getString(R.string.err_lname));
            requestFocus(et_lname);
            return false;
        } else {
            ti_lname.setErrorEnabled(false);
        }

        return true;
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
    private boolean validateConfirmPassword() {
        if (et_cpassword.getText().toString().trim().isEmpty())
        {
            ti_cpassword.setError(getString(R.string.err_cpassword));
            requestFocus(et_cpassword);
            return false;
        } else {
            if (et_password.getText().toString().trim().equalsIgnoreCase(et_cpassword.getText().toString().trim()))
            {
                ti_cpassword.setErrorEnabled(false);
            }else
            {
                ti_cpassword.setError(getString(R.string.err_cmatchpassword));
                requestFocus(et_cpassword);
                return false;
            }

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

    @Override
    public void onClick(View v) {
        if (!validateFirstName()) {
            return;
        }
        if (!validateLastName()) {
            return;
        }
        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }
        if (!validateConfirmPassword()) {
            return;
        }

        Toast.makeText(getActivity(), "Sucessfully register!", Toast.LENGTH_SHORT).show();
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
                case R.id.input_fname:
                    validateFirstName();
                    break;
                case R.id.input_lname:
                    validateLastName();
                    break;
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
                case R.id.input_cpassword:
                    validateConfirmPassword();
                    break;
            }
        }
    }

}
