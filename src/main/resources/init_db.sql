use wallet;
drop table if exists book;
create table book (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(30) NOT NULL,
    description VARCHAR(30) NOT NULL,
    author VARCHAR(30) NOT NULL
);