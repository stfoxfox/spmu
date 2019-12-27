exec sp_rename 'SPMU_CONTRACT.END_', 'END___U30393', 'COLUMN' ^
alter table SPMU_CONTRACT add END_ datetime2 ;
