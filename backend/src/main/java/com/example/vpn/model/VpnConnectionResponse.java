package com.example.vpn.model;

public record VpnConnectionResponse(
        String username,
        String serverId,
        VpnProtocol protocol,
        String status,
        String config
) {
}
