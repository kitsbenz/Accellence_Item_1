CREATE TABLE Branch (
    AppId INT PRIMARY KEY,
    BranchCode VARCHAR(10),
    ZoneCode VARCHAR(10),
    RegionCode VARCHAR(10),
    AppDate DATE
);
INSERT INTO Branch (AppId, BranchCode, ZoneCode, RegionCode, AppDate) VALUES
-- ภาคเหนือ (NORTH)
(1, 'B001', 'Z01', 'NORTH', '2024-01-15'),
(2, 'B001', 'Z01', 'NORTH', '2024-02-10'),
(3, 'B002', 'Z01', 'NORTH', '2024-03-20'),
(4, 'B002', 'Z01', 'NORTH', '2024-03-25'),
(5, 'B002', 'Z01', 'NORTH', '2024-04-01'),

-- ภาคกลาง (CENTRAL)
(6, 'B003', 'Z02', 'CENTRAL', '2024-01-12'),
(7, 'B003', 'Z02', 'CENTRAL', '2024-01-13'),
(8, 'B003', 'Z02', 'CENTRAL', '2024-05-14'),
(9, 'B004', 'Z02', 'CENTRAL', '2024-06-01'),

-- ภาคตะวันออกเฉียงเหนือ (NORTHEAST)
(10, 'B005', 'Z03', 'NORTHEAST', '2024-07-11'),
(11, 'B005', 'Z03', 'NORTHEAST', '2024-08-15'),
(12, 'B005', 'Z03', 'NORTHEAST', '2024-09-09'),
(13, 'B006', 'Z03', 'NORTHEAST', '2024-10-01'),

-- ภาคใต้ (SOUTH)
(14, 'B007', 'Z04', 'SOUTH', '2024-04-18'),
(15, 'B007', 'Z04', 'SOUTH', '2024-05-20'),
(16, 'B008', 'Z04', 'SOUTH', '2024-06-22');