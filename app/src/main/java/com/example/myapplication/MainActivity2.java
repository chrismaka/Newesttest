package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplication.navigationDrawer.ExpandableListAdapter;
import com.example.myapplication.navigationDrawer.MenuModel;
import com.example.myapplication.slider.SliderAdapter;
import com.example.myapplication.slider.SliderData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // creating variables for our adapter, array list,
    // firebase firestore and our sliderview.
    private SliderAdapter adapter;
    private ArrayList<SliderData> sliderDataArrayList;
    FirebaseFirestore db;
    private SliderView sliderView;

    //navigationview variables
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // creating a new array list fr our array list.
        sliderDataArrayList = new ArrayList<>();

        // initializing our slider view and
        // firebase firestore instance.
        sliderView = findViewById(R.id.slider);
        db = FirebaseFirestore.getInstance();

        // calling our method to load images.
        loadImages();
        //navigation view code
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    private void loadImages() {
        // getting data from our collection and after
        // that calling a method for on success listener.
        db.collection("Slider").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                // inside the on success method we are running a for loop
                // and we are getting the data from Firebase Firestore
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                    // after we get the data we are passing inside our object class.
                    SliderData sliderData = documentSnapshot.toObject(SliderData.class);
                    SliderData model = new SliderData();

                    // below line is use for setting our
                    // image url for our modal class.
                    model.setImgUrl(sliderData.getImgUrl());

                    // after that we are adding that
                    // data inside our array list.
                    sliderDataArrayList.add(model);

                    // after adding data to our array list we are passing
                    // that array list inside our adapter class.
                    adapter = new SliderAdapter(MainActivity2.this, sliderDataArrayList);

                    // belows line is for setting adapter
                    // to our slider view
                    sliderView.setSliderAdapter(adapter);

                    // below line is for setting animation to our slider.
                    sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);

                    // below line is for setting auto cycle duration.
                    sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);

                    // below line is for setting
                    // scroll time animation
                    sliderView.setScrollTimeInSec(3);

                    // below line is for setting auto
                    // cycle animation to our slider
                    sliderView.setAutoCycle(true);

                    // below line is use to start
                    // the animation of our slider view.
                    sliderView.startAutoCycle();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // if we get any error from Firebase we are
                // displaying a toast message for failure
                Toast.makeText(MainActivity2.this, "Fail to load slider data..", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //more navigation view code
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void prepareMenuData() {

        MenuModel menuModel = new MenuModel("Dioceses", true, true); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();

        MenuModel childModel = new MenuModel("ANKOLE", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("BUKEDI", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("BUNYORO-KITARA", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("BUSOGA", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("CENTRAL BUGANDA", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("CENTRAL BUSOGA", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("EAST RWENZORI", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("KAMPALA", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("KIGEZI", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("KINKIIZI", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("LANGO", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("MUHABURA", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("RUWENZORI", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("KARAMOJA", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("KITGUM", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("KUMI", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("KUMI", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("LUWEERO", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("MADI and WEST NILE", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("MASINDI KITARA", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("MBALE", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("MITYANA", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("MUKONO", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("NAMIREMBE", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("NEBBI", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("NORTH ANKOLE", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("NORTH KARAMOJA", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("NORTH KIGEZI", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("NORTH MBALE", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("NORTHERN UGANDA", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("NORTH WEST ANKOLE", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("SEBEI", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("SOROTI", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("SOUTH ANKOLE", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("SOUTH RWENZORI", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("WEST ANKOLE", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("WEST BUGANDA", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("WEST LANGO", false, false);
        childModelsList.add(childModel);






        if (menuModel.hasChildren) {
            Log.d("API123","here");
            childList.put(menuModel, childModelsList);
        }
        menuModel = new MenuModel("Others", true, true); //Menu of Java Tutorials
        headerList.add(menuModel);
        childModelsList = new ArrayList<>();
        childModel = new MenuModel("Church History", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("About Us", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("Our Events", false, false);
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {
            Log.d("API123","here");
            childList.put(menuModel, childModelsList);
        }


    }

    private void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);




    }
}
