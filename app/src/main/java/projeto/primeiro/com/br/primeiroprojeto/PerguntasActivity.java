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
                add(new Questao("Qual é o nome da primeira versão do Android?", R.id.rbResposta2, getResources().getDrawable(R.mipmap.ic_logo),"Donut", "Cupcake", "FroYo", "KitKat"));
                add(new Questao("Qual é o nome do mascote do Android", R.id.rbResposta1, getResources().getDrawable(R.mipmap.ic_logo), "Bugdroid", "Android", "Bug", "Bugzila"));
                add(new Questao("O Android está disponível como código aberto desde?", R.id.rbResposta3,getResources().getDrawable(R.mipmap.ic_logo), "2006", "2007", "2008", "2009"));
                add(new Questao("Por qual empresa foi criado o Android?", R.id.rbResposta2,getResources().getDrawable(R.mipmap.ic_logo), "Apple", "Google", "Microscoft", "Facebook"));
                add(new Questao("Qual a melhor plataforma mobile?", R.id.rbResposta4, getResources().getDrawable(R.mipmap.ic_logo),"Symbian", "BlackBerry", "iOS", "Android"));
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
        }
        else intent.putExtra("acertou", false);
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
