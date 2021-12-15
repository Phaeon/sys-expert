# Système Expert

#### Fait par Jack Hogg et Guillaume Grenon, Master 1 Informatique

## Présentation

Dans le cadre du module d'Intelligence Artificielle 1 en Master 1 Informatique à l'université d'Angers, il nous a été demandé de développer un système expert avec un moteur 0+.

Ce projet a donc été fait en Java avec notamment l'utilisation de Swing pour développer une interface utilisateur afin de rendre ce projet plus interactif.

Ce système expert traite donc un domaine prédéfini qui est la musique mais l'utilisateur peut saisir ses propres données, ça permet donc au projet d'être extensible et libre.


## Installation

Vous avez à votre disposition le fichier `sys-expert.jar` qui vous permettra de lancer le système expert.

Pour cela, il vous faut simplement exécuter la commande `java -jar sys-expert.jar`. Une interface d'accueil
s'ouvrira alors et vous aurez deux choix :

- Domaine prédéfini : Nous avons choisi la musique comme domaine, il vous faudra avoir dans le même répertoire que 
l'exécutable les fichiers `facts_musique.csv`, `rules_musique.csv` et `inc_musique.csv` ;
- Nouveau domaine : Vous pourrez saisir ou importer vos propres faits, règles et incohérences. Pour le domaine prédéfini,
il est aussi possible de modifier les valeurs du fichier par la suite.

## Processus

Lors du lancement, comme expliqué précédemment, vous aurez le choix entre un domaine prédéfini ou
saisir vos propres valeurs.
**Sur chaque fenêtre, une aide vous est proposée.**

###Saisie

Suite à cela, vous arriverez sur une interface de saisie avec les données affichées dans un panel. Sur
la gauche de l'interface, vous aurez la possibilité de choisir l'onglet à modifier (Faits, Règles, Incohérences).

Chaque interface vous permet de saisir ou d'importer vos valeurs et vous affiche des messages d'information en cas
de problème. Pour les fichiers, le format à respecter est décrit dans la partie `Annexes` de ce document.

Lors de la saisie manuelle, un parser vérifiera que vos données sont correctes et que celles-ci peuvent être ajoutées
dans la base correspondante.

Lorsque vous avez saisi l'ensemble de vos données, vous pourez passer à la fenêtre suivante qui réalisera les traitements
voulus (Chaînages).

### Moteur

Vous avez la possibilité d'inscrire et de retirer des faits au sein de la base de faits.

Sur le côté droit de la fenêtre, vous avez la possibilité de choisir le chaînage que vous souhaitez exécuter.
Si le chaînage utilise des méta-règles, alors le bouton devient actif.

Au centre, vous avez un espace d'affichage qui vous exposera l'ensemble des étapes réalisées
lors d'une opération. Celui-ci vous indiquera également les cas d'ambiguïtés et vous proposera de modifier
les méta-règles.

Vous avez aussi la possibilité de désactiver la trace détaillée, cela ne vous affichera que les informations
globales lors d'une exécution.

En cas d'erreurs, une bulle informative vous indiquera la marche à suivre.


## Annexes

### Format d'un fichier

Pour les variables, le format accepté est une lettre minuscule suivie de lettres et/ou de chiffres
(e.g. nomDeV4riable)

Pour les valeurs sont acceptés : Booléens (true, false), Nombres, Chaînes de caractères 
(ATTENTION: Entre guillemets simples).

Pour les règles sont acceptés comme opérateurs : +, -, *, /, <, <=, >, >=, ==, !=, ~

L'encodage UTF-8 est accepté pour les prémisses.

Attention : Chaque fichier doit être séparé

#### Faits et Incohérences

- Pour une variable : `v;nomDeLaVariable;Valeur`
- Pour une prémisse : `p;nomDeLaPrémisse;ValeurBooléenne`

#### Règles

- Format : `Expression Logique;Conclusion`

Chaque règle ne comporte qu'une conclusion, dans le cas où vous souhaiteriez en ajouter plusieurs,
il faudra reproduire la règle en plusieurs fois.

Exemple de ligne : `((age >= 18) & nat == 'FR');Majeur Français`