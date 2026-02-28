package com.example.vpn.model;

public record VpnServer(
        String id,
        String country,
        String city,
        int latencyMs,
        int loadPercent,
        VpnProtocol protocol,
        boolean streamingOptimized
) {
}
