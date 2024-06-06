CREATE SCHEMA lift;
CREATE TABLE lift.rus_names (
  id          INTEGER auto_increment PRIMARY KEY,
  name VARCHAR(64) NOT NULL,
  is_female   BIT NOT NULL);
