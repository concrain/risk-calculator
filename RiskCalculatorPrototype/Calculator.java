import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

/**
 * Tool to calculate your risk/reward per trade
 *
 * @author Russel Arthur
 * @version .001, 08-01-2019
 */
public class Calculator {
    
    private String pairing = "";
    private double equity = 0.0;
    private double riskPercentage = 0.0;
    private double entryPrice = 0.0;
    private double exitPrice = 0.0;
    private double totalEquityAtRisk = 0.0;
    private double stopPrice = 0.0;
    private double distanceToStop = 0.0;
    private double positionSizeShares = 0.0;
    private double positionSizeEquity = 0.0;
    private double riskVsReward = 0.0;
    private double winRate = 0.0;
    private double totalGainPercentage = 0.0;
    private double totalGainEquity = 0.0;
    boolean asterix = false;
   
    public static void main(String[] args) {
        System.out.println("configure your trading set up:\n");
            
        Calculator cal = new Calculator();
        cal.runInputs();
        cal.calculateResults(cal);
        cal.displayResults();
    }
    
    private int runInputs() {
        Scanner obj = new Scanner(System.in);
        
        System.out.println("  trade pairing: ");
        this.pairing = obj.nextLine();
        
        System.out.println(" total equity: ");
        this.equity = Double.parseDouble(obj.nextLine());
        
        System.out.println(" risk percentage .01-.99%: ");
        this.riskPercentage = Double.parseDouble(obj.nextLine());
        
        System.out.println(" target entry price: ");
        this.entryPrice = Double.parseDouble(obj.nextLine());
        
        System.out.println(" target exit price: ");
        this.exitPrice = Double.parseDouble(obj.nextLine());
        
        System.out.println(" target stop price: ");
        this.stopPrice = Double.parseDouble(obj.nextLine());
        
        return 0;
    }
    
    private void calculateResults(Calculator cal) {
        cal.calculateRisk(equity, riskPercentage);
        cal.calculateDistanceToStop(entryPrice, stopPrice);
        cal.calculatePositionSizeTotalEquity(totalEquityAtRisk, distanceToStop, equity, riskPercentage);
        cal.calculatePositionSizeTotalShares(entryPrice, positionSizeEquity);
        cal.calculateMaxReturnRate(entryPrice, exitPrice);
        cal.calculateMaxReturnEquity(totalGainPercentage, positionSizeEquity);
        cal.calculateRiskReward(totalGainEquity, totalEquityAtRisk);
        cal.calculateWinRate(riskVsReward);
    }
    
    private void displayResults() {
        DecimalFormat decimalFormatter = new DecimalFormat("##.##");
        decimalFormatter.setMinimumFractionDigits(2);
        decimalFormatter.setMaximumFractionDigits(2);
    
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Date date = new Date();
        
        System.out.println("\n  " + pairing +"  "+ f.format(date));
        System.out.print("\n  total risk: \t\t$" +Math.round(totalEquityAtRisk *100.0) /100.0);
        if(asterix)
            System.out.print("*");
        System.out.println("\n  distance to stop: \t\t" +Math.round(distanceToStop *100.0) /100.0 + "%");
        System.out.println("  total equity: \t\t$" +Math.round(positionSizeEquity *100.0) /100.0);
        System.out.println("  total shares: \t\t" +decimalFormatter.format(positionSizeShares));
        System.out.println("\n  max return rate: \t\t" +Math.round(totalGainPercentage *100.0) /100.0 + "%");
        System.out.println("  max return equity: \t$" +Math.round(totalGainEquity *100.0) /100.0);
        System.out.println("  reward/risk: \t\t\t" +Math.round(riskVsReward *100.0) /100.0 + " / 1.0");
        System.out.println("  minimum win rate: \t\t" +Math.round(winRate *100.0) /100.0 + "%");
        
        if(asterix)
            System.out.println("\n\n * risk is lower than maximum requested");
    }
    
    private void calculateRisk(double equity, double risk) {
        this.totalEquityAtRisk = equity * risk;
    }
    
    private void calculateDistanceToStop(double entryPrice, double stopPrice) {
        double delta = Math.abs(entryPrice - stopPrice);
        this.distanceToStop = (delta / entryPrice) *100;
    }
    
    private void calculatePositionSizeTotalEquity(double totalEquityAtRisk, double distanceToStop, double equity, double riskPercentage) {    
        //moves the decimal to the left 2 places
        distanceToStop = (distanceToStop /= 100);
        if(distanceToStop < riskPercentage) {
            this.positionSizeEquity = equity;
            this.totalEquityAtRisk = equity * distanceToStop;
            this.asterix = true;
        } else {
            this.positionSizeEquity = totalEquityAtRisk / distanceToStop;
        }
    }
    
    private void calculatePositionSizeTotalShares( double entryPrice, double positionSizeEquity) {
        this.positionSizeShares = positionSizeEquity / entryPrice;
    }
    
    private void calculateMaxReturnRate(double entryPrice, double exitPrice) {
        this.totalGainPercentage = 100 * (exitPrice - entryPrice) / (double) entryPrice;
    }
    
    private void calculateMaxReturnEquity(double totalGainPercentage, double positionSizeEquity) {
        //moves the decimal to the left 2 places
        this.totalGainEquity = positionSizeEquity * (totalGainPercentage /= 100);
    }
    
    private void calculateRiskReward(double totalGainEquity, double totalRiskAmount) {        
        this.riskVsReward = totalGainEquity / totalRiskAmount;
    }
    
    private void calculateWinRate(double riskVsReward) {
        this.winRate = 1 / (1+riskVsReward) * 100;
    }
    
}
