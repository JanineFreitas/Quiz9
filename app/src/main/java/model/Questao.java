package model;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class Questao {
    private String pergunta;
    private List<String> respostas = new ArrayList<>();
    private int respostaCerta;
    private Drawable imagem;

    public Questao(String pergunta, int respostaCerta, Drawable imagem, String... respostas){
        this.pergunta = pergunta;
        this.respostas.add(respostas[0]);
        this.respostas.add(respostas[1]);
        this.respostas.add(respostas[2]);
        this.respostas.add(respostas[3]);
        this.respostaCerta = respostaCerta;
        this.imagem = imagem;
    }

    public String getPergunta(){ return this.pergunta; }
    public List<String> getRespostas(){ return this.respostas; }
    public int getRespostaCerta(){ return this.respostaCerta; }
    public Drawable getImagem(){ return  this.imagem; }
}
