# ING-Spring-Task

---
This is a simple Spring Boot application which acts as a store for a generic Product.

### Product blueprint

---
```
{
  "id": 1 (Integer),
  "name": "Toy" (String),
  "price": 20.0 (Double),
  "colour": "red" (String)
}
```


### REST API

---
| Request                      | Request Params | Request Body    | Response      |
|------------------------------|----------------|-----------------|---------------|
| GET /store/all_products      | -              | -               | List\<Product> |
| GET /store/find_product      | Integer id     | -               | Product       |
| GET /store/find_colour       | String colour  | -               | List\<Product> |
| POST /store/add_product      | -              | Product product | String        |
| PUT /store/update_product    | Integer id     | Product product | String        |
| DELETE /store/delete_product | Integer id     | -               | String        |

### Authentication

---
The application provides a basic authentication system. Currently, the two users stored locally are:
- username: basic, password: password, roles: USER
- username: admin, password: admin, roles: USER, ADMIN

The application also has role based endpoint access, with the following rules in place:
- GET mappings - only accessible by USER role
- POST, PUT, DELETE mappings - only accessible by ADMIN role
