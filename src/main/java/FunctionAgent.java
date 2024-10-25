import jade.core.Agent;

import java.util.Arrays;

public class FunctionAgent extends Agent {

    @Override
    protected void setup() {
        super.setup();
        if (this.getLocalName().equals("Agent1")){
            this.addBehaviour(new StartBehavior());
        }
        this.addBehaviour(new CalculateBehavior());
        this.addBehaviour(new InitiatorBehavior());
        this.addBehaviour(new ReceiveResultBehavior());

        Arrays.stream(this.getArguments())
                        .forEach(arg -> PrinterFunc.valueOf((String) arg).exec());
    }
}
