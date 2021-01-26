package com.altipeak.safewalk;

public enum DeviceType {
    VIRTUAL ("Virtual"),
    SESAMI_MOBILE ("SESAMI:Mobile"),
    SESAMI_MOBILE_HYBRID ("SESAMI:Mobile:Hybrid"),
    TOTP_MOBILE ("TOTP:Mobile"),
    TOTP_MOBILE_HYBRID ("TOTP:Mobile:Hybrid"),
    FAST_AUTH ("Fast:Auth:Mobile:Asymmetric");
    
    private String code;
    
    private DeviceType(String code) {
        this.code = code;
    }
    
    // ************************************
    // * Public Methods
    // ************************************
    
    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return this.getCode();
    }

    public static DeviceType getEnum(String value) {
        for(DeviceType v : values())
            if(v.getCode().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
}
