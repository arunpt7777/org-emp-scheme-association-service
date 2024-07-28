USE employee_management;

 TRUNCATE TABLE employee_scheme_association;

INSERT INTO employee_scheme_association (id, employee_id, scheme_id) VALUES
(1,10001, 1),
(2,10002, 1),
(3,10003, 2),
(4,10004, 2),
(5,10005, 3),
(6,10006, 3)
;