class SubscriberRegister {
    
    protected String subscriberServiceName;
    protected StreamType streamType;

    public SubscriberRegister (String subscriber, StreamType type) {
        subscriberServiceName = subscriber;
        streamType            = type;
    }
}
