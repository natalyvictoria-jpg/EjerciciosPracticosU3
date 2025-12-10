package Nataly;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class GestorInventario extends JFrame {

    // Clase interna Producto (no afecta la funcionalidad actual)
    class Producto {
        private String nombre;
        private String codigo;
        private String categoria;
        private int cantidad;
        private double precio;

        public Producto(String nombre, String codigo, String categoria, int cantidad, double precio) {
            this.nombre = nombre;
            this.codigo = codigo;
            this.categoria = categoria;
            this.cantidad = cantidad;
            this.precio = precio;
        }

        public String getCodigo() {
            return codigo;
        }

        @Override
        public String toString() {
            return nombre + " [" + codigo + "] - Cant: " + cantidad + " - Precio: $" + precio;
        }
    }

    // Conjuntos (se mantienen como String para no alterar funcionalidad)
    private Set<String> categoriaA = new HashSet<>();
    private Set<String> categoriaB = new HashSet<>();

    // Componentes Swing
    private JTextArea txtCategoriaA, txtCategoriaB, txtResultado;
    private JTextField txtNombre, txtCodigo, txtCategoria, txtCantidad, txtPrecio;

    public GestorInventario() {
        setTitle("Gestor de Inventario - Sistema de Productos");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(new Color(255, 255, 153));
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panelPrincipal);

        JPanel panelTextos = new JPanel(new GridLayout(1, 3, 10, 10));
        txtCategoriaA = new JTextArea();
        txtCategoriaA.setBorder(BorderFactory.createTitledBorder("Categoría A"));
        txtCategoriaB = new JTextArea();
        txtCategoriaB.setBorder(BorderFactory.createTitledBorder("Categoría B"));
        txtResultado = new JTextArea();
        txtResultado.setBorder(BorderFactory.createTitledBorder("Resultado"));

        panelTextos.add(new JScrollPane(txtCategoriaA));
        panelTextos.add(new JScrollPane(txtCategoriaB));
        panelTextos.add(new JScrollPane(txtResultado));
        panelPrincipal.add(panelTextos, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new GridLayout(2, 3, 10, 10));

        JPanel panelAgregar = new JPanel(new GridLayout(6, 2, 5, 5));
        panelAgregar.setBorder(BorderFactory.createTitledBorder("Agregar Producto"));

        txtNombre = new JTextField();
        txtCodigo = new JTextField();
        txtCategoria = new JTextField();
        txtCantidad = new JTextField();
        txtPrecio = new JTextField();

        panelAgregar.add(new JLabel("Nombre:"));
        panelAgregar.add(txtNombre);
        panelAgregar.add(new JLabel("Código:"));
        panelAgregar.add(txtCodigo);
        panelAgregar.add(new JLabel("Categoría (A/B):"));
        panelAgregar.add(txtCategoria);
        panelAgregar.add(new JLabel("Cantidad:"));
        panelAgregar.add(txtCantidad);
        panelAgregar.add(new JLabel("Precio:"));
        panelAgregar.add(txtPrecio);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> agregarProducto());
        panelAgregar.add(btnAgregar);

        panelInferior.add(panelAgregar);

        JPanel panelBasico = new JPanel(new GridLayout(6, 1, 5, 5));
        panelBasico.setBorder(BorderFactory.createTitledBorder("Operaciones Básicas"));

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarProducto());

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarProducto());

        JButton btnTamano = new JButton("Tamaño");
        btnTamano.addActionListener(e -> mostrarTamano());

        JButton btnVaciar = new JButton("Vaciar");
        btnVaciar.addActionListener(e -> vaciarInventario());

        panelBasico.add(btnEliminar);
        panelBasico.add(btnBuscar);
        panelBasico.add(btnTamano);
        panelBasico.add(btnVaciar);

        panelInferior.add(panelBasico);

        JPanel panelConjuntos = new JPanel(new GridLayout(6, 1, 5, 5));
        panelConjuntos.setBorder(BorderFactory.createTitledBorder("Operaciones de Conjuntos"));

        JButton btnUnion = new JButton("Unión");
        btnUnion.addActionListener(e -> operacionUnion());
        JButton btnInterseccion = new JButton("Intersección");
        btnInterseccion.addActionListener(e -> operacionInterseccion());
        JButton btnDiferencia = new JButton("Diferencia");
        btnDiferencia.addActionListener(e -> operacionDiferencia());
        JButton btnDisjuntos = new JButton("Disjuntos");
        btnDisjuntos.addActionListener(e -> operacionDisjuntos());
        JButton btnSubconjunto = new JButton("Subconjunto");
        btnSubconjunto.addActionListener(e -> operacionSubconjunto());

        panelConjuntos.add(btnUnion);
        panelConjuntos.add(btnInterseccion);
        panelConjuntos.add(btnDiferencia);
        panelConjuntos.add(btnDisjuntos);
        panelConjuntos.add(btnSubconjunto);

        panelInferior.add(panelConjuntos);

        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
    }

    private void agregarProducto() {
        String nombre = txtNombre.getText();
        String codigo = txtCodigo.getText();
        String categoria = txtCategoria.getText().toUpperCase();
        String cantidad = txtCantidad.getText();
        String precio = txtPrecio.getText();

        if (nombre.isEmpty() || codigo.isEmpty() || categoria.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete los campos obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String producto = nombre + " [" + codigo + "] - Cant: " + cantidad + " - Precio: $" + precio;

        if (categoria.equals("A")) {
            categoriaA.add(producto);
            actualizarTextArea(txtCategoriaA, categoriaA);
        } else if (categoria.equals("B")) {
            categoriaB.add(producto);
            actualizarTextArea(txtCategoriaB, categoriaB);
        } else {
            JOptionPane.showMessageDialog(this, "Categoría inválida (A/B)", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProducto() {
        String codigo = txtCodigo.getText();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese código para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        eliminarDeConjunto(categoriaA, codigo);
        eliminarDeConjunto(categoriaB, codigo);
        actualizarTextArea(txtCategoriaA, categoriaA);
        actualizarTextArea(txtCategoriaB, categoriaB);
    }

    private void eliminarDeConjunto(Set<String> conjunto, String codigo) {
        conjunto.removeIf(p -> p.contains("[" + codigo + "]"));
    }

    private void buscarProducto() {
        String codigo = txtCodigo.getText();
        String resultado = "";
        for (String p : categoriaA) {
            if (p.contains("[" + codigo + "]")) resultado += "A: " + p + "\n";
        }
        for (String p : categoriaB) {
            if (p.contains("[" + codigo + "]")) resultado += "B: " + p + "\n";
        }
        txtResultado.setText(resultado.isEmpty() ? "Producto no encontrado" : resultado);
    }

    private void mostrarTamano() {
        txtResultado.setText("Tamaño Categoría A: " + categoriaA.size() + "\nTamaño Categoría B: " + categoriaB.size());
    }

    private void vaciarInventario() {
        categoriaA.clear();
        categoriaB.clear();
        actualizarTextArea(txtCategoriaA, categoriaA);
        actualizarTextArea(txtCategoriaB, categoriaB);
        txtResultado.setText("Inventario vaciado");
    }

    private void operacionUnion() {
        Set<String> union = new HashSet<>(categoriaA);
        union.addAll(categoriaB);
        txtResultado.setText("Unión:\n" + String.join("\n", union));
    }

    private void operacionInterseccion() {
        Set<String> inter = new HashSet<>(categoriaA);
        inter.retainAll(categoriaB);
        txtResultado.setText("Intersección:\n" + (inter.isEmpty() ? "Ninguno" : String.join("\n", inter)));
    }

    private void operacionDiferencia() {
        Set<String> diff = new HashSet<>(categoriaA);
        diff.removeAll(categoriaB);
        txtResultado.setText("Diferencia (A - B):\n" + (diff.isEmpty() ? "Ninguno" : String.join("\n", diff)));
    }

    private void operacionDisjuntos() {
        boolean disjuntos = categoriaA.stream().noneMatch(categoriaB::contains);
        txtResultado.setText(disjuntos ? "Los conjuntos son disjuntos" : "Los conjuntos NO son disjuntos");
    }

    private void operacionSubconjunto() {
        boolean sub = categoriaB.containsAll(categoriaA);
        txtResultado.setText(sub ? "A es subconjunto de B" : "A NO es subconjunto de B");
    }

    private void actualizarTextArea(JTextArea area, Set<String> conjunto) {
        area.setText("");
        conjunto.forEach(p -> area.append(p + "\n"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestorInventario app = new GestorInventario();
            app.setVisible(true);
        });
    }
}
