# API Collection de Films

Bienvenue sur l'API Collection de Films ! Cette API vous permet de gérer une collection de films, d'acteurs et d'auteurs. Vous pouvez effectuer diverses opérations, notamment la création, la mise à jour et la récupération de données.

## Pour Commencer

### Prérequis

- **Java** : Assurez-vous d'avoir Java 11 ou une version ultérieure installée sur votre système.
- **Maven** : Vous aurez besoin de Maven pour construire et exécuter le projet.
- **Base de données** : Le projet utilise une base de donées Mysql créé via le docker compose. Pour cela via votre terminal, placer vous dans le dossier contenant le fichier docker et ensuite taper cette commande
```shell
docker compose up
```

### Installation

1. Clonez le référentiel :

   ```shell
   git clone https://github.com/juva-quentin/API-rest-WebService
   ```
2. Accédez au répertoire du projet
```shell
cd MovieAPI
```
3. Contruisez le projet
```shell
docker compose up --build
```
4. Lancer le projet
L'API sera ensuite disponible à l'URL suivante : http://localhost:8080/swagger-ui/index.htm

