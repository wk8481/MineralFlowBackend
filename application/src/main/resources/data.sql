-- Seller 1's Warehouses
INSERT INTO warehouse.warehouses (warehouse_id, seller_id, material_type)
VALUES
    ('9f57ed44-4dcf-4a5a-8c7d-1b1a142763a1', 'dd2e2b7d-4622-441f-9200-123f8bb3e29b', 'GYPSUM'),
    ('8361182d-6ae4-42a6-9dff-9146b3ad4234', 'dd2e2b7d-4622-441f-9200-123f8bb3e29b', 'IRON_ORE'),
    ('0dfc49c1-8f1e-4b9e-b760-0b5a4a4b4550', 'dd2e2b7d-4622-441f-9200-123f8bb3e29b', 'CEMENT'),
    ('e2f7bcdc-69da-4b0e-b73f-6adf79e8d5f9', 'dd2e2b7d-4622-441f-9200-123f8bb3e29b', 'PET_COKE'),
    ('b3f15798-6c28-4a33-a8f5-507db494204b', 'dd2e2b7d-4622-441f-9200-123f8bb3e29b', 'SLAG');

-- Seller 2's Warehouses
INSERT INTO warehouse.warehouses (warehouse_id, seller_id, material_type)
VALUES
    ('39dcbb4a-b535-4ab1-97b2-cfcba92f462a', 'b5fa5dbd-1130-44a1-8db8-4e8d7155e7b6', 'GYPSUM'),
    ('bb182e36-4077-4987-b179-544dbd9a9cb6', 'b5fa5dbd-1130-44a1-8db8-4e8d7155e7b6', 'IRON_ORE'),
    ('3f23dd36-cbd9-4025-b476-c9a24c3e6c19', 'b5fa5dbd-1130-44a1-8db8-4e8d7155e7b6', 'CEMENT'),
    ('bf838cb6-6463-4b77-a70d-cb9f7b52af24', 'b5fa5dbd-1130-44a1-8db8-4e8d7155e7b6', 'PET_COKE'),
    ('ee5e9b7f-56b7-4f4a-a7df-561ff0cf720e', 'b5fa5dbd-1130-44a1-8db8-4e8d7155e7b6', 'SLAG');

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

INSERT into landside.trucks (license_plate, dock_number, material_type, seller_id)
VALUES
    ('ABC-123', 'WD-10','PET_COKE','dd2e2b7d-4622-441f-9200-123f8bb3e29b' ),
    ('XYZ-789', 'WD-2','GYPSUM','b5fa5dbd-1130-44a1-8db8-4e8d7155e7b6' ),
    ('LMN-456', 'WD-5', 'IRON_ORE','dd2e2b7d-4622-441f-9200-123f8bb3e29b' );


# -- Dummy data for Appointment 39 records
INSERT INTO landside.appointment (schedule_time, license_plate, material_type, window_start, window_end, seller_id)
VALUES
    ( '2024-10-30 10:00:00', 'ABC-001', 'GYPSUM', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '11111111-1111-1111-1111-111111111111'),
    ( '2024-10-30 10:00:00', 'ABC-002', 'IRON_ORE', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '11111111-1111-1111-1111-111111111111'),
    ('2024-10-30 10:00:00', 'ABC-003', 'CEMENT', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '11111111-1111-1111-1111-111111111111'),
    ( '2024-10-30 10:00:00', 'ABC-004', 'PET_COKE', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '11111111-1111-1111-1111-111111111111'),
    ( '2024-10-30 10:00:00', 'ABC-005', 'SLAG', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '11111111-1111-1111-1111-111111111111'),
    ('2024-10-30 10:00:00', 'ABC-006', 'GYPSUM', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '22222222-2222-2222-2222-222222222222'),
    ( '2024-10-30 10:00:00', 'ABC-007', 'IRON_ORE', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '22222222-2222-2222-2222-222222222222'),
    ( '2024-10-30 10:00:00', 'ABC-008', 'CEMENT', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '22222222-2222-2222-2222-222222222222'),
    ( '2024-10-30 10:00:00', 'ABC-009', 'PET_COKE', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '22222222-2222-2222-2222-222222222222'),
    ( '2024-10-30 10:00:00', 'ABC-010', 'SLAG', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '22222222-2222-2222-2222-222222222222'),
    ( '2024-10-30 10:00:00', 'ABC-011', 'GYPSUM', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '33333333-3333-3333-3333-333333333333'),
    ( '2024-10-30 10:00:00', 'ABC-012', 'IRON_ORE', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '33333333-3333-3333-3333-333333333333'),
    ( '2024-10-30 10:00:00', 'ABC-013', 'CEMENT', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '33333333-3333-3333-3333-333333333333'),
    ( '2024-10-30 10:00:00', 'ABC-014', 'PET_COKE', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '33333333-3333-3333-3333-333333333333'),
    ( '2024-10-30 10:00:00', 'ABC-015', 'SLAG', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '33333333-3333-3333-3333-333333333333'),
    ( '2024-10-30 10:00:00', 'ABC-016', 'GYPSUM', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '44444444-4444-4444-4444-444444444444'),
    ( '2024-10-30 10:00:00', 'ABC-017', 'IRON_ORE', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '44444444-4444-4444-4444-444444444444'),
    ( '2024-10-30 10:00:00', 'ABC-018', 'CEMENT', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '44444444-4444-4444-4444-444444444444'),
    ( '2024-10-30 10:00:00', 'ABC-019', 'PET_COKE', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '44444444-4444-4444-4444-444444444444'),
    ( '2024-10-30 10:00:00', 'ABC-020', 'SLAG', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '44444444-4444-4444-4444-444444444444'),
    ( '2024-10-30 10:00:00', 'ABC-021', 'GYPSUM', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '55555555-5555-5555-5555-555555555555'),
    ( '2024-10-30 10:00:00', 'ABC-022', 'IRON_ORE', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '55555555-5555-5555-5555-555555555555'),
    ('2024-10-30 10:00:00', 'ABC-023', 'CEMENT', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '55555555-5555-5555-5555-555555555555'),
    ('2024-10-30 10:00:00', 'ABC-024', 'PET_COKE', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '55555555-5555-5555-5555-555555555555'),
    ( '2024-10-30 10:00:00', 'ABC-025', 'SLAG', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '55555555-5555-5555-5555-555555555555'),
    ( '2024-10-30 10:00:00', 'ABC-026', 'GYPSUM', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '66666666-6666-6666-6666-666666666666'),
    ('2024-10-30 10:00:00', 'ABC-027', 'IRON_ORE', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '66666666-6666-6666-6666-666666666666'),
    ( '2024-10-30 10:00:00', 'ABC-028', 'CEMENT', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '66666666-6666-6666-6666-666666666666'),
    ( '2024-10-30 10:00:00', 'ABC-029', 'PET_COKE', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '66666666-6666-6666-6666-666666666666'),
    ( '2024-10-30 10:00:00', 'ABC-030', 'SLAG', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '66666666-6666-6666-6666-666666666666'),
    ('2024-10-30 10:00:00', 'ABC-032', 'IRON_ORE', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '77777777-7777-7777-7777-777777777777'),
    ( '2024-10-30 10:00:00', 'ABC-033', 'CEMENT', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '77777777-7777-7777-7777-777777777777'),
    ( '2024-10-30 10:00:00', 'ABC-034', 'PET_COKE', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '77777777-7777-7777-7777-777777777777'),
    ('2024-10-30 10:00:00', 'ABC-035', 'SLAG', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '77777777-7777-7777-7777-777777777777'),
    ( '2024-10-30 10:00:00', 'ABC-036', 'GYPSUM', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '88888888-8888-8888-8888-888888888888'),
    ( '2024-10-30 10:00:00', 'ABC-037', 'IRON_ORE', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '88888888-8888-8888-8888-888888888888'),
    ( '2024-10-30 10:00:00', 'ABC-038', 'CEMENT', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '88888888-8888-8888-8888-888888888888'),
    ( '2024-10-30 10:00:00', 'ABC-039', 'PET_COKE', '2024-10-30 10:00:00', '2024-10-30 10:59:00', '88888888-8888-8888-8888-888888888888');