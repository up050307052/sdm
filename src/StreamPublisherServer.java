import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;
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

        // colocar na hashtable
        if (providerList.containsKey(type)) {
            System.out.println("registerStream");
            Vector <String> v = (Vector<String>)providerList.remove(type);
            v.add(provider);
            providerList.put(type, v);
        }
        else {
            Vector <String> v = new Vector<String>();
            v.add(provider);
            providerList.put(type, v);
        }

        if (subscriberList.contains(type)) {
            System.out.println("tem subs");

            try {
                IStreamProvider isp = (IStreamProvider) Naming.lookup("rmi://localhost/"+provider);
                isp.startStreaming();
            } catch (NotBoundException ex) {
                Logger.getLogger(StreamPublisherServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(StreamPublisherServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void subscribeStream(String subscriber, StreamType type)
            throws RemoteException {
        if (subscriberList.containsKey(type)) {
            Vector <String> v = (Vector<String>)subscriberList.remove(type);
            v.add(subscriber);
            subscriberList.put(type, v);
        }
        else {
            Vector <String> v = new Vector<String>();
            v.add(subscriber);
            subscriberList.put(type, v);
        }
    }

    public void unsubscribeStream(String subscriber, StreamType type)
            throws RemoteException {
    }

    public void forwardStream(String provider, Stream data)
            throws RemoteException {
        Vector <String> v = (Vector<String>)subscriberList.remove(data.type);
        for (int i=0; i<v.size(); i++) {
            try {
                IStreamSubscriber iss = (IStreamSubscriber) Naming.lookup("rmi://localhost/"+v.get(i));
                iss.forwardStream(provider, data);
            } catch (NotBoundException ex) {
                Logger.getLogger(StreamPublisherServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(StreamPublisherServer.class.getName()).log(Level.SEVERE, null, ex);
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
