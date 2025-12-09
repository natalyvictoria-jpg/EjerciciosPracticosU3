package Nataly;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Nataly Victoria Gonzalez Aviles
 */

public class SimuladorDOM extends JFrame {

    private JTree arbolDOM;
    private DefaultMutableTreeNode raiz;
    private DefaultTreeModel modeloArbol;
    private JTextArea vistaHTML;
    private JEditorPane vistaPrevia;
    private JComboBox<String> comboTipoNodo;
    private JTextField txtNombreNodo;
    private JTextField txtContenido;

    public SimuladorDOM() {
        configurarVentana();
        inicializarComponentes();
        aplicarTemaMorado();
        configurarLayout();
    }

    private void configurarVentana() {
        setTitle("Simulador DOM - Creación de Página Web");
        setSize(1600, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void aplicarTemaMorado() {
        Color moradoClaro = new Color(230, 210, 255);
        Color moradoMedio = new Color(180, 120, 255);
        Color moradoOscuro = new Color(100, 0, 200);

        getContentPane().setBackground(moradoClaro);

        UIManager.put("Button.background", moradoMedio);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 13));

        UIManager.put("Panel.background", moradoClaro);
        UIManager.put("Label.foreground", moradoOscuro);
        UIManager.put("Label.font", new Font("Georgia", Font.BOLD, 14));

        UIManager.put("TextArea.background", Color.WHITE);
        UIManager.put("TextArea.foreground", Color.DARK_GRAY);
        UIManager.put("TextArea.border", BorderFactory.createLineBorder(moradoOscuro));

        UIManager.put("ComboBox.background", moradoMedio);
        UIManager.put("ComboBox.foreground", Color.WHITE);
    }

    private void inicializarComponentes() {
        raiz = new DefaultMutableTreeNode("html");
        modeloArbol = new DefaultTreeModel(raiz);
        arbolDOM = new JTree(modeloArbol);

        DefaultMutableTreeNode head = new DefaultMutableTreeNode("head");
        DefaultMutableTreeNode title = new DefaultMutableTreeNode("title: Mi Página Web");
        head.add(title);

        DefaultMutableTreeNode body = new DefaultMutableTreeNode("body");
        raiz.add(head);
        raiz.add(body);

        vistaHTML = new JTextArea();
        vistaHTML.setEditable(false);
        vistaHTML.setFont(new Font("Monospaced", Font.PLAIN, 12));

        vistaPrevia = new JEditorPane();
        vistaPrevia.setContentType("text/html");
        vistaPrevia.setEditable(false);

        String[] tiposNodo = {"div", "p", "h1", "h2", "h3", "span", "button", "input", "a", "img"};
        comboTipoNodo = new JComboBox<>(tiposNodo);

        txtNombreNodo = new JTextField(10);
        txtContenido = new JTextField(15);

        actualizarVistaHTML();
    }

    private void configurarLayout() {
        setLayout(new BorderLayout(10, 10));

        JPanel panelIzquierdo = new JPanel(new BorderLayout(5, 5));
        panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Estructura DOM (Árbol)"));
        JScrollPane scrollArbol = new JScrollPane(arbolDOM);
        scrollArbol.setPreferredSize(new Dimension(400, 600));
        panelIzquierdo.add(scrollArbol, BorderLayout.CENTER);

        JPanel panelControles = new JPanel();
        panelControles.setLayout(new BoxLayout(panelControles, BoxLayout.Y_AXIS));
        panelControles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        fila1.add(new JLabel("Tipo:"));
        fila1.add(comboTipoNodo);
        fila1.add(new JLabel("ID/Clase:"));
        fila1.add(txtNombreNodo);
        panelControles.add(fila1);

        JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        fila2.add(new JLabel("Contenido:"));
        fila2.add(txtContenido);
        panelControles.add(fila2);

        JPanel fila3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton btnAgregar = new JButton("Agregar Nodo");
        btnAgregar.setPreferredSize(new Dimension(140, 30));
        btnAgregar.addActionListener(e -> agregarNodo());

        JButton btnEliminar = new JButton("Eliminar Nodo");
        btnEliminar.setPreferredSize(new Dimension(140, 30));
        btnEliminar.addActionListener(e -> eliminarNodo());

        JButton btnLimpiar = new JButton("Limpiar Árbol");
        btnLimpiar.setPreferredSize(new Dimension(140, 30));
        btnLimpiar.addActionListener(e -> limpiarArbol());

        fila3.add(btnAgregar);
        fila3.add(btnEliminar);
        fila3.add(btnLimpiar);
        panelControles.add(fila3);

        panelIzquierdo.add(panelControles, BorderLayout.SOUTH);

        JPanel panelDerecho = new JPanel(new BorderLayout());

        JPanel panelHTML = new JPanel(new BorderLayout());
        panelHTML.setBorder(BorderFactory.createTitledBorder("Vista HTML Generada"));
        JScrollPane scrollHTML = new JScrollPane(vistaHTML);
        panelHTML.add(scrollHTML, BorderLayout.CENTER);

        JPanel panelPrevia = new JPanel(new BorderLayout());
        panelPrevia.setBorder(BorderFactory.createTitledBorder("Vista Previa (Renderizada)"));
        JScrollPane scrollPrevia = new JScrollPane(vistaPrevia);
        panelPrevia.add(scrollPrevia, BorderLayout.CENTER);

        JSplitPane splitDerecho = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelHTML, panelPrevia);
        splitDerecho.setDividerLocation(400);
        splitDerecho.setResizeWeight(0.5);

        JSplitPane splitPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, splitDerecho);
        splitPrincipal.setDividerLocation(500);
        splitPrincipal.setResizeWeight(0.35);
        add(splitPrincipal, BorderLayout.CENTER);

        JPanel panelInfo = new JPanel();
        panelInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel lblInfo = new JLabel("💡 Selecciona un nodo padre en el árbol y agrega elementos HTML");
        lblInfo.setForeground(new Color(100, 0, 200));
        panelInfo.add(lblInfo);
        add(panelInfo, BorderLayout.NORTH);
    }

    private void agregarNodo() { /* Lógica sin cambios */ }
    private void eliminarNodo() { /* Lógica sin cambios */ }
    private void limpiarArbol() { /* Lógica sin cambios */ }
    private void actualizarVistaHTML() { /* Lógica sin cambios */ }
    private void actualizarVistaPrevia(String htmlCode) { /* Lógica sin cambios */ }
    private void generarHTML(DefaultMutableTreeNode nodo, StringBuilder html, int nivel) { /* Lógica sin cambios */ }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimuladorDOM ventana = new SimuladorDOM();
            ventana.setVisible(true);
        });
    }
}
