package vista;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        super("Tienda - Menu principal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1, 10, 10));
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnClientes = new JButton("Clientes");
        JButton btnEmpleados = new JButton("Empleados");
        JButton btnMarcas = new JButton("Marcas");

        btnClientes.addActionListener(e -> new FormCliente().setVisible(true));
        btnEmpleados.addActionListener(e -> new FormEmpleado().setVisible(true));
        btnMarcas.addActionListener(e -> new FormMarca().setVisible(true));

        add(btnClientes);
        add(btnEmpleados);
        add(btnMarcas);

        setSize(300, 250);
        setLocationRelativeTo(null);
    }
}
