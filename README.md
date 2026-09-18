# Barista Simulator — Java & SQL

Team project — Java, SQL.

## Overview
A barista simulation game in the style of Papa's Pizzeria, where the 
player prepares drinks for customers by navigating different stations. 
Poor performance (or bad luck) can trigger a "LAWSUIT" event, exiling the 
player to a twist mode with altered, duplicated, glitch-styled controls — 
inspired by Five Nights at Freddy's — where a single failure ends the game.

Game statistics and runtime values are stored and managed through a SQL 
database, allowing persistent tracking of state across gameplay.

## Contents
- 'DBManager.java' — class establishes a connection to game sql database.
  Connection is used by other classes to write to and query different tables.
- All other files were written to provide methods for interaction between game
  code and specific database tables, including querying from and writing to. 

## My Contribution
I wrote the code responsible for all interaction between our Java game 
logic and the SQL database. This included establishing the connection to 
the SQL server, querying existing tables, and writing functions to update 
table values based on gameplay events and runtime state.

## Skills Demonstrated
- Database connectivity and query execution from Java (JDBC)
- Designing functions to read/update persistent application state in SQL
- Working within a pre-existing database schema
- Collaborative game development in a team setting

## Tools
Java, SQL
