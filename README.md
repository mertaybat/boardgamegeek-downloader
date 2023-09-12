# Boardgamegeek Converter

This is an opensource project which is based on data that is owned and published by boardgamegeek.com. All data that is 
processed by this project is owned by boardgamegeek.com.

The purpose of this project is to download all ranked game data that boardgamegeek keeps, to convert the original XML 
data to json format and to present this data via a small Angular single page application.

Boardgamegeek XML API v.1 is used to download the original XML data.

## Prerequisites

- Create a user account at https://boardgamegeek.com
- Login at boardgamegeek
- Open your browser developer tools to view the network stack
- While your network stack is open browse all boardgames and look at request headers of the GET request
- Note the Cookie valules for bggusername and bggpassword. You will use these values for the following step.
- At the root of this project create a file called credentials.properties. This file should contain the following lines
```
username=bggusername_cookie_value
password=bggpassword_cookie_value
``` 
This information is necessary in order to download all the html pages for all the ranked games from boardgamegeek. Note: bggpassword value
will be different from the password you created while creating your boardgamegeek account!

- You need to have the following software installed
    
    - curl
    - wget
    - silversearcher
    - Java JDK v 11+
    - awk
    - sed
    - tar
    - gzip

## How It Works

You need to run 

```
./create-data.sh
```

This will take about 1 hour to complete to download 24900 ranked games from boardgamegeek.
In order not to generate too much load on boardgamegeek, every XML API call is performed after 5seconds of wait. This is
recommended and required by boardgamegeek.

The script will first create some necessary directories and create archives of your old runs.
Then it will first download the first 249 html pages of ranked games from boardgamegeek.com. It will 
then generate a list of game ids that correspond to those 24900 games (every page contains 100 games).
It will then download using the boardgamegeek XML api v.1 to download the xml data of these games. Finally it will 
run a small software program contained in the converter directory to convert all the xml data to json. It will generate
2 json files; one maps 1-1 to the xml data and another which is custom format used by the frontend application.

## Frontend Application

games-list directory contains an Angular frontend application which presents all the games.
Running the script will copy for you the json data to the assets of the Angular application. In order to run 
the frontend application perform

```
cd games-list
npm install
ng serve
```

You will have to wait a few moments until the single page application
renders the material table because of the large number of data it renders. 
Optimizing the single page application is not within the scope of this project.

