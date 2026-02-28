# VPN Application (Spring Boot + React)

This project provides a starter VPN management application with:
- **Backend**: Java 17 + Spring Boot REST API.
- **Frontend**: React + Vite dashboard.

## Features
- Browse available VPN servers.
- View server summary by country.
- Simulate VPN connection creation.
- Display generated protocol-specific configuration snippet.

## Project structure

- `backend/` – Spring Boot API (`/api/v1/vpn/*`)
- `frontend/` – React app consuming backend endpoints

## Run backend

```bash
cd backend
mvn spring-boot:run
```

Backend URL: `http://localhost:8080`

## Run frontend

```bash
cd frontend
npm install
npm run dev
```

Frontend URL: `http://localhost:5173`

## API endpoints

- `GET /api/v1/vpn/servers`
- `GET /api/v1/vpn/summary`
- `POST /api/v1/vpn/connect`

Example connect payload:

```json
{
  "username": "demo-user",
  "serverId": "us-nyc-01",
  "protocol": "WIREGUARD"
}
```
