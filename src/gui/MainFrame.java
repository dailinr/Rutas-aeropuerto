/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import datos.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.text.AttributeSet.ColorAttribute;

import grafo.DFS;
import grafo.Floyd;
import grafo.Grafo;
import grafo.GrafoD;
import gui.util.Vector2D;
import persist.ArchivoProyecto;

/**
 * @author david
 */
public class MainFrame extends javax.swing.JFrame {
    Grafo<Ciudad, Viaje> grafo = new GrafoD<>();

    public MainFrame() throws ClassNotFoundException, IOException {
        initComponents();
    }

    private void initComponents() throws ClassNotFoundException, IOException {
        ArchivoProyecto archivoProyecto = new ArchivoProyecto();
        File f = new File("grafo.obj");
        grafo = f.exists() ? archivoProyecto.read() : grafo;

        JPanel canvas = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Lienzo.dibujarGrafo((Graphics2D) g, grafo);
                g.dispose();
            }
        };

        JPanel resultado = new JPanel();
        JTextField jtext = new JTextField();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                int height = e.getComponent().getHeight() - 120;
                canvas.setSize(e.getComponent().getWidth(), height);
                resultado.setBounds(0, e.getComponent().getHeight() - 120, e.getComponent().getWidth() - 30, 70);
                jtext.setBounds(10, 20, e.getComponent().getWidth() - 50, 40);
            }
        });

        getContentPane().add(resultado);
        setTitle("Proyecto grafos");
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(1200, 720));
        setBackground(new Color(19, 141, 117, 255));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        canvas.setBackground(new java.awt.Color(27, 38, 49, 255));
        jtext.setEnabled(false);
        jtext.setDisabledTextColor(Color.BLACK);
        
        resultado.setBorder(BorderFactory.createTitledBorder("Resultado"));
        resultado.setLayout(null);
        resultado.add(jtext);
        MouseAdapter ml = new MyMouseListener(canvas, jtext, grafo);
        WindowAdapter w1 = new MyWindowListener(archivoProyecto, grafo);
        canvas.addMouseListener(ml);
        canvas.addMouseMotionListener(ml);
        canvas.addMouseWheelListener(ml);
        addWindowListener(w1);
        getContentPane().add(canvas);

    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            JFrame f;
            try {
                f = new MainFrame();
                f.setVisible(true);
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }

        });
    }
}

class MyMouseListener extends MouseAdapter {
    Ciudad selected = null;

    private final Grafo<Ciudad, Viaje> grafo;
    private int n = 0;
    int nodo1 = -1, nodo2 = -1;
    JPanel panel;
    Graphics2D g;
    JTextField jtext;

    boolean popupmenuopen = false;

    private Floyd<Ciudad, Viaje> floyd = null;

    private List<Ciudad> dfsResult = null;

    public MyMouseListener(JPanel panel, JTextField jtext, Grafo<Ciudad, Viaje> grafo) {
        this.grafo = grafo;
        this.panel = panel;
        this.jtext = jtext;
    }

    public List<Integer> masSalidas(Grafo<Ciudad, Viaje> grafo) {
        List<Integer> masSalidas = new ArrayList<>();
        int mayor = -1;
        for (int i = 0; i < grafo.orden(); i++) {
            if (grafo.getSucesores(i).size() > mayor) {
                mayor = grafo.getSucesores(i).size();
                masSalidas.clear();
                masSalidas.add(i);
            }
            if (grafo.getSucesores(i).size() == mayor && !masSalidas.contains(i)) {
                masSalidas.add(i);
            }
        }
        return masSalidas;
    }

    private Viaje drawPath(int i, int destiny) {
        if (i == destiny) {
            return null;
        }

        int j = floyd.getRecorridos()[i][destiny];
        if (j == -1) {
            JOptionPane.showMessageDialog(panel, "No existe una ruta");
            return null;
        }

        Viaje viaje = grafo.getCosto(i, j);
        while (viaje == null) {
            j = floyd.getRecorridos()[i][j];
            viaje = grafo.getCosto(i, j);
        }

        Vector2D c1p = grafo.getVertice(i).getPosition();
        Vector2D c2p = grafo.getVertice(j).getPosition();
        Lienzo.pintarFlecha(g, c1p.x, c1p.y, c2p.x, c2p.y, viaje, Color.red);
        drawPath(j, destiny);
        return floyd.getMinimasDistancias()[i][destiny];
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        g = (Graphics2D) panel.getGraphics();

        int nodoClickeado = Lienzo.hayCiudadEn(grafo, evt.getX(), evt.getY());

        if (evt.getButton() == MouseEvent.BUTTON1) {
            if (floyd != null) {
                if (nodoClickeado == -1) {
                    floyd = null;
                    return;
                }

                var viaje = drawPath(PopupMenu.selected, nodoClickeado);
                if (viaje != null)
                    jtext.setText(viaje.toString());
                floyd = null;
                return;
            }
            int op = PopupMenu.click(evt.getX(), evt.getY());
            boolean op1 = PopupMenu.click2(evt.getX(), evt.getY());
            if (op1 && popupmenuopen) {
                List<Integer> indexes = masSalidas(grafo);
                List<String> ciudades = new ArrayList<>();
                for (var index : indexes) {
                    var city = grafo.getVertice(index);
                    var p = city.getPosition();
                    ciudades.add(city.getNombre());
                    Lienzo.pintarCirculo(g, city.getNombre(), p.x, p.y, Color.red);
                }
                panel.repaint(PopupMenu.rect1);
                jtext.setText(ciudades.toString());
                return;
            }
            else if (op != -1 && popupmenuopen) {
                if (op == 0) {
                    grafo.removeVertice(PopupMenu.selected);
                } else if (op == 1) {
                    grafo.aislar(PopupMenu.selected);
                } else if (op == 2) {
                    DFS<Ciudad, Viaje> dfs = new DFS<>();
                    dfsResult = dfs.recorridoDFS(grafo, PopupMenu.selected);
                    List<String> ciudades = new ArrayList<>();
                    for (var ciudad : dfsResult) {
                        ciudades.add(ciudad.getNombre());
                    }
                    jtext.setText(ciudades.toString());
                } else if (op == 3) {
                    floyd = new Floyd<>(grafo, new TimeIndicator(), Viaje.class);
                    jtext.setText("Selecciona una ciudad como destino!");
                } else if (op == 4) {
                    floyd = new Floyd<>(grafo, new DistanceIndicator(), Viaje.class);
                    jtext.setText("Selecciona una ciudad como destino!");
                } else if (op == 5) {
                    floyd = new Floyd<>(grafo, new CostIndicator(), Viaje.class);
                    jtext.setText("Selecciona una ciudad como destino!");
                }

                if (dfsResult != null) {
                    for (var ciudad : dfsResult) {
                        var p = ciudad.getPosition();
                        Lienzo.pintarCirculo(g, ciudad.getNombre(), p.x, p.y, Color.red);
                    }
                    dfsResult = null;
                    panel.repaint(PopupMenu.rect);
                } else {
                    panel.repaint();
                }
                popupmenuopen = false;
                return;
            }
            if (op == -1 && popupmenuopen) {
                panel.repaint();
                popupmenuopen = false;
                return;
            }
            if (popupmenuopen) {
                panel.repaint();
                popupmenuopen = false;
                return;
            }

            boolean haynodo = false;

            if (nodoClickeado != -1) {
                var c = grafo.getVertice(nodoClickeado);
                var p = c.getPosition();
                Lienzo.clickSobreNodo(g, p.x, p.y, Color.green);
                haynodo = true;
                n++;
                if (nodo1 == -1 && nodo2 == -1) {
                    nodo1 = nodoClickeado;
                } else {
                    nodo2 = nodoClickeado;
                }
            }

            if (!haynodo) {
                String nombre, pais;
                try {
                    nombre = JOptionPane.showInputDialog("Nombre de la ciudad: ");
                    if (nombre == null)
                        throw new Exception();
                    pais = JOptionPane.showInputDialog("Nombre del pais: ");
                    if (pais == null)
                        throw new Exception();
                } catch (Exception e) {
                    return;
                }

                grafo.addVertice(new Ciudad(nombre, pais, Lienzo.pos(evt.getX(), evt.getY())));
                panel.repaint();
            }
            if (n == 2) {
                double costo, distancia;
                int tiempo;
                try {
                    costo = Double.parseDouble(JOptionPane.showInputDialog("Costo: "));
                    distancia = Double.parseDouble(JOptionPane.showInputDialog("Distancia: "));
                    tiempo = Integer.parseInt(JOptionPane.showInputDialog("Tiempo: "));
                } catch (Exception e) {
                    nodo1 = nodo2 = -1;
                    n = 0;
                    panel.repaint();
                    return;
                }
                grafo.addCosto(nodo1, nodo2, new Viaje(costo, distancia, tiempo));
                Lienzo.modified.add(nodo1);
                Lienzo.modified.add(nodo2);

                nodo1 = nodo2 = -1;
                n = 0;

                panel.repaint();
            }
        } else if (evt.getButton() == MouseEvent.BUTTON3) {
            var ci = Lienzo.hayCiudadEn(grafo, evt.getX(), evt.getY());

            if (ci == -1) {
                PopupMenu.draw2(evt.getX(), evt.getY(), g);
                popupmenuopen = true;
                return;
            }
            popupmenuopen = true;
            PopupMenu.draw(evt.getX(), evt.getY(), g, ci);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        oldMousePosition.x = e.getX();
        oldMousePosition.y = e.getY();
        var ci = Lienzo.hayCiudadEn(grafo, e.getX(), e.getY());

        if (ci != -1) {
            selected = grafo.getVertice(ci);
            return;
        }

        selected = null;

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        selected = null;
    }

    Vector2D oldMousePosition = new Vector2D(0, 0);

    @Override
    public void mouseDragged(MouseEvent e) {
        g = (Graphics2D) panel.getGraphics();
        popupmenuopen = false;
        if (selected == null) {
            Lienzo.moverCamara(e.getX() - oldMousePosition.x, e.getY() - oldMousePosition.y);
            oldMousePosition.x = e.getX();
            oldMousePosition.y = e.getY();
            panel.repaint();
            return;
        }

        selected.setPosition(Lienzo.pos(e.getX(), e.getY()));
        panel.repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        g = (Graphics2D) panel.getGraphics();
        super.mouseWheelMoved(e);
        var rot = e.getPreciseWheelRotation();
        Lienzo.setScale(-0.05 * rot);
        panel.repaint();
        for (int i = 0; i < grafo.orden(); i++) {
            Lienzo.modified.add(i);
        }
    }
}

class MyWindowListener extends WindowAdapter {
    ArchivoProyecto ar;
    private final Grafo<Ciudad, Viaje> grafo;

    public MyWindowListener(ArchivoProyecto archivoProyecto, Grafo<Ciudad, Viaje> grafo) {
        this.ar = archivoProyecto;
        this.grafo = grafo;
    }

    @Override
    public void windowClosed(WindowEvent e) {
        try {
            ar.write(grafo);
        } catch (IOException e1) {
            System.out.println("no se guardo");
            e1.printStackTrace();
        }
    }
}