package mx.unam.aragon;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class VentanaPrincipal extends JFrame{
    private JTextField txtMagnitud;
    private JTextField txtAngulo;
    private JButton btnAgregar;
    private JButton btnCalcular;
    private  JButton btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextArea resultados;
    private PanelGrafica panelGrafica;
    private CalculadoraVectores calculadora;


    public VentanaPrincipal() {

        calculadora = new CalculadoraVectores();

        // Logo de la aplicación
        JPanel encabezado = new JPanel(new BorderLayout());

        setTitle("Programa Calculadora de Suma de Vectores");
        setSize(900,600);


// Logo
        ImageIcon logo = new ImageIcon(getClass().getResource("/recursos/Logo-personal.png"));
        ImageIcon logo2 = new ImageIcon(getClass().getResource("/recursos/FES-Aragon.png"));

        Image imagenEscalada = logo.getImage().getScaledInstance(180, 120, Image.SCALE_SMOOTH);
        Image imagenEscalada2 = logo2.getImage().getScaledInstance(120,120, Image.SCALE_SMOOTH);

        JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        JLabel lblLogo = new JLabel(new ImageIcon(imagenEscalada));
        JLabel lblLogo2 = new JLabel(new ImageIcon(imagenEscalada2));
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));

        panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 20));

        JLabel lblTitulo = new JLabel("CALCULADORA DE SUMA DE VECTORES");

        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel lblVersion = new JLabel("Versión 1.0");

        JLabel lblCreditos = new JLabel("Cano Sebastian | Chavez Citlali | Contreras Karol | Gutierrez Marco | Osorio Celia");

        JLabel lblFecha = new JLabel("Octubre 2026 - UNAM FES Aragón");

        panelInfo.add(lblTitulo);
        panelInfo.add(lblVersion);
        panelInfo.add(lblFecha);
        panelInfo.add(lblCreditos);

// Encabezado horizontal
        panelNorte.add(lblLogo, BorderLayout.WEST);
        panelNorte.add(panelInfo, BorderLayout.CENTER);
        panelNorte.add(lblLogo2,BorderLayout.EAST);

// ===== PANEL DE ENTRADAS =====
        JPanel panelSuperior = new JPanel();
        txtMagnitud = new JTextField(8);
        txtAngulo = new JTextField(8);
        btnAgregar = new JButton("Agregar");
        btnLimpiar = new JButton("Limpiar");

        panelSuperior.add(new JLabel("Magnitud"));
        panelSuperior.add(txtMagnitud);

        panelSuperior.add(new JLabel("Ángulo"));
        panelSuperior.add(txtAngulo);

        panelSuperior.add(btnAgregar);
        panelSuperior.add(btnLimpiar);

// Contenedor final
        JPanel contenedorNorte = new JPanel();
        contenedorNorte.setLayout(new BoxLayout(
                contenedorNorte,
                BoxLayout.Y_AXIS
        ));

        contenedorNorte.add(panelNorte);
        contenedorNorte.add(panelSuperior);

        add(contenedorNorte, BorderLayout.NORTH);


        modelo = new DefaultTableModel();

        modelo.addColumn("Vector");
        modelo.addColumn("Magnitud");
        modelo.addColumn("Ángulo");
        modelo.addColumn("X");
        modelo.addColumn("Y");

        tabla = new JTable(modelo);

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BorderLayout());

        resultados = new JTextArea();
        resultados.setEditable(false);

        btnCalcular = new JButton("Calcular");

        panelGrafica = new PanelGrafica();

        panelGrafica.setPreferredSize(new Dimension(300,250));

        panelDerecho.add(btnCalcular,
                BorderLayout.NORTH);

        panelDerecho.add(
                new JScrollPane(resultados),
                BorderLayout.CENTER
        );

        panelDerecho.add(
                panelGrafica,
                BorderLayout.SOUTH
        );

        add(panelDerecho, BorderLayout.EAST);

        eventos();

        setVisible(true);
    }

    private void eventos() {

        btnAgregar.addActionListener(e -> {

            if(calculadora.getVectores().size() >= 10){
                JOptionPane.showMessageDialog(this, "Máximo 5 vectores");
                return;
            }
            try {
                double m = Double.parseDouble(txtMagnitud.getText());
                if (m <= 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "La magnitud debe ser mayor que cero",
                            "Error de captura",
                            JOptionPane.ERROR_MESSAGE
                    );

                    txtMagnitud.requestFocus();
                    return;
                }
                double a = Double.parseDouble(txtAngulo.getText());
                if (a < 0 || a > 360) {

                    JOptionPane.showMessageDialog(
                            this,
                            "El ángulo debe estar entre 0° y 360°",
                            "Error de captura",
                            JOptionPane.ERROR_MESSAGE
                    );

                    txtAngulo.requestFocus();
                    return;
                }
                Vector2D v = new Vector2D(m,a);

                calculadora.agregarVector(v);

                modelo.addRow(new Object[]{
                        calculadora.getVectores().size(),
                        m,
                        a,
                        String.format("%.2f",v.getComponenteX()),
                        String.format("%.2f",v.getComponenteY())
                });
            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ingrese únicamente valores numéricos",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }


            // Limpiar campos
            txtMagnitud.setText("");
            txtAngulo.setText("");

            // Regresar el cursor al primer campo
            txtMagnitud.requestFocus();

        });


        btnCalcular.addActionListener(e -> {

            double sx = calculadora.sumaX();
            double sy = calculadora.sumaY();

            double mag =
                    calculadora.magnitudResultante();

            double dir =
                    calculadora.anguloResultante();

            resultados.setText(
                    "ΣX = " + String.format("%.2f",sx)
                            + " N" + "\nΣY = "
                            + String.format("%.2f",sy)
                            + " N" + "\n\nMagnitud = "
                            + String.format("%.2f",mag)
                            + " N" + "\nDirección = "
                            + String.format("%.2f",dir)
                            + "°"
            );
            panelGrafica.setVector(sx, sy);

        });
        btnLimpiar.addActionListener(e -> {

            // Limpiar campos de texto
            txtMagnitud.setText("");
            txtAngulo.setText("");

            // Limpiar área de resultados
            resultados.setText("");

            // Limpiar tabla
            modelo.setRowCount(0);

            // Vaciar lista de vectores
            calculadora.getVectores().clear();

            // Reiniciar gráfica
            panelGrafica.setVector(0, 0);

            // Regresar foco al primer campo
            txtMagnitud.requestFocus();

        });
    }
}
