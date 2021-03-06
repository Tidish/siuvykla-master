package com.example.mokytojas.siuvykla;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;



public class New_post extends Activity {

    private Spinner clothes_type, gender, color, delivery;
    private Button submit_button;
    private EditText order, price, clothes_length, clothes_width;

    private static final String REGISTER_URL = "http://venslovaitis.byethost10.com/App-siuvykla/new_post.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

      //  addItemsOnClothes();
        SubmitButton();
       // addListenerOnSpinnerItemSelection();
    }

    public void SubmitButton() {

        clothes_type = (Spinner) findViewById(R.id.clothes_type);
        gender = (Spinner) findViewById(R.id.gender);
        color = (Spinner) findViewById(R.id.color);
        delivery = (Spinner) findViewById(R.id.delivery);
        submit_button = (Button) findViewById(R.id.operation_one_submit);
        order = (EditText) findViewById(R.id.order);
        price = (EditText) findViewById(R.id.price);
        clothes_length = (EditText) findViewById(R.id.clothes_length);
        clothes_width = (EditText) findViewById(R.id.clothes_width);

        order.setError(null);
        price.setError(null);
        clothes_length.setError(null);
        clothes_width.setError(null);

        submit_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String order_text = order.getText().toString().trim();
                String price_text = price.getText().toString().trim();
                String clothes_length_text = clothes_length.getText().toString().trim();
                String clothes_width_text = clothes_width.getText().toString().trim();


            boolean cancel = false;
            boolean show = true;
                if (TextUtils.isEmpty(order_text)) {
                    order.setError(getString(R.string.operation_one_error));
                    show = false;
                }
                if (TextUtils.isEmpty(price_text)) {
                    price.setError(getString(R.string.operation_one_error));
                    show = false;
                }
                if (TextUtils.isEmpty(clothes_length_text)){
                    clothes_length.setError(getString(R.string.operation_one_error));
                    show = false;
                }
                if (TextUtils.isEmpty(clothes_width_text)) {
                    clothes_width.setError(getString(R.string.operation_one_error));
                    show = false;
                }

                if (show) {

                    double order_num = Double.parseDouble(order_text);
                    double price_num = Double.parseDouble(price_text);
                    double clothes_length_num = Double.parseDouble(clothes_length_text);
                    double clothes_width_num = Double.parseDouble(clothes_width_text);

                    Drabuzis cl = new Drabuzis(String.valueOf(clothes_type.getSelectedItem()), String.valueOf(gender.getSelectedItem()), String.valueOf(color.getSelectedItem()), String.valueOf(delivery.getSelectedItem()), order_num, price_num, clothes_length_num, clothes_width_num);

                    userRegistration(cl);
                    /*Toast.makeText(New_post.this,
                            "objekte: "+
                                    "\nDrabužio tipas : " + cl.getType()+
                                    "\nLytis : " + cl.getGender() +
                                    "\nSpalva : " + cl.getColor() +
                                    "\nPristatymas : " + cl.getDelivery() +
                                    "\nUžsakymų kiekis : " + cl.getOrder() +
                                    "\nKaina : " + cl.getPrice() +
                                    "\nDrabužio ilgis : " + cl.getLength() +
                                    "\nDrabužio plotis : " + cl.getWidth(),
                            Toast.LENGTH_LONG).show();
*/


                    cancel = true;
                }

                if (cancel) {
                    Intent myIntent = new Intent(New_post.this, MeniuActivity.class);
                    //myIntent.putExtra("key", value); //Optional parameters
                    New_post.this.startActivity(myIntent);


                }


            }

        });
    }

    private void userRegistration(final Drabuzis cl) {

        //register
        String urlSuffix = "?type="+cl.getType()+"&order="+cl.getOrder()+"&gender="+cl.getGender()+"&price="+cl.getPrice()+"&color="+cl.getColor()+
        "&clothes_length="+cl.getLength()+"&clothes_width="+cl.getWidth()+"&delivery="+cl.getDelivery();
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(New_post.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(REGISTER_URL + s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestProperty("Cookie", "__test=7a4d917e220fbf9a55cab3046bd1a3b7; expires=2038 m. sausio 1 d., penktadienis 01:55:55; path=/");
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String result;

                    result = bufferedReader.readLine();

                    return result;
                } catch (Exception e) {
                    return null;
                }
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(urlSuffix);
    }

}




/* public void addItemsOnClothes(){ without string arrays

        clothes_type = (Spinner) findViewById(R.id.clothes_type);
        List<String> list = new ArrayList<String>();
        list.add("list 1");
        list.add("list 2");
        list.add("list 3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clothes_type.setAdapter(dataAdapter);
    }*/

    /*public void addListenerOnSpinnerItemSelection() {
        gender = (Spinner) findViewById(R.id.gender);
        gender.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }*/