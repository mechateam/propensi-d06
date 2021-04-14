DROP TABLE IF EXISTS status;

CREATE TABLE IF NOT EXISTS status (
  id_status bigint(20) unsigned NOT NULL,
  nama_status varchar(50) NOT NULL
);



--
-- Dumping data for table status
--

INSERT INTO status (id_status, nama_status) VALUES
(1, 'Open'),
(2, 'Requested'),
(3, 'Waiting for Approval'),
(4, 'Waiting for Assignment'),
(5, 'Assigned'),
(6, 'In Progress'),
(7, 'Done'),
(8, 'Closed');

--
-- Indexes for table status
--
ALTER TABLE status
    ADD PRIMARY KEY (id_status);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table status
--
ALTER TABLE status
    MODIFY id_status bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;