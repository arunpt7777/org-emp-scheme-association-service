USE employee_management;

 TRUNCATE TABLE insurance_scheme;

INSERT INTO insurance_scheme (id, name, valid_from_date, valid_to_date, scheme_amount, scheme_type) VALUES
(1,'Full Family Coverage','2024-01-08', '2034-01-08', 50000, 'ANNUAL'),
(2,'Happiness Unlimited','2024-01-08', '2034-01-08', 60000, 'MONTHLY'),
(3,'Freedom Package','2024-01-08', '2034-01-08', 70000, 'QUARTERLY')
;