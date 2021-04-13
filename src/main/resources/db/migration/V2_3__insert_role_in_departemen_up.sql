DROP TABLE IF EXISTS `departemen`;

CREATE TABLE `departemen` (
  `id_dept` bigint(20) NOT NULL,
  `nama_departemen` varchar(50) NOT NULL,
  `nama_kepala` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `departemen`
--

INSERT INTO `departemen` (`id_dept`, `nama_departemen`, `nama_kepala`) VALUES
(1, 'IT Helpdesk', 'Earlene'),
(2, 'Security', 'Praya'),
(3, 'IT Sys Dev', 'Meldi'),
(4, 'IT User Admin', 'Bela'),
(5, 'IT Network', 'Ajeng'),
(6, 'IT Infrastructure', 'Nindya'),
(7, 'Finance', 'Aku'),
(8, 'HR', 'Kamu');