-- participants
insert into Participant (firstName, lastName, screenName, emailAddress, city, state)
values ('jimmy', 'crazyeyes', 'alligator', 'jimmy.crazyeyes@email.com', 'Monona', 'WI');
insert into Participant (firstName, lastName, screenName, emailAddress, city, state)
values ('cassie', 'snakeyes', 'gecko', 'casssie.snakeyes@email.com', 'Chicago', 'IL');

-- questions
insert into Question (question, answer, maxPoints, createdByRef)
values('1 + 1', '2', 1000, (select id from Participant where screenName = 'alligator'));
insert into Question (question, answer, maxPoints, createdByRef)
values('1 + 2', '3', 1000, (select id from Participant where screenName = 'alligator'));
insert into Question (question, answer, maxPoints, createdByRef)
values('1 + 3', '4', 1000, (select id from Participant where screenName = 'alligator'));
insert into Question (question, answer, maxPoints, createdByRef)
values('1 + 4', '5', 1000, (select id from Participant where screenName = 'alligator'));

-- choices - question 1
insert into Choice (ordinal, choiceValue, explanation, questionRef)
values(1, '2', 'it has to be two', (select id from Question where question = '1 + 1'));
insert into Choice (ordinal, choiceValue, explanation, questionRef)
values(2, '3', 'you went overboard', (select id from Question where question = '1 + 1'));
insert into Choice (ordinal, choiceValue, explanation, questionRef)
values(3, '1', 'you have to shoot higher', (select id from Question where question = '1 + 1'));
insert into Choice (ordinal, choiceValue, explanation, questionRef)
values(4, '5', 'you are beyond help', (select id from Question where question = '1 + 1'));

-- clues - question 1
insert into Clue (ordinal, clueValue, explanation, questionRef)
values(1, '2', 'it has to be two', (select id from Question where question = '1 + 1'));
insert into Clue (ordinal, clueValue, explanation, questionRef)
values(2, '3', 'you went overboard', (select id from Question where question = '1 + 1'));
insert into Clue (ordinal, clueValue, explanation, questionRef)
values(3, '1', 'you have to shoot higher', (select id from Question where question = '1 + 1'));
insert into Clue (ordinal, clueValue, explanation, questionRef)
values(4, '5', 'you are beyond help', (select id from Question where question = '1 + 1'));
