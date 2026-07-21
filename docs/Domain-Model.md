# DropRoute Domain Model

**Version:** 0.1.0  
**Status:** Draft  
**Last Updated:** 21 July 2026

---

# Purpose

This document defines the core business concepts of DropRoute.

The domain model serves as the foundation for the database design, REST APIs, and business logic.

---

# Core Domain

DropRoute consists of five core entities:

- User
- File
- Route
- Policy
- RouteEvent

---

# User

A User represents an authenticated account that owns files and manages delivery routes.

## Responsibilities

- Register and login
- Upload files
- Create delivery routes
- View delivery history
- Manage existing routes

## Relationships

- One User can own many Files.
- One User can create many Routes.

---

# File

A File represents an uploaded document or media file.

A File only stores metadata and storage information.

A File does **not** define how it is shared.

## Responsibilities

- Store metadata
- Maintain storage location
- Track file size and type

## Relationships

- A File belongs to one User.
- A File can have many Routes.

---

# Route

A Route is the primary business entity of DropRoute.

A Route defines **how** a file is delivered.

It contains:

- Secure share link
- Delivery status
- Delivery policies
- Download history

## Route Status

- DRAFT
- ACTIVE
- EXPIRED
- REVOKED

## Relationships

- A Route belongs to one File.
- A Route has many Policies.
- A Route has many RouteEvents.

---

# Policy

A Policy represents a single delivery rule.

Multiple policies together determine whether a download request should be allowed.

## Supported Policies (V1)

- Expiry Date
- Download Limit
- Password Protection
- Email Restriction

## Relationships

- A Policy belongs to one Route.

---

# RouteEvent

A RouteEvent records every important action performed on a Route.

## Examples

- ROUTE_CREATED
- DOWNLOAD_STARTED
- DOWNLOAD_SUCCESS
- DOWNLOAD_FAILED
- ROUTE_REVOKED
- ROUTE_EXPIRED

## Relationships

- A RouteEvent belongs to one Route.

---

# Business Rules

1. A user must be authenticated to upload files.

2. A file belongs to exactly one user.

3. A file can have multiple routes.

4. A route belongs to exactly one file.

5. A route can have multiple policies.

6. A route can have multiple route events.

7. Every download attempt generates a RouteEvent.

8. Policies are evaluated before every download.

9. A revoked route cannot be reactivated.

---

# Entity Relationships

```
User
 │
 └── owns ───────────────► File
                              │
                              └── has many ─────► Route
                                                     │
                              ┌──────────────────────┴──────────────────────┐
                              │                                             │
                              ▼                                             ▼
                           Policy                                     RouteEvent
```

---

# Ubiquitous Language

| Term | Meaning |
|------|---------|
| User | Authenticated owner of files |
| File | Uploaded binary object |
| Route | Delivery mechanism for a file |
| Policy | Single delivery rule |
| RouteEvent | Recorded activity performed on a route |
| Delivery | Successful download of a file |

---

# Out of Scope (V1)

The following concepts are intentionally excluded from the first version:

- File Versioning
- Multi-file Packages
- Teams / Organizations
- Public APIs
- Webhooks
- Scheduled Deliveries
- Cloud Storage Providers (S3, MinIO)