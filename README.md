# AutoTestingHub Backend

This is the backend for the **AutoTestingHub** project, developed using **Spring Boot**. The backend serves as the foundation for the **autotestinghub.org** platform, a knowledge-sharing hub for software testing professionals.

## Features
- Basic CRUD Resources:
  - **Post**: Manage blog posts with categories and comments.
  - **Comment**: Associate and manage comments for specific blog posts.
  - **Account**: Handle user accounts and authentication.
  - **Category**: Organize posts into categories.
- **Automation Testing**:
  - Backend APIs are tested with **RestAssured**.
  - Automation framework repository: [Automation Framework](https://github.com/zyzz15620/spring-blog-api-test)

## Tech Stack
- **Programming Language**: Java
- **Framework**: Spring Boot
  - **Spring Security 6**: For authentication and authorization, implementing JWT for secure access.
  - **Spring Data JPA**: For seamless integration with database operations.
- **Database**: MySQL
- **ORM**: Hibernate, for object-relational mapping and efficient database interactions.
- **API Design**: RESTful APIs, following standard best practices for resource-based architecture.
- **Authentication**: JWT (JSON Web Token) for stateless and secure authentication.

## API Documentation
[Swagger UI](https://springboot-docker-blog-0-3-release.onrender.com/swagger-ui/index.html#/), this will take a while to load because of free hosting

### Key Endpoints
- **Post APIs**:
  - Get all posts: `GET /api/posts`
  - Create a post: `POST /api/posts`
- **Comment APIs**:
  - Get all comments for a post: `GET /api/posts/{postId}/comments`
  - Add a comment to a post: `POST /api/posts/{postId}/comments`
- **Category APIs**:
  - Get all categories: `GET /api/categories`
  - Create a category: `POST /api/categories`
- **Account APIs**:
  - Register: `POST /api/auth/register`
  - Login: `POST /api/auth/login`

## How to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo-link/backend.git
