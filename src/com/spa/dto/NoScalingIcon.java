package com.spa.dto;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.*;


public class NoScalingIcon implements Icon
{
    private Icon icon;


    public NoScalingIcon(URL location)
    {
        this.icon = new ImageIcon(location);
    }

    public NoScalingIcon(Image image)
    {
        this.icon = new ImageIcon(image);
    }

    public NoScalingIcon(Image image, int iconWidth, int iconHeight)
    {
        this.icon = new ImageIcon(image);
        scalePix(iconWidth, iconHeight);
    }

    public NoScalingIcon(ImageIcon imageIcon)
    {
        this.icon = imageIcon;
    }

    public NoScalingIcon(ImageIcon imageIcon, int iconWidth, int iconHeight)
    {
        this.icon = imageIcon;
        scalePix(iconWidth, iconHeight);
    }

    public NoScalingIcon(URL location, int iconWidth, int iconHeight)
    {
        this.icon = new ImageIcon(location);
        //scalePix(x,y);
        scalePix(iconWidth, iconHeight);
    }

    public void Scale(double Scale) {
        Image image = getImage();
        Image scaledImage = image.getScaledInstance((int) (getIconWidth()*Scale), (int) (getIconHeight()*Scale),  java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);
    }

    public void scalePix(int iconWidth, int iconHeight) {
        Image image = getImage();
        Image scaledImage = image.getScaledInstance(iconWidth, iconHeight,  java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);
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

    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D)g.create();

        AffineTransform at = g2d.getTransform();

        double scaleX = at.getScaleX();
        double scaleY =  at.getScaleY();
        AffineTransform scaled = AffineTransform.getScaleInstance(1.0 / at.getScaleX(), 1.0 / at.getScaleY());
        at.concatenate( scaled );
        g2d.setTransform( at );

        Image scaledImage = getImage().getScaledInstance((int) (getIconWidth() * scaleX), (int) (getIconHeight() * scaleY), Image.SCALE_SMOOTH);

        // Using MediaTracker to wait for the image to load
        MediaTracker tracker = new MediaTracker(c);
        tracker.addImage(scaledImage, 0);
        try {
            tracker.waitForAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            return; // Exit the method
        }

        // Check if the image loading was successful
        if (!tracker.isErrorAny()) {
            g2d.drawImage(scaledImage, (int)(x * scaleX), (int)(y * scaleY), c);
        }

        g2d.dispose();

    }
}
