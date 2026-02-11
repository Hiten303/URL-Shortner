#!/bin/bash
echo "starting backend"
cd url-shortner-backend || exit
./mvnw spring-boot:run &

echo "starting frontend"
cd ../url-shortner-frontend || exit
npm run dev &
wait