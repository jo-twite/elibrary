-- Insert books
INSERT INTO BOOK (title, isbn, author, created_at, updated_at)
VALUES
    ('Book 1', '978-1234567891', 'Author A', NOW(), NOW()),
    ('Book 2', '978-1234567892', 'Author B', NOW(), NOW()),
    ('Book 3', '978-1234567893', 'Author C', NOW(), NOW()),
    ('Book 4', '978-1234567894', 'Author D', NOW(), NOW()),
    ('Book 5', '978-1234567895', 'Author E', NOW(), NOW()),
    ('Book 6', '978-1234567896', 'Author F', NOW(), NOW()),
    ('Book 7', '978-1234567897', 'Author G', NOW(), NOW()),
    ('Book 8', '978-1234567898', 'Author H', NOW(), NOW()),
    ('Book 9', '978-1234567899', 'Author I', NOW(), NOW()),
    ('Book 10', '978-1234567900', 'Author J', NOW(), NOW()),
    ('Book 11', '978-1234567901', 'Author K', NOW(), NOW()),
    ('Book 12', '978-1234567902', 'Author L', NOW(), NOW()),
    ('Book 13', '978-1234567903', 'Author M', NOW(), NOW()),
    ('Book 14', '978-1234567904', 'Author N', NOW(), NOW()),
    ('Book 15', '978-1234567905', 'Author O', NOW(), NOW());

-- Insert collections
INSERT INTO COLLECTION (name, created_at, updated_at)
VALUES
    ('Collection 1', NOW(), NOW()),
    ('Collection 2', NOW(), NOW()),
    ('Collection 3', NOW(), NOW()),
    ('Collection 4', NOW(), NOW()),
    ('Collection 5', NOW(), NOW());
