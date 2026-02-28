package com.example.vpn.controller;

import com.example.vpn.model.VpnConnectionRequest;
import com.example.vpn.model.VpnConnectionResponse;
import com.example.vpn.model.VpnServer;
import com.example.vpn.service.VpnService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/vpn")
@CrossOrigin(origins = "http://localhost:5173")
public class VpnController {

    private final VpnService vpnService;

    public VpnController(VpnService vpnService) {
        this.vpnService = vpnService;
    }

    @GetMapping("/servers")
    public ResponseEntity<List<VpnServer>> getServers() {
        return ResponseEntity.ok(vpnService.getServers());
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Long>> getSummary() {
        return ResponseEntity.ok(vpnService.getServerCountrySummary());
    }

    @PostMapping("/connect")
    public ResponseEntity<VpnConnectionResponse> connect(@Valid @RequestBody VpnConnectionRequest request) {
        return ResponseEntity.ok(vpnService.createConnection(request));
    }
}
