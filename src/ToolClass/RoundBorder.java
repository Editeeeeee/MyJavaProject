package ToolClass;

import javax.swing.border.Border;
import java.awt.*;

public class RoundBorder implements Border {

    private int radius;


    public RoundBorder(int radius) {
        this.radius = radius;
    }
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
}
