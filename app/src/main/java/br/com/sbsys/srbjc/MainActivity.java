package br.com.sbsys.srbjc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
    private Button btn;


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

        btn = findViewById(R.id.idButtonCalcular);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Põe dados iniciais nos campos para facilitar e servir como exemplo
        preenchimentoInicialDosCampos();
    }

    public void calcular(View view){
        Double capInicial = Double.parseDouble(edTxNumCapInicial.getText().toString());
        //Double capInicial = getDoubleValue(view, edTxNumCapInicial);
        Double aporteMensal = Double.parseDouble(edTxNumAporte.getText().toString());
        Double juros = Double.parseDouble(edTxNumJuros.getText().toString());
        Double tempo = Double.parseDouble(edTxNumTempo.getText().toString());

        Double fator = Math.pow(1 + juros / 100.0, tempo * 12.0);
        Double capFinal = capInicial * fator + aporteMensal * (fator - 1.0) / (juros / 100.0);

        edTxNumResultado = findViewById(R.id.editTextTextCapitalFinal);

        NumberFormat df = DecimalFormat.getInstance();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        df.setRoundingMode(RoundingMode.DOWN);
        String res = df.format(capFinal);

        edTxNumResultado.setText(res);
    }

    //Tentativa inicial de impedir que sejam usados nos cãlculos
    //string vazia o ponto que não pode se transformar em Double > 0
    private Double getDoubleValue(View view, TextInputEditText tiet){
        Double doubleVal = null;
        String str = tiet.getText().toString();
        if(str.equals("") || str.equals(".")) {
            tiet.setBackgroundColor(Color.RED);
            btn.setEnabled(false);
        }else{
            doubleVal = Double.parseDouble(str);
            btn.setEnabled(true);
        }
        return doubleVal;
    }
}