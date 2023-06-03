package com.example.trabalhopizza;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.BaseAdapter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CheckBox cbBorda;
    private CheckBox cbRefrigerante;

    private Spinner spSabor;

    private RadioGroup rgTamanho;
    private ListView lstSabores;
    private String[] vetorSabores;
    private ArrayList<String> ListaSabores;
    private int SelecaoSpinner;
    private int qntSabores;
    private double totPedido;
    private TextView tvPedido;
    private String tamSelecionado;
    private String cbBordaRefri = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPedido = findViewById(R.id.tvPedido);
        rgTamanho = findViewById(R.id.rgTamanho);

        cbBorda = findViewById(R.id.cbBorda);
        cbRefrigerante = findViewById(R.id.cbRefrigerante);

        spSabor = findViewById(R.id.spSabor);

        vetorSabores = new String[]{"", "Calabresa", "Strogonoff Boi",
                "Strogonoff Frango", "5 Queijos", "Baconbresa", "Carbonara", "Do Cheff"};

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, vetorSabores);
        spSabor.setAdapter(adapter);

        lstSabores = findViewById(R.id.lstSabores);
        ListaSabores = new ArrayList<>();

        spSabor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelecaoSpinner = position;
                adicionaSabor(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cbBorda = findViewById(R.id.cbBorda);
        cbRefrigerante = findViewById(R.id.cbRefrigerante);

        cbBorda.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                validaCampos();
            }
        });
        cbRefrigerante.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                validaCampos();
            }
        });
    }

    private void atualizaLista () {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ListaSabores);
        lstSabores.setAdapter(adapter);
        atualizaResultado();
    }

    public void adicionaSabor(int position) {
        if (ListaSabores.size() < qntSabores) {
            ListaSabores.add(vetorSabores[position]);
            atualizaLista();
        }else {
            Toast.makeText(this, "Limite de sabores para esse tamanho excedido!!", Toast.LENGTH_LONG).show();
        }
    }

    public void removeSabor() {
        int ultimoId = ListaSabores.size() - 1;
        ListaSabores.remove(ultimoId);
        atualizaLista();
    }

    public void validaCampos(){
        if(cbBorda.isChecked()){
            Toast.makeText(this,
                    "Selecionou a opção COM BORDA!",
                    Toast.LENGTH_LONG).show();
        }
        if(cbRefrigerante.isChecked()){
            Toast.makeText(this,
                    "Selecionou a opção COM REGRIGERANTE!",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void selecionarOcpao(View view) {
        RadioButton radio = (RadioButton) view;
//        boolean selecionado = radio.isChecked();


        boolean selecionado = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.rbPequena:
                if(selecionado){
                    Toast.makeText(this,
                            "Selecionou a pizza PEQUENA! \n" + " (1 sabor)" ,
                            Toast.LENGTH_LONG).show();
                    qntSabores = 1;
                    totPedido += 20.00;
                    tamSelecionado = "Pequena";
                    atualizaLista();
                }
                break;
            case R.id.rbMedia:
                if(selecionado){
                    Toast.makeText(this,
                            "Selecionou a pizza MEDIA! \n" + " (2 sabores)" ,
                            Toast.LENGTH_LONG).show();
                    qntSabores = 2;
                    totPedido += 30.00;
                    tamSelecionado = "Media";
                    atualizaLista();
                }
                break;
            case R.id.rbGrande:
                if(selecionado){
                    Toast.makeText(this,
                            "Selecionou a pizza GRANDE! \n" + " (4 sabores)" ,
                            Toast.LENGTH_LONG).show();
                    qntSabores = 4;
                    totPedido += 40.00;
                    tamSelecionado = "Grande";
                    atualizaLista();
                }
                break;
        }

    }

    public void zerarCheckin(){
        cbBorda.setChecked(false);
        cbRefrigerante.setChecked(false);
    }

    public void retornaCheckBox(View view) {
        boolean checkin = ((CheckBox) view).isChecked();

        switch (view.getId()){
            case R.id.cbBorda:
                if (checkin) {
                    totPedido += 10.00;
                    cbBordaRefri += "Com Borda; ";
                    atualizaLista();
                }
                break;
            case R.id.cbRefrigerante:
                if (checkin) {
                    totPedido += 5.00;
                    cbBordaRefri += "Com Refrigerante; ";
                    atualizaLista();
                }
                break;
        }
    }

    private void atualizaResultado() {
        tvPedido.setText("Pizza: " + tamSelecionado
                + "\n Sabores escolhidos: \n"
                + ListaSabores + "\n"
                + cbBordaRefri + "\n"
                + "Total do Pedido: R$"
                + totPedido);
    }

    public void btnConcluiPedido(View view) {
        Toast.makeText(this, "Pedido concluido com sucesso, enviando a cozinha", Toast.LENGTH_LONG).show();
        btnLimparPedido(view);
    }

    public void limpaLista(){
        ListaSabores.clear();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ListaSabores);
        lstSabores.setAdapter(adapter);
    }

    public void btnLimparPedido(View view) {
        tvPedido.setText("");
        totPedido = 0.00;
        tamSelecionado = "";
        cbBordaRefri = "";
        limpaLista();
        zerarCheckin();
        rgTamanho.clearCheck();
    }

    public void btnRemoverSabores(View view) {
        removeSabor();
    }
}