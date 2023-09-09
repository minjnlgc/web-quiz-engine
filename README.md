# Web Quiz Engine

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://mit-license.org/)

## Overview

Web Quiz Engine is a RESTful API-based quiz management system created as a project for JetBrains Academy. 
Explore the world of secure and interactive quizzes with Web Quiz Engine.

## Key Technologies Used

- **Java 11+**
- **Spring Boot**
- **Spring Security**
- **Spring JPA**
- **H2 Database**
- **REST API Design**

## Features

### User Registration

- **Endpoint:** `POST /api/register`
- **Authentication:** Not required
- **Request Body:**

```json
{
  "email": "<username>@<domain>.<extension>",
  "password": "<string, at least 5 characters long>"
}
```

- **Description:** Users can register using their email and a password. The system checks if the email is unique and saves it in the database if available.

### Quiz Listing

- **Endpoint:** `GET /api/quizzes`
- **Authentication:** Required
- **Request Parameter:** `page` for pagination: `/api/quizzes?page={number}`

- **Response:**

```json
{
  "totalPages": 1,
  "totalElements": 3,
  "last": true,
  "first": true,
  "sort": {},
  "number": 0,
  "numberOfElements": 3,
  "size": 10,
  "empty": false,
  "pageable": {},
  "content": [
    {
      "id": <quiz id>,
      "title": "<string>",
      "text": "<string>",
      "options": ["<string>", "<string>", "<string>", ...]
    },
    {
      "id": <quiz id>,
      "title": "<string>",
      "text": "<string>",
      "options": ["<string>", "<string>", ...]
    },
    {
      "id": <quiz id>,
      "title": "<string>",
      "text": "<string>",
      "options": ["<string>", "<string>", ...]
    }
  ]
}
```

- **Description:** Authenticated users can access a list of quizzes. The response includes pagination details.

### Quiz Creation

- **Endpoint:** `POST /api/quizzes`
- **Authentication:** Required
- **Request Body:**

```json
{
  "title": "<string, not null, not empty>",
  "text": "<string, not null, not empty>",
  "options": ["<string 1>", "<string 2>", "<string 3>", ...],
  "answer": [<integer>, <integer>, ...]
}
```

- **Response:**

```json
{
  "id": <integer>,
  "title": "<string>",
  "text": "<string>",
  "options": ["<string 1>", "<string 2>", "<string 3>", ...]
}
```

- **Description:** Authenticated users can create quizzes with specified titles, questions, options, and correct answers. Successful creation results in a response containing quiz details.

### Quiz Retrieval

- **Endpoint:** `GET /api/quizzes/{id}`
- **Authentication:** Required

- **Response:** Returns quiz details by ID or `404 NOT FOUND` if the quiz doesn't exist.

### Quiz Submission

- **Endpoint:** `POST /api/quizzes/{id}/solve`
- **Authentication:** Required
- **Request Body:**

```json
{
  "answer": [<integer>, <integer>, ...]
}
```

- **Response:**

```json
{
  "success": true,
  "feedback": "Congratulations, you're right!"
}
```

- **Description:** Authenticated users can submit answers to quizzes. The system provides feedback based on correctness.

### Quiz Deletion

- **Endpoint:** `DELETE /api/quizzes/{id}`
- **Authentication:** Required
- **Description:** Users can delete quizzes they've created. Successful deletion results in a `204 (NO CONTENT)` status. If the specified quiz doesn't exist, the server returns `404 (NOT FOUND)`. If the specified user is not the author of this quiz, the response is `403 (FORBIDDEN)`.

### Completed Quizzes

- **Endpoint:** `GET /api/quizzes/completed`
- **Authentication:** Required

- **Response:**

```json
{
  "totalPages": 1,
  "totalElements": 5,
  "last": true,
  "first": true,
  "empty": false,
  "content": [
    {
      "id": <quiz id>,
      "completedAt": "<date_time>"
    },
    {
      "id": <quiz id>,
      "completedAt": "<date_time>"
    },
    {
      "id": <quiz id>,
      "completedAt": "<date_time>"
    },
    {
      "id": <quiz id>,
      "completedAt": "<date_time>"
    },
    {
      "id": <quiz id>,
      "completedAt": "<date_time>"
    }
  ]
}
```

- **Description:** Authenticated users can view quizzes they've answered correctly. The response includes pagination details.


## License

This project is licensed under the [MIT License](https://mit-license.org/). 

## Credits

This project was based on https://hyperskill.org/projects/91.
