package com.example.kenedi.demogson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
//Libreria GSON de Google
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
//Libreria de Devazt
import devazt.devazt.networking.HttpClient;
import devazt.devazt.networking.OnHttpRequestComplete;
import devazt.devazt.networking.Response;

public class GSON extends AppCompatActivity{

    LinearLayout stackContenido; // declaramos la variable de tipo LinearLayaut
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson);

        stackContenido = (LinearLayout)findViewById(R.id.stackContenido);

        HttpClient cliente =new HttpClient(new OnHttpRequestComplete() {//hacemos el llamado a la  clase HttpClient del pajwtech "devazt" y la classe OnHttpRequestComplete.
            @Override
            public void onComplete(Response status) {
                if(status.isSuccess()) {
                    Gson gson= new GsonBuilder().create();
                    try {//creamos nuestra Esepcion try Catch
                        JSONObject obget = new JSONObject(status.getResult()); //creamos nuestro primer objeto
                        JSONArray jsonarry = obget.getJSONArray("records");//extraemos los objetos del arry list "RECORDS"
                        ArrayList<Persona> personas=new ArrayList<Persona>();
                        for(int i=0;i<jsonarry.length(); i++){//leemos toda la la lista de objetos del Array
                            String persona= jsonarry.getString(i);//creamos nuestra variable persona que absobera el obejto
                            Persona per=gson.fromJson(persona, Persona.class);//hacemos la llamada de la clase Persona que obtiene los vaklores de la pagina web
                            personas.add(per);
                            TextView n=new TextView(getBaseContext());
                            n.setText(per.getName());//Estraemos el dato Name del la pagina web
                            TextView c=new TextView(getBaseContext());
                            c.setText(per.getCity());//Estraemos el dato City del la pagina web
                            TextView p=new TextView(getBaseContext());
                            p.setText(per.getCountry());//Estraemos el dato Country del la pagina web
                            stackContenido.addView(n);//Mostramos el valor Name
                            stackContenido.addView(c);//Mostramos el valor City
                            stackContenido.addView(p);//Mostramos el valor Country
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    Toast.makeText(GSON.this, status.getResult(), Toast.LENGTH_SHORT).show();//Mostramos un toast del Archivo JSON al iniciar
                }
            }
        });
        cliente.excecute("https://www.w3schools.com/angular/customers.php");//ejecuamos la variable cliente antes declarada en la clase httpClient
    }
}
