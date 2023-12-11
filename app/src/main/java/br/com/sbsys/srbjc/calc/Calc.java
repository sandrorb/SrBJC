package br.com.sbsys.srbjc.calc;

public class Calc {

    public static double calculaCapitalFinal(double C, double A, double t, double i){
        i = i / 100.0;
        double fator = Math.pow(1 + i, t * 12.0);
        return C * fator + A * (fator - 1.0) / i;
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

        while(Math.abs(v-M)>0.00001){
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


    public static double calculaJuros(double M, double C, double A, double tempo){
        double v = 0.0;
        double i1 = 1.0;
        double i2 = 1.0;
        double factor = 0.0;

        while(v<M){
            i2 = i2 * 2.0;
            factor = Math.pow(1 + i2, tempo * 12);
            v = C * factor + A * (factor - 1.0) / i2;
        }

        while(v>M){
            i1 = i1 / 2.0;
            factor = Math.pow(1 + i1, tempo * 12);
            v = C * factor + A * (factor - 1.0) / i1;
        }

        double i = i1 + (i2 - i1) / 2.0;

        while(Math.abs(v-M)>0.00001){
            factor = Math.pow(1 + i, tempo * 12);
            v = C * factor + A * (factor - 1.0) / i;
            if(v>M){
                i2 = i;
            }
            if(v<M){
                i1 = i;
            }
            i = i1 + (i2 - i1) / 2.0;
        }
        return i;
    }

    public static double calculaAporte(double M, double C, double tempo, double i){
        double v = 0.0;
        double A1 = 1.0;
        double A2 = 1.0;
        double factor = 0.0;

        i = i / 100.0;

        while(v<M){
            A2 = A2 * 2.0;
            factor = Math.pow(1 + i, tempo * 12);
            v = C * factor + A2 * (factor - 1.0) / i;
        }

        while(v>M){
            A1 = A1 / 2.0;
            factor = Math.pow(1 + i, tempo * 12);
            v = C * factor + A1 * (factor - 1.0) / i;
        }

        double A = A1 + (A2 - A1) / 2.0;

        while(Math.abs(v-M)>0.00001){
            factor = Math.pow(1 + i, tempo * 12);
            v = C * factor + A * (factor - 1.0) / i;
            if(v>M){
                A2 = A;
            }
            if(v<M){
                A1 = A;
            }

            A = A1 + (A2 - A1) / 2.0;
        }
        return A;
    }

    public static double calculaCapitalInicial(double M, double A, double tempo, double i){
        double v = 0.0;
        double C1 = 1.0;
        double C2 = 1.0;
        double factor = 0.0;

        i = i / 100.0;

        while(v<M){
            C2 = C2 * 2.0;
            factor = Math.pow(1 + i, tempo * 12);
            v = C2 * factor + A * (factor - 1.0) / i;
        }

        while(v>M){
            C1 = C1 / 2.0;
            factor = Math.pow(1 + i, tempo * 12);
            v = C1 * factor + A * (factor - 1.0) / i;
        }

        double C = C1 + (C2 - C1) / 2.0;

        while(Math.abs(v-M)>0.00001){
            factor = Math.pow(1 + i, tempo * 12);
            v = C * factor + A * (factor - 1.0) / i;
            if(v>M){
                C2 = C;
            }
            if(v<M){
                C1 = C;
            }

            C = C1 + (C2 - C1) / 2.0;
        }
        return C;
    }

}
