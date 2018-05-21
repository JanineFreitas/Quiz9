package projeto.primeiro.com.br.primeiroprojeto;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import model.Questao;

public class PerguntasActivity  extends AppCompatActivity {
    TextView pergunta;
    RadioGroup rgRespostas;
    RadioButton rbResposta1, rbResposta2, rbResposta3, rbResposta4;
    int respostaCerta = R.id.rbResposta1;
    int pontos = 0;
    List<Questao> questoes;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perguntas);

        pergunta = (TextView)findViewById(R.id.pergunta);
        rbResposta1 = (RadioButton)findViewById(R.id.rbResposta1);
        rbResposta2 = (RadioButton)findViewById(R.id.rbResposta2);
        rbResposta3 = (RadioButton)findViewById(R.id.rbResposta3);
        rbResposta4 = (RadioButton)findViewById(R.id.rbResposta4);
        rgRespostas = (RadioGroup)findViewById(R.id.rgRespostas);
        imageView = (ImageView) findViewById(R.id.imageView);

        addPerguntas();
        carregarQuestao();
    }
    private void addPerguntas(){
        questoes = new ArrayList<Questao>(){
            {
                add(new Questao(getString(R.string.pergunta_1), R.id.rbResposta2, getResources().getDrawable(R.mipmap.ic_donut),getString(R.string.resposta_1_1), getString(R.string.resposta_1_2), getString(R.string.resposta_1_3), getString(R.string.resposta_1_4)));
                add(new Questao(getString(R.string.pergunta_2), R.id.rbResposta1, getResources().getDrawable(R.mipmap.ic_launcher), getString(R.string.resposta_2_1), getString(R.string.resposta_2_2), getString(R.string.resposta_2_3), getString(R.string.resposta_2_4)));
                add(new Questao(getString(R.string.pergunta_3), R.id.rbResposta3, getResources().getDrawable(R.mipmap.ic_calendario), getString(R.string.resposta_3_1), getString(R.string.resposta_3_2), getString(R.string.resposta_3_3), getString(R.string.resposta_3_4)));
                add(new Questao(getString(R.string.pergunta_4), R.id.rbResposta2, getResources().getDrawable(R.mipmap.ic_google), getString(R.string.resposta_4_1), getString(R.string.resposta_4_2), getString(R.string.resposta_4_3), getString(R.string.resposta_4_4)));
                add(new Questao(getString(R.string.pergunta_5), R.id.rbResposta4, getResources().getDrawable(R.mipmap.ic_launcher),getString(R.string.resposta_5_1), getString(R.string.resposta_5_2), getString(R.string.resposta_5_3), getString(R.string.resposta_5_4)));
            }
        };
    }

    private void carregarQuestao(){
        if(questoes.size() > 0) {
            Questao q = questoes.remove(0);
            pergunta.setText(q.getPergunta());
            imageView.setImageDrawable(q.getImagem());
            List<String> resposta = q.getRespostas();
            rbResposta1.setText(resposta.get(0));
            rbResposta2.setText(resposta.get(1));
            rbResposta3.setText(resposta.get(2));
            rbResposta4.setText(resposta.get(3));
            respostaCerta = q.getRespostaCerta();
            rgRespostas.setSelected(false);
        } else{
            Intent intent = new Intent(this, RespostaActivity.class);
            intent.putExtra("pontos", pontos);
            startActivity(intent);
            finish();
        }
    }

    public void btnResponderOnClick(View view) {
        RadioButton rb = (RadioButton)findViewById(rgRespostas.getCheckedRadioButtonId());
        Intent intent = new Intent(this, RespostaActivity.class);
        if(rgRespostas.getCheckedRadioButtonId() == respostaCerta) {
            intent.putExtra("acertou", true);
            pontos++;
        } else intent.putExtra("acertou", false);
        intent.putExtra("pontos", pontos);
        startActivity(intent);
        rb.setChecked(false);
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        carregarQuestao();
    }
}
