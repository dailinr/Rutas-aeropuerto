
package gui;

import datos.Ciudad;
import datos.Viaje;
import grafo.Grafo;
import gui.util.Vector2D;
import gui.util.Vector2F;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

class Flecha {
    public Vector2D A;
    public Vector2D B;
    public Vector2D R1;
    public Vector2D R2;
    public Vector2D StrPos;
    public Viaje cost;

    public Flecha(Vector2D a, Vector2D b, Vector2D r1, Vector2D r2, Vector2D StrPos, Viaje cost) {
        A = a;
        B = b;
        R1 = r1;
        R2 = r2;
        this.StrPos = StrPos;
        this.cost = cost;
    }
}

/**
 * @author david
 */
public class Lienzo {
    // Optimizacion
    private static final HashMap<Integer, Flecha> flechas = new HashMap<>();

    public static ArrayList<Integer> modified = new ArrayList<>();

    private static final Font font = new Font("Monospaced", Font.BOLD, 16);

    private static final Color colorCirculo = new Color(253, 254, 254);

    private static final Color bordeCirculo = new Color(33, 47, 60);

    private static final Color colorTexto = new Color(248, 249, 249);

    private static final Color colorFlecha = new Color(213, 245, 227);

    private static final BasicStroke stroke = new BasicStroke(2);

    private static final double tetha = 40 * Math.PI / 180;

    private static final double costetha = Math.cos(tetha);

    private static final double sintetha = Math.sin(tetha);

    private static final Vector2D camera = new Vector2D(0, 0);

    private static double scale = 1.0;

    private static final int RADIO = 30;

    private static final double MIN_SCALE = 0.7;

    private static final double MAX_SCALE = 1.8;

    private static int radio = RADIO;

    public static void dibujarGrafo(Graphics2D g, Grafo<Ciudad, Viaje> grafo) {
        for (int i = 0; i < grafo.orden(); i++) {
            var c = grafo.getVertice(i);
            var cp = c.getPosition();
            pintarCirculo(g, c.getNombre(), cp.x, cp.y, colorCirculo);
            for (int j = 0; j < grafo.orden(); j++) {
                var ccost = grafo.getCosto(i, j);

                if (ccost == null) continue;

                var flecha = flechas.get(i + j * grafo.orden());

                if (flecha != null && !modified.contains(i) && !modified.contains(j)) pintarFlecha(g, flecha);

                var ccp = grafo.getVertice(j).getPosition();

                flechas.put(i + j * grafo.orden(), pintarFlecha(g, cp.x, cp.y, ccp.x, ccp.y, ccost, colorFlecha));
            }
        }
        modified.clear();
    }

    public static void pintarCirculo(Graphics2D g, String nombre, int x, int y, Color color) {
        x += camera.x; y += camera.y;
        x *= scale; y *= scale;

        g.setColor(color);
        g.fillOval(x, y, radio, radio);

        g.setColor(bordeCirculo);
        g.drawOval(x, y, radio, radio);

        g.setColor(colorTexto);
        g.setFont(font);
        var strMetrics = g.getFontMetrics();
        var width = strMetrics.stringWidth(nombre);
        g.drawString(nombre, x + radio / 2 - width / 2, y + (int)(radio * 1.7));
    }

    private static void pintarFlecha(Graphics2D g, Flecha f) {
        g.setStroke(stroke);
        g.setColor(colorFlecha);
        g.drawLine(f.A.x, f.A.y, f.B.x, f.B.y);
        g.drawLine(f.A.x, f.A.y, f.A.x + f.R1.x, f.A.y + f.R1.y);
        g.drawLine(f.A.x, f.A.y, f.A.x + f.R2.x , f.A.y + f.R2.y);
        g.setFont(font);
        g.drawString(f.cost.getTiempo() + " h", f.StrPos.x, f.StrPos.y);
    }

    public static Flecha pintarFlecha(Graphics2D g, int x1, int y1, int x2, int y2, Viaje v, Color c) {
        g.setColor(c);
        g.setStroke(stroke);
        // Matematicas
        x1 += camera.x; x2 += camera.x;
        y1 += camera.y; y2 += camera.y;
        x1 *= scale; x2 *= scale;
        y1 *= scale; y2 *= scale;

        // Hallar el centro del circulo
        Vector2D B = new Vector2D(x1 + radio / 2, y1 + radio / 2);
        Vector2D A = new Vector2D(x2 + radio / 2, y2 + radio / 2);

        // Calcular las flechitas
        // Vertor desde A hasta B
        Vector2D AB = new Vector2D(B.x - A.x, B.y - A.y);
        // Magnitud
        double len = Math.sqrt(AB.x * AB.x + AB.y * AB.y);
        // Vector unitario
        Vector2F ABu = new Vector2F((double)AB.x / len, (double)AB.y / len);

        // Calculando el punto mas cercano al exterior del circulo
        A = A.plus(ABu.by(radio >> 1));
        B = B.mines(ABu.by(radio >> 1));

        g.drawLine(A.x, A.y, B.x, B.y);

        Vector2F r1 = new Vector2F(
                ABu.x * costetha - ABu.y * sintetha,
                ABu.x * sintetha + ABu.y * costetha
        );
        Vector2F r2 = new Vector2F(
                ABu.x * costetha - ABu.y * (-sintetha),
                ABu.x * (-sintetha) + ABu.y * costetha
        );

        r1 = r1.by(radio >> 1);
        r2 = r2.by(radio >> 1);

        g.drawLine(A.x, A.y, A.x + r1.x.intValue() , A.y + r1.y.intValue());
        g.drawLine(A.x, A.y, A.x + r2.x.intValue() , A.y + r2.y.intValue());

        g.setFont(font);

        var ABup = new Vector2F(ABu.y, -ABu.x);
        var ABm = new Vector2D(A.x + AB.x / 2, A.y + AB.y / 2);

        var StrPos = new Vector2D(ABm.x + (int)(25.0 * ABup.x), ABm.y + (int)(25.0 * ABup.y));

        g.drawString(v.getTiempo() + " h", StrPos.x, StrPos.y);

        var R1 = new Vector2D(r1.x, r1.y);
        var R2 = new Vector2D(r2.x, r2.y);

        return new Flecha(A, B, R1, R2, StrPos, v);
    }

    public static void clickSobreNodo(Graphics2D g, int x, int y, Color co) {
        x += camera.x; y += camera.y;
        x *= scale; y *= scale;
        g.setColor(co);
        g.setStroke(new BasicStroke(4));
        g.fillOval(x, y, radio, radio);
        g.setColor(Color.black);
        g.drawOval(x, y, radio, radio);
    }

    public static void moverCamara(int dx, int dy) {
        camera.x += dx; camera.y += dy;
    }

    public static int hayCiudadEn(Grafo<Ciudad, Viaje> grafo, int x, int y) {
        var pos = pos(x, y);

        var r = new Rectangle(RADIO, RADIO);

        for (int i = 0; i < grafo.orden(); i++) {
            var c = grafo.getVertice(i);
            var cp = c.getPosition();
            r.x = cp.x;
            r.y = cp.y;
            if (r.contains(pos.x, pos.y)) {
                return i;
            }
        }
        return -1;
    }

    public static Vector2D pos(int x, int y) {
        x /= scale; y /= scale;
        x -= camera.x; y -= camera.y;
        return new Vector2D(x, y);
    }

    public static void setScale(double ds) {
        scale = Math.max(MIN_SCALE, Math.min(MAX_SCALE, scale + ds));
        radio = (int) (RADIO * scale);
    }

}
