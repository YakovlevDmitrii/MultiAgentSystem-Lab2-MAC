import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class InitiatorBehavior extends Behaviour {

    public String[] agentsArray= {"Agent1","Agent2","Agent3"};

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchProtocol("Initiator");
        ACLMessage message = myAgent.receive(mt);
        if (message!=null){
            System.out.println();
            System.out.println(myAgent.getLocalName()+" is initiator(old initiator: "+message.getSender().getLocalName()+"), receive message for calculate to all agents");
            for (String agent : agentsArray){
                ACLMessage requestForCalculate=new ACLMessage();
                requestForCalculate.addReceiver(new AID(agent,false));
                requestForCalculate.setProtocol("Calculate");
                requestForCalculate.setContent(message.getContent());
                myAgent.send(requestForCalculate);
            }
        }else {
            block();
        }

    }

    @Override
    public boolean done() {
        return false;
    }
}
