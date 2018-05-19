package projeto.primeiro.com.br.primeiroprojeto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

class RespostaActivity  extends AppCompatActivity {
    ImageView imgResposta;
    TextView resposta;
    Button btnJogarNovamente;
    double porcentagem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resposta);
        getSupportActionBar().hide();

        imgResposta = (ImageView)findViewById(R.id.imgResposta);
        resposta = (TextView) findViewById(R.id.resposta);
        btnJogarNovamente = (Button) findViewById(R.id.btnJogarNovamente);

        regrarDoJogo();
    }
    private void regrarDoJogo(){
        Intent intent = getIntent();
        int pontos = intent.getIntExtra("pontos", 0);

        if(intent.hasExtra("acertou")) {
            btnJogarNovamente.setVisibility(View.INVISIBLE);
            boolean acertou = intent.getBooleanExtra("acertou", false);
            if (acertou) {
                imgResposta.setImageResource(R.drawable.ic_mood_black_24dp);
                resposta.setText("Acertou! Pontos: " + pontos);
            } else {
                imgResposta.setImageResource(R.drawable.ic_mood_bad_black_24dp);
                resposta.setText("Errou! Pontos: " + pontos);
            }
            tempoEspera();
        }
        else{
            btnJogarNovamente.setVisibility(View.VISIBLE);
            porcentagem = (100*pontos)/5;
            resposta.setText("Você acertou " + porcentagem + "% !");

            if(pontos >= 3) {
                imgResposta.setImageResource(R.drawable.ic_mood_black_24dp);
            } else {
                imgResposta.setImageResource(R.drawable.ic_mood_bad_black_24dp);
            }
        }
    }
    private void tempoEspera(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
        thread.start();
    }

    public void btnJogarNovamenteOnClick(View view) {
        Intent intent = new Intent(this, PerguntasActivity.class);
        startActivity(intent);
        finish();
    }
}
