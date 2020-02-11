package com.example.u2quispeejercicio1tema1.Ejercicio1.Tarea2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.u2quispeejercicio1tema1.Ejercicio1.InsertarCliente;
import com.example.u2quispeejercicio1tema1.Ejercicio1.MiNuevoAdaptador;
import com.example.u2quispeejercicio1tema1.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ListarClienteR extends AppCompatActivity {
    Retrofit retrofit;
    servicesRetrofit miserviceretrofit;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MiNuevoAdaptador adaptador;
    private ArrayList<ClienteR> misdatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        misdatos = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);

        final String url = "https://lumpier-comment.000webhostapp.com/upt/";
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        miserviceretrofit = retrofit.create(servicesRetrofit.class);

        Call<List<ClienteR>> call = miserviceretrofit.getUsersGet();
//Apartir de aqui la forma cambia de la manera sincrona a la asincrona
//basicamente mandamos a llamar el metodo enqueue, y le pasamos como parametro el call back
//Recuerda que el IDE es para ayudarte asi que lo creara automaticamente al escribir "new"
        call.enqueue(new Callback<List<ClienteR>>() {
            //Metodo que se ejecutara cuando no hay problemas y obtenemos respuesta del server
            @Override
            public void onResponse(Call<List<ClienteR>> call, Response<List<ClienteR>> response) {
//Exactamente igual a la manera sincrona,la respuesta esta en el body
                for (ClienteR res : response.body()) {

                    misdatos.add(new ClienteR(res.getcodigo(), res.getNombre(), res.getApellido()));
                    adaptador = new MiNuevoAdaptador(ListarClienteR.this , misdatos);
                    recyclerView.setAdapter(adaptador);
                    layoutManager = new LinearLayoutManager(ListarClienteR.this);
                    recyclerView.setLayoutManager(layoutManager);
                    Log.e("Usuario: ", res.getNombre() + " " + res.getApellido());

                }
            }

            //Metodo que se ejecutara cuando ocurrio algun problema
            @Override
            public void onFailure(Call<List<ClienteR>> call, Throwable t) {
                Log.e("onFailure", t.toString());// mostrmos el error
            }

        });

        //listar()
    }

    private ArrayList<ClienteR> ListaClientes() {
        final ArrayList<ClienteR> Clientes = new ArrayList<>();
        Call<List<ClienteR>> call = miserviceretrofit.getUsersGet();
        call.enqueue(new Callback<List<ClienteR>>() {
            @Override
            public void onResponse(Call<List<ClienteR>> call, Response<List<ClienteR>> response) {
                Log.e("mirespuesta: ", response.toString());
                for (ClienteR res : response.body()) {
                    Clientes.add(new ClienteR(res.getcodigo(), res.getNombre(), res.getApellido()));
                    Log.e("mirespuesta: ", "id= " + res.getcodigo() + " prod= " + res.getNombre() + " precio " + res.getApellido());
                }
            }

            @Override
            public void onFailure(Call<List<ClienteR>> call, Throwable t) {
                Log.e("onFailure", t.toString());// mostrmos el error
            }
        });
        return Clientes;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_insertar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_insertar:
                startActivity(new Intent(this, InsertarCliente.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

