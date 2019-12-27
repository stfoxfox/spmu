-- begin SPMU_CONTRACT_STAGE
create table SPMU_CONTRACT_STAGE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CONTRACT_ID uniqueidentifier,
    NUM integer,
    CODE varchar(32),
    NAME varchar(255),
    START_ datetime2,
    END_ datetime2,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_CONTRACT_STAGE
-- begin SPMU_APPLICATION_STAGE_VALUE
create table SPMU_APPLICATION_STAGE_VALUE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    PLAN_ACCOUNT_ID uniqueidentifier,
    ARTICLE_ID uniqueidentifier,
    ANALYTICA_ID uniqueidentifier,
    YEAR_ integer,
    MEASURE_ID uniqueidentifier,
    PRICE double precision,
    AMOUNT double precision,
    TOTAL double precision,
    VALUE_ double precision,
    Q1 double precision,
    Q2 double precision,
    Q3 double precision,
    Q4 double precision,
    M1 double precision,
    M2 double precision,
    M3 double precision,
    M4 double precision,
    M5 double precision,
    M6 double precision,
    M7 double precision,
    M8 double precision,
    M9 double precision,
    M10 double precision,
    M11 double precision,
    M12 double precision,
    APPLICATION_STAGE_ID uniqueidentifier,
    PARENT_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_APPLICATION_STAGE_VALUE
-- begin SPMU_PLAN_ARTICLE
create table SPMU_PLAN_ARTICLE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    GUID uniqueidentifier,
    PARENT_ID uniqueidentifier,
    FINSOURCE_ID uniqueidentifier,
    NAME varchar(255) not null,
    DESCRIPTION varchar(max),
    FULLNAME varchar(255),
    CODE varchar(255) not null,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_PLAN_ARTICLE
-- begin SPMU_CURRENCY_RATE
create table SPMU_CURRENCY_RATE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CODE varchar(3) not null,
    DATE datetime2 not null,
    RATE double precision not null,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_CURRENCY_RATE
-- begin SPMU_MEASURE
create table SPMU_MEASURE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CODE varchar(32) not null,
    NAME varchar(255) not null,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_MEASURE
-- begin SPMU_AFFILATE
create table SPMU_AFFILATE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    GUID uniqueidentifier not null,
    PARENT_ID uniqueidentifier,
    CODE varchar(32) not null,
    NAME varchar(255) not null,
    DESCRIPTION varchar(max),
    FULLNAME varchar(255),
    --
    primary key nonclustered (ID)
)^
-- end SPMU_AFFILATE
-- begin SPMU_ACCOUNT
create table SPMU_ACCOUNT (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    GUID uniqueidentifier,
    CODE varchar(32),
    NAME varchar(255),
    --
    primary key nonclustered (ID)
)^
-- end SPMU_ACCOUNT
-- begin SPMU_FINSOURCE
create table SPMU_FINSOURCE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CODE varchar(32) not null,
    NAME varchar(255) not null,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_FINSOURCE
-- begin SPMU_CURRENCY
create table SPMU_CURRENCY (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CODE varchar(3) not null,
    NAME varchar(255) not null,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_CURRENCY
-- begin SPMU_FLYGHT_TYPE
create table SPMU_FLYGHT_TYPE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CODE varchar(32) not null,
    NAME varchar(255) not null,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_FLYGHT_TYPE
-- begin SPMU_REGISTRY
create table SPMU_REGISTRY (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
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
)^
-- end SPMU_REGISTRY
-- begin SPMU_PLAN_TYPE
create table SPMU_PLAN_TYPE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    GUID uniqueidentifier not null,
    CODE varchar(32) not null,
    NAME varchar(255) not null,
    DESCRIPTION varchar(max),
    KIND varchar(50) not null,
    FIXED tinyint not null,
    FUNDING_ID uniqueidentifier not null,
    CREATED datetime2 not null,
    EXPIRED datetime2 not null,
    CREATOR_ID uniqueidentifier not null,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_PLAN_TYPE
-- begin SPMU_LOCATION
create table SPMU_LOCATION (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CODE varchar(32) not null,
    NAME varchar(255) not null,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_LOCATION
-- begin SPMU_STATUS_LINK
create table SPMU_STATUS_LINK (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    STATUS varchar(50),
    CODE varchar(255),
    --
    primary key nonclustered (ID)
)^
-- end SPMU_STATUS_LINK
-- begin SPMU_APPLICATION
create table SPMU_APPLICATION (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    DTYPE varchar(100),
    --
    TYPE_ varchar(50) not null,
    KIND varchar(50),
    CHANGE tinyint not null,
    ACTIVE varchar(50) not null,
    YEAR integer not null,
    CODE varchar(255),
    DATE datetime2 not null,
    AFFILIATE_ID uniqueidentifier not null,
    DATE_START datetime2,
    DATE_END datetime2,
    RESPONSIBLE_ID uniqueidentifier,
    TARGET_ID uniqueidentifier,
    VALUE double precision not null,
    NAME varchar(255),
    DESCRIPTION varchar(max),
    PLAN_ID uniqueidentifier,
    ARTICLE_ID uniqueidentifier,
    ORDER_ integer,
    LAYOUT integer,
    ASSIGNMENT varchar(255),
    PROOF varchar(255),
    STATUS varchar(50),
    DIVISION_ID uniqueidentifier,
    PLAN_ARTICLE_ID uniqueidentifier,
    --
    -- from spmu_ApplicationEvent
    ANALYTICA_ID uniqueidentifier,
    RESULT_ varchar(255),
    JUSTIFICATION varchar(255),
    COSTING varchar(255),
    IS_GGE tinyint,
    IS_PSD tinyint,
    GGE varchar(255),
    PSD varchar(255),
    OPTION_ tinyint,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_APPLICATION
-- begin SPMU_APPLICATION_STAGE_TYPE
create table SPMU_APPLICATION_STAGE_TYPE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CREATED datetime2,
    EXPIRED datetime2,
    CREATOR_ID uniqueidentifier not null,
    NAME varchar(255) not null,
    FRACTION double precision,
    DURATION integer,
    CALENDAR varchar(50),
    --
    primary key nonclustered (ID)
)^
-- end SPMU_APPLICATION_STAGE_TYPE
-- begin SPMU_MANDATE
create table SPMU_MANDATE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CODE varchar(32) not null,
    NAME varchar(255) not null,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_MANDATE
-- begin SPMU_COEFFICIENT_VALUE
create table SPMU_COEFFICIENT_VALUE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    COEFFICIENT_ID uniqueidentifier not null,
    PERIOD integer not null,
    VALUE double precision,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_COEFFICIENT_VALUE
-- begin SPMU_TARGET
create table SPMU_TARGET (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CODE varchar(32) not null,
    NAME varchar(255) not null,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_TARGET
-- begin SPMU_APPLICATION_DETAIL_1
create table SPMU_APPLICATION_DETAIL_1 (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    APPLICATION_ID uniqueidentifier not null,
    TYPE_ID uniqueidentifier not null,
    VALUE1 varchar(255),
    VALUE2 varchar(255),
    VALUE3 varchar(255),
    --
    primary key nonclustered (ID)
)^
-- end SPMU_APPLICATION_DETAIL_1
-- begin SPMU_APPLICATION_FILE_DESCRIPTOR
create table SPMU_APPLICATION_FILE_DESCRIPTOR (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    APPLICATION_ID uniqueidentifier,
    FILE_ID uniqueidentifier,
    DESCRIPTION_ varchar(255),
    --
    primary key nonclustered (ID)
)^
-- end SPMU_APPLICATION_FILE_DESCRIPTOR
-- begin SPMU_REGISTRY_CONTENT
create table SPMU_REGISTRY_CONTENT (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    REGISTRY_ID uniqueidentifier,
    APPLICATION_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_REGISTRY_CONTENT
-- begin SPMU_PLAN_SECTION
create table SPMU_PLAN_SECTION (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    GUID uniqueidentifier,
    CODE varchar(32) not null,
    NAME varchar(255) not null,
    PARENT_ID uniqueidentifier,
    TYPE_ID uniqueidentifier,
    CREATED datetime2,
    EXPIRED datetime2,
    CREATOR_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_PLAN_SECTION
-- begin SPMU_EVENT_TYPE
create table SPMU_EVENT_TYPE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CODE varchar(32) not null,
    NAME varchar(255) not null,
    TEMPLATE varchar(255) not null,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_EVENT_TYPE
-- begin SPMU_EMPLOYEE
create table SPMU_EMPLOYEE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    MANDATE_ID uniqueidentifier not null,
    FIRSTNAME varchar(255) not null,
    SECONDNAME varchar(255) not null,
    LASTNAME varchar(255) not null,
    GENDER varchar(255),
    BITHDATE datetime2,
    POSITION_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_EMPLOYEE
-- begin SPMU_APPLICATION_STAGE_CONTRACT
create table SPMU_APPLICATION_STAGE_CONTRACT (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    STAGE_ID uniqueidentifier,
    CONTRACT_ID uniqueidentifier,
    AMOUNT double precision,
    ACCRUED double precision,
    PAID double precision,
    COMPENSATED double precision,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_APPLICATION_STAGE_CONTRACT
-- begin SPMU_DIVISION
create table SPMU_DIVISION (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    GUID uniqueidentifier not null,
    PARENT_ID uniqueidentifier,
    CODE varchar(32) not null,
    NAME varchar(255) not null,
    DESCRIPTION varchar(max),
    AFFILIATE_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_DIVISION
-- begin SPMU_APPLICATION_LINK
create table SPMU_APPLICATION_LINK (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    PARENT_ID uniqueidentifier,
    PARENT_STAGE_ID uniqueidentifier,
    CHILD_STAGE_ID uniqueidentifier,
    LINKTYPE varchar(50),
    STEP integer,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_APPLICATION_LINK
-- begin SPMU_COEFFICIENT
create table SPMU_COEFFICIENT (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CODE varchar(32),
    TYPE integer,
    NAME varchar(255),
    MEASURE_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_COEFFICIENT
-- begin SPMU_ANO
create table SPMU_ANO (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    DATA_TYPE integer,
    VAL double precision,
    TIME_PERIOD uniqueidentifier,
    AFFILIATE_ID uniqueidentifier,
    FLIGHT_TYPE_ID uniqueidentifier,
    IS_FACT tinyint,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_ANO
-- begin SPMU_PLAN_ACCOUNT
create table SPMU_PLAN_ACCOUNT (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    GUID uniqueidentifier,
    CODE varchar(32),
    NAME varchar(255),
    ARTICLE_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_PLAN_ACCOUNT
-- begin SPMU_APPLICATION_RESPONSIBLE
create table SPMU_APPLICATION_RESPONSIBLE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    APPLICATION_ID uniqueidentifier,
    EMPLOYEE_ID uniqueidentifier,
    ROLE varchar(50),
    --
    primary key nonclustered (ID)
)^
-- end SPMU_APPLICATION_RESPONSIBLE
-- begin SPMU_APPLICATION_OBJECT_TYPE
create table SPMU_APPLICATION_OBJECT_TYPE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    PARENT_ID uniqueidentifier,
    CODE varchar(32) not null,
    NAME varchar(255) not null,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_APPLICATION_OBJECT_TYPE
-- begin SPMU_WORKFLOW
create table SPMU_WORKFLOW (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    GUID uniqueidentifier not null,
    PARENT_ID uniqueidentifier,
    DESCRIPTION varchar(255),
    EQUAL_TO_PROCESS_STAGE_STATUS varchar(255),
    CURRENT_STAGE integer,
    NEW_STAGE integer,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_WORKFLOW
-- begin SPMU_CONTRACT
create table SPMU_CONTRACT (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CODE varchar(32),
    NAME varchar(255),
    START_ datetime2,
    END_ datetime2,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_CONTRACT
-- begin SPMU_ORGANIZATION
create table SPMU_ORGANIZATION (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    GUID uniqueidentifier not null,
    CODE varchar(32) not null,
    NAME varchar(255) not null,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_ORGANIZATION

-- begin SPMU_APPLICATION_STAGE
create table SPMU_APPLICATION_STAGE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    DTYPE varchar(100),
    --
    PARENT_ID uniqueidentifier,
    NAME varchar(255) not null,
    DESCRIPTION varchar(255),
    ORDER_ integer,
    AFFILIATE_ID uniqueidentifier,
    OBJECT_ID uniqueidentifier,
    TYPE_ID uniqueidentifier,
    PLAN_ID uniqueidentifier,
    SECTION_ID uniqueidentifier,
    VALUE_ double precision,
    START_ datetime2,
    END_ datetime2,
    APPLICATION_ID uniqueidentifier,
    TASK_ uniqueidentifier,
    EDIT_NEEDED tinyint,
    IS_GGE tinyint,
    GGE_DET varchar(255),
    IS_PSD tinyint,
    PSD_DET varchar(255),
    STATUS integer,
    Q1 double precision,
    Q2 double precision,
    Q3 double precision,
    Q4 double precision,
    M1 double precision,
    M2 double precision,
    M3 double precision,
    M4 double precision,
    M5 double precision,
    M6 double precision,
    M7 double precision,
    M8 double precision,
    M9 double precision,
    M10 double precision,
    M11 double precision,
    M12 double precision,
    CLOSED_ tinyint,
    PLAN_ARTICLE_ID uniqueidentifier,
    ANALYTICA_ID uniqueidentifier,
    KIND varchar(50),
    IS_QUARTAL tinyint not null,
    FIXED_ASSET_ID uniqueidentifier,
    LOCATION_ID uniqueidentifier,
    EQUIPMENT_OBJECT_ID uniqueidentifier,
    PRIORITY_ID uniqueidentifier,
    PRICING_PROCEDURE_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_APPLICATION_STAGE
-- begin SPMU_APPLICATION_OBJECT_STAGE
create table SPMU_APPLICATION_OBJECT_STAGE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    OBJECT_ID uniqueidentifier not null,
    STAGE_ID uniqueidentifier not null,
    ORDER_ integer not null,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_APPLICATION_OBJECT_STAGE
-- begin SPMU_APPLICATION_STAGE_RESPONSIBLE
create table SPMU_APPLICATION_STAGE_RESPONSIBLE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    APPLICATIONSTAGE_ID uniqueidentifier,
    EMPLOYEE_ID uniqueidentifier,
    ROLE varchar(50),
    --
    primary key nonclustered (ID)
)^
-- end SPMU_APPLICATION_STAGE_RESPONSIBLE
-- begin SPMU_APPLICATION_STAGE_FILE_DESCRIPTOR
create table SPMU_APPLICATION_STAGE_FILE_DESCRIPTOR (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    APPLICATION_ID uniqueidentifier,
    FILE_ID uniqueidentifier,
    DESCRIPTION_ varchar(255),
    --
    primary key nonclustered (ID)
)^
-- end SPMU_APPLICATION_STAGE_FILE_DESCRIPTOR
-- begin SPMU_APPLICATION_DETAIL
create table SPMU_APPLICATION_DETAIL (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CODE varchar(32) not null,
    NAME varchar(255) not null,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_APPLICATION_DETAIL
-- begin SPMU_APPLICATION_OBJECT
create table SPMU_APPLICATION_OBJECT (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    GUID uniqueidentifier,
    ORIGIN_ID uniqueidentifier,
    PARENT_ID uniqueidentifier,
    AFFILIATE_ID uniqueidentifier,
    DIVISION_ID uniqueidentifier,
    RTOPUID uniqueidentifier,
    LOCATION varchar(255),
    LOCATION_ID integer,
    COORDINATE varchar(255),
    NAME varchar(255),
    DESCRIPTION varchar(max),
    TYPE_ID uniqueidentifier,
    LINENUM integer,
    QUANTITY integer,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_APPLICATION_OBJECT

-- begin SPMU_MESSAGE_LOG
create table SPMU_MESSAGE_LOG (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    primary key nonclustered (ID)
)^
-- end SPMU_MESSAGE_LOG
-- begin SPMU_CONNECTION_LOG
create table SPMU_CONNECTION_LOG (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    URL varchar(max),
    REQUEST varchar(max),
    HTTP_CODE integer,
    RESPONSE varchar(max),
    --
    primary key nonclustered (ID)
)^
-- end SPMU_CONNECTION_LOG
-- begin SPMU_POSITION
create table SPMU_POSITION (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CODE varchar(32),
    NAME varchar(255),
    --
    primary key nonclustered (ID)
)^
-- end SPMU_POSITION
-- begin SEC_USER
alter table SEC_USER add AFFILIATE_ID uniqueidentifier ^
alter table SEC_USER add MANDATE_ID uniqueidentifier ^
alter table SEC_USER add EMPLOYEE_ID uniqueidentifier ^
alter table SEC_USER add DTYPE varchar(100) ^
update SEC_USER set DTYPE = 'spmu_SpmuUser' where DTYPE is null ^
-- end SEC_USER
-- begin SPMU_APPLICATION_HISTORY
create table SPMU_APPLICATION_HISTORY (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    ENTITY_SNAPSHOT_ID uniqueidentifier not null,
    PROC_TASK_ID uniqueidentifier,
    USER_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_APPLICATION_HISTORY
-- end SPMU_APPLICATION_FILE_DESCRIPTOR
-- begin SPMU_FIXED_ASSET
create table SPMU_FIXED_ASSET (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    PARENT_ID uniqueidentifier,
    NAME varchar(255),
    CODE varchar(255),
    FULL_NAME varchar(max),
    VENDOR varchar(255),
    FACTORY_NUMBER varchar(255),
    PASSPORT_NUMBER varchar(255),
    RELEASED_AT datetime2,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_FIXED_ASSET
-- begin SPMU_EQUIPMENT_OBJECT
create table SPMU_EQUIPMENT_OBJECT (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    PARENT_ID uniqueidentifier,
    NAME varchar(255),
    code varchar(255),
    IS_EQUIPMENT tinyint,
    GEO_COORDINATE varchar(255),
    AFFILATE_ID uniqueidentifier not null,
    DIVISION_ID uniqueidentifier not null,
    LOCATION_ID uniqueidentifier not null,
    IS_SUBOBJECT tinyint,
    INVENTORY_NUMBER varchar(255),
    FACTORY_NUMBER varchar(255),
    RELEASED_YEAR integer,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_EQUIPMENT_OBJECT
-- begin SPMU_PRIORITY
create table SPMU_PRIORITY (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CODE varchar(255),
    NAME varchar(255),
    --
    primary key nonclustered (ID)
)^
-- end SPMU_PRIORITY
-- begin SPMU_PRICING_PROCEDURE
create table SPMU_PRICING_PROCEDURE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CODE varchar(255),
    NAME varchar(255),
    DESCRIPTION varchar(max),
    --
    primary key nonclustered (ID)
)^
-- end SPMU_PRICING_PROCEDURE
-- begin SPMU_PLAN_ARTICLE_STAGE
create table SPMU_PLAN_ARTICLE_STAGE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    PLAN_ARTICLE_ID uniqueidentifier,
    NAME varchar(255) not null,
    IS_CHANGE tinyint not null,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_PLAN_ARTICLE_STAGE
-- begin SPMU_PLAN_VARIANT
create table SPMU_PLAN_VARIANT (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CODE varchar(255),
    NAME varchar(255),
    DESCRIPTION varchar(max),
    YEAR integer,
    DATE datetime2,
    RESPONSIBLE_ID uniqueidentifier,
    STATUS varchar(50),
    --
    primary key nonclustered (ID)
)^
-- end SPMU_PLAN_VARIANT
-- begin SPMU_PLAN_VARIANT_LIMIT
create table SPMU_PLAN_VARIANT_LIMIT (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    PLAN_VARIANT_ID uniqueidentifier,
    YEAR integer,
    VALUE double precision not null,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_PLAN_VARIANT_LIMIT
-- begin SPMU_PLAN_VARIANT_TEMPLATE
create table SPMU_PLAN_VARIANT_TEMPLATE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    PLAN_VARIANT_ID uniqueidentifier,
    NAME varchar(255),
    ACTIVE tinyint not null,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_PLAN_VARIANT_TEMPLATE
-- begin SPMU_PLAN_VARIANT_FILTER
create table SPMU_PLAN_VARIANT_FILTER (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    PLAN_VARIANT_TEMPLATE_ID uniqueidentifier,
    FILTER_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_PLAN_VARIANT_FILTER
-- begin SPMU_PLAN_VARIANT_TEMPLATE_APPLICATION
create table SPMU_PLAN_VARIANT_TEMPLATE_APPLICATION (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    PLAN_VARIANT_TEMPLATE_ID uniqueidentifier,
    APPLICATION_TEMPLATE_ID uniqueidentifier,
    IN_PLAN tinyint not null,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_PLAN_VARIANT_TEMPLATE_APPLICATION
-- begin SPMU_PLAN_VARIANT_TEMPLATE_ELEMENT
create table SPMU_PLAN_VARIANT_TEMPLATE_ELEMENT (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    PLAN_VARIANT_TEMPLATE_ID uniqueidentifier,
    PAGE_ELEMENT varchar(50),
    ATTR_NAME varchar(255),
    VAR_VALUE varchar(255),
    --
    primary key nonclustered (ID)
)^
-- end SPMU_PLAN_VARIANT_TEMPLATE_ELEMENT
-- begin SPMU_STATIC_VARIABLE
create table SPMU_STATIC_VARIABLE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    COMPUTED_VARIABLE_ID uniqueidentifier,
    VALUE varchar(255),
    POSITION integer,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_STATIC_VARIABLE
-- begin SPMU_ENTITY_VARIABLE
create table SPMU_ENTITY_VARIABLE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    COMPUTED_VARIABLE_ID uniqueidentifier,
    ENTITY_NAME varchar(255),
    ENTITY_ATTRIBUTE varchar(255),
    POSITION integer,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_ENTITY_VARIABLE
-- begin SPMU_COMPUTED_VARIABLE
create table SPMU_COMPUTED_VARIABLE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    PLAN_VARIANT_TEMPLATE_ID uniqueidentifier,
    NAME varchar(255),
    AGGREGATE_FUNCTION varchar(50),
    --
    primary key nonclustered (ID)
)^
-- end SPMU_COMPUTED_VARIABLE
-- begin SPMU_ENUM_VARIABLE
create table SPMU_ENUM_VARIABLE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    COMPUTED_VARIABLE_ID uniqueidentifier,
    TYPE varchar(255),
    POSITION integer,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_ENUM_VARIABLE
-- begin SPMU_COMPUTED_VARIABLE_LINK
create table SPMU_COMPUTED_VARIABLE_LINK (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    COMPUTED_VARIABLE_ID uniqueidentifier,
    COMPUTED_VARIABLE_CHILD_ID uniqueidentifier,
    POSITION integer,
    --
    primary key nonclustered (ID)
)^
-- end SPMU_COMPUTED_VARIABLE_LINK
