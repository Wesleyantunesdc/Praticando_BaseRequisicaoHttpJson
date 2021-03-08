package wesleyantunes.cursoandroid.threads.requisicaohttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.botao_requisicao);
        texto = findViewById(R.id.texto_resultado);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTask task = new MyTask();
                String urlApi = "https://viacep.com.br/ws/81900748/json/";
                task.execute(urlApi);
            }
        });
    }

    class MyTask extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = strings[0];
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            StringBuffer buffer = new StringBuffer();

            try {
                URL url = new URL(stringUrl);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                //Recupera os dados em Byte
                inputStream = conexao.getInputStream();
                //Codifica os bytes em caracteres
                inputStreamReader = new InputStreamReader(inputStream);
                //Traduz os caracteres em strings
                BufferedReader reader = new BufferedReader(inputStreamReader);

                String linha = "";
                while( (linha = reader.readLine()) !=null ){
                    buffer.append(linha);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);
            String lougradouro = null;
            try {
                JSONObject jsonObject = new JSONObject(resultado);
                lougradouro = jsonObject.getString("logradouro");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            texto.setText(lougradouro);
        }
    }
}