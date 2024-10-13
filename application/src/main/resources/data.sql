-- Seller 1's Warehouses
INSERT INTO warehouse.warehouses (warehouse_id, seller_id, material_type, capacity)
VALUES
    ('9f57ed44-4dcf-4a5a-8c7d-1b1a142763a1', 'dd2e2b7d-4622-441f-9200-123f8bb3e29b', 'GYPSUM', 0),
    ('8361182d-6ae4-42a6-9dff-9146b3ad4234', 'dd2e2b7d-4622-441f-9200-123f8bb3e29b', 'IRON_ORE', 0),
    ('0dfc49c1-8f1e-4b9e-b760-0b5a4a4b4550', 'dd2e2b7d-4622-441f-9200-123f8bb3e29b', 'CEMENT', 0),
    ('e2f7bcdc-69da-4b0e-b73f-6adf79e8d5f9', 'dd2e2b7d-4622-441f-9200-123f8bb3e29b', 'PET_COKE', 0),
    ('b3f15798-6c28-4a33-a8f5-507db494204b', 'dd2e2b7d-4622-441f-9200-123f8bb3e29b', 'SLAG', 0);

-- Seller 2's Warehouses
INSERT INTO warehouse.warehouses (warehouse_id, seller_id, material_type, capacity)
VALUES
    ('39dcbb4a-b535-4ab1-97b2-cfcba92f462a', 'b5fa5dbd-1130-44a1-8db8-4e8d7155e7b6', 'GYPSUM', 0),
    ('bb182e36-4077-4987-b179-544dbd9a9cb6', 'b5fa5dbd-1130-44a1-8db8-4e8d7155e7b6', 'IRON_ORE', 0),
    ('3f23dd36-cbd9-4025-b476-c9a24c3e6c19', 'b5fa5dbd-1130-44a1-8db8-4e8d7155e7b6', 'CEMENT', 0),
    ('bf838cb6-6463-4b77-a70d-cb9f7b52af24', 'b5fa5dbd-1130-44a1-8db8-4e8d7155e7b6', 'PET_COKE', 0),
    ('ee5e9b7f-56b7-4f4a-a7df-561ff0cf720e', 'b5fa5dbd-1130-44a1-8db8-4e8d7155e7b6', 'SLAG', 0);

-- Landside WarehouseProjection
INSERT INTO landside.warehouse_projection (warehouse_id, seller_id, material_type, current_capacity, timestamp)
VALUES
    ('9f57ed44-4dcf-4a5a-8c7d-1b1a142763a1', 'dd2e2b7d-4622-441f-9200-123f8bb3e29b', 'GYPSUM', 0, NOW()),
    ('8361182d-6ae4-42a6-9dff-9146b3ad4234', 'dd2e2b7d-4622-441f-9200-123f8bb3e29b', 'IRON_ORE', 0, NOW()),
    ('0dfc49c1-8f1e-4b9e-b760-0b5a4a4b4550', 'dd2e2b7d-4622-441f-9200-123f8bb3e29b', 'CEMENT', 0, NOW()),
    ('e2f7bcdc-69da-4b0e-b73f-6adf79e8d5f9', 'dd2e2b7d-4622-441f-9200-123f8bb3e29b', 'PET_COKE', 0, NOW()),
    ('b3f15798-6c28-4a33-a8f5-507db494204b', 'dd2e2b7d-4622-441f-9200-123f8bb3e29b', 'SLAG', 0, NOW()),
    ('39dcbb4a-b535-4ab1-97b2-cfcba92f462a', 'b5fa5dbd-1130-44a1-8db8-4e8d7155e7b6', 'GYPSUM', 0, NOW()),
    ('bb182e36-4077-4987-b179-544dbd9a9cb6', 'b5fa5dbd-1130-44a1-8db8-4e8d7155e7b6', 'IRON_ORE', 0, NOW()),
    ('3f23dd36-cbd9-4025-b476-c9a24c3e6c19', 'b5fa5dbd-1130-44a1-8db8-4e8d7155e7b6', 'CEMENT', 0, NOW()),
    ('bf838cb6-6463-4b77-a70d-cb9f7b52af24', 'b5fa5dbd-1130-44a1-8db8-4e8d7155e7b6', 'PET_COKE', 0, NOW()),
    ('ee5e9b7f-56b7-4f4a-a7df-561ff0cf720e', 'b5fa5dbd-1130-44a1-8db8-4e8d7155e7b6', 'SLAG', 0, NOW());

-- Dummy data for Weighbridge
INSERT INTO landside.weighbridge (weighbridge_number, license_plate)
VALUES
    ('ABC-123-WB', 'ABC-123'),
    ('XYZ-789-WB', 'XYZ-789'),
    ('LMN-456-WB', 'LMN-456');