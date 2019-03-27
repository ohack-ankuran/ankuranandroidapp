package com.ankuran.ui.fragment;

import android.app.ProgressDialog;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {



    public void showDatePicker(DatePickerFragment.DatePickerDialogListener listener) {
        DialogFragment newFragment = new DatePickerFragment();
        ((DatePickerFragment) newFragment).setListener(listener);
        newFragment.show(getActivity().getSupportFragmentManager(), "date picker");
    }
}
