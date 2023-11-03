package gui;

import java.awt.*;

public class PopupMenu {
    final static int OPTION_HEIGHT = 40;
    final static int OPTION_WIDTH = 200;
    final static int FONT_HEIGHT = 16;

    final static String[] options = new String[] {
            "Eliminar", "Aislar", "Destinos", "Tiempo minimo", "Distancia minima", "Costo minimo"
    };

    private static final Rectangle opt = new Rectangle(OPTION_WIDTH, OPTION_HEIGHT);

    public static Rectangle rect = new Rectangle(OPTION_WIDTH, OPTION_HEIGHT * options.length);
    public static Rectangle rect1 = new Rectangle(OPTION_WIDTH, OPTION_HEIGHT);

    public static int selected = -1;

    private static final Font font = new Font(Font.MONOSPACED, Font.BOLD, FONT_HEIGHT);

    public static void draw(int x, int y, Graphics2D g, int idx) {
        rect.x = x; rect.y = y; selected = idx;

        g.setColor(Color.black);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
        g.setColor(Color.white);

        g.setFont(font);
        for (int i = 0; i < options.length; i++) {
            g.drawRect(rect.x, rect.y + i * OPTION_HEIGHT, OPTION_WIDTH - 1, OPTION_HEIGHT - 1);
            g.drawString(options[i], rect.x + 15, rect.y + (i + 1) * OPTION_HEIGHT - (OPTION_HEIGHT - FONT_HEIGHT) / 2);
        }
    }

    public static void draw2(int x, int y, Graphics2D g){
        rect1.x = x; rect1.y = y;

        g.setColor(Color.black);
        g.fillRect(rect1.x, rect1.y, rect1.width, rect1.height);
        g.setColor(Color.white);

        g.setFont(font);
        g.drawRect(rect1.x, rect1.y, OPTION_WIDTH - 1, OPTION_HEIGHT - 1);
        g.drawString("Mas Salidas", rect1.x + 15, rect1.y + OPTION_HEIGHT - (OPTION_HEIGHT - FONT_HEIGHT) / 2);
    }

    public static int click(int x, int y) {
        if (!rect.contains(x, y)) {
            return -1;
        }

        for (int i = 0; i < options.length; i++) {
            opt.x = rect.x; opt.y = rect.y + OPTION_HEIGHT * i;
            if (opt.contains(x, y)){ 
                return i;
            }
        }

        return -1;
    }

    public static boolean click2(int x, int y){
        return rect1.contains(x, y);
    }

}
