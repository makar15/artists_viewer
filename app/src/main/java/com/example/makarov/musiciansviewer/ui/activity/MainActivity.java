package com.example.makarov.musiciansviewer.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.makarov.musiciansviewer.R;
import com.example.makarov.musiciansviewer.ui.fragments.ArtistDetailFragment;
import com.example.makarov.musiciansviewer.ui.fragments.ArtistsFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String ADD_FRAGMENT_TO_BACK_STACK_KEY = "key";
    public static final String ARTIST_ID_KEY = "artist_id";

    @Bind(R.id.toolbar_actionbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this, this);
        setSupportActionBar(mToolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new ArtistsFragment()).commit();
    }

    public void openArtistDetailFragment(int artistId) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARTIST_ID_KEY, artistId);
        Fragment newFragment = new ArtistDetailFragment();
        newFragment.setArguments(bundle);
        openFragment(newFragment, true);
    }

    private void openFragment(Fragment fragment, boolean saveInBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        if (saveInBackStack) {
            transaction.addToBackStack(ADD_FRAGMENT_TO_BACK_STACK_KEY);
        }
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
