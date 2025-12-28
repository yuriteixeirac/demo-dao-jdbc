CREATE TABLE IF NOT EXISTS department (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS seller (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    birthdate DATE NOT NULL,
    salary DECIMAL(7, 2) NOT NULL,
    department_id INT,
    FOREIGN KEY (department_id) REFERENCES department(id) ON DELETE SET NULL
);

INSERT INTO department (name) VALUES
      ('Computers'),
      ('Electronics'),
      ('Fashion'),
      ('Books'),
      ('Home & Garden'),
      ('Sports'),
      ('Automotive'),
      ('Health & Beauty');

INSERT INTO seller (name, email, birthdate, salary, department_id) VALUES
   ('John Smith', 'john.smith@example.com', '1985-03-15', 6500.00, 1),
   ('Emma Johnson', 'emma.johnson@example.com', '1990-07-22', 7200.00, 1),
   ('Michael Brown', 'michael.brown@example.com', '1982-11-30', 8000.00, 1),
   ('Sarah Davis', 'sarah.davis@example.com', '1993-05-10', 5800.00, 1),
   ('Robert Wilson', 'robert.wilson@example.com', '1978-09-14', 9500.00, 1),
   ('Jennifer Miller', 'jennifer.miller@example.com', '1987-12-05', 6900.00, 1),
   ('William Taylor', 'william.taylor@example.com', '1991-02-28', 6300.00, 1),
   ('Lisa Anderson', 'lisa.anderson@example.com', '1984-08-17', 7700.00, 1),
   ('David Thomas', 'david.thomas@example.com', '1975-06-21', 8500.00, 1),
   ('Maria Jackson', 'maria.jackson@example.com', '1995-04-03', 6000.00, 1),
   ('James White', 'james.white@example.com', '1988-01-12', 7000.00, 2),
   ('Patricia Harris', 'patricia.harris@example.com', '1992-03-25', 6800.00, 2),
   ('Charles Martin', 'charles.martin@example.com', '1980-07-08', 8200.00, 2),
   ('Susan Thompson', 'susan.thompson@example.com', '1986-11-19', 7300.00, 2),
   ('Jessica Garcia', 'jessica.garcia@example.com', '1994-09-02', 5500.00, 3),
   ('Thomas Martinez', 'thomas.martinez@example.com', '1983-04-15', 6200.00, 3),
   ('Karen Robinson', 'karen.robinson@example.com', '1979-12-30', 7800.00, 3),
   ('Christopher Clark', 'christopher.clark@example.com', '1989-06-22', 6700.00, 3),
   ('Nancy Rodriguez', 'nancy.rodriguez@example.com', '1991-08-11', 5900.00, 4),
   ('Daniel Lewis', 'daniel.lewis@example.com', '1981-10-05', 7100.00, 4),
   ('Betty Lee', 'betty.lee@example.com', '1976-05-28', 8300.00, 4),
   ('Paul Walker', 'paul.walker@example.com', '1993-02-14', 6100.00, 4),
   ('Mark Hall', 'mark.hall@example.com', '1987-03-16', 6400.00, 5),
   ('Sandra Allen', 'sandra.allen@example.com', '1990-09-09', 6900.00, 5),
   ('George Young', 'george.young@example.com', '1984-12-22', 7500.00, 5);