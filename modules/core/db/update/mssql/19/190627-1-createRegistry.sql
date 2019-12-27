create table SPMU_REGISTRY (
    ID uniqueidentifier,
    --
    CODE varchar(255),
    DATE_ datetime2,
    TO_UIM tinyint,
    PROCESSED tinyint,
    REPLY_STATUS integer,
    REPLY_MESSAGE varchar(max),
    REPLY_RESULT varchar(max),
    MESSAGE varchar(max),
    --
    primary key nonclustered (ID)
);