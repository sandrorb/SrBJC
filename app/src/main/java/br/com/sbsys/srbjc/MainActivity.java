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

        //Exemplo de uso:
        //Double teste = (Double) df.parse(nStr);
        //String str = df.format(teste));
        //DecimalFormat df = new DecimalFormat("#,##0.00"); //"\u00A4#,##0.00" = R$ 0.000,00
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

        edTxNumCapInicial = findViewById(R.id.textInputEditTextCapitalInicial);
        //edTxNumCapInicial.setText("10000");
        edTxNumCapInicial.setText(df.format(Double.parseDouble("10000")));

        edTxNumAporte = findViewById(R.id.textInputEditTextAporteMensal );
        //edTxNumAporte.setText("300");
        edTxNumAporte.setText(df.format(Double.parseDouble("300")));

        edTxNumJuros = findViewById(R.id.textInputEditTextJurosMensal);
        //edTxNumJuros.setText("2.5");
        edTxNumJuros.setText(df.format(Double.parseDouble("2.5")));

        edTxNumTempo = findViewById(R.id.textInputEditTextTempo);
        //edTxNumTempo.setText("12");
        edTxNumTempo.setText(df.format(Double.parseDouble("12")));

        edTxNumCapitalFinal = findViewById(R.id.textInputEditTextCapitalFinal);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Põe dados iniciais nos campos para facilitar e servir como exemplo
        preenchimentoInicialDosCampos();
    }

//    public void calcular(View view){
//        Double capInicial = Double.parseDouble(edTxNumCapInicial.getText().toString());
//        Double aporteMensal = Double.parseDouble(edTxNumAporte.getText().toString());
//        Double juros = Double.parseDouble(edTxNumJuros.getText().toString());
//        Double tempo = Double.parseDouble(edTxNumTempo.getText().toString());
//        //edTxNumResultado.setText("1000000");
//        //Double capFinal = 1000000.00;
//        double t = Calc.calculaTempo(Double.parseDouble("1000000"), capInicial, aporteMensal, juros / 100.0);
//        double capFinal = Calc.calculaCapitalFinal(capInicial, aporteMensal, tempo, juros);
//        edTxNumTempo.setText(String.valueOf(t));
//        edTxNumResultado.setText(String.valueOf(capFinal));
//    }

    public void calcular(View view){
        if(isDataFieldsValid()) {
            Double capInicial = Double.parseDouble(edTxNumCapInicial.getText().toString().replace(",",""));
            Double aporteMensal = Double.parseDouble(edTxNumAporte.getText().toString().replace(",",""));
            Double juros = Double.parseDouble(edTxNumJuros.getText().toString().replace(",",""));
            Double tempo = Double.parseDouble(edTxNumTempo.getText().toString().replace(",",""));

            Double capFinal = Calc.calculaCapitalFinal(capInicial, aporteMensal, tempo, juros);

            String res = df.format(capFinal);
            edTxNumCapitalFinal.setText(res);
        }else{
            edTxNumCapitalFinal.setText("Há campo(s) inválido(s)");
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

        return isOk;
    }

}
