# DropRoute Architecture

**Version:** 0.1.0  
**Status:** Draft  
**Last Updated:** 21 July 2026

---

# Overview

DropRoute is a programmable file delivery platform built using a **Modular Monolith Architecture**.

The application is organized into independent business modules with clear responsibilities while remaining a single deployable application.

This architecture enables rapid development for the MVP while allowing future migration to microservices if required.

---

# High-Level Architecture

```
                         +----------------------+
                         |     React Client     |
                         +----------+-----------+
                                    |
                              HTTPS / REST API
                                    |
                         +----------v-----------+
                         |    Spring Boot API   |
                         +----------+-----------+
                                    |
      -----------------------------------------------------------------
      |            |              |             |          |            |
      |            |              |             |          |            |
 Authentication  Route Service  File Service  Policy Engine Audit Service Notification Service
      |            |              |             |          |            |
      -----------------------------------------------------------------
                                    |
                          PostgreSQL Database
                                    |
                           Storage Provider
                                    |
                    Local Storage / MinIO / AWS S3
```

---

# Architecture Style

DropRoute follows a **Modular Monolith** architecture.

Each business capability is implemented as an independent module.

Modules communicate through service interfaces instead of direct implementation coupling.

---

# Modules

## Authentication Module

Responsible for:

- User Registration
- Login
- JWT Authentication
- Password Encryption
- Password Reset (Future)

---

## Route Service

Responsible for:

- Create Route
- Update Route
- Delete Route
- Revoke Route
- Generate Secure Link

A Route represents the delivery mechanism for a file.

One file may have multiple routes with different delivery policies.

---

## File Service

Responsible for:

- Upload File
- Delete File
- File Metadata
- Storage Interaction
- File Retrieval

This module never evaluates access permissions.

---

## Policy Engine

The heart of DropRoute.

Responsible for evaluating all delivery policies before allowing access.

Example flow:

```
Download Request

↓

Validate Token

↓

Check Expiration

↓

Check Download Limit

↓

Check Password

↓

Check Recipient Rules

↓

Allow / Reject Download
```

Every future delivery rule will be implemented here.

---

## Audit Service

Responsible for recording every important system event.

Examples:

- User Logged In
- File Uploaded
- Route Created
- Download Started
- Download Successful
- Download Failed
- Route Revoked

This data powers analytics and delivery history.

---

## Notification Service

Responsible for:

- Download Notifications
- Expiry Notifications
- Future Email Notifications

Notification delivery should remain independent from business logic.

---

# Storage Provider

The storage layer is abstracted from the business logic.

Initial implementation:

- Local File System

Future implementations:

- MinIO
- AWS S3

This abstraction allows storage providers to be swapped without changing business logic.

---

# Why Modular Monolith?

Advantages

- Faster development
- Easier debugging
- Easier testing
- Single deployment
- Lower operational complexity

Disadvantages

- Entire application is deployed together
- Independent scaling is not possible
- Requires future extraction if product grows significantly

---

# Future Evolution

If DropRoute scales significantly, modules such as:

- Notification
- Audit
- File Storage

can be extracted into independent microservices with minimal changes because module boundaries are defined from the beginning.