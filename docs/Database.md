# DropRoute Database Design

**Version:** 0.1.0

---

# Database

PostgreSQL 17

---

# Primary Keys

All entities use UUID as their primary key.

Reason:

- Secure public identifiers
- Better scalability
- Easier future migration
- No predictable IDs

---

# Entity Relationship Diagram

```
User (1)
    |
    | owns
    |
    | (N)
File
    |
    | has
    |
    | (N)
Route
   / \
  /   \
(N)   (N)
 |     |
Policy RouteEvent
```

---

# Tables

## User

Description

Represents an authenticated account.

Fields

- id (UUID)
- full_name
- email
- password_hash
- created_at
- updated_at

---

## File

Description

Represents uploaded file metadata.

Fields

- id (UUID)
- owner_id (FK User)
- original_name
- storage_path
- size
- content_type
- checksum
- created_at

---

## Route

Description

Represents a delivery link for a file.

Fields

- id (UUID)
- file_id (FK File)
- secure_token
- status
- created_at
- expires_at
- revoked_at

Status values

- DRAFT
- ACTIVE
- EXPIRED
- REVOKED

---

## Policy

Description

Represents a single delivery rule.

Fields

- id (UUID)
- route_id (FK Route)
- policy_type
- policy_value
- created_at

Policy Types

- EXPIRY_DATE
- DOWNLOAD_LIMIT
- PASSWORD
- EMAIL_RESTRICTION

---

## RouteEvent

Description

Stores every significant action performed on a Route.

Fields

- id (UUID)
- route_id (FK Route)
- event_type
- ip_address
- user_agent
- timestamp

Event Types

- ROUTE_CREATED
- DOWNLOAD_STARTED
- DOWNLOAD_SUCCESS
- DOWNLOAD_FAILED
- ROUTE_REVOKED
- ROUTE_EXPIRED

---

# Indexes

User

- email (Unique)

File

- owner_id

Route

- secure_token (Unique)
- status

Policy

- route_id

RouteEvent

- route_id
- timestamp

---

# Future Tables

Not included in V1

- RefreshToken
- Notification
- FileVersion
- Package
- Organization