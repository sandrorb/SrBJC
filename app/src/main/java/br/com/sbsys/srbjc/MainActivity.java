package br.com.sbsys.srbjc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Objects;

import br.com.sbsys.srbjc.calc.Calc;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout textInputLayoutCapitalInicial;
    private TextInputEditText edTxNumCapInicial;
    private TextInputLayout textInputLayoutAporte;
    private TextInputEditText edTxNumAporte;
    private TextInputLayout textInputLayoutJuros;
    private TextInputEditText edTxNumJuros;
    private TextInputLayout textInputLayoutTempo;
    private TextInputEditText edTxNumTempo;
    private TextInputLayout textInputLayoutCapitalFinal;
    private TextInputEditText edTxNumCapitalFinal;
    private TextView edTxViewVersion;
    private TextView textViewErros;
    private NumberFormat df;
    Double capInicial = 0.00;
    Double aporteMensal = 0.00;
    Double juros = 0.00;
    Double tempo = 0.00;
    Double capFinal = 0.00;

    public MainActivity(){
        df = DecimalFormat.getNumberInstance(Locale.ENGLISH);
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        df.setRoundingMode(RoundingMode.DOWN);
/*
 *      //Exemplo de uso:
 *      Double teste = (Double) df.parse(nStr);
 *      String str = df.format(teste));
 *      DecimalFormat df = new DecimalFormat("#,##0.00"); //"\u00A4#,##0.00" = R$ 0.000,00
 */

    }

    private void preenchimentoInicialDosCampos(){

        edTxViewVersion = findViewById(R.id.textViewVersionName);
        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);
            edTxViewVersion.setText("Versão: " + info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            edTxViewVersion.setText("");
            throw new RuntimeException(e);
        }

        textInputLayoutCapitalInicial = findViewById(R.id.textInputLayoutCapitalInicial);
        textInputLayoutAporte = findViewById(R.id.textInputLayoutAporteMensal);
        textInputLayoutJuros = findViewById(R.id.textInputLayoutJurosMensal);
        textInputLayoutTempo = findViewById(R.id.textInputLayoutTempo);
        textInputLayoutCapitalFinal = findViewById(R.id.textInputLayoutCapitalFinal);

        //Definição dos valores iniciais a serem usados nos preenchimentos dos campos
        Double capInicial = Double.parseDouble("10000.00");
        Double aporteMensal = Double.parseDouble("300.00");
        Double juros = Double.parseDouble("2.50");
        Double tempo = Double.parseDouble("12.0");
        Double capFinal = Calc.calculaCapitalFinal(capInicial, aporteMensal, tempo, juros);

        edTxNumCapInicial = findViewById(R.id.textInputEditTextCapitalInicial);
        edTxNumCapInicial.setText(df.format(capInicial));

        edTxNumAporte = findViewById(R.id.textInputEditTextAporteMensal );
        edTxNumAporte.setText(df.format(aporteMensal));

        edTxNumJuros = findViewById(R.id.textInputEditTextJurosMensal);
        edTxNumJuros.setText(df.format(juros));

        edTxNumTempo = findViewById(R.id.textInputEditTextTempo);
        edTxNumTempo.setText(df.format(tempo));

        edTxNumCapitalFinal = findViewById(R.id.textInputEditTextCapitalFinal);
        edTxNumCapitalFinal.setText(df.format(capFinal));

        textViewErros = findViewById(R.id.textViewErros);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Põe dados iniciais nos campos para facilitar e servir como exemplo
        preenchimentoInicialDosCampos();

        textInputLayoutCapitalFinal.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDataFieldsValid()) {
                    capFinal = Calc.calculaCapitalFinal(capInicial, aporteMensal, tempo, juros);
                    String res = df.format(capFinal);
                    edTxNumCapitalFinal.setText(res);
                }
            }
        });

        textInputLayoutTempo.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDataFieldsValid()) {
                    tempo = Calc.calculaTempo(capFinal, capInicial, aporteMensal, juros);
                    String res = df.format(tempo);
                    edTxNumTempo.setText(res);
                }
            }
        });

        textInputLayoutJuros.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDataFieldsValid()) {
                    juros = Calc.calculaJuros(capFinal, capInicial, aporteMensal, tempo);
                    String res = df.format(juros * 100.0);
                    edTxNumJuros.setText(res);
                }
            }
        });

        textInputLayoutAporte.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDataFieldsValid()) {
                    aporteMensal = Calc.calculaAporte(capFinal, capInicial, tempo, juros);
                    String res = df.format(aporteMensal);
                    edTxNumAporte.setText(res);
                }
            }
        });

        textInputLayoutCapitalInicial.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDataFieldsValid()) {
                    capInicial = Calc.calculaCapitalInicial(capFinal, aporteMensal, tempo, juros);
                    String res = df.format(capInicial);
                    edTxNumCapInicial.setText(res);
                }
            }
        });

    }

    private boolean isDataFieldsValid(){
        boolean isOk = true;
        String str = null;

        textViewErros.setText("");

        str = edTxNumCapInicial.getText().toString();
        if(str.equals(".") || str.equals("")){
            isOk = false;
            textViewErros.append("Há erro em Capital Inicial\n");
        }else{
            capInicial = Double.parseDouble(edTxNumCapInicial.getText().toString().replace(",",""));
        }

        str = edTxNumAporte.getText().toString();
        if(str.equals(".") || str.equals("")){
            isOk = false;
            textViewErros.append("Há erro em Aporte Mensal\n");
        }else{
            aporteMensal = Double.parseDouble(edTxNumAporte.getText().toString().replace(",",""));
        }

        str = edTxNumJuros.getText().toString();
        if(str.equals(".") || str.equals("")){
            isOk = false;
            textViewErros.append("Há erro em Juros Mensal\n");
        } else {
            Double i = (Double) Double.parseDouble(str);
            if (i == 0.0) { // juros não podem ser zero
                i = 0.00001;
                //Temporariamente 5 casas para poder ser pego corretamente a posteriori
                df.setMaximumFractionDigits(5);
                edTxNumJuros.setText(df.format(i));
                df.setMaximumFractionDigits(2);
                textViewErros.append("Juros não pode ser zero\n");
                juros = Double.parseDouble(edTxNumJuros.getText().toString().replace(",",""));
            }else{
                juros = Double.parseDouble(edTxNumJuros.getText().toString().replace(",",""));
            }
        }

        str = edTxNumTempo.getText().toString();
        if(str.equals(".") || str.equals("")){
            isOk = false;
            textViewErros.append("Há erro em Tempo\n");
        }else{
            tempo = Double.parseDouble(edTxNumTempo.getText().toString().replace(",",""));
        }

        str = edTxNumCapitalFinal.getText().toString();
        if(str.equals(".") || str.equals("")){
            isOk = false;
            textViewErros.append("Há erro em Capital Final\n");
        }else{
            capFinal = Double.parseDouble(edTxNumCapitalFinal.getText().toString().replace(",",""));
        }

        return isOk;
    }

}
