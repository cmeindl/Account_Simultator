package AccountSim.SmartPayments.Hackathon.WBC;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class SimulatedTransaction {

    public double getTransactionValue() {
        return RoundToDecimals(TransactionValue);
    }

    public double getAccountValue() {
        return RoundToDecimals(AccountValue);
    }

    public Calendar getTransDate() {
        return TransDate;
    }

    public SimulatedTransaction(double transactionValue, double accountValue, Calendar transDate) {
        TransactionValue = transactionValue;
        AccountValue = accountValue;
        TransDate = transDate;
    }


    public SimulatedTransaction(double transactionValue, double accountValue, String transDate) {
        TransactionValue = transactionValue;
        AccountValue = accountValue;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            TransDate.setTime(sdf.parse(transDate));
        }
        catch (Exception e){}
        //TransDate = transDate;
    }

    private double TransactionValue;
    private double AccountValue;
    private Calendar TransDate = Calendar.getInstance();

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


}
