import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Arrays;
import java.util.OptionalDouble;

public class ReceiveResultBehavior extends Behaviour {

    boolean isDone = false;

    double precision = 0.01;

    double[] resultOfSum = new double[3];
    int countOfReceivedResult = 0;
    double currentX = 0;
    double delta = 0;

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchProtocol("ResultOfCalculate");
        ACLMessage message = myAgent.receive(mt);
        if (message != null) {
            System.out.println(myAgent.getLocalName() + " receive result of calculate from " + message.getSender().getLocalName() + " with content: " + message.getContent());
            String[] content = message.getContent().split(":");
            String[] currentData = content[0].split(",");
            currentX = Double.parseDouble(currentData[0]);
            delta = Double.parseDouble(currentData[1]);
            String[] resultOfCalculate = content[1].split(",");
            for (int i = 0; i < resultOfCalculate.length; i++) {
                resultOfSum[i] += Double.parseDouble(resultOfCalculate[i]);
            }
            countOfReceivedResult++;
        } else {
            if (countOfReceivedResult == 3) {
                System.out.println(myAgent.getLocalName() + " received all result; result of sum values: " + convertDoubleArrayToString(resultOfSum));
                double max = Arrays.stream(resultOfSum).max().getAsDouble();
                if (delta < precision) {
                    isDone = true;
                    System.out.println("\nCalculate finish. Max value: " + max + " in x = " + currentX);
                    return;
                }
                double newX = currentX;
                double newDelta = delta;
                if (max == resultOfSum[0]) {
                    newX = currentX - delta;
                } else if (max == resultOfSum[1]) {
                    newDelta = delta / 2;
                } else {
                    newX = currentX + delta;
                }
                ACLMessage messageForNewInitiator = new ACLMessage();
                messageForNewInitiator.addReceiver(new AID(choiceNewInitiator(myAgent.getLocalName()), false));
                messageForNewInitiator.setProtocol("Initiator");
                messageForNewInitiator.setContent(newX + "," + newDelta);
                myAgent.send(messageForNewInitiator);
                resultOfSum = new double[3];
                countOfReceivedResult = 0;
                block();
            }
        }
    }

    @Override
    public boolean done() {
        return isDone;
    }

    public String convertDoubleArrayToString(double[] values) {
        String resultString = "";
        for (int i = 0; i < values.length; i++) {
            resultString += (values[i]) + ",";
        }
        resultString = resultString.substring(0, resultString.length() - 1);
        return resultString;
    }

    public String choiceNewInitiator(String currentAgentName) {
        int newNumberOfAgent = Integer.parseInt(currentAgentName.substring(currentAgentName.length() - 1)) + 1;
        if (newNumberOfAgent > 3) {
            return "Agent" + 1;
        }
        return "Agent" + newNumberOfAgent;
    }
}
