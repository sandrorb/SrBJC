package br.com.sbsys.srbjc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Objects;

import br.com.sbsys.srbjc.calc.Calc;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText edTxNumCapInicial;
    private TextInputEditText edTxNumAporte;
    private TextInputEditText edTxNumJuros;
    private TextInputEditText edTxNumTempo;
    private TextInputEditText edTxNumCapitalFinal;
    private TextView edTxViewVersion;
    private NumberFormat df;

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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Põe dados iniciais nos campos para facilitar e servir como exemplo
        preenchimentoInicialDosCampos();
    }

    public void calcular(View view){
        if(isDataFieldsValid()) {
            Double capInicial = Double.parseDouble(edTxNumCapInicial.getText().toString().replace(",",""));
            Double aporteMensal = Double.parseDouble(edTxNumAporte.getText().toString().replace(",",""));
            Double juros = Double.parseDouble(edTxNumJuros.getText().toString().replace(",",""));
            Double tempo = Double.parseDouble(edTxNumTempo.getText().toString().replace(",",""));

            Double capFinal = Calc.calculaCapitalFinal(capInicial, aporteMensal, tempo, juros);

            String res = df.format(capFinal);
            edTxNumCapitalFinal.setText(res);

            //Este código abaixo funciona: pega o texto do capital final e usa,
            //com os outros dados, para calcular o tempo.
//            capFinal = Double.parseDouble(edTxNumCapitalFinal.getText().toString().replace(",",""));
//            Double t = Calc.calculaTempo(capFinal, capInicial, aporteMensal, juros);
//            edTxNumTempo.setText(df.format(t));

        }
    }

    private boolean isDataFieldsValid(){
        boolean isOk = true;
        String str = null;
        str = edTxNumCapInicial.getText().toString();
        if(str.equals(".") || str.equals("")){
            isOk = false;
        }
        str = edTxNumAporte.getText().toString();
        if(str.equals(".") || str.equals("")){
            isOk = false;
        }
        str = edTxNumJuros.getText().toString();
        if(str.equals(".") || str.equals("")){
            isOk = false;
        }
        str = edTxNumTempo.getText().toString();
        if(str.equals(".") || str.equals("")){
            isOk = false;
        }
        str = edTxNumCapitalFinal.getText().toString();
        if(str.equals(".") || str.equals("")){
            isOk = false;
        } else {
            Double d = null;
            try {
                d = 12.0; //isto está dando errado: Double.parseDouble(str);
                edTxNumTempo.setText(df.format(d+1.0));
            } catch (NumberFormatException e) {
                d = null;
                edTxNumTempo.setText("Erro");
            }
        }

        return isOk;
    }

}
