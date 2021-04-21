# DROP TABLE IF EXISTS sihedes.role;
#
# CREATE TABLE IF NOT EXISTS sihedes.role (
#   id_role bigint(20)  NOT NULL,
#   nama_role varchar(50) NOT NULL
# );
#
# --
# -- Dumping data for table role
# --
#
# INSERT INTO sihedes.role (id_role, nama_role) VALUES
# (1, 'Admin'),
# (2, 'Helpdesk'),
# (3, 'Pengaju'),
# (4, 'Kepala Departemen IT'),
# (5, 'Staff IT'),
# (6, 'Kepala Departemen');
#
# --
# -- Indexes for table role
# --
# ALTER TABLE sihedes.role
#     ADD PRIMARY KEY (id_role);
#
# --
# -- AUTO_INCREMENT for dumped tables
# --
#
# --
# -- AUTO_INCREMENT for table role
# --
# ALTER TABLE sihedes.role
#     MODIFY id_role bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
# COMMIT;