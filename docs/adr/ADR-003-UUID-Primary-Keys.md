# ADR-003: Use UUID Primary Keys

**Date:** 21 July 2026

---

# Context

DropRoute exposes Routes through publicly accessible URLs.

Sequential integer identifiers are predictable and may expose internal implementation details.

---

# Decision

All entities will use UUID as their primary key.

---

# Why

- Secure identifiers
- Better distributed system support
- No predictable IDs
- Easier future migration

---

# Consequences

Advantages

- Improved security
- Better scalability
- Cleaner external APIs

Disadvantages

- Larger index size
- Slightly slower inserts compared to integers