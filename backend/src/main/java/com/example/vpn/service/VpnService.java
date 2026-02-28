package com.example.vpn.service;

import com.example.vpn.model.VpnConnectionRequest;
import com.example.vpn.model.VpnConnectionResponse;
import com.example.vpn.model.VpnProtocol;
import com.example.vpn.model.VpnServer;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class VpnService {

    private static final List<VpnServer> SERVERS = List.of(
            new VpnServer("us-nyc-01", "USA", "New York", 38, 45, VpnProtocol.WIREGUARD, true),
            new VpnServer("uk-lon-01", "UK", "London", 52, 34, VpnProtocol.OPENVPN, true),
            new VpnServer("de-fra-01", "Germany", "Frankfurt", 42, 29, VpnProtocol.IKEV2, false),
            new VpnServer("jp-tok-01", "Japan", "Tokyo", 110, 50, VpnProtocol.WIREGUARD, true),
            new VpnServer("sg-sin-01", "Singapore", "Singapore", 95, 40, VpnProtocol.OPENVPN, false)
    );

    public List<VpnServer> getServers() {
        return SERVERS.stream()
                .sorted(Comparator.comparingInt(VpnServer::latencyMs))
                .toList();
    }

    public Map<String, Long> getServerCountrySummary() {
        return SERVERS.stream()
                .collect(Collectors.groupingBy(VpnServer::country, Collectors.counting()));
    }

    public VpnConnectionResponse createConnection(VpnConnectionRequest request) {
        VpnServer selectedServer = SERVERS.stream()
                .filter(server -> server.id().equals(request.serverId()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Server not found: " + request.serverId()));

        String config = switch (request.protocol()) {
            case OPENVPN -> "client\nproto udp\nremote " + selectedServer.id() + " 1194\nauth-user-pass";
            case WIREGUARD -> "[Interface]\nPrivateKey=<client-key>\n[Peer]\nEndpoint=" + selectedServer.id() + ":51820";
            case IKEV2 -> "conn vpn\n  keyexchange=ikev2\n  right=" + selectedServer.id();
        };

        return new VpnConnectionResponse(
                request.username(),
                selectedServer.id(),
                request.protocol(),
                "CONNECTED",
                config
        );
    }
}
