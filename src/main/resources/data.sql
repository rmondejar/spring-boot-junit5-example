insert ignore into author (id, first_name, last_name, email) values
  (1, 'Philip K', 'Dick', 'editor@nexus.corp'),
  (2, 'Stephen', 'King', 'stking@darktower.it'),
  (3, 'John', 'Doe', 'john.doe@acme.io');

insert ignore into book (id, title, description, genre, price, author_id) values
  (1, 'The Man in the High Castle', '', 'Sci-Fi', 14.95, 1),
  (2, 'Do Androids Dream of Electric Sheep?', '', 'Sci-Fi', 9.95, 1),
  (3, 'A Scanner Darkly', '', 'Sci-Fi', 12.45, 1),
  (4, 'VALIS', '', 'Sci-Fi', 7.65, 1),
  (5, 'Carrie', '', 'Terror', 8.95, 2),
  (6, 'Salems Lot', '', 'Terror', 10.95, 2),
  (7, 'The Shining', '', 'Terror', 13.45, 2),
  (8, 'Misery', '', 'Terror', 9.54, 2);