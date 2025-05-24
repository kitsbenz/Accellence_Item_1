CREATE TABLE applications (
    app_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    product_type VARCHAR(100) NOT NULL,
    product_program VARCHAR(100),
    card_type VARCHAR(50),
    campaign_code VARCHAR(50),
    is_vip BIT,
    app_status VARCHAR(50),
    created_on DATETIME2,
    modified_on DATETIME2
);
