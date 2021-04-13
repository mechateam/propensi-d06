DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id_role` bigint(20) NOT NULL,
  `nama_role` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id_role`, `nama_role`) VALUES
(1, 'Admin'),
(2, 'Helpdesk'),
(3, 'Pengaju'),
(4, 'Kepala Departemen IT'),
(5, 'Staff IT'),
(6, 'Kepala Departemen');