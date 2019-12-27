update SPMU_PLAN_VARIANT_TEMPLATE_APPLICATION set IN_PLAN = 0 where IN_PLAN is null ;
alter table SPMU_PLAN_VARIANT_TEMPLATE_APPLICATION alter column IN_PLAN tinyint not null ;
