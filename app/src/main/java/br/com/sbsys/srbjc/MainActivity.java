package br.com.sbsys.srbjc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private EditText edTxNumCapInicial;
    private EditText edTxNumAporte;
    private EditText edTxNumJuros;
    private EditText edTxNumTempo;
    private EditText edTxNumResultado;

    private void preenchimentoInicialDosCampos(){
        edTxNumCapInicial = findViewById(R.id.textInputEditTextCapitalInicial);
        edTxNumCapInicial.setText("10000");

        edTxNumAporte = findViewById(R.id.textInputEditTextAporteMensal );
        edTxNumAporte.setText("300");

        edTxNumJuros = findViewById(R.id.textInputEditTextJurosMensal);
        edTxNumJuros.setText("2.5");

        edTxNumTempo = findViewById(R.id.textInputEditTextTempo);
        edTxNumTempo.setText("12");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Forma que encontrei para mudar a cor de fundo, já que quando eu mudo
        //por meio do design, o App crashes.
        //Parece que esse problema tem a ver com o ConstraintLayout
        //findViewById(R.id.container).setBackgroundColor(Color.WHITE);

        preenchimentoInicialDosCampos();
    }

    public void calcular(View view){

        Double capInicial = Double.parseDouble(edTxNumCapInicial.getText().toString());
        Double aporteMensal = Double.parseDouble(edTxNumAporte.getText().toString());
        Double juros = Double.parseDouble(edTxNumJuros.getText().toString());
        Double tempo = Double.parseDouble(edTxNumTempo.getText().toString());

        Double fator = Math.pow( 1 + juros / 100.0, tempo * 12.0);
        Double capFinal = capInicial * fator + aporteMensal * (fator - 1.0) / (juros/100.0);

        edTxNumResultado = findViewById(R.id.editTextTextCapitalFinal);

        NumberFormat df = DecimalFormat.getInstance();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        df.setRoundingMode(RoundingMode.DOWN);
        String res = df.format(capFinal);

        edTxNumResultado.setText(res);

    }
}