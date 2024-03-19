-- players
insert into Player (firstName, lastName, screenName, emailAddress, city, state)
values ('jimmy', 'crazyeyes', 'alligator', 'jimmy.crazyeyes@email.com', 'Nashville', 'TN');
insert into Player (firstName, lastName, screenName, emailAddress, city, state)
values ('cassie', 'bigfoot', 'gecko', 'casssie.bigfoot@email.com', 'Raleigh', 'NC');
insert into Player (firstName, lastName, screenName, emailAddress, city, state)
values ('debbie', 'coolcat', 'tiger', 'debbie.coolcat@email.com', 'Plano', 'TX');

-- game
insert into Game (title, organizerRef)
values ('eye of a tiger', (select id from Player where screenName = 'alligator'));
insert into Game (title, organizerRef)
values ('ribeye steak', (select id from Player where screenName = 'alligator'));

-- questions
insert into Question (question, answer, maxPoints, createdByRef)
values ('1 + 1', '2', 1000, (select id from Player where screenName = 'alligator'));
insert into Question (question, answer, maxPoints, createdByRef)
values ('1 + 2', '3', 1000, (select id from Player where screenName = 'alligator'));
insert into Question (question, answer, maxPoints, createdByRef)
values ('1 + 3', '4', 1000, (select id from Player where screenName = 'alligator'));
insert into Question (question, answer, maxPoints, createdByRef)
values ('1 + 4', '5', 1000, (select id from Player where screenName = 'alligator'));

-- choices - question 1
insert into Choice (ordinal, choiceValue, explanation, questionRef)
values (1, '2', 'it has to be two', (select id from Question where question = '1 + 1'));
insert into Choice (ordinal, choiceValue, explanation, questionRef)
values (2, '3', 'you went overboard', (select id from Question where question = '1 + 1'));
insert into Choice (ordinal, choiceValue, explanation, questionRef)
values (3, '1', 'you have to shoot higher', (select id from Question where question = '1 + 1'));
insert into Choice (ordinal, choiceValue, explanation, questionRef)
values (4, '5', 'you are beyond help', (select id from Question where question = '1 + 1'));

-- clues - question 1
insert into Clue (ordinal, clueValue, explanation, questionRef)
values (1, '2', 'it has to be two', (select id from Question where question = '1 + 1'));
insert into Clue (ordinal, clueValue, explanation, questionRef)
values (2, '3', 'you went overboard', (select id from Question where question = '1 + 1'));
insert into Clue (ordinal, clueValue, explanation, questionRef)
values (3, '1', 'you have to shoot higher', (select id from Question where question = '1 + 1'));
insert into Clue (ordinal, clueValue, explanation, questionRef)
values (4, '5', 'you are beyond help', (select id from Question where question = '1 + 1'));
