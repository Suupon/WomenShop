📘 WomenShop – README
🛒 Présentation générale
WomenShop est une application JavaFX de gestion de stock pour une boutique fictive de vêtements, chaussures et accessoires.
Elle utilise une base de données MySQL, respecte les patterns MVC et DAO, et propose une interface graphique claire et simple d’utilisation.
✨ Fonctionnalités
🔍 2.1 Affichage des produits
Tableau dynamique
Mise à jour automatique
Stock toujours synchronisé avec la base de données
🏷️ 2.2 Filtrage par catégorie
Clothes 👗
Shoes 👟
Accessories 🎒
Le tableau se met instantanément à jour.
💰 2.3 Tri par prix
Tri des produits du moins cher au plus cher
Tri spécifique à chaque catégorie
➕ 2.4 Ajout de produit
Formulaire complet
Vérification des champs
Enregistrement direct en base
✏️ 2.5 Modification de produit
Modification de toutes les propriétés
Mise à jour instantanée dans le tableau
🗑️ 2.6 Suppression
Suppression propre d’un produit
Confirmation avant suppression
Synchronisation immédiate
📊 2.7 Indicateurs financiers
Indicateur	Description
Stock Value	Valeur totale du stock
Incomes	Revenus générés par les ventes
Costs	Coûts cumulés des achats
Capital	Calculé selon (initial capital + incomes – costs)
🔄 2.8 Transactions
Vente :
Stock ↓
Income ↑
Achat :
Stock ↑
Costs ↑
Les valeurs sont recalculées automatiquement.
🏷️ 2.9 Discounts
Reductions automatiques par catégorie :
Clothes : -30%
Shoes : -20%
Accessories : -50%
Un clic → prix remisés
Un clic → prix normaux
🗄️ 2.10 Base de données structurée
Base composée de 3 tables :
clothes
shoes
accessories
Chaque table contient :
id, name, price, purchase_price, stock, (attributs spécifiques)
🧩 Architecture du projet
src/
 ├── controllers/
 ├── dao/
 ├── models/
 ├── views/
 ├── utils/
 └── Main.java
✔️ MVC
✔️ DAO
✔️ FXML
✔️ CSS thème clair/sombre
🗄️ Configuration de la base MySQL
Créer la base
CREATE DATABASE womenshop;
config/db.properties
db.url=jdbc:mysql://localhost:3306/womenshop
db.user=root
db.password=motdepasse
▶️ Lancement du projet
Depuis IntelliJ
Ouvrir le projet
Configurer le module JavaFX
Lancer Main.java
Depuis le terminal
java --module-path /path/to/javafx \
     --add-modules javafx.controls,javafx.fxml \
     -cp out/production/WomenShop Main
🎥 Script de démo (muet)
Ouvrir l’app
Filtrer par catégorie
Trier par prix
Ajouter un produit
Modifier un produit
Supprimer un produit
Afficher les indicateurs financiers
Faire une vente
Faire un achat
Activer discounts
Désactiver discounts
Panorama final → fermer l’app
⚠️ Difficultés rencontrées
Rafraîchissement du tableau après chaque action
Recalcul du capital, incomes, costs
Gestion des discounts catégorie par catégorie
Validation des formulaires
Structuration MVC + DAO propre
👤 Auteur
Saighi Aymen
CC3 – ESILV
