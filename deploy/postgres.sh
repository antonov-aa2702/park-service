#!/bin/bash
docker run --name pg-13.3 -p 5433:5432 \
       -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres \
       -d ghcr.io/cloudnative-pg/postgresql:13-bookworm