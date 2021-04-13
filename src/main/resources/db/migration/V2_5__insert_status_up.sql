DROP TABLE IF EXISTS `status`;

CREATE TABLE `status` (
  `id_status` bigint(20) NOT NULL,
  `nama_status` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `status`
--

INSERT INTO `status` (`id_status`, `nama_status`) VALUES
(1, 'Open'),
(2, 'Requested'),
(3, 'Waiting for Approval'),
(4, 'Waiting for Assignment'),
(5, 'Assigned'),
(6, 'In Progress'),
(7, 'Done'),
(8, 'Closed');