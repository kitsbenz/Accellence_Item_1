INSERT INTO applications (product_type, product_program, card_type, campaign_code, is_vip, app_status, created_on, modified_on) VALUES
('Credit Card', 'Platinum Plus', 'Visa', 'CAMPAIGN2025', 1, 'Pending', GETDATE(), GETDATE()),
('Credit Card', 'Gold Basic', 'MasterCard', 'GOLD2025', 0, 'Approved', GETDATE(), GETDATE()),
('Loan', 'Personal Flexi', 'N/A', 'LOAN55', 0, 'Rejected', GETDATE(), GETDATE()),
('Credit Card', 'Titanium Elite', 'Amex', 'TITAN100', 1, 'Pending', GETDATE(), GETDATE());
