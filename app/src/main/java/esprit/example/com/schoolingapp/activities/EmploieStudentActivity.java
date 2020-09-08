package esprit.example.com.schoolingapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.entities.ActivityDay;
import esprit.example.com.schoolingapp.entities.Cours;
import esprit.example.com.schoolingapp.entities.Days;
import esprit.example.com.schoolingapp.entities.Enseignant;
import esprit.example.com.schoolingapp.fragment.FragmentAncientCredit;
import esprit.example.com.schoolingapp.fragment.FragmentExamens;
import esprit.example.com.schoolingapp.fragment.FragmentResultat;
import esprit.example.com.schoolingapp.fragment.SingleDayFragment;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.List;

public class EmploieStudentActivity extends AppCompatActivity {

    ViewPager viewPager;
    PagerTabStrip tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emploie_student);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.page_indicator);
        populateEmploie();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.emploie_download_menu, menu);
        getSupportActionBar().setTitle("Emploie du temp");
        return true;
    }

    private void populateEmploie() {
        ArrayList<ActivityDay> activityDays = new ArrayList<>();
        activityDays.add(new ActivityDay(1, "5SIM2", Days.Lundi, new ArrayList<String>() {{
            add("Java");
            add("Java");
            add("Android");
            add("Android");
        }}));
        activityDays.add(new ActivityDay(2, "5SIM2", Days.Mardi, new ArrayList<String>() {{
            add("Theorie des langages");
            add("Theorie des langages");
            add("Anglais");
            add("Anglais");
        }}));
        activityDays.add(new ActivityDay(3, "5SIM2", Days.Mercredi, new ArrayList<String>() {{
            add("Java");
            add("Java");
            add("Android");
            add("Android");
        }}));
        activityDays.add(new ActivityDay(4, "5SIM2", Days.Jeudi, new ArrayList<String>() {{
            add("Mathematic pour l'ingenieur 1");
            add("Mathematic pour l'ingenieur 1");
            add("Français");
            add("Français");
        }}));
        activityDays.add(new ActivityDay(5, "5SIM2", Days.Vendredi, new ArrayList<String>() {{
            add("Complexité");
            add("Theroie des graphes");
            add("System expert");
            add("System expert");
        }}));
        activityDays.add(new ActivityDay(6, "5SIM2", Days.Samedi, new ArrayList<String>() {{
            add("Journée porte ouverte");
            add("Journée porte ouverte");
            add("");
            add("");

        }}));
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (ActivityDay act : activityDays) {
            SingleDayFragment singleDayFragment = new SingleDayFragment();
            Bundle bundle = new Bundle();
            bundle.putString(SingleDayFragment.GROUPE, act.getGroupe());
            bundle.putString(SingleDayFragment.COURS_1, act.getCours().get(0));
            bundle.putString(SingleDayFragment.COURS_2, act.getCours().get(1));
            bundle.putString(SingleDayFragment.COURS_3, act.getCours().get(2));
            bundle.putString(SingleDayFragment.COURS_4, act.getCours().get(3));
            singleDayFragment.setArguments(bundle);
            fragments.add(singleDayFragment);
        }
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), fragments));

    }
 // public Cours(int id, Enseignant enseignant, List<Groupe> groupes, String date, String horraire, String classroom) {

    private class PagerAdapter extends FragmentPagerAdapter {

        List<Fragment> fragments;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public PagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragments = fragmentList;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Lundi";
                case 1:
                    return "Mardi";
                case 2:
                    return "Mercredi";
                case 3:
                    return "Jeudi";
                case 4:
                    return "Vendredi";
                case 5:
                    return "Samedi";
            }
            return null;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return 6;
        }
    }
}