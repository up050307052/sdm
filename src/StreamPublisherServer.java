import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StreamPublisherServer
        extends java.rmi.server.UnicastRemoteObject
        implements IStreamPublisher {

    private static final String streamPublisherServiceName = "StreamPublisherService";
    private static final String streamPublisherServerName = "StreamPublisherServer";

    private Hashtable subscriberList;
    private Hashtable providerList;

    public StreamPublisherServer()
            throws java.rmi.RemoteException {

        subscriberList = new Hashtable();
        providerList   = new Hashtable();
    }
    
    public void registerStream(String provider, StreamType type)
            throws RemoteException {

        providerList.put(provider, type);

        if (subscriberList.contains(type))

    }

    public void subscribeStream(String subscriber, StreamType type)
            throws RemoteException {

        subscriberList.add(new SubscriberRegister(subscriber, type));
    }

    public void unsubscribeStream(String subscriber, StreamType type)
            throws RemoteException {
    }

    public void forwardStream(String provider, Stream data)
            throws RemoteException {

        for (int i=0; i< subscriberList.size(); i++) {
            if (subscriberList.get(i).streamType.type == data.type.type) {
                try {
                    IStreamSubscriber iss = (IStreamSubscriber) Naming.lookup("rmi://localhost/"+subscriberList.get(i).subscriberServiceName);
                    iss.forwardStream(provider, data);
                } catch (NotBoundException ex) {
                    Logger.getLogger(StreamPublisherServer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(StreamPublisherServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void main (String args[]) {
        try {
            System.out.print("Start " + streamPublisherServerName);
            StreamPublisherServer sps = new StreamPublisherServer();
            System.out.println(" .. Done");

            System.out.print("Registry " + streamPublisherServiceName + " in rmi");
            Naming.rebind("rmi://localhost:1099/"+streamPublisherServiceName, sps);
            System.out.println(" .. Done");

        } catch (MalformedURLException ex) {
            Logger.getLogger(StreamPublisherServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(StreamPublisherServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
