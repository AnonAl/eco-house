\c eco-house;

------------------------------------ fill users table -----------------------------------

INSERT INTO users (username, password, enabled)
VALUES ('user', 'user', true),
        ('testuser', 'testuser', true),
        ('testuser2', 'testuser2', true),
        ('manager', 'manager', true),
        ('admin', 'admin', true);

------------------------------------ fill user_roles table -----------------------------------

INSERT INTO user_roles (username, role)
VALUES ('user', 'ROLE_USER'),
        ('testuser', 'ROLE_USER'),
        ('testuser2', 'ROLE_USER'),
        ('manager', 'ROLE_USER'),
        ('manager', 'ROLE_MANAGER'),
        ('admin', 'ROLE_USER'),
        ('admin', 'ROLE_MANAGER'),
        ('admin', 'ROLE_ADMIN');

----------------------------------- fill categoriestable ---------------------------------

INSERT INTO categories (name)
VALUES ('Finished houses'),
        ('Equipment'),
        ('Construction Materials');

----------------------------------- fill products table ---------------------------------

INSERT INTO products (name, category, price, description)
VALUES ('Eco-house', 'Finished houses', 10000, 'Good house'),
        ('Eco wall', 'Construction Materials', 1500, 'Good wall.'),
        ('Sun batery', 'Equipment', 2000, 'batery.');

----------------------------------- fill auctions table ---------------------------------

INSERT INTO auctions (owner, product_id, create_time, end_time, description, finished)
VALUES ('user', 1, '2019-02-09 10:10:10', '2019-05-10 10:10:10', 'Selling bicycle.', false),
        ('user', 2, '2019-02-09 12:12:12', '2019-05-10 12:12:12', 'Selling cakes.', false),
        ('testuser', 3, '2019-02-09 12:12:12', '2019-05-10 12:12:12', 'Selling cakes.', false);

----------------------------------- fill bets table ---------------------------------

INSERT INTO bets (auction_id, username, bet_time, price)
VALUES (1, 'testuser', '2019-02-09 10:11:10', 35.20),
        (1, 'testuser2', '2019-02-09 10:12:10', 37.30),
        (1, 'testuser', '2019-02-09 10:14:10', 39.40),
        (2, 'testuser2', '2019-02-09 12:12:20', 1010),
        (2, 'testuser', '2019-02-09 12:14:20', 1040),
        (3, 'testuser2', '2019-02-09 12:13:20', 730.40),
        (3, 'user', '2019-02-09 12:15:20', 755.75),
        (3, 'testuser2', '2019-02-09 12:17:45', 770.65);
