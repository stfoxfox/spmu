create table SPMU_REGISTRY_CONTENT (
    UUID uniqueidentifier,
    --
    REGISTRY_ID uniqueidentifier,
    APPLICATION_ID uniqueidentifier,
    --
    primary key (REGISTRY_ID, APPLICATION_ID)
);