/**
* Interface IStreamSubscriber
*/
public interface IStreamSubscriber
        extends java.rmi.Remote {
    /**
    * @param publisher the name of the host providing the stream
    * @param data the stream
    */
    void forwardStream(String provider, Stream data)
        throws java.rmi.RemoteException;
}