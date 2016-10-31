package com.casey.hbweather.base;

import org.kymjs.aframe.widget.TransparentDialog;

import com.casey.hbweather.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class MyBaseFragment extends Fragment {

	public TransparentDialog transparentDialog;
	private boolean mHasLoadedOnce = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setUserVisibleHint(true);
		super.onCreate(savedInstanceState);

		transparentDialog = new TransparentDialog(getActivity(), R.style.TransparentDialog, R.drawable.transparentdialog2);
	}

	public void dialogShow() {
		transparentDialog.show();
	}

	public void dialogDismiss() {
		if (transparentDialog.isShowing()) {
			transparentDialog.dismiss();
		}
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (this.isVisible()) {
			if (isVisibleToUser && !mHasLoadedOnce) {
				mHasLoadedOnce = true;
			}
		}
		super.setUserVisibleHint(isVisibleToUser);
	}
}
