# Concert Ticketing



## Présentation



Ce projet est une application de billetterie de concerts composée de deux parties :



\- `back/` : backend Java REST API avec JAX-RS, JPA/Hibernate et HSQLDB

\- `front/` : frontend Angular



L'application permet de gérer des concerts, des utilisateurs, des commandes, des tickets et des notifications.



\---



\## Structure du projet



```text

concert-ticketing/

├── back/

│   ├── src/

│   ├── run-hsqldb-server.bat

│   ├── run-hsqldb-server.sh

│   └── README.md

│

├── front/

│   ├── src/

│   ├── angular.json

│   └── package.json

│   └── README.md

│

└── README.md
```

## Lancer le backend

cd ./back

window: 
```text
run-hsqldb-server.bat
```

Linux / macOS :
```text
./run-hsqldb-server.sh
```

Lancer la classe suivante depuis l'IDE :
```text
src/main/java/fr/istic/taa/jaxrs/RestServer.java
```

Une fois le backend lancé, la documentation OpenAPI / Swagger est disponible à l'adresse : http://localhost:8080/api

## Lancer le frontend

```text
cd ./front
```
```text
npm install
```
```text
ng serve
```

Le frontend est disponible sur :
http://localhost:4200

## 3 comptes disponible depuis Backend -> DataInitializer:

### Admin

admin@yopmail.com

passw0rd

### Organisateur

organisateur@yopmail.com

passw0rd

### Client

client@yopmail.com

passw0rd