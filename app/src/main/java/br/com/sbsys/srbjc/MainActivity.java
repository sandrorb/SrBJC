package br.com.sbsys.srbjc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Forma que encontrei para mudar a cor de fundo, já que quando eu mudo
        //por meio do design, o App crashes. Parece que esse problema tem a ver
        //com o ContraintLayout
        //findViewById(R.id.container).setBackgroundColor(Color.WHITE);

        EditText edTxNumCapInicial = findViewById(R.id.editTextNumberCapitalInicial);
        edTxNumCapInicial.setText("10000");
        EditText edTxNumAporte = findViewById(R.id.editTextNumberAporte);
        edTxNumAporte.setText("300");
        EditText edTxNumJuros = findViewById(R.id.editTextNumberJuros);
        edTxNumJuros.setText("2.5");
        EditText edTxNumTempo = findViewById(R.id.editTextNumberTempo);
        edTxNumTempo.setText("12");
    }

    public void calcular(View view){
        EditText edTxNumCapInicial = findViewById(R.id.editTextNumberCapitalInicial);
        EditText edTxNumAporte = findViewById(R.id.editTextNumberAporte);
        EditText edTxNumJuros = findViewById(R.id.editTextNumberJuros);
        EditText edTxNumTempo = findViewById(R.id.editTextNumberTempo);
        Double capInicial = Double.parseDouble(edTxNumCapInicial.getText().toString());
        Double aporteMensal = Double.parseDouble(edTxNumAporte.getText().toString());
        Double juros = Double.parseDouble(edTxNumJuros.getText().toString());
        Double tempo = Double.parseDouble(edTxNumTempo.getText().toString());

        Double fator = Math.pow( 1 + juros / 100.0, tempo * 12.0);
        Double capFinal = capInicial * fator + aporteMensal * (fator - 1.0) / (juros/100.0);

        EditText edTxNumResultado = findViewById(R.id.editTextNumberResultado);

        NumberFormat df = DecimalFormat.getInstance();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        df.setRoundingMode(RoundingMode.DOWN);
        String res = df.format(capFinal);

        edTxNumResultado.setText(res);
    }
}