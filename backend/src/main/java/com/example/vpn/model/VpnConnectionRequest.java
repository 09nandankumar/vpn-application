package com.example.vpn.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VpnConnectionRequest(
        @NotBlank(message = "username is required") String username,
        @NotBlank(message = "serverId is required") String serverId,
        @NotNull(message = "protocol is required") VpnProtocol protocol
) {
}
