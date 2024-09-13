# Backend for Frontend (BFF) web Service
- Backend-for-Frontend (BFF) service that’s responsible for aggregating data from multiple microservices
- and returning a single response to the frontend.
- The BFF service is a specialized backend layer designed for the frontend
- It orchestrates calls to Student Service, Teacher Service, and Storage Service... and returns the aggregated result.

## Workflow:
- BFF call different Service Student-Service, Teacher-Service, DM-Service
- BFF aggregate the data and send response back to Frontend
