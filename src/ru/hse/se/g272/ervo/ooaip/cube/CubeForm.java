package ru.hse.se.g272.ervo.ooaip.cube;

import ru.hse.se.g272.ervo.ooaip.Form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Форма с изображением вращающегося куба.
 *
 * @author Эрво Виктор, 272(2)
 * @since 15.02.14
 */
public class CubeForm extends Form {
    /**
     * Поворот по оси Z.
     */
    private int turnX;

    /**
     * Поворот по оси X.
     */
    private int turnY;

    /**
     * Создаёт форму.
     */
    public CubeForm() {
        setTitle("Псевдо3D-куб");
        Ps3DCube cube = new Ps3DCube();
        MouseAdapter mouseAdapter = new MouseAdapter() {
            private Point rotationStartPoint;
            private int currentTurnX;
            private int currentTurnY;

            @Override
            public void mousePressed(final MouseEvent mouseEvent) {
                System.out.println("Start rotating");
                rotationStartPoint = mouseEvent.getPoint();
                currentTurnX = turnX;
                currentTurnY = turnY;
                repaint();
            }

            @Override
            public void mouseDragged(final MouseEvent mouseEvent) {
                System.out.println("Rotating");
                turnX = currentTurnX
                        - rotationStartPoint.x + mouseEvent.getPoint().x;
                turnY = currentTurnY
                        - rotationStartPoint.y + mouseEvent.getPoint().y;
                System.out.println("turnX = " + turnX + "; turnY = " + turnY);
                repaint();
            }
        };
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        add(cube);
    }

    /**
     * Метод, вызываемый при запуске программы.
     * @param args Аргументы командной строки
     */
    public static void main(final String[] args) {
        CubeForm form = new CubeForm();
        form.setDefaultCloseOperation(EXIT_ON_CLOSE);
        form.pack();
        form.setVisible(true);
        form.setDefaultSize(Form.HALFSCREEN);
    }

    /**
     * Псевдо3D куб.
     */
    private class Ps3DCube extends JComponent {

        @Override
        protected void paintComponent(final Graphics graphics) {
            Graphics2D g = (Graphics2D) graphics;
            g.translate(getWidth() / 2, getHeight() / 2);
            int width = getCubeWidth();

            drawCube(g, width);
        }

        /**
         * Получает ширину куба из ширины окна.
         * @return Ширина куба
         */
        private int getCubeWidth() {
            return getWidth() /  3;
        }

        /**
         * Рисует куб.
         * @param graphics Параметры рисования
         * @param width Ширина куба
         */
        private void drawCube(final Graphics2D graphics, final int width) {
            int c = width / 2;
            Point leftTopFront = getTurnedPoint(-c, c, c);
            Point leftTopBack = getTurnedPoint(-c, c, -c);
            Point leftBotFront = getTurnedPoint(-c, -c, c);
            Point leftBotBack = getTurnedPoint(-c, -c, -c);
            Point rightTopFront = getTurnedPoint(c, c, c);
            Point rightTopBack = getTurnedPoint(c, c, -c);
            Point rightBotFront = getTurnedPoint(c, -c, c);
            Point rightBotBack = getTurnedPoint(c, -c, -c);

            drawLine(graphics, leftTopFront, leftTopBack);
            drawLine(graphics, leftTopFront, leftBotFront);
            drawLine(graphics, leftTopFront, rightTopFront);
            drawLine(graphics, leftTopBack, rightTopBack);
            drawLine(graphics, rightTopFront, rightTopBack);
            drawLine(graphics, leftBotFront, rightBotFront);
            drawLine(graphics, rightTopFront, rightBotFront);
            drawLine(graphics, rightBotFront, rightBotBack);
            drawLine(graphics, rightTopBack, rightBotBack);
            drawLine(graphics, leftBotFront, leftBotBack);
            drawLine(graphics, leftTopBack, leftBotBack);
            drawLine(graphics, leftBotBack, rightBotBack);
        }

        /**
         * Получает повёрнутую точку.
         * @param x Координата x
         * @param y Координата y
         * @param z Координата z
         * @return Повёрнутая точка
         */
        private Point getTurnedPoint(final int x, final int y, final int z) {
            return new Ps3DPoint(x * Math.cos(aX()) - y(y, z) * Math.sin(aX()),
                    x * Math.sin(aX()) + y(y, z) * Math.cos(aX()),
                    z(y, z));
        }

        /**
         * Поворот по оси Z в радианах.
         * @return Поворот по оси Z в радианах
         */
        private double aX() {
            return Math.toRadians(turnX);
        }

        /**
         * Координата z точки, повёрнутой по оси Х.
         * @param y Координата y
         * @param z Координата z
         * @return Координата z
         */
        private double z(final int y, final int z) {
            return y * Math.sin(aY())
                    + z * Math.cos(aY());
        }

        /**
         * Поворот по оси X в радианах.
         * @return Поворот по оси X в радианах
         */
        private double aY() {
            return Math.toRadians(turnY);
        }

        /**
         * Координата y точки, повёрнутой по оси Z.
         * @param y Координата y
         * @param z Координата z
         * @return Координата y
         */
        private double y(final int y, final int z) {
            return y * Math.cos(aY())
                    - z * Math.sin(aY());
        }

        /**
         * Рисует отрезок.
         * @param graphics2D Параметры графики
         * @param p1 Первая точка
         * @param p2 Вторая точка
         */
        private void drawLine(final Graphics2D graphics2D,
                              final Point p1, final Point p2) {
            graphics2D.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

    }

    /**
     * Псевдо3D точка.
     */
    private class Ps3DPoint extends Point {
        /**
         * Создаёт точку.
         * @param x Координата х
         * @param y Координата y
         * @param z Координата z
         */
        public Ps3DPoint(final double x, final double y, final double z) {
            super((int) (x + z / 2), (int) (y - z / 2));
        }
    }
}
