package esprit.example.com.schoolingapp.activities;

import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.entities.FichePFE;
import esprit.example.com.schoolingapp.fragment.FragmentDepotRapport;
import esprit.example.com.schoolingapp.fragment.FragmentDepotVCS;
import esprit.example.com.schoolingapp.fragment.FragmentFonctionaliteProblematiqueDepot;
import esprit.example.com.schoolingapp.fragment.FragmentNotePFE;

public class DepotEnseignantActivity extends AppCompatActivity {
    FichePFE fichePFE;
    ViewPager viewPager;
    PagerTabStrip page_indicator;

    public FichePFE getFichePFE() {
        return fichePFE;
    }

    public void setFichePFE(FichePFE fichePFE) {
        this.fichePFE = fichePFE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depot_enseignant);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("fiche"))
            this.fichePFE = bundle.getParcelable("fiche");

        if (fichePFE != null) {
            PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
            viewPager = findViewById(R.id.viewPager);
            viewPager.setAdapter(pagerAdapter);
        }
        page_indicator = findViewById(R.id.page_indicator);
        page_indicator.setDrawFullUnderline(true);
        page_indicator.setTabIndicatorColor(Color.WHITE);
        page_indicator.setTextColor(Color.WHITE);
        getSupportActionBar().hide();
    }


    private class PagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            fragments.add(new FragmentDepotRapport());
            fragments.add(new FragmentDepotVCS());
            FragmentFonctionaliteProblematiqueDepot problematiqueDepot = new FragmentFonctionaliteProblematiqueDepot();
            FragmentFonctionaliteProblematiqueDepot fonctionaliteProblematiqueDepot = new FragmentFonctionaliteProblematiqueDepot();
            problematiqueDepot.setProblematiques(fichePFE.getProblematiques());
            fonctionaliteProblematiqueDepot.setFonctionalites(fichePFE.getFunctions());
            FragmentNotePFE fragmentNotePFE = new FragmentNotePFE();
            fragmentNotePFE.setFichePFE(fichePFE);
            fragments.add(fonctionaliteProblematiqueDepot);
            fragments.add(problematiqueDepot);
            fragments.add(fragmentNotePFE);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Rapport";
                case 1:
                    return "Liens VCS";
                case 2:
                    return "Fonctionalités";
                case 3:
                    return "Problematiques";
                case 4:
                    return "Grille D'évaluation";

            }
            return null;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }


        @Override
        public int getCount() {
            return fragments.size();
        }

    }
}