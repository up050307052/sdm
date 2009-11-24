/**
* Interface IStreamProvider
*/
public interface IStreamProvider extends java.rmi.Remote {
    void startStreaming() throws java.rmi.RemoteException;
    void stopStreaming() throws java.rmi.RemoteException;
}