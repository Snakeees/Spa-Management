package com.spa.dto;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.*;


public class NoScalingIcon implements Icon
{
    private Icon icon;
    private Icon ScaledIcon;


    public NoScalingIcon(URL location)
    {
        this.icon = new ImageIcon(location);
        SetScaled();
    }
    public NoScalingIcon(URL location, Boolean Invert)
    {
        this.icon = new ImageIcon(location);
        if(Invert) {
            invertIcon();
        }
        SetScaled();
    }

    public void invertIcon() {
        BufferedImage buffered = toBufferedImage();
        for (int x = 0; x < buffered.getWidth(); x++) {
            for (int y = 0; y < buffered.getHeight(); y++) {
                int rgba = buffered.getRGB(x, y);
                Color col = new Color(rgba, true);
                if (col.getAlpha() != 0) {
                    int p = buffered.getRGB(x, y);
                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = p & 0xff;
                    r = 255 - r;
                    g = 255 - g;
                    b = 255 - b;
                    p = (a << 24) | (r << 16) | (g << 8) | b;
                    buffered.setRGB(x, y, p);
                }
            }
        }
        setImage(buffered);
    }

    public void Scale(double Scale) {
        Image image = getImage();
        Image scaledImage = image.getScaledInstance((int) (getIconWidth()*Scale), (int) (getIconHeight()*Scale),  java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);
    }

    private void SetScaled() {
        Image image = getImage();
        Image scaledImage = image.getScaledInstance((int) (getIconWidth()*1.5), (int) (getIconHeight()*1.5),  java.awt.Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon();
        imageIcon.setImage(scaledImage);
        this.ScaledIcon = imageIcon;
    }

    public Image getScaled() {
        ImageIcon imageIcon = (ImageIcon) ScaledIcon;
        return imageIcon.getImage();
    }
    public void setImage(Image image) {
        ImageIcon imageIcon = new ImageIcon();
        imageIcon.setImage(image);
        this.icon = imageIcon;
    }

    public Image getImage() {
        ImageIcon imageIcon = (ImageIcon) icon;
        return imageIcon.getImage();
    }

    public int getIconWidth()
    {
        return icon.getIconWidth();
    }

    public int getIconHeight()
    {
        return icon.getIconHeight();
    }

    public void paintIcon(Component c, Graphics g, int x, int y)
    {
        Graphics2D g2d = (Graphics2D)g.create();

        /*AffineTransform at = g2d.getTransform();
        int scaleX = (int) (x * at.getScaleX());
        int scaleY = (int) (y * at.getScaleY());


        AffineTransform scaled = AffineTransform.getScaleInstance(1.0 / at.getScaleX(), 1.0 / at.getScaleY());
        at.concatenate( scaled );
        g2d.setTransform( at );

        g2d.drawImage(getScaled(), scaleX, scaleY, c);*/

        g2d.drawImage(getImage(), x, y, c);

        g2d.dispose();
    }

    public BufferedImage toBufferedImage() {
        Image img = getImage();
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bimage;
    }

}
