import java.util.Scanner;

/**
 * Tool to calculate your risk/reward per trade
 *
 * @author Russel Arthur
 * @version .001, 08-01-2019
 */
public class Calculator {
    
    private static double equity = 0.0;
    private static double riskPercentage = 0.0;
    private static double entryPrice = 0.0;
    private static double exitPrice = 0.0;
    private static double totalRiskAmount = 0.0;
    private static double distanceToStop = 0.0;
    private static double positionSize = 0.0;
    private static double riskVsReward = 0.0;
    private static double winRate = 0.0;
    private static double totalGainPercentage = 0.0;
    private static double totalGainEquity = 0.0;
    
    public static void main(String[] args) {
        System.out.println("configure your stop loss:\n");
            
        Calculator cal = new Calculator();
        cal.runInputs();
        
        System.out.println("\n  total equity at risk: \t\t" +Math.round(cal.calculateRisk(equity, riskPercentage) *100.0) /100.0);
        System.out.println("  position size: \t\t\t" +Math.round(cal.calculatePositionSize(totalRiskAmount, distanceToStop) *100.0) /100.0);
        System.out.println("\n  max return rate: \t\t" +Math.round(cal.calculateMaxReturnRate(entryPrice, exitPrice) *100.0) /100.0 + "%");
        System.out.println("  max return equity: \t" +Math.round(cal.calculateMaxReturnEquity(totalGainPercentage, positionSize) *100.0) /100.0);
        System.out.println("  reward/risk: \t\t\t" +Math.round(cal.calculateRiskReward(totalGainEquity, totalRiskAmount) *100.0) /100.0 + " / 1");
        System.out.println("  minimum win rate: \t\t" +Math.round(cal.calculateWinRate(riskVsReward) *100.0) /100.0 + "%");
        
        //System.out.printf("%n total equity at risk: %21f", cal.calculateRisk(equity, riskPercentage));
        //System.out.printf("%n position size: %28f", cal.calculatePositionSize(totalRiskAmount, distanceToStop));
        //System.out.printf("%n reward/risk: %30f", cal.calculateRiskReward(totalRiskAmount, exitPrice));
        //System.out.printf("%n minimum win rate: %25f", cal.calculateWinRate(riskVsReward));
    }
    
    private int runInputs() {
        Scanner obj = new Scanner(System.in);
        
        System.out.println(" total equity: ");
        equity = Double.parseDouble(obj.nextLine());
        
        System.out.println(" risk percentage .01-.99%: ");
        riskPercentage = Double.parseDouble(obj.nextLine());
        
        System.out.println(" target entry price: ");
        entryPrice = Double.parseDouble(obj.nextLine());
        
        System.out.println(" target exit price: ");
        exitPrice = Double.parseDouble(obj.nextLine());
        
        System.out.println(" distance to stop .01-.99%: ");
        distanceToStop = Double.parseDouble(obj.nextLine());
        
        return 0;
    }
    
    private double calculateRisk(double equity, double risk) {
        return totalRiskAmount = equity * risk;
    }
    
    private double calculatePositionSize(double totalRiskAmount, double distanceToStop) {
        return positionSize = totalRiskAmount / distanceToStop;
    }
    
    private double calculateMaxReturnRate(double entryPrice, double exitPrice) {
        return totalGainPercentage = 100 * (exitPrice - entryPrice) / (double) entryPrice;
    }
    
    private double calculateMaxReturnEquity(double totalGainPercentage, double positionSize) {
        // (totalGainPercentage /= 100) moves the decimal to the left 2 places
        // https://stackoverflow.com/questions/4937402/moving-decimal-places-over-in-a-double
        return totalGainEquity = positionSize * (totalGainPercentage /= 100);
    }
    
    private double calculateRiskReward(double totalGainEquity, double totalRiskAmount) {        
        return riskVsReward = totalGainEquity / totalRiskAmount;
    }
    
    private double calculateWinRate(double riskVsReward) {
        return winRate = 1 / (1+riskVsReward) * 100;
    }
    
}
