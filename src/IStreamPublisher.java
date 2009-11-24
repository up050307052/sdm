/**
* Interface IStreamPublisher
*/
public interface IStreamPublisher
        extends java.rmi.Remote {
    /**
    * @param provider the name of the host providing the stream
    * @param type the stream type
    */
    void registerStream(String provider, StreamType type)
        throws java.rmi.RemoteException;
    /**
    * @param subscriber the name of the host subscribing the stream
    * @param type the stream type
    */
    void subscribeStream(String subscriber, StreamType type)
        throws java.rmi.RemoteException;
    /**
    * @param subscriber the name of the host unsubscribing the stream
    * @param type the stream type
    */
    void unsubscribeStream(String subscriber, StreamType type)
        throws java.rmi.RemoteException;
    /**
    * @param publisher the name of the host providing the stream
    * @param data the stream
    */
    void forwardStream(String provider, Stream data)
        throws java.rmi.RemoteException;
}