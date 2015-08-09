package AccountSim.SmartPayments.Hackathon.WBC;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Simulator {
    private Calendar SimStartDate = Calendar.getInstance();
    private Calendar SimEndDate = Calendar.getInstance();

    public Simulator(Calendar simStartDate, Calendar simEndDate, ArrayList<Transaction_Rules> TRules) {
        SimStartDate = simStartDate;
        SimEndDate = simEndDate;
        this.TRules = TRules;
    }

    public Simulator(String simStartDate, String simEndDate, ArrayList<Transaction_Rules> TRules) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            //   TriggerDate.setTime(sdf.parse(triggerDate));
            SimStartDate.setTime(sdf.parse(simStartDate));
            SimEndDate.setTime(sdf.parse(simEndDate));
            this.TRules = TRules;
            this.RunSim();
        }
        catch(Exception e){}
    }

    public Simulator(Calendar simStartDate, Calendar simEndDate, int transPerDay, ArrayList<Transaction_Rules> TRules) {
        SimStartDate = simStartDate;
        SimEndDate = simEndDate;
        TransPerDay = transPerDay;
        this.TRules = TRules;

    }

    public Simulator(Calendar simStartDate, Calendar simEndDate, int transPerDay) {
        SimStartDate = simStartDate;
        SimEndDate = simEndDate;
        TransPerDay = transPerDay;
    }

    public Simulator(Calendar simStartDate, Calendar simEndDate, int transPerDay, double minTranValue, double maxTranValue, double minAccountBalance, double maxAccountBalance, ArrayList<Transaction_Rules> TRules) {
        SimStartDate = simStartDate;
        SimEndDate = simEndDate;
        TransPerDay = transPerDay;
        MinTranValue = minTranValue;
        MaxTranValue = maxTranValue;
        MinAccountBalance = minAccountBalance;
        MaxAccountBalance = maxAccountBalance;
        this.TRules = TRules;
    }

    private int TransPerDay = 5;
    private double MinTranValue = 220.00;
    private double MaxTranValue = 900000.20;
    private double MinAccountBalance = -10000.00;
    private double MaxAccountBalance = 5000000.00;
    private double OpeningBalance = 200000.10;
    private ArrayList<Transaction_Rules> TRules = new ArrayList<Transaction_Rules>();
    private ArrayList<SimulatedTransaction> TransOut = new ArrayList<SimulatedTransaction>();
    private boolean EOF = false;
    private Integer ArrayPos = 0;
    public boolean isEOF() {
        return EOF;
    }

    public void ResetArray(){

        ArrayPos = 0;
                EOF = false;
    }

    public SimulatedTransaction GetNext(){

        ArrayPos++;
        if (ArrayPos == TransOut.size()) {
        EOF = true;
        }
        return TransOut.get(ArrayPos-1);
    }

    public ArrayList<SimulatedTransaction> getTransOut() {
        return TransOut;
    }

    public void RunSim() {
        Random R = new Random();

        long SimDays = TimeUnit.DAYS.convert(SimEndDate.getTimeInMillis() - SimStartDate.getTimeInMillis(), TimeUnit.MILLISECONDS);
        double CurrentAccountBalance = OpeningBalance;

        for (int DayCount = 0; DayCount <= SimDays +1; DayCount++) {

            for (int DayTranCount = 0; DayTranCount < TransPerDay; DayTranCount++) {

                Double SuggestedTran = RoundToDecimals(MinTranValue + (MaxTranValue - MinTranValue) * R.nextDouble());
                if (R.nextInt(2) == 0) {
                    SuggestedTran = 0 - SuggestedTran;
                }
                // if the transaction takes us outside the allowable Account value limit reverse the transaction sign Debit or Credit
                if ((CurrentAccountBalance + SuggestedTran) > MaxAccountBalance || (CurrentAccountBalance + SuggestedTran) < MinAccountBalance) {
                    SuggestedTran = 0 - SuggestedTran;
                }

                Double SuggestedBalance = RoundToDecimals(CurrentAccountBalance + SuggestedTran);

                Boolean FoundTransaction = false;
                for (Transaction_Rules TR : TRules) {
                    Calendar CurrentDate = Calendar.getInstance();
                    CurrentDate.setTimeInMillis(SimStartDate.getTimeInMillis());
                    CurrentDate.add(Calendar.DAY_OF_YEAR, DayCount);
                    // System.out.println(CompareDates(TR.getTriggerDate(),CurrentDate));

                    if (CompareDates(TR.getTriggerDate(), CurrentDate) && !TR.isTriggered()) {
                        if (TR.getTarget_Balance() == 0) {
                            SuggestedTran = TR.getTarget_Transaction();
                            CurrentAccountBalance = CurrentAccountBalance + TR.getTarget_Transaction();
                            SimulatedTransaction ST = new SimulatedTransaction(TR.getTarget_Transaction(), CurrentAccountBalance, CurrentDate);
                            TransOut.add(ST);
                            TR.setTriggered(true);
                            FoundTransaction = true;
                            break;
                        }
                        if (TR.getTarget_Transaction() == 0) {
                            SuggestedTran = TR.getTarget_Balance() - CurrentAccountBalance;
                            SimulatedTransaction ST = new SimulatedTransaction(SuggestedTran, TR.getTarget_Balance(), CurrentDate);
                            CurrentAccountBalance = TR.getTarget_Balance();
                            TransOut.add(ST);
                            TR.setTriggered(true);
                            FoundTransaction = true;
                            break;
                        }
                        if (TR.getTarget_Transaction() + CurrentAccountBalance == TR.getTarget_Balance()) {
                            SuggestedTran = TR.getTarget_Transaction();
                            CurrentAccountBalance = TR.getTarget_Balance();
                            SimulatedTransaction ST = new SimulatedTransaction(TR.getTarget_Transaction(), TR.getTarget_Balance(), CurrentDate);
                            TransOut.add(ST);
                            TR.setTriggered(true);
                            FoundTransaction = true;
                            break;
                        }

                        if (TR.getTarget_Balance() != 0 && TR.getTarget_Transaction() != 0) {

                            SuggestedTran = TR.getTarget_Balance() - TR.getTarget_Transaction() - CurrentAccountBalance;
                            CurrentAccountBalance = CurrentAccountBalance + SuggestedTran;
                            SimulatedTransaction ST = new SimulatedTransaction(SuggestedTran, CurrentAccountBalance, CurrentDate);
                            TransOut.add(ST);
                           // TR.setTriggered(true);
                            FoundTransaction = true;
                            break;
                        }
                    }


                    // FoundTransaction = false;
                }
                if (!FoundTransaction) {
                    Calendar CurrentDate = Calendar.getInstance();
                    CurrentDate.setTimeInMillis(SimStartDate.getTimeInMillis());
                    CurrentDate.add(Calendar.DAY_OF_YEAR, DayCount);
                    CurrentAccountBalance = CurrentAccountBalance + SuggestedTran;
                    SimulatedTransaction ST = new SimulatedTransaction(SuggestedTran, CurrentAccountBalance, CurrentDate);
                    TransOut.add(ST);
                    FoundTransaction = false;
                }
                //  System.out.println(SuggestedTran + " " + SuggestedBalance);
                //  FoundTransaction = false;


            }

        }

    }

    public boolean CompareDates(Calendar Date1, Calendar Date2) {

        if (Date1.get(Calendar.YEAR) == Date2.get(Calendar.YEAR)) {
            if (Date1.get(Calendar.DAY_OF_YEAR) == Date2.get(Calendar.DAY_OF_YEAR)) {
                return true;
            }
        }
        return false;
    }

    private double RoundToDecimals(double valtoRound) {
        BigDecimal BD = new BigDecimal(valtoRound);
        BD = BD.setScale(2, RoundingMode.HALF_UP);
        return BD.doubleValue();
    }

    private double RoundToDecimals(double valtoRound, Integer NumberOfDecimals) {
        BigDecimal BD = new BigDecimal(valtoRound);
        BD = BD.setScale(NumberOfDecimals, RoundingMode.HALF_UP);
        return BD.doubleValue();
    }

}
