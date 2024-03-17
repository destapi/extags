drop table GameInProgress if exists;
drop table GameParticipant if exists;
drop table GameScore if exists;
drop table Game if exists;
drop table Choice if exists;
drop table Clue if exists;
drop table Question if exists;
drop table Team if exists;
drop table Participant if exists;

create table if not exists Participant
(
    id           bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    firstName    varchar(64),
    lastName     varchar(64),
    screenName   varchar(64)        not null,
    emailAddress varchar(64) unique not null,
    city         varchar(64)        not null,
    state        varchar(64)        not null,
    dateCreated  timestamp default now()
);

create table if not exists Team
(
    id          bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name        varchar(64) not null,
    city        varchar(64),
    state       varchar(32),
    captainRef  bigint      not null references Participant (id),
    dateCreated timestamp default now()
);

create table if not exists Question
(
    id           bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    question     varchar(256)                                                                                                                       not null,
    questionType ENUM ('GENERAL', 'SCIENCE', 'MATH', 'ENGLISH', 'GEOGRAPHY', 'POLITICS', 'HISTORY', 'RELIGION', 'MUSIC', 'MOVIES', 'TV', 'ANIMALS') not null default 'GENERAL',
    answer       varchar(256)                                                                                                                       not null,
    answerReason varchar(1024),
    maxPoints    int                                                                                                                                         default 0,
    createdByRef bigint                                                                                                                             not null references Participant (id),
    dateCreated  timestamp                                                                                                                                   default now()
);

create table if not exists Choice
(
    ordinal     int         not null,
    questionRef bigint      not null references Question (id),
    choiceValue varchar(64) not null,
    explanation varchar(256),
    constraint pkChoice primary key (ordinal, questionRef)
);

create table if not exists Clue
(
    ordinal     int         not null,
    questionRef bigint      not null references Question (id),
    clueValue   varchar(64) not null,
    explanation varchar(256),
    constraint pkClue primary key (ordinal, questionRef)
);

create table if not exists Game
(
    id           bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title        varchar(64)                              not null,
    description  varchar(256),
    gameStatus   ENUM ('CREATED', 'STARTED', 'COMPLETED') not null default 'CREATED',
    organizerRef bigint                                   not null references Participant (id),
    startTime    timestamp,
    dateCreated  timestamp                                         default now()
);

create table if not exists GameScore
(
    gameRef      bigint       not null references Game (id) on delete cascade,
    playerRef    bigint       not null references Participant (id) on delete cascade,
    questionRef  bigint       not null references Question (id) on delete cascade,
    response     varchar(256) not null,
    pointsEarned int       default 0,
    timePosted   timestamp default now(),
    constraint pkScore primary key (gameRef, playerRef, questionRef)
);

create table if not exists GameParticipant
(
    gameRef    bigint not null references Game (id) on delete cascade,
    playerRef  bigint not null references Participant (id) on delete cascade,
    timeJoined timestamp default now(),
    constraint pkGameParticipant primary key (gameRef, playerRef)
);

create table if not exists GameInProgress
(
    groupNum             int                                                 not null,
    questionNum          int                                                 not null,
    gameRef              bigint                                              not null references Game (id),
    questionRef          bigint                                              not null references Question (id),
    autoProgression      boolean                                                      default false,
    delayBeforeCountdown long                                                         default 0,
    delayAfterCountdown  long                                                         default 0,
    countdownDuration    long                                                         default 0,
    countdownIntervals   long                                                         default 0,
    progressStatus       ENUM ('READY', 'COMING_UP', 'SHOWING', 'COMPLETED') not null default 'READY',
    timeStarted          timestamp                                                    default now(),
    timeEnded            timestamp,
    constraint pkCurrent primary key (gameRef, questionRef)
);
