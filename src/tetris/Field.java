package tetris;

public class Field {
    private int width;
    private int height;
    private int[][] matrix;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Field (int width, int heigth) {
        this.width = width;
        this.height = heigth;

        matrix = new int[heigth][width];

        for (int j = 0; j < matrix[0].length; j++) {
            matrix[9][j] = 1;
        }
        matrix [0][0] = 1;
        matrix [14][8] = 1;
        matrix [1][1] = 1;
        matrix [1][3] = 1;
        matrix [1][5] = 1;
        matrix [1][7] = 1;
        matrix [1][9] = 1;
        matrix [2][2] = 1;
        matrix [3][3] = 1;
        matrix [4][4] = 1;
        matrix [5][5] = 1;
        matrix [6][6] = 1;
        matrix [7][7] = 1;
        matrix [8][0] = 1;
        matrix [8][2] = 1;
        matrix [8][8] = 1;
    }

    public void print (){
        for (int i = 0; i< matrix.length; i++) {
            StringBuilder str = new StringBuilder();
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 0)
                    str.append(".");
                else
                    str.append("X");
            }
            System.out.println(str.toString());
        }
    }

    public void removeFullLines() {
// Удалить все заполненные строки
        for (int i = 0; i < matrix.length; i++) {
            boolean rowFull = true;         // по умолчанию считаю, что строка вся в единицах
            for (int j= 0; j< matrix[i].length; j++)
                if (matrix[i][j] == 0 ) {
                    rowFull = false;
                    break;
                }

            if (rowFull)
                RemoveSingleLine (i);
        }


/*        matrix[9] = null;
        matrix[9] = new int[width];
        matrix[9] = matrix[8];
        matrix[8] = matrix[7];

 */
    }

    private void RemoveSingleLine (int delLine) {   //Тут нам нужно удалить одну линию (ш)
        matrix[delLine] = null;
        matrix[delLine] = new int[width];
        for (int i = delLine -1; i >=0 ; i--) {
            matrix[i+1] = matrix[i];
        }
        matrix[0] = null;               // В конце нулевую строчку обнулим!
        matrix[0] = new int[width];
    }

    public Integer getValue (int x, int y) {
        return null;
    }

    public void setValue(int x, int y, int value) {

    }
}

