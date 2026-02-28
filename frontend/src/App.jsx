import { useEffect, useMemo, useState } from 'react';
import ServerList from './components/ServerList';

const API_BASE = 'http://localhost:8080/api/v1/vpn';

export default function App() {
  const [servers, setServers] = useState([]);
  const [summary, setSummary] = useState({});
  const [selectedServer, setSelectedServer] = useState('');
  const [username, setUsername] = useState('');
  const [protocol, setProtocol] = useState('WIREGUARD');
  const [connection, setConnection] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    fetch(`${API_BASE}/servers`).then((res) => res.json()).then((data) => {
      setServers(data);
      if (data.length > 0) {
        setSelectedServer(data[0].id);
        setProtocol(data[0].protocol);
      }
    });

    fetch(`${API_BASE}/summary`).then((res) => res.json()).then(setSummary);
  }, []);

  const totalCountries = useMemo(() => Object.keys(summary).length, [summary]);

  const handleConnect = async (event) => {
    event.preventDefault();
    setError('');
    setConnection(null);

    const response = await fetch(`${API_BASE}/connect`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username, serverId: selectedServer, protocol })
    });

    const payload = await response.json();
    if (!response.ok) {
      setError(payload.error || 'Unable to connect');
      return;
    }

    setConnection(payload);
  };

  return (
    <main className="app-shell">
      <header>
        <h1>Spring + React VPN Application</h1>
        <p>{servers.length} servers across {totalCountries} countries</p>
      </header>

      <ServerList
        servers={servers}
        selectedServer={selectedServer}
        onSelect={setSelectedServer}
      />

      <form className="card" onSubmit={handleConnect}>
        <h2>Connect</h2>
        <label>
          Username
          <input
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            placeholder="enter username"
          />
        </label>

        <label>
          Protocol
          <select value={protocol} onChange={(e) => setProtocol(e.target.value)}>
            <option value="WIREGUARD">WireGuard</option>
            <option value="OPENVPN">OpenVPN</option>
            <option value="IKEV2">IKEv2</option>
          </select>
        </label>

        <button type="submit">Connect to VPN</button>
      </form>

      {error && <p className="error">{error}</p>}

      {connection && (
        <section className="card">
          <h2>Connection Config</h2>
          <p>Status: <strong>{connection.status}</strong></p>
          <p>Server: {connection.serverId}</p>
          <pre>{connection.config}</pre>
        </section>
      )}
    </main>
  );
}
