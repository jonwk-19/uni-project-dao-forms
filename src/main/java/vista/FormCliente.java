package vista;

import dao.ClienteDAO;
import modelo.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class FormCliente extends JFrame {

    private final ClienteDAO dao = new ClienteDAO();
    private final JTextField txtNit = new JTextField(20);
    private final JTextField txtNombre = new JTextField(20);
    private final JTextField txtApellidos = new JTextField(20);
    private final JTextField txtEmail = new JTextField(20);
    private final DefaultTableModel modeloTabla =
            new DefaultTableModel(new String[]{"ID", "NIT", "Nombre", "Apellidos", "Email"}, 0);

    public FormCliente() {
        super("Clientes");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel campos = new JPanel(new GridLayout(5, 2, 5, 5));
        campos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        campos.add(new JLabel("NIT:"));
        campos.add(txtNit);
        campos.add(new JLabel("Nombre:"));
        campos.add(txtNombre);
        campos.add(new JLabel("Apellidos:"));
        campos.add(txtApellidos);
        campos.add(new JLabel("Email:"));
        campos.add(txtEmail);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardar());
        campos.add(new JLabel());
        campos.add(btnGuardar);

        add(campos, BorderLayout.NORTH);
        add(new JScrollPane(new JTable(modeloTabla)), BorderLayout.CENTER);

        setSize(600, 450);
        setLocationRelativeTo(null);
        cargarTabla();
    }

    private void guardar() {
        String nit = txtNit.getText().trim();
        String nombre = txtNombre.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String email = txtEmail.getText().trim();

        if (nit.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            dao.insertar(new Cliente(nit, nombre, apellidos, email));
            JOptionPane.showMessageDialog(this, "Cliente guardado correctamente");
            txtNit.setText("");
            txtNombre.setText("");
            txtApellidos.setText("");
            txtEmail.setText("");
            cargarTabla();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo guardar: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        try {
            for (Cliente c : dao.listar()) {
                modeloTabla.addRow(new Object[]{c.getIdCliente(), c.getNit(), c.getNombre(),
                        c.getApellidos(), c.getEmail()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar la lista: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
