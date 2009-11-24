import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StreamSubscriberServer
        extends java.rmi.server.UnicastRemoteObject
        implements IStreamSubscriber {

    private static final String streamSubscriberServerName = "StreamSubscriberServer";
    private static final String streamSubscriberServiceName = "StreamSubscriberService1";
    private static final String streamPublisherServiceName = "StreamPublisherService";
    private static StreamType streamType;
    private static IStreamPublisher ips;

    public StreamSubscriberServer () throws java.rmi.RemoteException{
        streamType = new StreamType(StreamType.TEXT);
    }

    public void forwardStream(String provider, Stream data)
            throws RemoteException {

        System.out.println(data.x);
    }

    public static void main (String args []) {
        try {
            System.out.print("Start " + streamSubscriberServerName);
            StreamSubscriberServer sss = new StreamSubscriberServer();
            System.out.println(" .. Done");

            System.out.print("Registry " + streamSubscriberServiceName + " in rmi");
            Naming.rebind("rmi://localhost:1099/" + streamSubscriberServiceName, sss);
            System.out.println(" .. Done");

            System.out.print("Look for " + streamPublisherServiceName);
            ips = (IStreamPublisher) Naming.lookup("rmi://localhost/"+streamPublisherServiceName);
            System.out.println(" .. Done");

            System.out.print("Registry in " + streamPublisherServiceName);
            ips.subscribeStream(streamSubscriberServiceName, streamType);
            System.out.println(" .. Done");

        } catch (NotBoundException ex) {
            Logger.getLogger(StreamSubscriberServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(StreamSubscriberServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(StreamSubscriberServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
