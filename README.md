# README

### REST API


CRUD Operations for Books:

* GET /api/books: Get a list of all books (alphabetically sorted by title).
* GET /api/books/{id}: Get details of a specific book by ID.
* POST /api/books: Create a new book.
* PUT /api/books/{id}: Update an existing book by ID.
* DELETE /api/books/{id}: Delete a book by ID.

CRUD Operations for Collections:
* GET /api/collections: Get a list of all collections (alphabetically sorted by name).
* GET /api/collections/{id}: Get details of a specific collection by ID.
* POST /api/collections: Create a new collection.
* PUT /api/collections/{id}: Update an existing collection by ID.
* DELETE /api/collections/{id}: Delete a collection by ID.


Additional Endpoints for Adding/Removing Books from a Collection:

* POST /api/collections/{collectionId}/addBook/{bookId}: Add a book to a collection.
* POST /api/collections/{collectionId}/removeBook/{bookId}: Remove a book from a collection.
