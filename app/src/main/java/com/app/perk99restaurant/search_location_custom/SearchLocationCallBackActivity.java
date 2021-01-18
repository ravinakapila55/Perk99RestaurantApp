package com.app.perk99restaurant.search_location_custom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.app.perk99restaurant.R;
import com.app.perk99restaurant.autoComplete.PlaceAPIModel;
import com.app.perk99restaurant.autoComplete.PlaceAdapter;
import com.app.perk99restaurant.utils.CommonMethod;
import com.app.perk99restaurant.utils.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;



public class SearchLocationCallBackActivity extends AppCompatActivity {

    //https://github.com/futurestudio/android-tutorials-delay-changedtextevent/blob/master/app/src/main/java/tutorials/futurestud/io/futurestudiotutorialsdelaychangedtextevent/MainActivity.java

   // https://c1ctech.com/google-places-api-example-with-autocompletetextviewkotlin-java/

    // https://stackoverflow.com/questions/36999647/how-to-customize-placeautocomplete-widget-dialog-design-to-list-places



    ListView lvAddresses;


    EditText edAddress;

    RelativeLayout relativeAddress;

    TextView txtHeader;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_search);
        txtHeader=(TextView)findViewById(R.id.txt_header);
        relativeAddress=(RelativeLayout) findViewById(R.id.relativeAddress);
        edAddress=(EditText) findViewById(R.id.edt_search);
        lvAddresses=(ListView) findViewById(R.id.lvAddresses);

        txtHeader.setText("Search location");
        final ArrayList<PlaceAPIModel> placeList = new ArrayList<>();
        final PlaceAdapter mPlaceAdapter = new PlaceAdapter(this
                , placeList);
        lvAddresses.setAdapter(mPlaceAdapter);
        txtHeader.setText(getString(R.string.location));
        edAddress.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        edAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                placeList.clear();
                placeList.addAll(autocomplete(s.toString()));
                //   Log.e("ssss", "sss " + placeList.size());
                mPlaceAdapter.notifyList(placeList);
                if (placeList.size() == 0) {
                    relativeAddress.setVisibility(View.GONE);
                } else {
                    relativeAddress.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
//                placeList.clear();
//                placeList.addAll(autocomplete(s.toString()));
//                mPlaceAdapter.notifyList(placeList);
            }
        });


        lvAddresses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                edAddress.setText(" " + placeList.get(position).getDesc());
                String placeId = placeList.get(position).getPlaceId();
                Log.e("placeId", "dd " + placeId);
                relativeAddress.setVisibility(View.GONE);
                final String url = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" +
                        placeId + "&key=AIzaSyAK5It4p1CiJ2gFzWRbfs24Cibo2QTcPRU";

                serviceArrive(url);


            }
        });
    }

  /*  @OnClick(R.id.img_back_btn)
    public void backClicked(){
        finish();
    }

*/
    private void serviceArrive(String url1) {
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            URL url = new URL(url1);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            Log.e("url ", "" + url);
            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e("EXC", "Error processing Places API URL", e);
        } catch (IOException e) {
            Log.e("EXC", "Error connecting to Places API", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            Log.e("JSONObjectValue ", jsonObj + "");
            JSONObject res = jsonObj.getJSONObject("result");
            JSONObject geo = res.getJSONObject("geometry");
            final JSONObject loc = geo.getJSONObject("location");
//            latitude = loc.getString("lat");
//            longitude = loc.getString("lng");


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent();

                        try {
                            CommonMethod.hideKeyboard(SearchLocationCallBackActivity.this);
                            intent.putExtra("latitude", loc.getString("lat"));
                            intent.putExtra("longitude", loc.getString("lng"));
                            intent.putExtra("address",edAddress.getText().toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            setResult(RESULT_CANCELED, intent);
                        }

                        setResult(RESULT_OK, intent);

                    finish();
                }
            }, 500);
        }


           /*


            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mMap.clear();
                    LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                    mMap.addMarker(new MarkerOptions().position(latLng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));
                }
            },500);*/

           /* handler.post(new Runnable(){
                             @Override
                             public void run() {

                             }

                         });*/

     //   Log.e("sss", "sss " + latitude + "longg " + longitude);


        catch(JSONException e)
        {
            e.printStackTrace();
        }

}

    private ArrayList<PlaceAPIModel> autocomplete(String input) {

        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ArrayList<PlaceAPIModel> resultList = new ArrayList<>();

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(Constants.PLACES_API_BASE);
            sb.append("?key=" + Constants.API_KEY);
            //for cities
            // sb.append("&types=(regions)");
            //  sb.append("&components=country:in");
            ///////////
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

//            StringBuilder sb = new StringBuilder(Constants.PLACES_API_BASE);
//            sb.append("?sensor=false&key=" + Constants.API_KEY);
//            sb.append("&components=country:in");
//            sb.append("&types=(cities)");
//            sb.append("&input=" + URLEncoder.encode(input, "utf8"));
            // sb.append("?key=" + Constants.API_KEY);
//            //for cities
//			sb.append("&types=(cities)");
//            //  sb.append("&components=country:in");
//            ///////////
//            sb.append("&input=" + URLEncoder.encode(input, "utf8"));
            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            return resultList;
        } catch (IOException e) {

            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");
            Log.e("jsonResult", "json " + jsonResults.toString());
            // Extract the Place descriptions from the results
            resultList = new ArrayList<PlaceAPIModel>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                PlaceAPIModel mObj = new PlaceAPIModel();
                mObj.setDesc(predsJsonArray.getJSONObject(i).getString("description"));
                mObj.setPlaceId(predsJsonArray.getJSONObject(i).getString("place_id"));
                resultList.add(mObj);
                // resultList.add(predsJsonArray.getJSONObject(i).getString("description"));

            }
        } catch (JSONException e) {
        }

        return resultList;
    }
}
