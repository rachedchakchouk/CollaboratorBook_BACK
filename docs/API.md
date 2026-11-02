# API REST du back-end CollaboratorBook

**Français** · [English](API.en.md)

Retour au [README](../README.md) · Voir aussi : [architecture](ARCHITECTURE.md) · [modèle de données](DATA-MODEL.md)

## Généralités

- **Adresses** : le front appelle chaque service directement : securityMs sur `http://localhost:8087`, botRH sur
  `http://localhost:8081`, businessMS sur `http://localhost:8082`. Tous les chemins ci-dessous sont relatifs à ces
  adresses.
- **Swagger UI** : `/swagger-ui.html` sur chacun de ces trois services (description OpenAPI sur `/v3/api-docs`).
- **Authentification** : aucune route n'exige de jeton ni de rôle (voir la section Sécurité du README). Le front envoie
  tout de même `Authorization: Bearer <jeton d'accès>` ; securityMs refuse un jeton invalide.
- **Format** : JSON. Les corps de requête sont des `XxxRequestDto` (voir [modèle de données](DATA-MODEL.md)) ; les
  réponses sont des `XxxResponseDto`. Les routes de liaison (`addTo...`, `...To...`) n'ont pas de corps.
- **Archivage** : `archive` met `archived` à `true`, `noarchive` le remet à `false`. Les listes `active/...` et
  `archived/...` filtrent sur ce champ.
- **Erreurs** : pas de format d'erreur commun ; un identifiant inconnu produit une réponse 500.
- Les chemins sont reproduits tels quels, fautes d'orthographe comprises (`comapny`, `upadte`, `byEmplyee`,
  `getemloyeebycompany`, `prouve`…).

## securityMs — `/api`

| Méthode | Chemin | Rôle de la route |
|---|---|---|
| POST | `/api/login` | Connexion. Paramètres de formulaire (ou de requête) `username` et `password`. Réponse : `access_Token`, `refresh_token`, `username`, `roles`. Traitée par `CustomAuthenticationFilter`. |
| GET | `/api/token/refresh` | Avec `Authorization: Bearer <refresh_token>` : renvoie `Access_token` (nouveau, 10 min) et `refresh_token`. Erreur 403 et message JSON si le jeton est invalide. |
| GET | `/api/users` | Liste des utilisateurs (entités `User`, mot de passe haché compris). |
| GET | `/api/user/{username}` | Utilisateur par identifiant ; corps vide s'il n'existe pas. |
| GET | `/api/employee/{username}` | Employé (botRH) lié à l'utilisateur, lu par OpenFeign. |
| POST | `/api/user/save` | Crée un utilisateur. Corps `User` : `username`, `password`, `role`, `idEmployee`. Mot de passe haché en BCrypt ; rôles attribués selon `role` (voir README, Sécurité). Si l'identifiant existe, renvoie l'utilisateur existant. |
| POST | `/api/role/save` | Crée un rôle. Corps `{"name": "ADMIN"}`. Réponse 201. |
| POST | `/api/role/addtouser` | Corps `RoleToUserForm` (`username`, `roleName`). Voir les points d'attention : en l'état, la méthode remplace la liste des rôles de l'utilisateur par `null`. |
| POST | `/api/user/addemloyeetouser` | Corps `EmployeeToUserForm` (identifiant utilisateur, identifiant employé) : lie un employé à un utilisateur. |
| DELETE | `/api/user/deletebyEid/{ide}` | Supprime l'utilisateur lié à l'employé `{ide}` (appelée par botRH). |

## botRH — `/RH`

### Employés — `/RH/employees`

| Méthode | Chemin | Rôle de la route |
|---|---|---|
| GET | `/RH/employees` | Tous les employés. |
| GET | `/RH/employees/getemployeebyId/{id}` | Employé par id, avec son poste (`job`) lu dans businessMS. |
| GET | `/RH/employees/getemployeesbyjob/{id}`<br>`/RH/employees/active/getemployeesbyjob/{id}`<br>`/RH/employees/archived/getemployeesbyjob/{id}` | Employés du poste `{id}` : tous, actifs, archivés. |
| GET | `/RH/employees/getemloyeebycompany/{cId}`<br>`/RH/employees/active/getemloyeebycompany/{cId}`<br>`/RH/employees/archived/getemloyeebycompany/{cId}` | Employés de l'entreprise `{cId}` : tous, actifs, archivés. |
| GET | `/RH/employees/getemloyeebyproject/{pId}`<br>`/RH/employees/active/getemloyeebyproject/{pId}`<br>`/RH/employees/archived/getemloyeebyproject/{pId}` | Employés affectés au projet `{pId}` : tous, actifs, archivés. |
| POST | `/RH/employees/newEmployee` | Crée un employé (corps `EmployeeRequestDto`) puis son compte securityMs : identifiant = `professionalMail`, mot de passe = `firstName`, rôle = `role`. Corps vide si un compte existe déjà pour cet e-mail. |
| PUT | `/RH/employees/update/{id}` | Corps `EmployeeRequestDto`. Renvoie l'employé construit à partir du corps, sans l'enregistrer (voir points d'attention). |
| PUT | `/RH/employees/leavescalculator/{id}` | Recalcule le solde de congés : 1,8 jour par mois écoulé depuis le début du contrat actif. |
| PUT | `/RH/employees/addJob/{eId}/{jId}` | Affecte le poste `{jId}` (businessMS) à l'employé `{eId}`. |
| PUT | `/RH/employees/addproject/{employeeId}/{projectId}` | Affecte l'employé au projet. |
| DELETE | `/RH/employees/archive/{id}`<br>`/RH/employees/noarchive/{id}` | Archive / désarchive l'employé. |
| DELETE | `/RH/employees/delete/{id}` | Supprime l'employé puis son compte securityMs. |

### Contrats — `/RH/contracts`

| Méthode | Chemin | Rôle de la route |
|---|---|---|
| GET | `/RH/contracts` | Tous les contrats. |
| GET | `/RH/contracts/{id}` | Contrat par id. |
| GET | `/RH/contracts/byEmployer/{id}`<br>`/RH/contracts/active/byEmployer/{id}`<br>`/RH/contracts/archived/byEmployer/{id}` | Contrats dont l'employé `{id}` est l'employeur (`employer`) : tous, actifs, archivés. |
| GET | `/RH/contracts/byEmplyee/{id}`<br>`/RH/contracts/active/byEmplyee/{id}`<br>`/RH/contracts/archived/byEmplyee/{id}` | Contrats de l'employé `{id}` : tous, actifs, archivés. |
| GET | `/RH/contracts/employeeFormContract/{id}` | Employé titulaire du contrat. |
| POST | `/RH/contracts/newcontract` | Crée un contrat (corps `ContractRequestDto`). CIVP et stage : salaire brut = salaire net ; CIVP : durée `ONE_YEAR` ; CDI : durée `UNLIMITED`. |
| PUT | `/RH/contracts/addtoEmployer/{idContract}/{idEmployer}` | Définit l'employeur du contrat. |
| PUT | `/RH/contracts/addtoEmployee/{idEmployee}/{idContract}` | Attribue le contrat à l'employé (employé en premier dans le chemin). |
| PUT | `/RH/contracts/update/{id}` | Remplace le contrat par le corps `ContractRequestDto`. |
| DELETE | `/RH/contracts/archive/{id}`<br>`/RH/contracts/noarchive/{id}` | Archive / désarchive. |
| DELETE | `/RH/contracts/delete/{id}` | Supprime. |

### Clauses — `/RH/clauses`

| Méthode | Chemin | Rôle de la route |
|---|---|---|
| GET | `/RH/clauses` | Toutes les clauses. |
| GET | `/RH/clauses/{id}` | Clause par id. |
| GET | `/RH/clauses/comapny/{id}`<br>`/RH/clauses/active/comapny/{id}`<br>`/RH/clauses/archived/comapny/{id}` | Clauses rédigées par les employés de l'entreprise `{id}` : toutes, actives, archivées. |
| GET | `/RH/clauses/contract/{id}` | Clauses du contrat. |
| GET | `/RH/clauses/active/contract/{id}`<br>`/RH/clauses/archived/contract/{id}` | Non implémentées : réponse vide. |
| GET | `/RH/clauses/writer/{id}`<br>`/RH/clauses/active/writer/{id}`<br>`/RH/clauses/archived/writer/{id}` | Clauses rédigées par l'employé `{id}` : toutes, actives, archivées. |
| POST | `/RH/clauses/addclause` | Crée une clause (corps `ClauseRequestDto`), datée du jour. |
| PUT | `/RH/clauses/update/{id}` | Remplace la clause par le corps `ClauseRequestDto`. |
| PUT | `/RH/clauses/addtocontract/{idClause}/{idContract}` | Ajoute la clause au contrat. |
| PUT | `/RH/clauses/removefromcontract/{idClause}/{idContract}` | Retire la clause du contrat. |
| PUT | `/RH/clauses/addtowriter/{idClause}/{idWriter}` | Définit le rédacteur de la clause. |
| DELETE | `/RH/clauses/archive/{id}`<br>`/RH/clauses/noarchive/{id}` | Archive / désarchive. |
| DELETE | `/RH/clauses/delete/{id}` | Supprime. |

### Congés — `/RH/holidays`

| Méthode | Chemin | Rôle de la route |
|---|---|---|
| GET | `/RH/holidays` | Toutes les demandes de congé. |
| GET | `/RH/holidays/byId/{id}` | Demande par id. |
| GET | `/RH/holidays/byemployee/{id}`<br>`/RH/holidays/active/byemployee/{id}`<br>`/RH/holidays/archived/byemployee/{id}` | Demandes de l'employé : toutes, actives, archivées. |
| POST | `/RH/holidays/newHoliday/{idEmployee}` | Crée une demande (corps `HoldayRequestDto` : `startDate`, `duration` parmi `HALF_DAY`, `ONE_DAY`, `TWO_DAYS`, `THREE_DAYS`). Recalcule le solde de l'employé ; corps vide si le solde est insuffisant, sinon statut `PENDING` ou `REFUSED` (voir [architecture](ARCHITECTURE.md#demandes-de-congé)). |
| PUT | `/RH/holidays/holidaytoemployee/{idHolday}/{idEmployee}` | Rattache la demande à un employé. |
| PUT | `/RH/holidays/upadte/{id}` | Remplace la demande par le corps `HoldayRequestDto`. |
| DELETE | `/RH/holidays/prouve/{id}` | Valide la demande (statut `PROVED`). |
| DELETE | `/RH/holidays/refuse/{id}` | Prévue pour refuser ; en l'état, attribue aussi le statut `PROVED` (voir points d'attention). |
| DELETE | `/RH/holidays/archive/{id}`<br>`/RH/holidays/noarchive/{id}` | Archive / désarchive. |
| DELETE | `/RH/holidays/delete/{id}` | Supprime. |

### Projets — `/RH/projects`

| Méthode | Chemin | Rôle de la route |
|---|---|---|
| GET | `/RH/projects` | Tous les projets. |
| GET | `/RH/projects/{id}` | Projet par id. |
| GET | `/RH/projects/byemployee/{id}`<br>`/RH/projects/active/byemployee/{id}`<br>`/RH/projects/archived/byemployee/{id}` | Projets de l'employé : tous, actifs, archivés. |
| GET | `/RH/projects/byCompany/{id}`<br>`/RH/projects/active/ByCompany/{id}`<br>`/RH/projects/archived/ByCompany/{id}` | Projets de l'entreprise (`idCompany`) : tous, actifs, archivés. |
| POST | `/RH/projects/newProject` | Crée un projet (corps `ProjectRequestDto`). |
| PUT | `/RH/projects/addtoemployee/{projectId}/{employeeId}` | Méthode vide : n'a aucun effet. Utilisez `PUT /RH/employees/addproject/{employeeId}/{projectId}`. |
| PUT | `/RH/projects/update/{id}` | Non implémentée : réponse vide. |
| DELETE | `/RH/projects/archive/{id}`<br>`/RH/projects/noarchive/{id}` | Archive / désarchive. |
| DELETE | `/RH/projects/delete/{id}` | Supprime. |

### Documents — `/RH/documents`

| Méthode | Chemin | Rôle de la route |
|---|---|---|
| GET | `/RH/documents` | Tous les documents, contenu (`data`, encodé en Base64 dans le JSON) compris. |
| GET | `/RH/documents/documentbyid/{id}` | Document par id. |
| GET | `/RH/documents/byemployee/{id}`<br>`/RH/documents/active/byemployee/{id}`<br>`/RH/documents/archived/byemployee/{id}` | Documents de l'employé : tous, actifs, archivés. |
| GET | `/RH/documents/activeproimg/byemployee/{id}`<br>`/RH/documents/archivedproimg/byemployee/{id}` | Photos de profil (`PROFIL_IMG`) actives / archivées de l'employé. |
| POST | `/RH/documents/newDocument` | Crée la fiche d'un document (corps `DocumentRequestDto`), sans fichier. |
| PUT | `/RH/documents/uploadDocument/{id}` | Téléverse le fichier de la fiche `{id}` : multipart, champ `file`, 200 Mo au plus. Renvoie la fiche avec `downloadUrl`. |
| GET | `/RH/documents/download/{id}` | Contenu du fichier, avec son type MIME. |
| PUT | `/RH/documents/addToEmployee/{documentId}/{employeeId}` | Rattache le document à un employé. |
| PUT | `/RH/documents/addTPost/{documentId}/{postId}` | Rattache le document à une publication et lui donne le type `POST_IMG`. |
| PUT | `/RH/documents/update/{id}` | Remplace la fiche par le corps `DocumentRequestDto`. |
| DELETE | `/RH/documents/archive/{id}`<br>`/RH/documents/noarchive/{id}` | Archive / désarchive. |
| DELETE | `/RH/documents/delete/{id}` | Supprime. |

### Publications — `/RH/post`

| Méthode | Chemin | Rôle de la route |
|---|---|---|
| GET | `/RH/post/{idc}` | Publications des employés de l'entreprise `{idc}`. |
| GET | `/RH/post/active/{idc}`<br>`/RH/post/archived/{idc}` | Publications actives / archivées de l'entreprise, de la plus récente à la plus ancienne. |
| GET | `/RH/post/active/e/{ide}` | Toutes les publications de l'employé `{ide}`. |
| GET | `/RH/post/archived/e/{ide}` | Publications **actives** de l'employé. |
| GET | `/RH/post/e/{ide}` | Publications **archivées** de l'employé. |
| GET | `/RH/post/employeebypost/{idp}` | Auteur de la publication. |
| POST | `/RH/post/newpost` | Crée une publication (corps `PostRequestDto`), datée du jour. |
| PUT | `/RH/post/addToEmployee/{idPost}/{idEmployee}` | Définit l'auteur. |
| PUT | `/RH/post/update/{id}` | Sans corps : réenregistre la publication telle quelle. |
| DELETE | `/RH/post/archivepost/{idp}`<br>`/RH/post/noarchivepost/{idp}` | Archive / désarchive ; renvoie la publication. |
| DELETE | `/RH/post/deletepost/{idp}` | Supprime. |

### Commentaires — `/RH/comment`

| Méthode | Chemin | Rôle de la route |
|---|---|---|
| GET | `/RH/comment` | Tous les commentaires. |
| GET | `/RH/comment/{id}` | Commentaire par id. |
| GET | `/RH/comment/post/{idp}`<br>`/RH/comment/active/post/{idp}`<br>`/RH/comment/archived/post/{idp}` | Commentaires de la publication : tous, actifs, archivés. |
| GET | `/RH/comment/getwriter/{id}` | Auteur du commentaire. |
| POST | `/RH/comment/newComment` | Crée un commentaire (corps `CommentRequestDto`). |
| PUT | `/RH/comment/update/{id}` | Remplace le commentaire par le corps `CommentRequestDto`. |
| PUT | `/RH/comment/addtoPost/{commentId}/{postId}` | Rattache le commentaire à une publication. |
| PUT | `/RH/comment/addtoemployee/{commentId}/{employeeId}` | Définit l'auteur. |
| DELETE | `/RH/comment/archive/{id}`<br>`/RH/comment/noarchive/{id}` | Archive / désarchive ; renvoie le commentaire. |
| DELETE | `/RH/comment/delete/{id}` | Supprime. |

### Notifications — `/RH/notifications`

| Méthode | Chemin | Rôle de la route |
|---|---|---|
| GET | `/RH/notifications` | Toutes les notifications. |
| GET | `/RH/notifications/getbyId/{id}` | Notification par id. |
| GET | `/RH/notifications/byemployee/{id}`<br>`/RH/notifications/active/byemployee/{id}`<br>`/RH/notifications/archived/byemployee/{id}` | Notifications destinées à l'employé : toutes, actives, archivées. |
| POST | `/RH/notifications/newNotification` | Crée une notification (corps `NotificationRequestDto` : `text`, `notificationType` parmi `ALERT`, `INFO`, `IMPORTANT`, `REMEMBER`). |
| PUT | `/RH/notifications/addtoemployee/{idNotification}/{idEmployee}` | Définit le destinataire. |
| DELETE | `/RH/notifications/archive/{id}`<br>`/RH/notifications/noarchive/{id}` | Archive / désarchive. |
| DELETE | `/RH/notifications/delete/{id}` | Supprime. |

### Réclamations — `/RH/Reclamations`

| Méthode | Chemin | Rôle de la route |
|---|---|---|
| GET | `/RH/Reclamations` | Toutes les réclamations. |
| GET | `/RH/Reclamations/getbyId/{id}` | Réclamation par id. |
| GET | `/RH/Reclamations/getbysource/{senderId}`<br>`/RH/Reclamations/active/getbysource/{senderId}`<br>`/RH/Reclamations/archived/getbysource/{senderId}` | Réclamations envoyées par l'employé : toutes, actives, archivées. |
| GET | `/RH/Reclamations/getbyDestination/{receiverId}`<br>`/RH/Reclamations/active/getbyDestination/{receiverId}`<br>`/RH/Reclamations/archived/getbyDestination/{receiverId}` | Réclamations reçues par l'employé : toutes, actives, archivées. |
| POST | `/RH/Reclamations/newReclamation` | Crée une réclamation (corps `ReclamationRequestDto`). |
| PUT | `/RH/Reclamations/update/{id}` | Remplace la réclamation par le corps `ReclamationRequestDto`. |
| PUT | `/RH/Reclamations/reclamationToSender/{reclamationId}/{senderId}` | Définit l'émetteur. |
| PUT | `/RH/Reclamations/reclamationToReceiver/{reclamationId}/{receiverId}` | Définit le destinataire. |
| DELETE | `/RH/Reclamations/archive/{id}`<br>`/RH/Reclamations/noarchive/{id}` | Archive / désarchive. |
| DELETE | `/RH/Reclamations/delete/{id}` | Supprime. |

## businessMS — `/business`

### Entreprises — `/business/Companies`

| Méthode | Chemin | Rôle de la route |
|---|---|---|
| GET | `/business/Companies` | Toutes les entreprises. |
| POST | `/business/Companies/newCompany` | Inscription d'une entreprise (corps `CompanyRequestDto`, coordonnées du gérant comprises) : crée l'entreprise, le bureau `header_office`, le département `Administration`, le poste `Manager` et le gérant (rôle `ADMIN`) dans botRH, puis enregistre son id dans `idManger`. Voir [architecture](ARCHITECTURE.md#inscription-dune-entreprise). |
| GET | `/business/Companies/getCompanyById/{id}` | Entreprise par id, avec la fiche du gérant lue dans botRH. |
| GET | `/business/Companies/getCompanyByEmployeeId/{id}` | Entreprise de l'employé `{id}`, retrouvée par son poste. |
| GET | `/business/Companies/totalofemployeesbyCompany/{id}` | Non implémentée : renvoie toujours `1`. |
| GET | `/business/Companies/totalofofficessbyCompany/{id}` | Non implémentée : renvoie toujours `0`. |
| GET | `/business/Companies/totalofDepartmentsbyCompany/{id}` | Nombre de départements de l'entreprise. |
| PUT | `/business/Companies/update/{id}` | Remplace l'entreprise par le corps `CompanyRequestDto`. |
| PUT | `/business/Companies/archive/{id}` | Archive l'entreprise et, en cascade, ses bureaux, départements et postes. |
| PUT | `/business/Companies/addManager/{idManager}/{idCompany}` | Définit le gérant (`idManger`). |
| DELETE | `/business/Companies/delete/{id}` | Supprime l'entreprise. |

### Bureaux — `/business/offices`

| Méthode | Chemin | Rôle de la route |
|---|---|---|
| GET | `/business/offices` | Tous les bureaux. |
| GET | `/business/offices/byid/{id}` | Bureau par id. |
| GET | `/business/offices/byCompany/{id}`<br>`/business/offices/active/byCompany/{id}`<br>`/business/offices/archived/byCompany/{id}` | Bureaux de l'entreprise : tous, actifs, archivés. |
| POST | `/business/offices/newOffice` | Crée un bureau (corps `OfficeRequestDto`). |
| PUT | `/business/offices/update/{id}` | Remplace le bureau par le corps `OfficeRequestDto`. |
| PUT | `/business/offices/addToCompany/{idOffice}/{idCompany}` | Rattache le bureau à une entreprise. |
| PUT | `/business/offices/archive/{id}` | Archive. |
| DELETE | `/business/offices/delete/{id}` | Supprime. |

### Départements — `/business/Departments`

| Méthode | Chemin | Rôle de la route |
|---|---|---|
| GET | `/business/Departments` | Tous les départements. |
| GET | `/business/Departments/{id}` | Département par id. |
| GET | `/business/Departments/byOffice/{id}`<br>`/business/Departments/active/byOffice/{id}`<br>`/business/Departments/archived/byOffice/{id}` | Départements du bureau : tous, actifs, archivés. |
| GET | `/business/Departments/getOffice/{id}` | Bureau du département. |
| POST | `/business/Departments/newDepartment` | Crée un département (corps `DepartmentRequestDto` : `name`). |
| PUT | `/business/Departments/update/{id}` | Remplace le département par le corps `DepartmentRequestDto`. |
| PUT | `/business/Departments/addToOffice/{departmentId}/{officeId}` | Rattache le département à un bureau. |
| PUT | `/business/Departments/archive/{id}` | Archive. |
| DELETE | `/business/Departments/delete/{id}` | Supprime. |

### Postes — `/business/jobs`

| Méthode | Chemin | Rôle de la route |
|---|---|---|
| GET | `/business/jobs` | Tous les postes. |
| GET | `/business/jobs/{id}` | Poste par id. |
| GET | `/business/jobs/getdep/{id}` | Département du poste. |
| GET | `/business/jobs/jobsbycompany/{cid}` | Postes de l'entreprise. |
| GET | `/business/jobs/jobsbydepartment/{id}`<br>`/business/jobs/active/jobsbydepartment/{id}`<br>`/business/jobs/archived/jobsbydepartment/{id}` | Postes du département : tous, actifs, archivés. |
| GET | `/business/jobs/cidbyjob/{jid}` | Identifiant de l'entreprise du poste. |
| GET | `/business/jobs/companybyjob/{jid}` | Entreprise du poste. |
| GET | `/business/jobs/jobByEmployeeId/{eid}` | Poste de l'employé (fiche lue dans botRH). |
| POST | `/business/jobs/newjob` | Crée un poste (corps `JobRequestDto`). |
| PUT | `/business/jobs/update/{id}` | Remplace le poste par le corps `JobRequestDto`. |
| PUT | `/business/jobs/addToDepartement/{jobId}/{departmentId}` | Rattache le poste à un département. |
| PUT | `/business/jobs/archive/{id}` | Archive. |
| DELETE | `/business/jobs/delete/{id}` | Supprime. |

## Points d'attention

Comportements constatés dans le code, qui diffèrent de ce que le nom de la route laisse attendre :

- `POST /api/role/addtouser` compare des chaînes avec `==` : aucune branche ne s'exécute et la liste des rôles de
  l'utilisateur est remplacée par `null`.
- `PUT /RH/employees/update/{id}` ne sauvegarde pas l'employé.
- `DELETE /RH/holidays/refuse/{id}` attribue le statut `PROVED`. `prouve` et `refuse` construisent une notification
  sans l'enregistrer.
- Dans `POST /RH/holidays/newHoliday/{idEmployee}`, le `switch` sur la durée n'a pas de `break` : la durée prise en
  compte vaut toujours 3 jours.
- `GET /RH/post/active/e/{ide}`, `/archived/e/{ide}` et `/e/{ide}` sont décalées (voir le tableau des publications).
- `PUT /RH/projects/addtoemployee/...` et `PUT /RH/projects/update/{id}` ne sont pas implémentées, de même que
  `GET /RH/clauses/active/contract/{id}` et `/archived/contract/{id}`.
- `GET /business/Companies/totalofemployeesbyCompany/{id}` et `totalofofficessbyCompany/{id}` renvoient des
  constantes.
