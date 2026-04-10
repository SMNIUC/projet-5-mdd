# User Stories — MVP « Monde De Dev » ✅

---

## Epic 1 — Authentication & Access

### ☐ US-01 — Access landing page
**As a** visitor  
**I want** to access a landing page without being authenticated  
**So that** I can choose to register or log in.

**Acceptance Criteria**
- ☐ Two buttons visible: *S’inscrire* and *Se connecter*
- ☐ No authentication required

---

### ☐ US-02 — Register account
**As a** visitor  
**I want** to create an account with email, username and password  
**So that** I can access the platform.

**Acceptance Criteria**
- ☐ Email is unique
- ☐ Username is unique
- ☐ Password respects security rules
- ☐ Password encrypted in database
- ☐ Redirect to Feed on success
- ☐ Validation errors displayed clearly

---

### ☐ US-03 — Login
**As a** user  
**I want** to log in using email or username and password  
**So that** I can access my feed.

**Acceptance Criteria**
- ☐ Login with email **or** username
- ☐ JWT created
- ☐ JWT stored in HTTPOnly cookie
- ☐ Session persists between browser sessions
- ☐ Redirect to Feed on success
- ☐ Error message on failure

---

### ☐ US-04 — Logout
**As a** user  
**I want** to log out  
**So that** my session is terminated.

**Acceptance Criteria**
- ☐ JWT cookie removed
- ☐ Redirect to Landing page

---

## Epic 2 — Topics & Subscriptions

### ☐ US-05 — View all topics
**As a** logged-in user  
**I want** to see the list of all topics  
**So that** I can choose what to follow.

**Acceptance Criteria**
- ☐ All topics displayed
- ☐ “Déjà abonné” shown for subscribed topics (disabled)
- ☐ “S’abonner” shown for others

---

### ☐ US-06 — Subscribe to a topic
**As a** logged-in user  
**I want** to subscribe to a topic  
**So that** I can see related posts in my feed.

**Acceptance Criteria**
- ☐ Subscription created on click
- ☐ Button becomes “Déjà abonné” and disabled

---

### ☐ US-07 — Unsubscribe from a topic
**As a** logged-in user  
**I want** to unsubscribe from a topic from my profile  
**So that** I stop seeing related posts.

**Acceptance Criteria**
- ☐ Topic visible in profile subscriptions
- ☐ Unsubscribe removes subscription

---

## Epic 3 — Feed & Posts

### ☐ US-08 — View my feed
**As a** logged-in user  
**I want** to see posts from my subscribed topics  
**So that** I can read relevant content.

**Acceptance Criteria**
- ☐ Posts ordered newest → oldest by default
- ☐ Only posts from subscribed topics shown

---

### ☐ US-09 — Sort feed
**As a** logged-in user  
**I want** to reverse chronological order  
**So that** I can read older posts first.

**Acceptance Criteria**
- ☐ Toggle asc/desc
- ☐ Feed updates accordingly

---

### ☐ US-10 — Write an article
**As a** logged-in user  
**I want** to write a post with title, content and topic  
**So that** I can share knowledge.

**Acceptance Criteria**
- ☐ Topic selection required
- ☐ Author auto-set
- ☐ Date auto-set
- ☐ Input validation
- ☐ Post appears in feed

---

### ☐ US-11 — Read an article
**As a** logged-in user  
**I want** to open a post  
**So that** I can read its content and comments.

**Acceptance Criteria**
- ☐ Title, content, author, date, topic displayed
- ☐ Comments displayed

---

## Epic 4 — Comments

### ☐ US-12 — Comment a post
**As a** logged-in user  
**I want** to add a comment to a post  
**So that** I can interact.

**Acceptance Criteria**
- ☐ Textbox for comment
- ☐ Author auto-set
- ☐ Date auto-set
- ☐ Linked to post
- ☐ No nested comments

---

## Epic 5 — Profile Management

### ☐ US-13 — View profile
**As a** logged-in user  
**I want** to see my email, username and subscriptions  
**So that** I can manage my account.

**Acceptance Criteria**
- ☐ Email displayed
- ☐ Username displayed
- ☐ Subscribed topics listed

---

### ☐ US-14 — Edit profile
**As a** logged-in user  
**I want** to modify my email, username or password  
**So that** I keep my information up to date.

**Acceptance Criteria**
- ☐ Same validation rules as registration
- ☐ Password rules enforced
- ☐ Data updated in database

---

## Epic 6 — Navigation Layout

### ☐ US-15 — Header navigation
**As a** logged-in user  
**I want** a header with navigation links  
**So that** I can move easily between pages.

**Acceptance Criteria**
- ☐ Links: Feed, Themes, Write Article, Profile
- ☐ Logo redirects to Feed
- ☐ Logout button present

---

### ☐ US-16 — Footer consistency
**As a** logged-in user  
**I want** a footer on all authenticated pages  
**So that** layout is consistent.

**Acceptance Criteria**
- ☐ Footer visible on all authenticated pages

---

## Epic 7 — Non-functional Requirements

### ☐ US-17 — Responsive design
**Acceptance Criteria**
- ☐ Works on mobile
- ☐ Works on desktop

---

### ☐ US-18 — Accessibility (A11y)
**Acceptance Criteria**
- ☐ W3C accessibility guidelines respected

---

### ☐ US-19 — Security compliance (OWASP)
**Acceptance Criteria**
- ☐ SQL injection protection
- ☐ XSS protection
- ☐ CSRF protection
- ☐ Password encrypted
- ☐ Secure JWT handling