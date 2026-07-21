# DropRoute API Specification

Version: v1

---

# Authentication

POST /api/v1/auth/register

Create a new account.

---

POST /api/v1/auth/login

Authenticate a user.

---

GET /api/v1/auth/me

Return current user.

---

# Files

POST /api/v1/files

Upload file.

---

GET /api/v1/files

List user's files.

---

GET /api/v1/files/{id}

Get file metadata.

---

DELETE /api/v1/files/{id}

Delete file.

---

# Routes

POST /api/v1/routes

Create a delivery route.

---

GET /api/v1/routes

List routes.

---

GET /api/v1/routes/{id}

Route details.

---

PATCH /api/v1/routes/{id}

Update route.

---

DELETE /api/v1/routes/{id}

Revoke route.

---

# Download

GET /d/{token}

Validate policies and download file.

---

# Route Events

GET /api/v1/routes/{id}/events

View delivery history.