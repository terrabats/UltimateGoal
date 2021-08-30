package util;

public class point {
    public double x;
    public double y;
    public double dx;
    public double dy;
    double point_x = x+dx;
    double point_y = y+dy;

    public point(double point_x, double point_y) {
    }

    public util.point add(util.point in){
        return new util.point(point_x, point_y);
    }

}
