import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;



public class CalculateBehavior extends Behaviour {

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchProtocol("Calculate");
        ACLMessage message = myAgent.receive(mt);
        if (message!=null){
            System.out.println(myAgent.getLocalName()+" receive message for calculate func by values: "+ message.getContent()+ " {send by "+message.getSender().getLocalName()+"}");
            String[] values= message.getContent().split(",");
            double currentX=Double.parseDouble(values[0]);
            double delta=Double.parseDouble(values[1]);
            String calculateResult=calculateValuesAndConertToString(myAgent, new double[]{currentX-delta, currentX, currentX+delta });
            System.out.println(myAgent.getLocalName()+" send reply with values: "+ calculateResult);
            ACLMessage reply = message.createReply();
            reply.setProtocol("ResultOfCalculate");
            reply.setContent(message.getContent()+" : "+calculateResult);
            myAgent.send(reply);
        }else {
            block();
        }

    }

    @Override
    public boolean done() {
        return false;
    }
    private String calculateValuesAndConertToString(Agent agent, double[] values){
        double[] resultInDouble = Functions.calculateValueByAgentName(agent.getLocalName(), values);
        String resultString="";
        for (int i = 0; i < resultInDouble.length; i++) {
            resultString += (resultInDouble[i])+",";
        }
        resultString= resultString.substring(0,resultString.length()-1);
        return resultString;
    }

}
