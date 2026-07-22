-- Run this in the Supabase SQL editor before starting the app.
-- Hibernate is configured with ddl-auto=validate, so the table must already exist.

CREATE TABLE IF NOT EXISTS fun_facts (
    id         SERIAL PRIMARY KEY,
    fact_text  TEXT NOT NULL,
    source     TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    is_active  BOOLEAN NOT NULL DEFAULT TRUE
);
