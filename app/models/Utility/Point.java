package models.Utility;

public class Point {
    protected int X, Y;

    public Point(int x, int y){
        this.X = x;
        this.Y = y;
    }

    public Point(){
        this.X = 0;
        this.Y = 0;
    }

    public int getX(){
        return this.X;
    }

    public int getY(){
        return this.Y;
    }

}
