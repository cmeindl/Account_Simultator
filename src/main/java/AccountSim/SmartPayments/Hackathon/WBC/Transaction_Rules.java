package AccountSim.SmartPayments.Hackathon.WBC;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Transaction_Rules {

    public Calendar getTriggerDate() {
        return TriggerDate;
    }

    public double getTarget_Balance() {
        return RoundToDecimals(Target_Balance);
    }

    public double getTarget_Transaction() {
        return RoundToDecimals(Target_Transaction);
    }

//    public boolean isExcludeOnly() {
//        return excludeOnly;
//    }

    public boolean isTriggered() {
        return triggered;
    }

    public void setTriggered(boolean triggered) {
        this.triggered = triggered;
    }

    private Calendar TriggerDate = Calendar.getInstance();

    public Transaction_Rules() {
    }

    private double Target_Balance;
    private double Target_Transaction;
   // private boolean excludeOnly = false;
    private boolean triggered = false;


    public Transaction_Rules(double target_Balance, double target_Transaction) {
        Target_Balance = target_Balance;
        Target_Transaction = target_Transaction;
    //    this.excludeOnly = excludeOnly;
    }

    public Transaction_Rules(Calendar triggerDate, double target_Transaction) {
        TriggerDate = triggerDate;
        Target_Transaction = target_Transaction;
    }

    public Transaction_Rules(double target_Balance, Calendar triggerDate) {
        TriggerDate = triggerDate;
        Target_Balance = target_Balance;
    }


    public Transaction_Rules(Calendar triggerDate, double target_Balance, double target_Transaction) {
        TriggerDate = triggerDate;
        Target_Balance = target_Balance;
        Target_Transaction = target_Transaction;
    }

    public Transaction_Rules(String triggerDate, double target_Balance, double target_Transaction) {
        try {
           // Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            TriggerDate.setTime(sdf.parse(triggerDate));
            //  TriggerDate = triggerDate;
            Target_Balance = target_Balance;
            Target_Transaction = target_Transaction;
        }
        catch (Exception e){}
    }



    private double RoundToDecimals(double valtoRound){
        BigDecimal BD = new BigDecimal(valtoRound);
        BD = BD.setScale(2, RoundingMode.HALF_UP);
        return BD.doubleValue();
    }
    private double RoundToDecimals(double valtoRound, Integer NumberOfDecimals){
        BigDecimal BD = new BigDecimal(valtoRound);
        BD = BD.setScale(NumberOfDecimals, RoundingMode.HALF_UP);
        return BD.doubleValue();
    }

    public ArrayList<Transaction_Rules> buildDefault(){
        ArrayList<Transaction_Rules> TRules = new ArrayList<Transaction_Rules>();
//        Calendar cal1 = Calendar.getInstance();
//        cal1.set(2015, 8, 8);
//        TRules.add(new Transaction_Rules("08/08/2015",1000.00,100.00));
//        TRules.add(new Transaction_Rules(cal1,1000.00,100.00));
//        TRules.add(new Transaction_Rules(cal1, 0, 100.00));
//        TRules.add(new Transaction_Rules(cal1, 1000.00, 0));
//        cal1 = Calendar.getInstance();
//        cal1.set(2015, 8, 9);
//        TRules.add(new Transaction_Rules(cal1, 2000.00, 200.10));
//        cal1 = Calendar.getInstance();
//        cal1.set(2015, 8, 10);
//
//        TRules.add(new Transaction_Rules(cal1, 3000.00, 330.00));
//        TRules.add(new Transaction_Rules(cal1, 40000.00, 10000.00));
//        cal1 = Calendar.getInstance();
//        cal1.set(2015, 8, 11);
//        TRules.add(new Transaction_Rules(cal1, 12000.00, 0));
//        TRules.add(new Transaction_Rules(cal1, 0, 122200.00));
//        cal1 = Calendar.getInstance();
//        cal1.set(2015, 8, 12);
//        TRules.add(new Transaction_Rules(cal1,10200.00,900.00));
//        TRules.add(new Transaction_Rules(cal1, 8000.00, 800.00));


        TRules.add(new Transaction_Rules("08/08/2015",1000.00,100.00));
        TRules.add(new Transaction_Rules("08/08/2015",1000.00,100.00));
        TRules.add(new Transaction_Rules("08/08/2015", 0, 100.00));
        TRules.add(new Transaction_Rules("08/08/2015", 1000.00, 0));
        TRules.add(new Transaction_Rules("09/08/2015", 2000.00, 200.10));
        TRules.add(new Transaction_Rules("10/08/2015", 3000.00, 330.00));
        TRules.add(new Transaction_Rules("10/08/2015", 40000.00, 10000.00));
        TRules.add(new Transaction_Rules("11/08/2015", 12000.00, 0));
        TRules.add(new Transaction_Rules("11/08/2015", 0, 122200.00));
        TRules.add(new Transaction_Rules("12/08/2015",10200.00,900.00));
        TRules.add(new Transaction_Rules("12/08/2015", 8000.00, 800.00));

return TRules;
    }


}
