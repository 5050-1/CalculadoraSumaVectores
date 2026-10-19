package mx.unam.aragon;

import java.util.ArrayList;

public class CalculadoraVectores {
    private ArrayList<Vector2D> vectores;

    public CalculadoraVectores() {
        vectores = new ArrayList<>();
    }

    public void agregarVector(Vector2D v) {
        vectores.add(v);
    }

    public double sumaX() {

        double suma = 0;
        for(Vector2D v : vectores) {
            suma += v.getComponenteX();
        }
        return suma;
    }

    public double sumaY() {

        double suma = 0;
        for(Vector2D v : vectores) {
            suma += v.getComponenteY();
        }
        return suma;
    }

    public double magnitudResultante() {

        return Math.sqrt(Math.pow(sumaX(), 2) + Math.pow(sumaY(), 2));
    }

    public double anguloResultante() {
        return Math.toDegrees(Math.atan2(sumaY(),sumaX()));
    }

    public ArrayList<Vector2D> getVectores() {
        return vectores;
    }
}
