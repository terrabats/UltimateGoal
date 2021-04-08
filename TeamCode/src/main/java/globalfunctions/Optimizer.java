package globalfunctions;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

public class Optimizer {
    public ArrayList<Double> times = new ArrayList<>();
    public ElapsedTime timer = new ElapsedTime();
    public double lastTime = 0;
    public double deltaTime = 0;
    public double avgDeltaTime = 0;

    public boolean show = false;


    public void update(){
        if(!show) {
            deltaTime = timer.seconds() - lastTime;
            times.add(deltaTime);
            lastTime += deltaTime;
        }
    }

    public void show(){
        show = true;
        avgDeltaTime = calcAvg(times);
    }

    public void reset(){
        lastTime = timer.seconds();
        deltaTime = 0;
        avgDeltaTime = 0;
        show = false;
        times = new ArrayList<>();
    }

    public static double calcAvg(ArrayList<Double> in){
        double total = 0;
        for(double i:in){
            total += (i/in.size());
        }

        return total;
    }

    public static double max(ArrayList<Double> in){
        double max = 0;
        for(double i:in){
            if (i > max){
                max = i;
            }
        }
        return max;
    }

    public static boolean inRange(double in, double[] range){
        return in > range[0] && in < range[1];
    }

    public static double weightedAvg(double[] in, double[] weights){
        double sum = 0;
        double wsum = 0;
        for (int i = 0; i < in.length; i++) {
            sum += in[i]*weights[i];
            wsum += weights[i];
        }
        return sum/wsum;
    }


}