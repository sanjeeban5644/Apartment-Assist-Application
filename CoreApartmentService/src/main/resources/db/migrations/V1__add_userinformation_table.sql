CREATE TABLE IF NOT EXISTS apt_core.t_user_information (
    user_id BIGINT DEFAULT generate_user_id() PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    dob VARCHAR(50),
    aadharno VARCHAR(20) UNIQUE,
    panno VARCHAR(20) UNIQUE,
    mobile VARCHAR(15) UNIQUE,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(255),
    flag VARCHAR(5),
    uniqueid VARCHAR(50),
    CONSTRAINT uq_email UNIQUE (email),
    CONSTRAINT uq_aadharno UNIQUE (aadharno),
    CONSTRAINT uq_panno UNIQUE (panno),
    CONSTRAINT uq_mobile UNIQUE (mobile),
    CONSTRAINT uq_username UNIQUE (username)
);