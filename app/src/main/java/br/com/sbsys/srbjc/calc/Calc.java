package br.com.sbsys.srbjc.calc;

public class Calc {

    public static double calculaCapitalFinal(double C, double A, double t, double i){
        double fator = Math.pow(1 + i / 100.0, t * 12.0);
        return C * fator + A * (fator - 1.0) / (i / 100.0);
    }

    public static double calculaTempo(double M, double C, double A, double i){
        double v = 0.0;
        double t1 = 1.0;
        double t2 = 1.0;
        double factor = 0.0;

        i = i / 100.0;

        while(v<M){
            t2 = t2 * 2.0;
            factor = Math.pow(1+i,t2*12);
            v = C * factor + A * (factor - 1.0) / i;
        }

        while(v>M){
            t1 = t1 / 2.0;
            factor = Math.pow(1+i,t1*12);
            v = C * factor + A * (factor - 1.0) / i;
        }

        double t = t1 +(t2 - t1) / 2.0;

        while(Math.abs(v-M)>0.001){
            factor = Math.pow(1+i,t*12);
            v = C * factor + A * (factor - 1.0) / i;
            if(v>M){
                t2 = t;
            }
            if(v<M){
                t1 = t;
            }
            t = t1 +(t2 - t1) / 2.0;
        }
        return t;
    }
}
