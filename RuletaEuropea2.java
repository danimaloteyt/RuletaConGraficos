import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class RuletaEuropea2 {
    private static double saldo = 50000.0; // Saldo inicial del jugador
    private static Random random = new Random();
    
    public static void main(String[] args) {
        // Crear la ventana
        JFrame frame = new JFrame("Ruleta Europea");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Etiquetas de información
        JLabel saldoLabel = new JLabel("Saldo actual: $" + saldo);
        saldoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(saldoLabel);

        // Campo para ingresar la apuesta
        JTextField apuestaField = new JTextField();
        apuestaField.setMaximumSize(new Dimension(200, 30));
        apuestaField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(new JLabel("¿Cuánto deseas apostar?"));
        panel.add(apuestaField);

        // Botones para elegir las opciones
        JButton colorButton = new JButton("Apostar por Color");
        JButton numeroButton = new JButton("Apostar por Número");
        JButton paridadButton = new JButton("Apostar por Paridad");
        JButton docenaButton = new JButton("Apostar por Docena");

        panel.add(colorButton);
        panel.add(numeroButton);
        panel.add(paridadButton);
        panel.add(docenaButton);

        // Label de mensaje para mostrar el resultado
        JLabel resultadoLabel = new JLabel("Haz tu apuesta.");
        resultadoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(resultadoLabel);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);

        // Acción de los botones
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double apuesta = obtenerApuesta(apuestaField, saldo);
                if (apuesta > 0) {
                    String color = JOptionPane.showInputDialog(frame, "Elige un color: rojo (1) o negro (2):");
                    int numeroGanador = random.nextInt(36) + 1;
                    boolean esRojo = numeroGanador % 2 == 1;
                    boolean apuestaGanadaPorColor = (color.equals("1") && esRojo) || (color.equals("2") && !esRojo);
                    resultadoLabel.setText("Número ganador: " + numeroGanador);
                    if (apuestaGanadaPorColor) {
                        saldo += apuesta;
                        resultadoLabel.setText("¡Ganaste! Saldo: $" + saldo);
                    } else {
                        saldo -= apuesta;
                        resultadoLabel.setText("Perdiste. Saldo: $" + saldo);
                    }
                }
                saldoLabel.setText("Saldo actual: $" + saldo);
            }
        });

        numeroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double apuesta = obtenerApuesta(apuestaField, saldo);
                if (apuesta > 0) {
                    String numero = JOptionPane.showInputDialog(frame, "Ingresa un número (0-36):");
                    int numeroElegido = Integer.parseInt(numero);
                    if (numeroElegido < 0 || numeroElegido > 36) {
                        JOptionPane.showMessageDialog(frame, "Número no válido.");
                        return;
                    }
                    int numeroGanador = random.nextInt(36) + 1;
                    resultadoLabel.setText("Número ganador: " + numeroGanador);
                    if (numeroElegido == numeroGanador) {
                        saldo += apuesta * 36;
                        resultadoLabel.setText("¡Ganaste en el número " + numeroElegido + "! Saldo: $" + saldo);
                    } else {
                        saldo -= apuesta;
                        resultadoLabel.setText("Perdiste. Saldo: $" + saldo);
                    }
                }
                saldoLabel.setText("Saldo actual: $" + saldo);
            }
        });

        paridadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double apuesta = obtenerApuesta(apuestaField, saldo);
                if (apuesta > 0) {
                    String paridad = JOptionPane.showInputDialog(frame, "Elige paridad: par (1) o impar (2):");
                    int numeroGanador = random.nextInt(36) + 1;
                    boolean esPar = numeroGanador % 2 == 0;
                    boolean apuestaGanadaPorParidad = (paridad.equals("1") && esPar) || (paridad.equals("2") && !esPar);
                    resultadoLabel.setText("Número ganador: " + numeroGanador);
                    if (apuestaGanadaPorParidad) {
                        saldo += apuesta;
                        resultadoLabel.setText("¡Ganaste! Saldo: $" + saldo);
                    } else {
                        saldo -= apuesta;
                        resultadoLabel.setText("Perdiste. Saldo: $" + saldo);
                    }
                }
                saldoLabel.setText("Saldo actual: $" + saldo);
            }
        });

        docenaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double apuesta = obtenerApuesta(apuestaField, saldo);
                if (apuesta > 0) {
                    String docena = JOptionPane.showInputDialog(frame, "Elige una docena: 1 (1-12), 2 (13-24), 3 (25-36):");
                    int numeroGanador = random.nextInt(36) + 1;
                    int docenaGanadora = (numeroGanador - 1) / 12 + 1;
                    resultadoLabel.setText("Número ganador: " + numeroGanador);
                    if (docena.equals(String.valueOf(docenaGanadora))) {
                        saldo += apuesta * 3;
                        resultadoLabel.setText("¡Ganaste en la docena! Saldo: $" + saldo);
                    } else {
                        saldo -= apuesta;
                        resultadoLabel.setText("Perdiste. Saldo: $" + saldo);
                    }
                }
                saldoLabel.setText("Saldo actual: $" + saldo);
            }
        });
    }

    // Función para obtener la apuesta del jugador y validar
    private static double obtenerApuesta(JTextField apuestaField, double saldo) {
        double apuesta = 0;
        try {
            apuesta = Double.parseDouble(apuestaField.getText());
            if (apuesta <= 0 || apuesta > saldo) {
                JOptionPane.showMessageDialog(null, "La apuesta debe ser mayor que 0 y no puede exceder tu saldo.");
                return 0;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa un número válido.");
            return 0;
        }
        return apuesta;
    }
}
