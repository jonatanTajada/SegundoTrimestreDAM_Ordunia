package com.gestorventasapp.util;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import com.gestorventasapp.view.ClienteView;
import com.gestorventasapp.view.DetalleVentaView;
import com.gestorventasapp.view.ProductoView;
import com.gestorventasapp.view.VentaView;

import java.awt.*;

public class EstiloUI {

    // Colores y fuentes principales
    public static final Color COLOR_PRIMARIO = new Color(41, 128, 185);
    public static final Color COLOR_SECUNDARIO = new Color(236, 240, 241);
    public static final Color COLOR_TEXTO = new Color(44, 62, 80);
    public static final Color COLOR_HOVER = new Color(52, 152, 219);
    public static final Font FUENTE_TITULO = new Font("Poppins", Font.BOLD, 18);
    public static final Font FUENTE_GENERAL = new Font("Poppins", Font.PLAIN, 14);


    /**
     * Método para crear un encabezado uniforme con el título, logo y hora actual.
     * @param titulo Título del encabezado.
     * @return JPanel con el diseño del encabezado.
     */
    public static JPanel crearEncabezado(String titulo) {
        JPanel panelEncabezado = new JPanel(new BorderLayout());
        panelEncabezado.setBackground(COLOR_PRIMARIO);

        // Título del encabezado
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        panelEncabezado.add(lblTitulo, BorderLayout.CENTER);

        // Logo en el lado derecho
        JLabel lblLogo = new JLabel();
        ImageIcon logoIcon = new ImageIcon(EstiloUI.class.getResource("/logo.jpg"));
        lblLogo.setIcon(new ImageIcon(logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        lblLogo.setHorizontalAlignment(SwingConstants.RIGHT);

        panelEncabezado.add(lblLogo, BorderLayout.EAST);

        // Hora actual en el lado izquierdo
        JLabel lblHora = new JLabel();
        lblHora.setFont(FUENTE_GENERAL);
        lblHora.setForeground(Color.WHITE);
        lblHora.setHorizontalAlignment(SwingConstants.LEFT);
        actualizarHora(lblHora); // Método para configurar el Timer

        panelEncabezado.add(lblHora, BorderLayout.WEST);

        return panelEncabezado;
    }

    /**
     * Método para actualizar dinámicamente la hora en un JLabel.
     * @param lblHora JLabel donde se mostrará la hora.
     */
    private static void actualizarHora(JLabel lblHora) {
        Timer timer = new Timer(1000, e -> {
            String horaActual = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));
            lblHora.setText("Hora: " + horaActual);
        });
        timer.start(); // Iniciar el Timer
    }


    /**
     * Aplica estilo a botones con diseño moderno.
     * @param boton JButton al que se le aplicará el estilo.
     */
    public static void aplicarEstiloBoton(JButton boton) {
        // Fondo claro con bordes redondeados
        boton.setBackground(new Color(210, 230, 250)); // Color suave para el fondo
        boton.setForeground(COLOR_TEXTO); // Color del texto
        boton.setFont(FUENTE_GENERAL); // Fuente general
        boton.setFocusPainted(false); // Eliminar borde de foco
        boton.setBorder(new RoundedBorder(15)); // Borde redondeado con mayor radio
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursor tipo mano

        // Efecto hover (cambio de color al pasar el ratón)
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(180, 210, 240)); // Color más oscuro en hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(210, 230, 250)); // Color original
            }
        });

        // Añadir sombra suave al botón (opcional)
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 180, 210), 1), // Borde exterior
                BorderFactory.createEmptyBorder(10, 20, 10, 20) // Padding interno
        ));
    }


    /**
     * Aplica un estilo moderno y uniforme a los botones del workspace.
     *
     * @param boton JButton al que se le aplicará el estilo.
     */
    public static void aplicarEstiloBotonWorkspace(JButton boton) {
        // Fondo base oscuro
        boton.setBackground(new Color(50, 50, 50)); // Fondo oscuro
        boton.setForeground(Color.WHITE); // Texto en blanco
        boton.setFont(new Font("Poppins", Font.BOLD | Font.ITALIC, 16)); // Fuente elegante
        boton.setFocusPainted(false); // Sin borde de foco
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursor tipo mano
        boton.setBorder(new RoundedBorder(20)); // Bordes redondeados

        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(Color.WHITE); // Cambiar a fondo blanco
                boton.setForeground(Color.BLACK); // Cambiar texto a negro para contraste
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(50, 50, 50)); // Restaurar fondo oscuro
                boton.setForeground(Color.WHITE); // Restaurar texto blanco
            }
        });

        // Efecto sombra personalizada y eliminación de bordes visibles
        boton.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Dibujar fondo del botón con bordes redondeados
                g2.setColor(boton.getBackground());
                g2.fillRoundRect(0, 0, boton.getWidth(), boton.getHeight(), 20, 20);

                // Dibujar texto del botón
                super.paint(g, c);
            }
        });

        // Eliminar bordes adicionales
        boton.setContentAreaFilled(false);
        boton.setOpaque(false);
    }


    
    
    /**
     * Configura el estilo de las tablas.
     * @param tabla JTable a la que se aplicará el estilo.
     */
    public static void configurarEstiloTabla(JTable tabla) {
        tabla.setFont(FUENTE_GENERAL);
        tabla.setRowHeight(25);
        tabla.setGridColor(COLOR_SECUNDARIO);

        // Header
        JTableHeader header = tabla.getTableHeader();
        header.setBackground(COLOR_PRIMARIO);
        header.setForeground(Color.WHITE);
        header.setFont(FUENTE_GENERAL);

        // Alternar colores en las filas
        tabla.setDefaultRenderer(Object.class, new AlternatingRowRenderer());
    }

    /**
     * Aplica estilo a etiquetas (JLabel) para un diseño moderno y uniforme.
     * @param etiqueta JLabel al que se le aplicará el estilo.
     */
    public static void aplicarEstiloEtiqueta(JLabel etiqueta) {
        etiqueta.setFont(FUENTE_GENERAL);
        etiqueta.setForeground(COLOR_TEXTO);
    }

    /**
     * Aplica estilo a los JTextField (campos de texto) para un diseño uniforme.
     * @param campoTexto JTextField al que se le aplicará el estilo.
     */
    public static void aplicarEstiloCampoTexto(JTextField campoTexto) {
        campoTexto.setFont(FUENTE_GENERAL);
        campoTexto.setForeground(COLOR_TEXTO);
        campoTexto.setBorder(BorderFactory.createLineBorder(COLOR_PRIMARIO, 1));
        campoTexto.setBackground(Color.WHITE);
    }

    /**
     * Crea una etiqueta estilizada para el área de trabajo.
     * @param texto Texto que mostrará la etiqueta.
     * @return JLabel configurado.
     */
    public static JLabel crearEtiquetaWorkspace(String texto) {
        JLabel lblWorkspace = new JLabel(texto);
        lblWorkspace.setFont(FUENTE_GENERAL);
        lblWorkspace.setForeground(COLOR_TEXTO);
        lblWorkspace.setHorizontalAlignment(SwingConstants.CENTER);
        lblWorkspace.setBorder(new EmptyBorder(10, 0, 10, 0));
        return lblWorkspace;
    }

    /**
     * Clase para bordes redondeados en botones.
     */
    static class RoundedBorder implements Border {

        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 1, radius + 1);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    /**
     * Clase para alternar colores de las filas de la tabla.
     */
    static class AlternatingRowRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (!isSelected) {
                c.setBackground(row % 2 == 0 ? Color.WHITE : COLOR_SECUNDARIO);
            }
            return c;
        }
    }
    
    /**
     * Aplica estilo a un panel de botones para mantener uniformidad en los módulos.
     * 
     * @param panel JPanel al que se le aplicará el estilo.
     */
    public static void aplicarEstiloPanelBotones(JPanel panel) {
        panel.setBackground(COLOR_SECUNDARIO); // Fondo uniforme
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Espaciado interno
    }

    /**
     * Crea un panel con un buscador dinámico.
     * 
     * @param textoEtiqueta Texto de la etiqueta que acompaña al buscador.
     * @param campoBuscador JTextField donde el usuario ingresará el texto.
     * @return JPanel con diseño y estilo aplicado.
     */
    public static JPanel crearPanelBuscadorDinamico(String textoEtiqueta, JTextField campoBuscador) {
        JPanel panelBuscador = new JPanel(new BorderLayout(10, 0)); // Espaciado entre componentes
        panelBuscador.setBackground(COLOR_SECUNDARIO); // Fondo uniforme

        // Crear etiqueta y aplicar estilo
        JLabel lblEtiqueta = new JLabel(textoEtiqueta);
        aplicarEstiloEtiqueta(lblEtiqueta);
        panelBuscador.add(lblEtiqueta, BorderLayout.WEST);

        // Aplicar estilo al campo de texto
        aplicarEstiloCampoTexto(campoBuscador);
        panelBuscador.add(campoBuscador, BorderLayout.CENTER);

        return panelBuscador;
    }

    /**
     * Crea una barra de menú común para las vistas principales de la aplicación.
     * La barra incluye opciones para acceder a los módulos de gestión y un apartado de ayuda.
     *
     * @param ventana La ventana (JFrame) en la que se añadirá la barra de menú.
     * @return JMenuBar configurado con las opciones de la aplicación.
     */
    public static JMenuBar crearBarraMenu(JFrame ventana) {
        JMenuBar barraMenu = new JMenuBar();

        // Menú "Gestión"
        JMenu menuGestion = new JMenu("Gestión");
        JMenuItem menuClientes = new JMenuItem("Clientes");
        JMenuItem menuVentas = new JMenuItem("Ventas");
        JMenuItem menuProductos = new JMenuItem("Productos");
        JMenuItem menuDetalles = new JMenuItem("Detalles de Ventas");

        // Agregar acciones a los elementos
        menuClientes.addActionListener(e -> new ClienteView().mostrar());
        menuVentas.addActionListener(e -> new VentaView().mostrar());
        menuProductos.addActionListener(e -> new ProductoView().mostrar());
        menuDetalles.addActionListener(e -> new DetalleVentaView().mostrar());

        // Añadir elementos al menú
        menuGestion.add(menuClientes);
        menuGestion.add(menuVentas);
        menuGestion.add(menuProductos);
        menuGestion.add(menuDetalles);

        barraMenu.add(menuGestion);

        // Menú "Ayuda"
        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem menuAcercaDe = new JMenuItem("Acerca de");
        menuAcercaDe.addActionListener(e -> JOptionPane.showMessageDialog(ventana, "Gestión de Negocio v1.0"));
        menuAyuda.add(menuAcercaDe);

        barraMenu.add(menuAyuda);

        return barraMenu;
    }


	
}
