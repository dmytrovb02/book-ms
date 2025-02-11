# 📚 Book & Review Service

**Book & Review Service** is a RESTful API that enables users to manage books and reviews. The service allows creating, updating, deleting, and retrieving books along with their reviews.

## 🚀 Technologies Used

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **Lombok**
- **PostgreSQL**
- **Slf4j (Logging)**
- **JUnit & Mockito (Testing)**

## 📦 Setup & Run

### 1️⃣ Clone the Repository
```sh
git clone git@github.com:dmytrovb02/book-ms.git
cd book-review-service
```

## 📌 API Documentation
### 📖 Books
#### ➕ Add a New Book
``` 
POST /api/books
{
"title": "Spring Boot in Action",
"author": "Craig Walls",
"publishedYear": 2018
}
```
#### 📋 Get All Books
```
GET /api/books
```

#### 🔍 Get Book by ID
```
GET /api/books/{id}
```

#### ✏️ Update a Book

```
PUT /api/books/{id}
{
  "title": "Spring Boot Advanced",
  "publishedYear": 2020
}
```
#### ❌ Delete a Book
```
DELETE /api/books/{id}
```

### ✍️ Reviews
#### ➕ Add a Review
```
POST /api/reviews
{
  "bookId": 1,
  "rating": 5,
  "comment": "Very useful book!"
}
```

#### 📋 Get Reviews for a Book
```
GET /api/reviews/book/{bookId}
```

#### ✏️ Update a Review
```
PUT /api/reviews/{id}
{
  "rating": 4,
  "comment": "Good book, but some parts are unclear."
}
```

#### ❌ Delete a Review
```
DELETE /api/reviews/{id}
```

## 🛠 Contributors
#### Dmytro Vyshnivskyi - Backend Developer

### 🚀 Happy coding!








