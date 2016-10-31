package com.casey.hbweather.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> fragments;
	private List<String> titles;
	FragmentTransaction transaction;

	public SectionsPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
		transaction = fm.beginTransaction();
	}

	@Override
	public Fragment getItem(int position) {
		// Bundle b = new Bundle();
		// b.putInt("no", position + 1);
		// fragments.get(position).setArguments(b);
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titles.get(position);
	}

	public void setTitle(String title) {
		titles.set(1, title);
	}
}
