package vista;

import dao.MarcaDAO;
import modelo.Marca;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class FormMarca extends JFrame {

    private final MarcaDAO dao = new MarcaDAO();
    private final JTextField txtNombre = new JTextField(20);
    private final DefaultTableModel modeloTabla =
            new DefaultTableModel(new String[]{"ID", "Nombre"}, 0);

    public FormMarca() {
        super("Marcas");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel campos = new JPanel(new GridLayout(2, 2, 5, 5));
        campos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        campos.add(new JLabel("Nombre:"));
        campos.add(txtNombre);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardar());
        campos.add(new JLabel());
        campos.add(btnGuardar);

        add(campos, BorderLayout.NORTH);
        add(new JScrollPane(new JTable(modeloTabla)), BorderLayout.CENTER);

        setSize(450, 350);
        setLocationRelativeTo(null);
        cargarTabla();
    }

    private void guardar() {
        String nombre = txtNombre.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            dao.insertar(new Marca(nombre));
            JOptionPane.showMessageDialog(this, "Marca guardada correctamente");
            txtNombre.setText("");
            cargarTabla();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo guardar: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        try {
            for (Marca m : dao.listar()) {
                modeloTabla.addRow(new Object[]{m.getIdMarca(), m.getNombre()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar la lista: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
