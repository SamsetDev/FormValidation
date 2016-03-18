package com.samset.validationloginform;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.samset.validationloginform.fragments.LoginFragment;
import com.samset.validationloginform.fragments.RegistratinFragment;
import com.samset.validationloginform.listener.OnCustomclickListener;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements OnCustomclickListener{

    Stack<String> stringStack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stringStack=new Stack<>();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = new LoginFragment();

        String tag = fragment.toString();
        stringStack.add(tag);
        transaction.add(R.id.FragContainer, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();

    }

    @Override
    public void OnClick(String tag)
    {
        if (tag.equalsIgnoreCase("login"))
        {
            LoginFragment loginFragment=new LoginFragment();
            beginTransction(loginFragment);
        }else if (tag.equalsIgnoreCase("registration"))
        {
            RegistratinFragment fragment=new RegistratinFragment();
            beginTransction(fragment);
        }
    }

    private void beginTransction(Fragment fragment)
    {
        Bundle args = new Bundle();
        String tag = fragment.toString();
        fragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(stringStack.peek());
        transaction.hide(currentFragment);
        transaction.add(R.id.FragContainer, fragment, fragment.toString());
        // This is important, we must addToBackStack so we can pull it out later.
        transaction.addToBackStack(tag);

        stringStack.add(tag);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        // from the stack we can get the latest fragment
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(stringStack.peek());
        // If its an instance of Fragment1 I don't want to finish my activity, so I launch a Toast instead.
        if (fragment instanceof LoginFragment){
            Toast.makeText(getApplicationContext(), "YOU ARE AT THE TOP FRAGMENT", Toast.LENGTH_SHORT).show();
        }
        else{
            // Remove the framg
            removeFragment();
            super.onBackPressed();
        }
    }

    private void removeFragment(){
        // remove the current fragment from the stack.
        stringStack.pop();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(stringStack.peek());

        transaction.show(fragment);
        transaction.commit();
    }


}
