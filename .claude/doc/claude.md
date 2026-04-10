# Plan d’action — MVP Application « Monde De Dev »

Ce document décrit **exactement** comment construire le MVP du réseau social développeur **Monde De Dev**.

Il est basé sur :
- Les contraintes techniques ORION
- Les spécifications fonctionnelles ORION
- Les exigences de sécurité, d’architecture et de tests précisées

---

## 1. Choix techniques (à figer immédiatement)

### Backend
- Java 21
- Spring Boot 3.x
- Maven
- Spring Data JPA (Hibernate)
- Spring Security + JWT
- BCryptPasswordEncoder
- Clean Architecture + Hexagonal
- Respect strict des principes SOLID
- GlobalExceptionHandler (@ControllerAdvice)
- Flyway pour migrations

### Frontend
- Angular 17+
- TypeScript
- SCSS + TailwindCSS
- Architecture Feature-based (core/shared/features)
- RxJS + Services + Signals pour l’état
- Respect des bonnes pratiques Angular
- W3C Accessibility (A11y)

### Base de données
- PostgreSQL

### Sécurité globale (OWASP)
- JWT stocké en **HTTP Only Secure Cookie**
- Protection XSS / CSRF / SQL Injection
- Validation stricte des entrées

---

## 2. Architecture globale

```

[ Angular Front ]
|
| HTTPS + JWT Cookie
v
[ Spring Boot API ]
|
v
[ PostgreSQL ]

```

Front et Back totalement séparés.

---

## 3. Modèle de données (Domain Model)

### User
- id
- email (unique)
- username (unique)
- password
- createdAt
- updatedAt

### Topic
- id
- name
- description

### Subscription
- id
- user_id
- topic_id

### Post
- id
- title
- content
- author_id
- topic_id
- createdAt

### Comment
- id
- content
- author_id
- post_id
- createdAt

---

## 4. Sécurité & Authentification

### Règles mot de passe
- Minimum 8 caractères
- 1 minuscule
- 1 majuscule
- 1 chiffre
- 1 caractère spécial autorisé : `!@#$%^&*()-_=+[]{};:,.<>?`

### Authentification
- JWT généré au login
- Stocké en cookie HTTPOnly Secure
- Persistance entre sessions
- Filtre JWT Spring Security

---

## 5. Endpoints API

### Auth
| Méthode | Endpoint | Description |
|---|---|---|
| POST | /api/auth/register | Inscription |
| POST | /api/auth/login | Connexion |
| POST | /api/auth/logout | Déconnexion |

### Users
| Méthode | Endpoint |
|---|---|
| GET | /api/users/me |
| PUT | /api/users/me |

### Topics
| Méthode | Endpoint |
|---|---|
| GET | /api/topics |
| POST | /api/topics/{id}/subscribe |
| DELETE | /api/topics/{id}/unsubscribe |

### Posts
| Méthode | Endpoint |
|---|---|
| GET | /api/posts/feed?order=asc\|desc |
| POST | /api/posts |
| GET | /api/posts/{id} |

### Comments
| Méthode | Endpoint |
|---|---|
| POST | /api/posts/{id}/comments |

---

## 6. Architecture Backend (packages)

```

domain/
application/
infrastructure/
presentation/
security/
config/
exception/

```

---

## 7. Architecture Frontend Angular

```

core/
shared/
features/
auth/
feed/
themes/
article/
profile/
layout/

```

Header et Footer partagés sur toutes les pages authentifiées.

---

## 8. Pages / Features (ordre d’implémentation)

### Étape 1 — Authentification
- Landing page
- Register page
- Login page
- JWT + Cookie

### Étape 2 — Thèmes
- Liste des thèmes
- Subscribe
- Bouton « Déjà abonné »

### Étape 3 — Articles
- Write article
- Feed chronologique + tri
- Read article

### Étape 4 — Commentaires
- Ajout commentaire non récursif

### Étape 5 — Profil
- Voir / modifier email, username, password
- Voir abonnements
- Se désabonner

---

## 9. Gestion des erreurs

### Backend
- Exceptions métier custom
- Codes HTTP cohérents
- Réponses JSON normalisées

### Frontend
- HTTP Interceptor
- Gestion erreurs formulaire
- Notifications visuelles

---

## 10. Tests

### Backend
- Unit tests : JUnit 5 + Mockito
- Integration tests : SpringBootTest + Testcontainers
- Tests API : MockMvc

### Frontend
- Unit tests : Jasmine + Karma
- E2E : Cypress

---

## 11. Lien avec Figma

- Mapping Figma components → Angular components
- Design tokens → configuration Tailwind
- Respect strict du design system

---

## 12. Organisation Git

- Un seul repository
- Dossiers `/backend` et `/frontend`
- Gitflow :
  - main
  - develop
  - feature/*
  - fix/*

---

## 13. Ordre réel de développement

1. Setup repository + CI
2. Setup Spring Boot + PostgreSQL + Flyway
3. Création entités JPA
4. Auth + Security + JWT
5. Endpoints Topics
6. Endpoints Posts
7. Endpoints Comments
8. Setup Angular + routing
9. Pages Auth
10. Page Themes
11. Page Feed
12. Pages Articles
13. Page Profile
14. Tests complets
15. Hardening sécurité OWASP
16. Responsive + Accessibilité
17. Validation finale MVP

---

## 14. Critères de validation MVP

- Connexion persistante entre sessions
- Responsive mobile/desktop
- Respect SOLID et séparation front/back
- Aucun back-office
- Parcours utilisateur complet sans bug