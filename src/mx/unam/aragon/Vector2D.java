package mx.unam.aragon;

public class Vector2D {
    private double magnitud;
    private double angulo;

    public Vector2D(double magnitud, double angulo) {
        this.magnitud = magnitud;
        this.angulo = angulo;
    }

    public double getMagnitud() {
        return magnitud;
    }

    public double getAngulo() {
        return angulo;
    }

    public double getComponenteX() {
        return magnitud * Math.cos(Math.toRadians(angulo));
    }

    public double getComponenteY() {
        return magnitud * Math.sin(Math.toRadians(angulo));
    }
}
