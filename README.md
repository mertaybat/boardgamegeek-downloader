# Boardgamegeek Converter

This is an opensource project which is based on data that is owned and published by boardgamegeek.com. All data that is 
processed by this project is owned by boardgamegeek.com.

The purpose of this project is to download all ranked game data that boardgamegeek keeps, to convert the original XML 
data to json format and to present this data via a small Angular single page application.

Boardgamegeek XML API v.1 is used to download the original XML data.

## Prerequisites

You need to have the following software installed
    
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

This will take about 4-5 hours to complete to download all games' XML data from boardgamegeek.
In order not to generate too much load on boardgamegeek, every XML API call is performed after 5 seconds of wait. This is
recommended and required by boardgamegeek.

The script will first create some necessary directories and create archives of your old runs.
Then it will first download the sitemap boardgame pages from boardgamegeek.com. It will 
then generate a list of game ids that correspond to those games.
It will then download, using the boardgamegeek XML api v.1, the xml data of these games. Finally it will 
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

