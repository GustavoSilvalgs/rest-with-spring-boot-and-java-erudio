package br.com.erudio.math;

public class SimpleMath {

    public Double sum( Double numberOne, Double numberTwo) {
        return numberOne + numberTwo;
    }

    public double sub( Double numberOne, Double numberTwo){
        return numberOne - numberTwo;
    }

    public double mult( Double numberOne, Double numberTwo) {
        return numberOne * numberTwo;
    }

    public double div(Double numberOne, Double numberTwo) {
        return numberOne / numberTwo;
    }

    public double med(Double numberOne, Double numberTwo) {
        return (numberOne + numberTwo) / 2.0;
    }

    public double raiz(Double number) {
        return Math.sqrt(number);
    }
}
