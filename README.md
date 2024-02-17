# Learning Portal Project

## Description
This project is a Learning Portal application designed to facilitate online learning. It provides functionalities for users to register, enroll in courses, add favorite courses, and view enrolled and favorite courses.

## User Roles and Capabilities
- **ADMIN**: 
  - Can create user accounts.
- **AUTHOR**: 
  - Can publish new courses.
- **LEARNER**: 
  - Can search for courses.
  - Can enroll in courses.
  - Can favourite courses

    ![learning_portal](https://github.com/yyogesh0301/learning-portal/assets/101698207/73201c8e-6449-494a-8e16-40fcdbeeb3ef)




## Endpoints

### Users

| Method | Endpoint                         | Description                               |
|--------|----------------------------------|-------------------------------------------|
| GET    | `/api/users`                     | Retrieve all users.                       |
| GET    | `/api/users/{id}`                | Retrieve a user by ID.                    |
| POST   | `/api/users`                     | Register a new user.                      |
| DELETE | `/api/users/{id}`                | Remove a user.                            |
| POST   | `/api/users/courses/enroll`      | Enroll a user in a course.                |
| POST   | `/api/users/courses/favourite`   | Add a course to user's favorites.         |
| DELETE | `/api/users/courses/unenroll`    | Remove enrollment from a course.          |
| DELETE | `/api/users/courses/unfavorite`  | Remove a course from user's favorites.    |
| POST   | `/api/users/login`               | Login user.                               |

### Categories

| Method | Endpoint                    | Description                              |
|--------|-----------------------------|------------------------------------------|
| GET    | `/api/categories`           | Retrieve all categories.                 |
| GET    | `/api/categories/{id}`      | Retrieve a category by ID.               |
| POST   | `/api/categories`           | Add a new category.                      |
| PUT    | `/api/categories/{id}`      | Update an existing category.             |

### Courses

| Method | Endpoint                          | Description                                   |
|--------|-----------------------------------|-----------------------------------------------|
| GET    | `/api/courses`                    | Retrieve all courses.                         |
| GET    | `/api/courses/{id}`               | Retrieve a course by ID.                      |
| GET    | `/api/courses/author/{id}`        | Retrieve courses by author ID.                |
| GET    | `/api/courses/category/{id}`      | Retrieve courses by category ID.              |
| GET    | `/api/courses/{course_id}/users`  | Retrieve enrolled users for a course.         |
| POST   | `/api/courses`                    | Add a new course.   
