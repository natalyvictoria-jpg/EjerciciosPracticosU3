package Nataly;

//@author Nataly Victoria Gonzalez Aviles

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.RenderingHints;

public class ArbolBB extends JFrame {

    private Nodo raiz;
    private JPanel panelDibujo;
    private JTextField txtValor;
    private JTextArea txtResultado;

    // Guardamos botones en variables
    private JButton btnInsertar, btnEliminar, btnBuscar, btnLimpiar;
    private JButton btnInOrden, btnPreOrden, btnPostOrden, btnEjecutar;
    private JComboBox<String> comboPruebas;

    public ArbolBB() {
        configurarVentana();
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
    }

    private void configurarVentana() {
        setTitle("Árbol Binario de Búsqueda - Nataly");
        setSize(1300, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.decode("#F8BBE6"));
    }

    private void inicializarComponentes() {
        panelDibujo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (raiz != null) {
                    dibujarArbol(g2d, raiz, getWidth() / 2, 50, getWidth() / 4);
                }
            }
        };
        panelDibujo.setBackground(Color.WHITE);

        txtValor = new JTextField(10);
        txtValor.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtValor.setBorder(BorderFactory.createLineBorder(Color.decode("#F06292"), 2));

        txtResultado = new JTextArea(9, 35);
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Consolas", Font.PLAIN, 15));
        txtResultado.setBackground(Color.decode("#AD1457"));
        txtResultado.setForeground(Color.WHITE);

        String[] pruebas = {
                "Seleccionar Prueba...",
                "P1.1 Árbol Balanceado",
                "P1.2 Degenerado Derecha",
                "P1.3 Degenerado Izquierda",
                "P1.4 Duplicados"
        };
        comboPruebas = new JComboBox<>(pruebas);

        // 🌸 Crear botones
        btnInsertar = crearBoton("Insertar");
        btnEliminar = crearBoton("Eliminar");
        btnBuscar = crearBoton("Buscar");
        btnLimpiar = crearBoton("Limpiar");

        btnInOrden = crearBoton("InOrden");
        btnPreOrden = crearBoton("PreOrden");
        btnPostOrden = crearBoton("PostOrden");
        btnEjecutar = crearBoton("Ejecutar");
    }

    private void configurarLayout() {
        JPanel panelSuperior = new JPanel(new FlowLayout());
        panelSuperior.setBackground(Color.decode("#F48FB1"));

        panelSuperior.add(new JLabel("Valor:"));
        panelSuperior.add(txtValor);
        panelSuperior.add(btnInsertar);
        panelSuperior.add(btnEliminar);
        panelSuperior.add(btnBuscar);
        panelSuperior.add(btnLimpiar);

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(Color.decode("#F8BBE6"));

        JPanel panelRecorridos = new JPanel(new FlowLayout());
        panelRecorridos.setBackground(Color.decode("#F8BBE6"));
        panelRecorridos.add(new JLabel("Recorridos:"));
        panelRecorridos.add(btnInOrden);
        panelRecorridos.add(btnPreOrden);
        panelRecorridos.add(btnPostOrden);

        JPanel panelPruebas = new JPanel(new FlowLayout());
        panelPruebas.setBackground(Color.decode("#F8BBE6"));
        panelPruebas.add(new JLabel("Pruebas Automáticas:"));
        panelPruebas.add(comboPruebas);
        panelPruebas.add(btnEjecutar);

        JPanel panelTexto = new JPanel();
        panelTexto.add(new JScrollPane(txtResultado));
        panelTexto.setBackground(Color.decode("#F8BBE6"));

        JScrollPane scrollArbol = new JScrollPane(panelDibujo);
        scrollArbol.setBorder(BorderFactory.createTitledBorder("🌸 Visualización del Árbol"));

        panelInferior.add(panelRecorridos, BorderLayout.NORTH);
        panelInferior.add(panelPruebas, BorderLayout.CENTER);
        panelInferior.add(panelTexto, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);
        add(scrollArbol, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private JButton crearBoton(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(Color.decode("#EC407A"));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));

        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { b.setBackground(Color.decode("#D81B60")); }
            @Override
            public void mouseExited(MouseEvent e) { b.setBackground(Color.decode("#EC407A")); }
        });
        return b;
    }

    private void configurarEventos() {

        btnInsertar.addActionListener(e -> {
            try {
                insertar(Integer.parseInt(txtValor.getText()));
                panelDibujo.repaint();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Número inválido"); }
        });

        btnEliminar.addActionListener(e -> {
            try {
                raiz = eliminar(raiz, Integer.parseInt(txtValor.getText()));
                panelDibujo.repaint();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Número inválido"); }
        });

        btnBuscar.addActionListener(e -> {
            try {
                int v = Integer.parseInt(txtValor.getText());
                txtResultado.append("Buscar " + v + ": " + (buscar(raiz, v) ? "Encontrado ✓\n" : "No encontrado ✗\n"));
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Número inválido"); }
        });

        btnLimpiar.addActionListener(e -> {
            raiz = null;
            txtResultado.setText("");
            panelDibujo.repaint();
        });

        btnInOrden.addActionListener(e -> txtResultado.append("InOrden: " + inOrden(raiz) + "\n"));
        btnPreOrden.addActionListener(e -> txtResultado.append("PreOrden: " + preOrden(raiz) + "\n"));
        btnPostOrden.addActionListener(e -> txtResultado.append("PostOrden: " + postOrden(raiz) + "\n"));
    }

    // ----------------------------------------------
    // Árbol
    // ----------------------------------------------
    private static class Nodo {
        int valor;
        Nodo izq, der;
        Nodo(int v) { valor = v; }
    }

    private void insertar(int valor) { raiz = insertarR(raiz, valor); }

    private Nodo insertarR(Nodo n, int valor) {
        if (n == null) return new Nodo(valor);
        if (valor < n.valor) n.izq = insertarR(n.izq, valor);
        else n.der = insertarR(n.der, valor);
        return n;
    }

    private boolean buscar(Nodo n, int v) {
        if (n == null) return false;
        if (n.valor == v) return true;
        return v < n.valor ? buscar(n.izq, v) : buscar(n.der, v);
    }

    private Nodo eliminar(Nodo n, int v) {
        if (n == null) return null;

        if (v < n.valor) n.izq = eliminar(n.izq, v);
        else if (v > n.valor) n.der = eliminar(n.der, v);
        else {
            if (n.izq == null) return n.der;
            if (n.der == null) return n.izq;
            Nodo s = n.der;
            while (s.izq != null) s = s.izq;
            n.valor = s.valor;
            n.der = eliminar(n.der, s.valor);
        }
        return n;
    }

    private String inOrden(Nodo n) {
        return n == null ? "" : inOrden(n.izq) + " " + n.valor + " " + inOrden(n.der);
    }

    private String preOrden(Nodo n) {
        return n == null ? "" : n.valor + " " + preOrden(n.izq) + " " + preOrden(n.der);
    }

    private String postOrden(Nodo n) {
        return n == null ? "" : postOrden(n.izq) + " " + postOrden(n.der) + " " + n.valor;
    }

    private void dibujarArbol(Graphics2D g, Nodo n, int x, int y, int sep) {
        if (n == null) return;

        g.setColor(Color.BLACK);
        if (n.izq != null) g.drawLine(x, y, x - sep, y + 60);
        if (n.der != null) g.drawLine(x, y, x + sep, y + 60);

        g.setColor(Color.decode("#FF80AB"));
        g.fillOval(x - 20, y - 20, 40, 40);

        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(n.valor), x - 6, y + 6);

        dibujarArbol(g, n.izq, x - sep, y + 60, sep / 2);
        dibujarArbol(g, n.der, x + sep, y + 60, sep / 2);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ArbolBB().setVisible(true));
    }
}

