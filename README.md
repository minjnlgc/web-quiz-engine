# web-quiz-engine
A project from JetBrains Academy: https://hyperskill.org/projects/91.
Learn about REST API, an embedded database, security, and other technologies by doing this project.

Implemented a REST API of a quiz engine with Spring boot, H2 database, Spring security, and Spring JPA.


---
Endpoints:

#### `POST /api/register`
No authentication is needed.

Included the email and the password in the request body:
```json
{
  "email": "<username>@<domain>.<extension>",
  "password": "<string, at least 5 characters long>"
}
```

If the email hadn't been taken, it would have been saved in the database.

Else, it would return `400 Bad Requests` 

#### `GET /api/quizzes`
Authentication is needed.

Accept the `page` parameter `/api/quizzes?page={number}`.


```json
{
  "totalPages":1,
  "totalElements":3,
  "last":true,
  "first":true,
  "sort":{ },
  "number":0,
  "numberOfElements":3,
  "size":10,
  "empty":false,
  "pageable": { },
  "content":[
    {"id":<quiz id>,"title":"<string>","text":"<string>","options":["<string>","<string>","<string>", ...]},
    {"id":<quiz id>,"title":"<string>","text":"<string>","options":["<string>", "<string>", ...]},
    {"id":<quiz id>,"title":"<string>","text":"<string>","options":["<string>","<string>", ...]}
  ]
}
```

#### `POST /api/quizzes`
Authentication is needed.

Create a new quiz.

Including the new quiz in the request body.

- `title`: a string, required;
- `text`: a string, required;
- `options`: an array of strings, required, should contain at least 2 items;
- `answer`: an array of integer indexes of correct options, can be absent or empty if all options are wrong.

```json
{
  "title": "<string, not null, not empty>",
  "text": "<string, <not null, not empty>",
  "options": ["<string 1>","<string 2>","<string 3>", ...],
  "answer": [<integer>,<integer>, ...]
}
```

If the quiz is created successfully, the response would be:
```json
{
  "id": <integer>,
  "title": "<string>",
  "text": "<string>",
  "options": ["<string 1>","<string 2>","<string 3>", ...]
}
```

#### `GET /api/quizzes/{id}`
Authentication is needed. Get the quiz by id.

If no such quiz, the response would be `404 NOT FOUND`.

#### `POST /api/quizzes/{id}/solve`
Authentication is needed.

Including the answer in the request body:
```json
{
  "answer": [<integer>, <integer>, ...]
}
```

If the answer is correct, the response:
```json
{
  "success":true,
  "feedback":"Congratulations, you're right!"
}
```

otherwise, the response would be:
```json
{
  "success":false,
  "feedback":"Wrong answer! Please, try again."
}
```

If no such quiz, the response would be `404 NOT FOUND`.


#### `DELETE /api/quizzes/{id}`
Authentication is needed.

Users can only delete the quiz created by themselves but not by others.

If the operation was successful, the service returns the `204 (NO CONTENT)` status code without any content.

If the specified quiz does not exist, the server returns `404 (NOT FOUND)`. If the specified user is not the author of this quiz, the response is the `403 (FORBIDDEN)` status code.

#### `GET /api/quizzes/completed`
Authentication is needed.

Get the quiz the current user has answered correctly.

If there are no completed quizzes for the authenticated user, the content is empty `[]`. If the user is authenticated, the status code is `200 (OK)`; otherwise, it's `401 (UNAUTHORIZED)`.
```json
{
  "totalPages":1,
  "totalElements":5,
  "last":true,
  "first":true,
  "empty":false,
  "content":[
    {"id":<quiz id>,"completedAt":"<date_time>"},
    {"id":<quiz id>,"completedAt":"<date_time>"},
    {"id":<quiz id>,"completedAt":"<date_time>"},
    {"id":<quiz id>,"completedAt":"<date_time>"},
    {"id":<quiz id>,"completedAt":"<date_time>"}
  ]
}
```
