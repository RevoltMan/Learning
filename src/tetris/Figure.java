package tetris;

public class Figure {
    private int x;
    private int y;
    private int[][] matrix = new int[3][3];

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void left() {
//для движения фигурки влево
        x--;
        if (!isCurrentPositionAvailable()) {
            x++;       // если новое положение не доступно, то вернём старое
        }
    }

    public void right() {
//для движения фигурки вправо
        x++;
        if (!isCurrentPositionAvailable()) {
            x--;       // если новое положение не доступно, то вернём старое
        }
    }

    public void down() {
//для движения фигурки вниз
        y++;
        if (!isCurrentPositionAvailable()) {
            y--;       // если новое положение не доступно, то вернём старое
        }
    }

    public void up() {
//для поворота фигурки вокруг главной диагонали
        y--;
        if (!isCurrentPositionAvailable()) {
            y++;       // если новое положение не доступно, то вернём старое
        }
    }

    public void rotate() {
//для поворота фигурки вокруг главной диагонали
    }

    public void downMaximum () {
//падение фигурки вниз до дна

    }

    public boolean isCurrentPositionAvailable () {
//проверка - может ли фигурка быть помещена в текущую позицию.
        return true;
    }

    public void landed() {
//вызывается, когда фигурка достигла дна или уперлась в другую фигурку
//Все ее занятые клетки теперь должны добавиться в Field.
    }


    public Figure (int x, int y, int [][] matrix) {
        this.x = x;
        this.y = y;
        this.matrix = matrix;
    }
}
