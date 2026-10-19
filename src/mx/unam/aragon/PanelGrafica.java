package mx.unam.aragon;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;

public class PanelGrafica extends JPanel {
    private double xFinal;
    private double yFinal;

    public void setVector(double x, double y) {
        this.xFinal = x;
        this.yFinal = y;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        int centroX = getWidth() / 2;
        int centroY = getHeight() / 2;

        // Ejes
        g2.setColor(Color.BLACK);
        g2.drawLine(0, centroY, getWidth(), centroY);
        g2.drawLine(centroX, 0, centroX, getHeight());

        double escala = 10;
        // Magnitud del vector resultante
        double magnitud = Math.sqrt(
                xFinal * xFinal +
                        yFinal * yFinal
        );

// Longitud fija para la flecha
        double longitudFija = 80;

        int destinoX = centroX;
        int destinoY = centroY;

        if (magnitud > 0) {

            destinoX = centroX
                    + (int)((xFinal / magnitud) * longitudFija);

            destinoY = centroY
                    - (int)((yFinal / magnitud) * longitudFija);
        }

        // Vector resultante
        g2.setColor(Color.RED);
        g2.drawLine(centroX, centroY, destinoX, destinoY);

        // Punta
        g2.fillOval(destinoX - 4, destinoY - 4, 8, 8);

        // Calcular ángulo del vector resultante
        double angulo = Math.toDegrees(
                Math.atan2(yFinal, xFinal)
        );

// Radio del arco
        int radio = 40;

// Dibujar arco del ángulo
        g2.setColor(Color.BLUE);

        Arc2D.Double arco = new Arc2D.Double(
                centroX - radio,
                centroY - radio,
                radio * 2,
                radio * 2,
                0,          // inicia sobre eje +X
                angulo,     // extensión del ángulo real
                Arc2D.OPEN
        );

        g2.draw(arco);

// Posicionar texto aproximadamente a la mitad del arco
        double mitad = Math.toRadians(angulo / 2.0);

        int textoX = centroX +
                (int) ((radio + 15) * Math.cos(mitad));

        int textoY = centroY -
                (int) ((radio + 15) * Math.sin(mitad));

        g2.setFont(new Font("Arial", Font.BOLD, 12));

        g2.drawString(
                String.format("%.2f°", angulo),
                textoX,
                textoY
        );
    }
}


