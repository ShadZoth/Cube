package ru.hse.se.g272.ervo.ooaip;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Форма, имеющая заданный размер.
 *
 * @author Эрво Виктор, 272
 * @since 17.02.14
 */
public class Form extends JFrame {
    /**
     * Размер окна, занимающего треть экрана
     */
    public static final int THIRDSCREEN = 3;

    /**
     * Размер окна, занимающего половину экрана
     */
    public static final int HALFSCREEN = 2;

    /**
     * Размер окна, занимающего весь экран
     */
    public static final int FULLSCREEN = 1;

    /**
     * Устанавливает размер формы в соответствии с размером экрана.
     * @param size Size of form
     */
    public void setDefaultSize(int size) {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / size, screenHeight / size);
        setLocation(screenWidth / 2 - screenWidth / (2 * size),
                screenHeight / 2 - screenHeight / (2 * size));
    }
}
