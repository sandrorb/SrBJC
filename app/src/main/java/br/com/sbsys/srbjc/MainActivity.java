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

public class MainActivity extends AppCompatActivity {

    private TextInputEditText edTxNumCapInicial;
    private TextInputEditText edTxNumAporte;
    private TextInputEditText edTxNumJuros;
    private TextInputEditText edTxNumTempo;
    private EditText edTxNumResultado;
    private TextView edTxViewVersion;

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
        edTxNumCapInicial.setText("10000");

        edTxNumAporte = findViewById(R.id.textInputEditTextAporteMensal );
        edTxNumAporte.setText("300");

        edTxNumJuros = findViewById(R.id.textInputEditTextJurosMensal);
        edTxNumJuros.setText("2.5");

        edTxNumTempo = findViewById(R.id.textInputEditTextTempo);
        edTxNumTempo.setText("12");

        edTxNumResultado = findViewById(R.id.editTextTextCapitalFinal);
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
            Double capInicial = Double.parseDouble(edTxNumCapInicial.getText().toString());
            Double aporteMensal = Double.parseDouble(edTxNumAporte.getText().toString());
            Double juros = Double.parseDouble(edTxNumJuros.getText().toString());
            Double tempo = Double.parseDouble(edTxNumTempo.getText().toString());

            Double fator = Math.pow(1 + juros / 100.0, tempo * 12.0);
            Double capFinal = capInicial * fator + aporteMensal * (fator - 1.0) / (juros / 100.0);

            NumberFormat df = DecimalFormat.getInstance();
            df.setMinimumFractionDigits(2);
            df.setMaximumFractionDigits(2);
            df.setRoundingMode(RoundingMode.DOWN);
            String res = df.format(capFinal);

            edTxNumResultado.setText(res);
        }else{
            edTxNumResultado.setText("Há campo(s) inválido(s)");
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


//Código em Google Sheet Script que testei para calcular o tempo
//    function calculaTempo(M, C, A, i) {
//
//        var v = 0.0;
//        var t1 = 1.0;
//        var t2 = 1.0;
//
//        while(v<M){
//            t2 = t2 * 2.0;
//            factor = Math.pow(1+i,t2*12);
//            v = C * factor + A * (factor - 1.0) / i;
//        }
//
//        while(v>M){
//            t1 = t1 / 2.0;
//            factor = Math.pow(1+i,t1*12);
//            v = C * factor + A * (factor - 1.0) / i;
//        }
//
//        var t = t1 +(t2 - t1) / 2.0;
//
//        while(Math.abs(v-M)>0.001){
//            factor = Math.pow(1+i,t*12);
//            v = C * factor + A * (factor - 1.0) / i;
//            if(v>M){
//                t2 = t;
//            }
//            if(v<M){
//                t1 = t;
//            }
//            t = t1 +(t2 - t1) / 2.0;
//        }
//
//        return t;
//    }