package com.example.retrofit_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.retrofit_app.model.Alumno;
import com.example.retrofit_app.network.GetDataService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView mJsonTxtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mJsonTxtView = findViewById(R.id.jsonText);
        getAlumnos();
    }

    private void getAlumnos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.16.64.155:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetDataService getDataService = retrofit.create(GetDataService.class);

        Call<List<Alumno>> call = getDataService.getAlumnos();

        call.enqueue(new Callback<List<Alumno>>() {
            @Override
            public void onResponse(Call<List<Alumno>> call, Response<List<Alumno>> response) {
                if(!response.isSuccessful()){
                    mJsonTxtView.setText("Código: " + response.code());
                }

                List<Alumno> alumnosList = response.body();

                for(Alumno alumno: alumnosList){
                    String content = "";
                    content += "Nombre: " + alumno.getNombre() + "\n";
                    content += "Matrícula: " + alumno.getMatricula() + "\n";
                    content += "Correo: " + alumno.getCorreo() + "\n";
                    content += "Telefono: " + alumno.getTelefono() + "\n \n";
                    mJsonTxtView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Alumno>> call, Throwable t) {
                mJsonTxtView.setText(t.getMessage());
            }
        });
    }
}