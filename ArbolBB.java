// Author: Nataly Victoria Gonzalez Aviles
// Materia: Estructura de Datos
// Grupo: GTID0141

package Nataly;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.RenderingHints;

public class ArbolBB extends JFrame {

    // Raíz del árbol
    private Nodo raiz;

    // Componentes visuales
    private JPanel panelDibujo;
    private JTextField txtValor;
    private JTextArea txtResultado;

    private JButton btnInsertar, btnEliminar, btnBuscar, btnLimpiar;
    private JButton btnInOrden, btnPreOrden, btnPostOrden, btnEjecutar;
    private JComboBox<String> comboPruebas;

    public ArbolBB() {
        configurarVentana();
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
    }

    /**
     * Configura la ventana principal
     */
    private void configurarVentana() {
        setTitle("Árbol Binario de Búsqueda - Nataly");
        setSize(1300, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fondo general
        getContentPane().setBackground(Color.decode("#F8BBD0"));
    }

    /**
     * Inicializa todos los componentes visuales
     */
    private void inicializarComponentes() {

        // Panel donde se dibuja el árbol
        panelDibujo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Dibuja el árbol solo si existe raíz
                if (raiz != null) {
                    dibujarArbol(g2d, raiz, getWidth() / 2, 50, getWidth() / 4);
                }
            }
        };
        panelDibujo.setBackground(Color.WHITE);

        // Entrada de valor
        txtValor = new JTextField(10);
        txtValor.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtValor.setBorder(BorderFactory.createLineBorder(Color.decode("#F06292"), 2));

        // Área de texto para mostrar resultados
        txtResultado = new JTextArea(9, 35);
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Consolas", Font.PLAIN, 15));
        txtResultado.setBackground(Color.decode("#AD1457"));
        txtResultado.setForeground(Color.WHITE);

        // Pruebas automáticas
        String[] pruebas = {
                "Seleccionar Prueba...",
                "P1.1 Árbol Balanceado",
                "P1.2 Degenerado Derecha",
                "P1.3 Degenerado Izquierda",
                "P1.4 Duplicados"
        };
        comboPruebas = new JComboBox<>(pruebas);

        // Botones
        btnInsertar = crearBoton("Insertar");
        btnEliminar = crearBoton("Eliminar");
        btnBuscar = crearBoton("Buscar");
        btnLimpiar = crearBoton("Limpiar");

        btnInOrden = crearBoton("InOrden");
        btnPreOrden = crearBoton("PreOrden");
        btnPostOrden = crearBoton("PostOrden");
        btnEjecutar = crearBoton("Ejecutar");
    }

    /**
     * Configura la distribución visual de todos los paneles
     */
    private void configurarLayout() {

        // Panel superior con textfield y botones principales
        JPanel panelSuperior = new JPanel(new FlowLayout());
        panelSuperior.setBackground(Color.decode("#F48FB1"));

        panelSuperior.add(new JLabel("Valor:"));
        panelSuperior.add(txtValor);
        panelSuperior.add(btnInsertar);
        panelSuperior.add(btnEliminar);
        panelSuperior.add(btnBuscar);
        panelSuperior.add(btnLimpiar);

        // Panel inferior
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(Color.decode("#F8BBD0"));

        // Panel de recorridos
        JPanel panelRecorridos = new JPanel(new FlowLayout());
        panelRecorridos.setBackground(Color.decode("#F8BBD0"));
        panelRecorridos.add(new JLabel("Recorridos:"));
        panelRecorridos.add(btnInOrden);
        panelRecorridos.add(btnPreOrden);
        panelRecorridos.add(btnPostOrden);

        // Panel de pruebas automáticas
        JPanel panelPruebas = new JPanel(new FlowLayout());
        panelPruebas.setBackground(Color.decode("#F8BBD0"));
        panelPruebas.add(new JLabel("Pruebas Automáticas:"));
        panelPruebas.add(comboPruebas);
        panelPruebas.add(btnEjecutar);

        // Panel del área de texto
        JPanel panelTexto = new JPanel();
        panelTexto.add(new JScrollPane(txtResultado));
        panelTexto.setBackground(Color.decode("#F8BBD0"));

        // Panel con scroll para el árbol
        JScrollPane scrollArbol = new JScrollPane(panelDibujo);
        scrollArbol.setBorder(BorderFactory.createTitledBorder("🌸 Visualización del Árbol"));

        // Agregar paneles al layout principal
        panelInferior.add(panelRecorridos, BorderLayout.NORTH);
        panelInferior.add(panelPruebas, BorderLayout.CENTER);
        panelInferior.add(panelTexto, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);
        add(scrollArbol, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    /**
     * Crea un botón con estilo personalizado
     */
    private JButton crearBoton(String texto) {
        JButton b = new JButton(texto);

        b.setBackground(Color.decode("#EC407A"));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setBorder(BorderFactory.createLineBorder(Color.decode("#880E4F"), 2));

        // Efecto hover
        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b.setBackground(Color.decode("#AD1457"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                b.setBackground(Color.decode("#EC407A"));
            }
        });

        return b;
    }

    /**
     * Configura los eventos de los botones
     */
    private void configurarEventos() {

        // INSERTAR
        btnInsertar.addActionListener(e -> {
            try {
                insertar(Integer.parseInt(txtValor.getText()));
                panelDibujo.repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Número inválido");
            }
        });

        // ELIMINAR
        btnEliminar.addActionListener(e -> {
            try {
                raiz = eliminar(raiz, Integer.parseInt(txtValor.getText()));
                panelDibujo.repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Número inválido");
            }
        });

        // BUSCAR
        btnBuscar.addActionListener(e -> {
            try {
                int v = Integer.parseInt(txtValor.getText());
                txtResultado.append("Buscar " + v + ": " +
                        (buscar(raiz, v) ? "Encontrado ✓\n" : "No encontrado ✗\n"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Número inválido");
            }
        });

        // LIMPIAR
        btnLimpiar.addActionListener(e -> {
            raiz = null;
            txtResultado.setText("");
            panelDibujo.repaint();
        });

        // RECORRIDOS
        btnInOrden.addActionListener(e -> txtResultado.append("InOrden: " + inOrden(raiz) + "\n"));
        btnPreOrden.addActionListener(e -> txtResultado.append("PreOrden: " + preOrden(raiz) + "\n"));
        btnPostOrden.addActionListener(e -> txtResultado.append("PostOrden: " + postOrden(raiz) + "\n"));

        // PRUEBAS AUTOMÁTICAS
        btnEjecutar.addActionListener(e -> {
            String opcion = (String) comboPruebas.getSelectedItem();
            txtResultado.append("\n=== Ejecutando " + opcion + " ===\n");

            raiz = null;
            panelDibujo.repaint();

            switch (opcion) {

                case "P1.1 Árbol Balanceado":
                    int[] bal = {50, 30, 70, 20, 40, 60, 80};
                    for (int n : bal) insertar(n);
                    txtResultado.append("Insertados: 50 30 70 20 40 60 80\n");
                    break;

                case "P1.2 Degenerado Derecha":
                    int[] der = {10, 20, 30, 40, 50, 60};
                    for (int n : der) insertar(n);
                    txtResultado.append("Insertados: 10 20 30 40 50 60\n");
                    break;

                case "P1.3 Degenerado Izquierda":
                    int[] izq = {60, 50, 40, 30, 20, 10};
                    for (int n : izq) insertar(n);
                    txtResultado.append("Insertados: 60 50 40 30 20 10\n");
                    break;

                case "P1.4 Duplicados":
                    int[] dup = {30, 15, 30, 15, 45, 45};
                    for (int n : dup) insertar(n);
                    txtResultado.append("Insertados: 30 15 30 15 45 45\n");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Selecciona una prueba válida.");
                    return;
            }

            panelDibujo.repaint();

            txtResultado.append("InOrden:  " + inOrden(raiz) + "\n");
            txtResultado.append("PreOrden: " + preOrden(raiz) + "\n");
            txtResultado.append("PostOrden:" + postOrden(raiz) + "\n");
        });
    }

    // ============================================================
    // --------------------- ÁRBOL BINARIO -------------------------
    // ============================================================

    /**
     * Clase Nodo del Árbol
     */
    private static class Nodo {
        int valor;
        Nodo izq, der;

        Nodo(int v) { valor = v; }
    }

    /**
     * Inserta un valor en el árbol
     */
    private void insertar(int valor) { raiz = insertarR(raiz, valor); }

    /**
     * Inserción recursiva
     */
    private Nodo insertarR(Nodo n, int valor) {
        if (n == null) return new Nodo(valor);
        if (valor < n.valor) n.izq = insertarR(n.izq, valor);
        else n.der = insertarR(n.der, valor); // incluye duplicados (van a la derecha)
        return n;
    }

    /**
     * Búsqueda recursiva
     */
    private boolean buscar(Nodo n, int v) {
        if (n == null) return false;
        if (n.valor == v) return true;
        return v < n.valor ? buscar(n.izq, v) : buscar(n.der, v);
    }

    /**
     * Eliminación de un nodo en ABB
     */
    private Nodo eliminar(Nodo n, int v) {
        if (n == null) return null;

        if (v < n.valor) n.izq = eliminar(n.izq, v);
        else if (v > n.valor) n.der = eliminar(n.der, v);
        else {
            // Caso 1: sin hijos o 1 hijo
            if (n.izq == null) return n.der;
            if (n.der == null) return n.izq;

            // Caso 2: dos hijos → encontrar sucesor
            Nodo s = n.der;
            while (s.izq != null) s = s.izq;

            n.valor = s.valor;
            n.der = eliminar(n.der, s.valor);
        }
        return n;
    }

    /**
     * Recorrido InOrden
     */
    private String inOrden(Nodo n) {
        return n == null ? "" : inOrden(n.izq) + " " + n.valor + " " + inOrden(n.der);
    }

    /**
     * Recorrido PreOrden
     */
    private String preOrden(Nodo n) {
        return n == null ? "" : n.valor + " " + preOrden(n.izq) + " " + preOrden(n.der);
    }

    /**
     * Recorrido PostOrden
     */
    private String postOrden(Nodo n) {
        return n == null ? "" : postOrden(n.izq) + " " + postOrden(n.der) + " " + n.valor;
    }

    /**
     * Dibuja gráficamente el árbol recursivamente
     */
    private void dibujarArbol(Graphics2D g, Nodo n, int x, int y, int sep) {
        if (n == null) return;

        g.setColor(Color.BLACK);
        if (n.izq != null) g.drawLine(x, y, x - sep, y + 60);
        if (n.der != null) g.drawLine(x, y, x + sep, y + 60);

        // Nodo rosa
        g.setColor(Color.decode("#F06292"));
        g.fillOval(x - 20, y - 20, 40, 40);

        // Texto del nodo
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(n.valor), x - 6, y + 6);

        // Llamadas recursivas
        dibujarArbol(g, n.izq, x - sep, y + 60, sep / 2);
        dibujarArbol(g, n.der, x + sep, y + 60, sep / 2);
    }

    /**
     * Método main para iniciar la aplicación
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ArbolBB().setVisible(true));
    }
}
