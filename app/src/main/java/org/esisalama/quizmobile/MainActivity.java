package org.esisalama.quizmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText keywordField;
    private TextView shower;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        submitButtonAction();
    }

    private void initComponents(){
        shower = findViewById(R.id.shower);
        keywordField = findViewById(R.id.keywordField);
        submitButton = findViewById(R.id.submitButton);
    }

    private void submitButtonAction(){
        submitButton.setOnClickListener(
            v -> {
                String keyword = keywordField.getText().toString();
                if (!keyword.isEmpty()){
                    getRequest(keyword);
                } else {
                    Toast.makeText(
                            this,
                            "Veuillez insérez un mots clé",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        );
    }

    private void getRequest(String keyword){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GithubUserService githubUserService = retrofit.create(GithubUserService.class);
        Call<Result> callback = githubUserService.getResult(keyword);

        callback.enqueue(
                new Callback<Result>() {
                    @Override
                    public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                        if (response.isSuccessful()){
                            Result result = response.body();
                            assert result != null;
                            StringBuilder str = new StringBuilder();

                            if(!result.getItems().isEmpty()){
                                int count = 0;
                                for (User e: result.getItems()) {
                                    if(count > 1){
                                        break;
                                    }else {
                                        count++;
                                    }
                                    shower.setText(
                                            str.append("Id : ").append(e.getId()).append("\n")
                                                    .append("Login : ").append(e.getLogin()).append("\n")
                                    );
                                }
                            } else {
                                Toast.makeText(
                                        MainActivity.this,
                                        "La liste est vide. Vérifiez que le mot clé est valide",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Result> call, @NonNull Throwable t) {
                        Toast.makeText(
                                MainActivity.this,
                                "Erreur : " + t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );
    }
}