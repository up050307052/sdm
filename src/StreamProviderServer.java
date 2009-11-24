import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StreamProviderServer
        extends java.rmi.server.UnicastRemoteObject
        implements IStreamProvider {

    private static final String streamProviderServerName  = "StreamProviderServer";
    private static final String streamProviderServiceName  = "StreamProviderService1";
    private static final String streamPublisherServiceName = "StreamPublisherService";
    private static StreamType streamType;
    private static IStreamPublisher ips;

    public StreamProviderServer () throws java.rmi.RemoteException{
        streamType = new StreamType(StreamType.TEXT);
    }

    public void startStreaming() throws RemoteException {
        int i=0;
        while (true)
            ips.forwardStream(streamProviderServiceName, new Stream(i++, streamType));
    }

    public void stopStreaming() throws RemoteException {
    }

    public static void main (String args []) {
        try {
            System.out.print("Start "+streamProviderServerName);
            StreamProviderServer sps = new StreamProviderServer();
            System.out.println(" .. Done");

            System.out.print("Registry " + streamProviderServiceName + " in rmi");
            Naming.rebind("rmi://localhost:1099/" + streamProviderServiceName, sps);
            System.out.println(" .. Done");

            System.out.print("Look for "+streamPublisherServiceName);
            ips = (IStreamPublisher) Naming.lookup("rmi://localhost/"+streamPublisherServiceName);
            System.out.println(" .. Done");

            System.out.print("Registry in "+streamPublisherServiceName);
            ips.registerStream(streamProviderServiceName, streamType);
            System.out.println(" .. Done");
            
        } catch (NotBoundException ex) {
            Logger.getLogger(StreamProviderServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(StreamProviderServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(StreamProviderServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
