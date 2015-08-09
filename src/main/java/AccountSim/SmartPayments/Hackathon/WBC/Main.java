package AccountSim.SmartPayments.Hackathon.WBC;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import  org.springframework.data.web.config.EnableSpringDataWebSupport;


@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
             ArrayList<Transaction_Rules> TRules = new ArrayList<Transaction_Rules>();
        Transaction_Rules TR = new Transaction_Rules();
        TRules = TR.buildDefault();
        Simulator fred = new Simulator("08/08/2015","12/08/2015",TRules);
        fred.RunSim();
//        ArrayList<SimulatedTransaction> TransOut = new ArrayList<SimulatedTransaction>();
//        TransOut =  fred.getTransOut();

//        for (SimulatedTransaction tr : TransOut){
//         //   Integer Day = tr.getTransDate().get(Calendar.DAY_OF_WEEK);
//
//            System.out.println(tr.getTransDate().get(Calendar.DAY_OF_MONTH) + " : " + tr.getTransactionValue() + " : " + tr.getAccountValue());
//
//
//        }
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Date"+ "\t\t" + "Transaction" + "\t\t" + "Balance");
        while(!fred.isEOF()){
            SimulatedTransaction ST;
            ST = fred.GetNext();
           System.out.println(df.format(ST.getTransDate().getTime()) + "\t\t" + ST.getTransactionValue() + "\t\t" + ST.getAccountValue());
          //  System.out.println(ST.getTransDate().get(Calendar.DAY_OF_MONTH) + " : " + ST.getTransactionValue() + " : " + ST.getAccountValue());

        }
    }
}
