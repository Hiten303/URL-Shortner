#!/bin/bash
echo "starting backend"
cd url-shortner-backend || exit
./mvn spring-boot:run &

echo "starting frontend"
cd url-shortner-frontend || exit
npm run dev &
wait