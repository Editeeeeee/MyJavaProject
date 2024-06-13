package ToolClass;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class ImagePanel extends JPanel {


    public Image image;


    public ImagePanel(String imagePath) {


        try {


            image = ImageIO.read(new File(imagePath));


        } catch (IOException e) {


            e.printStackTrace();


        }


    }


    @Override


    protected void paintComponent(Graphics g) {


        super.paintComponent(g);


        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);


    }


}
