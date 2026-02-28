export default function ServerList({ servers, selectedServer, onSelect }) {
  return (
    <div className="card">
      <h2>Available VPN Servers</h2>
      <div className="server-grid">
        {servers.map((server) => (
          <button
            key={server.id}
            className={`server-item ${selectedServer === server.id ? 'selected' : ''}`}
            onClick={() => onSelect(server.id)}
          >
            <strong>{server.country} - {server.city}</strong>
            <span>{server.protocol}</span>
            <span>{server.latencyMs}ms latency · {server.loadPercent}% load</span>
          </button>
        ))}
      </div>
    </div>
  );
}
