package com.example.campusrideapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ride")
public class RideProperties {
    private int pageSize = 6;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize){
        this.pageSize = pageSize;
    }
}
