package com.example.a1.projecttest.zavedushaia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.example.a1.projecttest.MainActivity;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.adapters.NewPagerFragmentAdapter;
import com.example.a1.projecttest.fragments.ShcolnilFragment;
import com.example.a1.projecttest.fragments.VospitannikFragment;
import com.example.a1.projecttest.utils.ContentFactory;
import com.example.a1.projecttest.vospitatel.fragments.RaspisanieFragment;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;


@EFragment
public class DirectoryFragment extends Fragment implements ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener{
    ViewPager viewPager;
    TabHost tabHost;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.directory_layout, container, false);
        initViewPager(view);
        initTab(view);
        return view;
    }

    public void initViewPager(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        NewPagerFragmentAdapter pagerFragmentAdapter;
        viewPager.setOffscreenPageLimit(4);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ServicesFragment());
        fragments.add(new RaspisanieFragment());
        fragments.add(new ShcolnilFragment());
        fragments.add(new VospitannikFragment());
        FragmentManager fragmentManager;
        MainZavDetSad mainZavDetSad = new MainZavDetSad();
        fragmentManager = mainZavDetSad.getFragmentMan();
        pagerFragmentAdapter = new NewPagerFragmentAdapter(getFragmentManager(), fragments);
       // pagerFragmentAdapter = new NewPagerFragmentAdapter(getFragmentManager(), fragments);
        viewPager.setAdapter(pagerFragmentAdapter);

        viewPager.setOnPageChangeListener(DirectoryFragment.this);
    }


    private void initTab(View view) {

        tabHost = (TabHost) view.findViewById(R.id.tabHost);
        tabHost.setup();

        String[] tabName = {getString(R.string.group_tab_header),
                getString(R.string.tutor_tab_header),
                getString(R.string.services_tab_header),
                getString(R.string.calendar_tab_header)};

        for (int i = 0; i < tabName.length; i++) {
            TabHost.TabSpec tabSpec;
            tabSpec = tabHost.newTabSpec(tabName[i]);
            tabSpec.setIndicator(tabName[i]);
            tabSpec.setContent(new ContentFactory(getActivity()));
            tabHost.addTab(tabSpec);
        }
        tabHost.setOnTabChangedListener(DirectoryFragment.this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String s) {
        int selectedItem = tabHost.getCurrentTab();
        viewPager.setCurrentItem(selectedItem);

    }
}
