# README

### REST API

Database schema:
I have 2 entities: Book and Collection. A 
Collection can have multiple Books and a Book can be in multiple Collections. Therefore I have a join table named Book_collection managed by spring.


Here are the CRUD Operations for Books:

1. GET /api/books: Get a list of all books (alphabetically sorted by title). Returns all books
2. GET /api/books/{id}: Get details of a specific book by ID. Return the fetched book
2. POST /api/books: Create a new book. Returns the created book
4. PUT /api/books/{id}: Update an existing book by ID. Returns the updated book
5. DELETE /api/books/{id}: Delete a book by ID. Returns a deletion confirmation

CRUD Operations for Collections:
6. GET /api/collections: Get a list of all collections (alphabetically sorted by name). Returns all the collections
7. GET /api/collections/{id}: Get details of a specific collection by ID. Returns the fetched collection
8. POST /api/collections: Create a new empty collection. Return the created collection
9. PUT /api/collections/{id}: Update an existing collection by ID. Return the updated collection
10. DELETE /api/collections/{id}: Delete a collection by ID. Return a deletion confirmation
11. POST /api/collections/{collectionId}/addBook/{bookId}: Add a book to a collection. Return the updated collection
12. POST /api/collections/{collectionId}/removeBook/{bookId}: Remove a book from a collection. Return the updated collection


I used an postgres container to run my db and connect my spring app to it but you can run the scripts in "src/main/resources/migration" to have a data sample.
There is a in-memory db for testing that runs the same scripts (in test resources) before running the test.


### POSTMAN Testing

If you want to use postman, here are some URIs that you can use to test (in the database, we have 15 books and 5 collection):

1. POST http://localhost:8080/api/books <br>
 
* **body**: { "title": "Jean Barois", "isbn" : "9780976773665", "author": "Jonathan TWITE" }

2. GET http://localhost:8080/api/books
3. GET http://localhost:8080/api/books/1
4. PUT http://localhost:8080/api/books
* **body**: {
  "id": 2,
  "title": "Jean Barois",
  "isbn": "978-1234567892"
  }
5. DELETE http://localhost:8080/api/books/2
6. http://localhost:8080/api/collections
* **body**: {
  "name": "Fiction Books"
  }
7. GET /api/collections/{id}: Get details of a specific collection by ID.
8. POST http://localhost:8080/api/collections/5
9. PUT http://localhost:8080/api/collections/3
* **body**: {
  "id": 3,
  "books": [
  {
  "id": 1
  },
  {
  "id": 2
  }
  ]
  }
10. DELETE http://localhost:8080/api/collections/4
11. POST http://localhost:8080/api/collections/5/addBook/2
12. POST http://localhost:8080/api/collections/5/removeBook/2
