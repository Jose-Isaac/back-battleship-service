create table if not exists player (
      id          uuid            not null primary key,
      username    varchar(20)     not null unique,
      password    varchar(255)    not null
);

create table if not exists board (
     id              uuid            not null primary key,
     plays           text            not null,
     player          varchar(20)     not null,
     CONSTRAINT      fk_player_id
        FOREIGN KEY (player) REFERENCES player(username)
);

create table if not exists game_room(
    id                      uuid        not null primary key,
    board_player_one        uuid        not null,
    board_player_two        uuid        not null,
    player_turn             varchar(20) not null,
    turn                    int         not null default 1,
    winner                  varchar(20) default null,
    CONSTRAINT      fk_board_player_one
        FOREIGN KEY (board_player_one)  REFERENCES board(id),
    CONSTRAINT      fk_board_player_two
        FOREIGN KEY (board_player_two)  REFERENCES board(id)
);

create table players_game_room (
    game_room_id    uuid,
    player_id       uuid,
    PRIMARY KEY     (game_room_id, player_id),
    CONSTRAINT      fk_game_room
        FOREIGN KEY (game_room_id) REFERENCES game_room(id),
    CONSTRAINT      fk_player_id
        FOREIGN KEY (player_id) REFERENCES player(id)
);