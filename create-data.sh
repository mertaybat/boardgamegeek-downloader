#! /bin/bash

today=`date +%d-%m-%y`
uuid=`uuidgen`
html_archive_tar=`echo htmls-$today-$uuid.tar`
xml_archive_tar=`echo xmls-$today-$uuid.tar`
source ./credentials.properties

# check if necessary directories are there, if not create them
if [ ! -d ./html-pages ]; then
	mkdir html-pages
fi

if [ ! -d ./xmls ]; then
	mkdir xmls
fi

if [ ! -d ./archives ]; then
	mkdir archives
fi

# empty directories if they contain files, create archive for them
cd ./html-pages
html_count=`ls -1 *.html 2> /dev/null | wc -l`
if [ $html_count != 0 ]; then
	tar -cf $html_archive_tar ./*.html
	gzip $html_archive_tar
	mv ./$html_archive_tar.gz ../archives
	rm -rf ./*.html
	rm -rf ./ids.txt
fi
cd ../

cd ./xmls
xml_count=`ls -1 *.xml 2> /dev/null | wc -l`
if [ $xml_count != 0 ]; then
	tar -cf $xml_archive_tar ./*.xml ./*.json
	gzip $xml_archive_tar
	mv ./$xml_archive_tar.gz ../archives
	rm -rf ./*.xml ./*.json
fi
cd ../

# build converter and copy app.jar to xmls directory
cd ./converter
./gradlew clean build
./gradlew jar
cp ./app/build/libs/app.jar ../xmls
cd ../

# download all html data create ids
cd html-pages
for i in {1..249}; do curl https://boardgamegeek.com/browse/boardgame/page/$i -o $i.html --compressed -H "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8" -H "Accept-Language: en-US,en;q=0.5" -H "Accept-Encoding: gzip, deflate, br" -H "DNT: 1" -H "Alt-Used: boardgamegeek.com" -H "Connection: keep-alive" -H "Cookie: bggusername=$username; bggpassword=$password" -H "Upgrade-Insecure-Requests: 1" -H "Sec-Fetch-Dest: document" -H "Sec-Fetch-Mode: navigate" -H "Sec-Fetch-Site: none" -H "Sec-Fetch-User: ?1" -H "TE: trailers"; done
ag -o "href=\"/boardgame/\d{1,10}/.*?\"\s>" | awk -F "/" '{print $3}' > ids.txt
cd ../


# download all xmls
cd xmls
for i in {1..249}
do
        let start="1+($i-1)*100"
        let end="$i*100"
        v=$(for j in `cat ../html-pages/ids.txt | awk -v a=$start -v b=$end 'FNR >=a && FNR <=b'`; do echo -n $j,; done)
        wget https://boardgamegeek.com/xmlapi/boardgame/$v?stats=1 -O ./$i.xml
        sleep 5
done

# run conversion app on xmls
sed -i 's/value/valueAttribute/g' ./*.xml
java -jar app.jar
sed -i 's/valueAttribute/value/g' ./*.xml
sed -i 's/valueAttribute/value/g' ./*.json
cp boardgames-custom.json ../games-list/src/assets/
cd ../

unset username
unset password

