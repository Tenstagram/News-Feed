USE newsfeed;

CREATE TABLE members(
                        id BIGINT AUTO_INCREMENT PRIMARY KEY ,
                        name VARCHAR(255) NOT NULL ,
                        email VARCHAR(255) NOT NULL ,
                        image VARCHAR(255) NOT NULL ,
                        password VARCHAR(255) NOT NULL ,
                        created_at TIMESTAMP NOT NULL ,
                        modified_at TIMESTAMP NOT NULL
);