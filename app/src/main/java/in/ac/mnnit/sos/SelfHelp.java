package in.ac.mnnit.sos;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

public class SelfHelp extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_help);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_self_help, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_self_help, container, false);
            ImageView selfHelpImage = (ImageView) rootView.findViewById(R.id.self_help_image);
            TextView selfHelpTitle = (TextView) rootView.findViewById(R.id.self_help_title);
            TextView selfHelpDescription = (TextView) rootView.findViewById(R.id.self_help_description);

            int selfHelpNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            switch (selfHelpNumber){
                case 1:
                    selfHelpImage.setImageResource(R.drawable.sh_hit_the_eyes);
                    selfHelpTitle.setText("Hit In The Eyes");
                    selfHelpDescription.setText("Use open palms, both if possible, with fingers directed towards the eyes. Using the same position of your palms (and at the same time) you can hit or strongly push away the nose or chin with the heel of your hand.");
                    break;
                case 2:
                    selfHelpImage.setImageResource(R.drawable.sh_hit_the_groin);
                    selfHelpTitle.setText("Hit In The Groin");
                    selfHelpDescription.setText("Use your intuition and do what feels right in the quickest possible way. The hit should be sharp, straight forward and as strong as possible.");
                    break;
                case 3:
                    selfHelpImage.setImageResource(R.drawable.sh_hit_the_nose);
                    selfHelpTitle.setText("Distract The Airways: Hit In The Nose");
                    selfHelpDescription.setText("Use a hammer strike motion by using the heel of your hand while your clinching your fist.");
                    break;
                case 4:
                    selfHelpImage.setImageResource(R.drawable.sh_hit_the_throat);
                    selfHelpTitle.setText("Hit In The Throat");
                    selfHelpDescription.setText("With an opened palm, use your hand and push your fingers inside the throat. Do this while pushing your attacker away.");
                    break;
                case 5:
                    selfHelpImage.setImageResource(R.drawable.sh_hit_the_neck);
                    selfHelpTitle.setText("Hit In The Neck");
                    selfHelpDescription.setText("Use an open hand strike to the neck using the same technique as a tennis shot. The ONLY difference is, instead of the front of the hand moving towards the neck, you will be leading with the pinky side of your hand.");
                    break;
                case 6:
                    selfHelpImage.setImageResource(R.drawable.sh_attack_the_knees);
                    selfHelpTitle.setText("Attack The Knees");
                    selfHelpDescription.setText("Use your foot with a horizontal stomping kick directly to the knee.");
                    break;
            }
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
