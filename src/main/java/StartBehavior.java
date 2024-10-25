import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class StartBehavior extends OneShotBehaviour {
    @Override
    public void action() {
        ACLMessage startInitiatorMessage= new ACLMessage();
        startInitiatorMessage.addReceiver(myAgent.getAID());
        startInitiatorMessage.setProtocol("Initiator");
        int initX= (int) (Math.random() * 10);
        startInitiatorMessage.setContent(initX+","+initX/2);
        myAgent.send(startInitiatorMessage);
    }
}
