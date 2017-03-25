package sumfun;

import java.awt.*;
import java.io.*;
import javax.swing.*;

/**
 * @param Class declaration that takes the class name Tile and extends JButton to it, followed by making the class serializable.
 */
public class Tile extends JButton implements Serializable {

    private String label;
    private Color foregroundColor;
    private Color backgroundColor;
    private Boolean isClicked = false;
    private int counter = 0;
    /**
     * @param Constructor method used to construct Tile objects
     */
    public Tile(String label, Color foregroundColor, Color backgroundColor) {
        this.label = label;
        this.foregroundColor = foregroundColor;
        this.backgroundColor = backgroundColor;
        this.setForeground(getForegroundColor());
        this.setBackground(getBackgroundColor());
        this.setOpaque(true);
        this.setBorderPainted(false);
        this.setText(this.label);
    }
    /**
     * @param Getter and setter for the 'X' and 'O' in each tile
     */
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
        this.setText(label);
    }
    /**
     * @ param Getter and setter for the foreground color
     */
    public Color getForegroundColor() {
        return foregroundColor;
    }
    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
        this.setForeground(foregroundColor);
    }
    /**
     * @param Getter and setter for the background color
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        this.setBackground(backgroundColor);
    }
    /**
     *
     * @return Returns a boolean whether a tile is clicked or not
     */
    public Boolean getIsClicked() {
        return isClicked;
    }
    /**
     * @param Getter and setter for the counter, which is used to help indicate if the tile object should show and X, O or blank.
     */
    public int getCounter() {
        return counter;
    }
    public void setCounter(int counter) {
        this.counter = counter;
    }

}
