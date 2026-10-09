package vista;

import dao.EmpleadoDAO;
import dao.PuestoDAO;
import modelo.Empleado;
import modelo.Puesto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;

public class FormEmpleado extends JFrame {

    private final EmpleadoDAO dao = new EmpleadoDAO();
    private final PuestoDAO puestoDao = new PuestoDAO();
    private final JComboBox<Puesto> cmbPuesto = new JComboBox<>();
    private final JTextField txtNombre = new JTextField(20);
    private final JTextField txtApellidos = new JTextField(20);
    private final JTextField txtFecha = new JTextField(20);
    private final DefaultTableModel modeloTabla =
            new DefaultTableModel(new String[]{"ID", "Nombre", "Apellidos", "Puesto", "Contratacion"}, 0);

    public FormEmpleado() {
        super("Empleados");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel campos = new JPanel(new GridLayout(5, 2, 5, 5));
        campos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        campos.add(new JLabel("Nombre:"));
        campos.add(txtNombre);
        campos.add(new JLabel("Apellidos:"));
        campos.add(txtApellidos);
        campos.add(new JLabel("Puesto:"));
        campos.add(cmbPuesto);
        campos.add(new JLabel("Fecha contratacion (aaaa-mm-dd):"));
        campos.add(txtFecha);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardar());
        campos.add(new JLabel());
        campos.add(btnGuardar);

        add(campos, BorderLayout.NORTH);
        add(new JScrollPane(new JTable(modeloTabla)), BorderLayout.CENTER);

        setSize(650, 450);
        setLocationRelativeTo(null);
        cargarPuestos();
        cargarTabla();
    }

    private void cargarPuestos() {
        try {
            for (Puesto p : puestoDao.listar()) {
                cmbPuesto.addItem(p);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "No se pudieron cargar los puestos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardar() {
        String nombre = txtNombre.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String fechaTexto = txtFecha.getText().trim();
        Puesto puesto = (Puesto) cmbPuesto.getSelectedItem();

        if (nombre.isEmpty() || apellidos.isEmpty() || fechaTexto.isEmpty() || puesto == null) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Date fecha;
        try {
            fecha = Date.valueOf(fechaTexto);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "La fecha debe tener el formato aaaa-mm-dd",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            dao.insertar(new Empleado(puesto.getIdPuesto(), nombre, apellidos, fecha));
            JOptionPane.showMessageDialog(this, "Empleado guardado correctamente");
            txtNombre.setText("");
            txtApellidos.setText("");
            txtFecha.setText("");
            cargarTabla();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo guardar: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        try {
            for (Empleado e : dao.listar()) {
                modeloTabla.addRow(new Object[]{e.getIdEmpleado(), e.getNombre(), e.getApellidos(),
                        e.getNombrePuesto(), e.getFechaContratacion()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar la lista: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
