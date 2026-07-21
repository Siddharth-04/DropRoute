# ADR-002: Store Policies as Relational Entities

**Status:** Accepted

**Date:** 21 July 2026

---

# Context

Each Route can have multiple delivery policies such as:

- Expiration
- Download Limit
- Password Protection
- Email Restriction

The system should allow new policy types to be added without redesigning the database.

---

# Decision

Policies will be stored as separate relational records linked to a Route.

Example:

Route
    
    |

    +---- Policy (DOWNLOAD_LIMIT)
    |
    +---- Policy (EXPIRY_DATE)
    |
    +---- Policy (PASSWORD)
    |
    +---- Policy (EMAIL_RESTRICTION)

---

# Why?

- Extensible design
- Easy querying
- Better normalization
- Cleaner business logic
- Future-proof for additional policy types

---

# Consequences

Advantages

- New policy types require minimal changes
- Policies can be evaluated independently
- Easier reporting
- Better maintainability

Disadvantages

- Additional table
- Slightly more joins