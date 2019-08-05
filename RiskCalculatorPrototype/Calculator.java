import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Tool to calculate your risk/reward per trade
 *
 * @author Russel Arthur
 * @version .001, 08-01-2019
 */
public class Calculator {
    
    private static String pairing = "";
    private static double equity = 0.0;
    private static double riskPercentage = 0.0;
    private static double entryPrice = 0.0;
    private static double exitPrice = 0.0;
    private static double totalEquityAtRisk = 0.0;
    private static double stopPrice = 0.0;
    private static double distanceToStop = 0.0;
    private static double positionSizeShares = 0.0;
    private static double positionSizeEquity = 0.0;
    private static double riskVsReward = 0.0;
    private static double winRate = 0.0;
    private static double totalGainPercentage = 0.0;
    private static double totalGainEquity = 0.0;
    
    public static void main(String[] args) {
        System.out.println("configure your stop loss:\n");
            
        Calculator cal = new Calculator();
        cal.runInputs();
        
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Date date = new Date();
        
        System.out.println("\n  " + pairing +"  "+ f.format(date));
        System.out.println("\n  total risk: \t\t$" +Math.round(cal.calculateRisk(equity, riskPercentage) *100.0) /100.0);
        System.out.println("  distance to stop: \t\t" +Math.round(cal.calculateDistanceToStop(entryPrice, stopPrice) *100.0) /100.0 + "%");
        System.out.println("  total equity: \t\t$" +Math.round(cal.calculatePositionSizeTotalEquity(totalEquityAtRisk, entryPrice, stopPrice) *100.0) /100.0);
        System.out.println("  total shares: \t\t" +Math.round(cal.calculatePositionSizeTotalShares(entryPrice, positionSizeEquity) *100.0) /100.0);
        System.out.println("\n  max return rate: \t\t" +Math.round(cal.calculateMaxReturnRate(entryPrice, exitPrice) *100.0) /100.0 + "%");
        System.out.println("  max return equity: \t" +Math.round(cal.calculateMaxReturnEquity(totalGainPercentage, positionSizeEquity) *100.0) /100.0);
        System.out.println("  reward/risk: \t\t\t" +Math.round(cal.calculateRiskReward(totalGainEquity, totalEquityAtRisk) *100.0) /100.0 + " / 1.0");
        System.out.println("  minimum win rate: \t\t" +Math.round(cal.calculateWinRate(riskVsReward) *100.0) /100.0 + "%");
    }
    
    private int runInputs() {
        Scanner obj = new Scanner(System.in);
        
        System.out.println("  trade pairing: ");
        pairing = obj.nextLine();
        
        System.out.println(" total equity: ");
        equity = Double.parseDouble(obj.nextLine());
        
        System.out.println(" risk percentage .01-.99%: ");
        riskPercentage = Double.parseDouble(obj.nextLine());
        
        System.out.println(" target entry price: ");
        entryPrice = Double.parseDouble(obj.nextLine());
        
        System.out.println(" target exit price: ");
        exitPrice = Double.parseDouble(obj.nextLine());
        
        System.out.println(" target stop price: ");
        stopPrice = Double.parseDouble(obj.nextLine());
        
        return 0;
    }
    
    private double calculateRisk(double equity, double risk) {
        return totalEquityAtRisk = equity * risk;
    }
    
    private double calculateDistanceToStop(double entryPrice, double stopPrice) {
        double delta = Math.abs(entryPrice - stopPrice);
        return distanceToStop = (delta / entryPrice) *100;
    }
    
    private double calculatePositionSizeTotalEquity(double totalEquityAtRisk, double entryPrice, double stopPrice) {
        double delta = Math.abs(entryPrice - stopPrice);
        return positionSizeEquity = totalEquityAtRisk / delta;
    }
    
    private double calculatePositionSizeTotalShares( double entryPrice, double positionSizeEquity) {
        return positionSizeShares = positionSizeEquity / entryPrice;
    }
    
    private double calculateMaxReturnRate(double entryPrice, double exitPrice) {
        return totalGainPercentage = 100 * (exitPrice - entryPrice) / (double) entryPrice;
    }
    
    private double calculateMaxReturnEquity(double totalGainPercentage, double positionSizeEquity) {
        //moves the decimal to the left 2 places
        return totalGainEquity = positionSizeEquity * (totalGainPercentage /= 100);
    }
    
    private double calculateRiskReward(double totalGainEquity, double totalRiskAmount) {        
        return riskVsReward = totalGainEquity / totalRiskAmount;
    }
    
    private double calculateWinRate(double riskVsReward) {
        return winRate = 1 / (1+riskVsReward) * 100;
    }
    
}
