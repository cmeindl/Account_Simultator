package AccountSim.SmartPayments.Hackathon.WBC;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class JsonController {
    Transaction_Rules TR = new Transaction_Rules();
    ArrayList<Transaction_Rules> TRules = TR.buildDefault();
    Simulator fred = new Simulator("08/08/2015", "12/08/2015", TRules);

    @RequestMapping("/accountvalue")
        public SimulatedTransaction greeting() {
        if (fred.isEOF()) {
            TRules = TR.buildDefault();
            fred = new Simulator("08/08/2015", "12/08/2015", TRules);
        }
        return fred.GetNext();
    }
}


