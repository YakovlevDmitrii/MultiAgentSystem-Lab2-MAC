import org.decimal4j.util.DoubleRounder;

public class Functions {

    public static double func1(double x){
        return DoubleRounder.round(-x*x+5,5);
    }
    public static double func2(double x){
        return  DoubleRounder.round(2*x+2,5);
    }
    public static double func3(double x){
        return  DoubleRounder.round(Math.sin(x),5);
    }

    public static double[] calculateValueByAgentName(String myAgent, double[] values){
        double[] calculateResult = new double[values.length] ;
        if (myAgent.equals("Agent1")){
            for(int i=0; i< values.length; i++){
                calculateResult[i] = func1(values[i]);
            }
        } else if (myAgent.equals("Agent2")) {
            for(int i=0; i< values.length; i++){
                calculateResult[i] = func2(values[i]);
            }
        }else {
            for(int i=0; i< values.length; i++){
                calculateResult[i] = func3(values[i]);
            }
        }
        return calculateResult;
    }
}
