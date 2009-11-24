class ProviderRegister {

    protected String providerServiceName;
    protected StreamType streamType;
    
    public ProviderRegister (String provider, StreamType type) {
        providerServiceName = provider;
        streamType          = type;
    }
}
