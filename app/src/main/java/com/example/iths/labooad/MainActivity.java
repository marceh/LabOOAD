package com.example.iths.labooad;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CallbackExecutor{

    private static final String TAG = "MainActivity";

    private ListView listView;
    private EditText editTextCityName;
    private EditText editTextCityHabitants;
    private Button buttonAdd;
    private ItemManager itemManager;
    private CitiesListViewAdapter citiesListViewAdapter;
    private AdapterView.OnItemClickListener onItemClicklistener;
    private SQLiteHelper sqlHelper;
    private Activity self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instantiate();
        checkInternetThenLoadList();

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
        sqlHelper.saveCitiesToDbFromItemManager();
    }

    private void instantiate() {
        self = this;
        listView = (ListView) findViewById(R.id.listViewCities);
        editTextCityName = (EditText) findViewById(R.id.editTextCityName);
        editTextCityHabitants = (EditText) findViewById(R.id.editTextCityInhabitants);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        itemManager = ItemManager.getInstance();
        sqlHelper = new SQLiteHelper(this);
    }

    private void checkInternetThenLoadList() {

        if(InternetChecker.checkInternet(this)) {
            new GetJsonHelper(this, this).execute();

        } else {
            Toast.makeText(this, "Offline", Toast.LENGTH_SHORT).show();
            noInternetUpdateListViewBasedOnSavedData();
        }
    }

    private void updateListViewWithItemManagerCityData() {
        citiesListViewAdapter = new CitiesListViewAdapter(this, itemManager.getCities());
        listView.setAdapter(citiesListViewAdapter);
        this.instantiateListeners();
    }

    private void instantiateListeners() {

        initiateTheItemClickListener();
        initiateTheButtonAddClickListener();

    }

    private void initiateTheItemClickListener() {

        onItemClicklistener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City city = (City) parent.getItemAtPosition(position);
                String tempToastString = city.getName() + ", People: " + city.getHabitants();
                Toast.makeText(getApplicationContext(), tempToastString, Toast.LENGTH_SHORT).show();
            }

        };

        listView.setOnItemClickListener(onItemClicklistener);
    }

    private void initiateTheButtonAddClickListener() {

        View.OnClickListener buttonAddClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (InternetChecker.checkInternet(self)){
                        String cityName = editTextCityName.getText().toString();
                        int cityHabitants = Integer.parseInt(editTextCityHabitants.getText().toString());
                        CityJson cityJson = new CityJson.CityJsonBuilder().setName(cityName).setHabitants(cityHabitants).build();
                        new PostJsonHelper(self, cityJson.getCityJsonStringToPost(), (CallbackExecutor) self).execute();

                        itemManager.addCityToCities(new City(cityName,cityHabitants));

                        clearEditTextFields();

                    } else {

                        Toast.makeText(self, "Need Internet Connection", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e) {
                    Toast.makeText(self, "Invalid Input!", Toast.LENGTH_SHORT).show();
                }

            }
        };

        buttonAdd.setOnClickListener(buttonAddClickListener);

    }

    private void clearEditTextFields() {
        editTextCityName.setText("");
        editTextCityHabitants.setText("");
    }

    private void noInternetUpdateListViewBasedOnSavedData() {
        sqlHelper.getCitiesFromDbToItemManager();
        this.updateListViewWithItemManagerCityData();
    }

    @Override
    public void executeCallbackUpdateListWithValues() {
        updateListViewWithItemManagerCityData();
    }

    public void executeCallbackNotifyListArrayHasChanged() {
        citiesListViewAdapter.notifyDataSetChanged();
    }
    
}
