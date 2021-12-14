## launching
* build fat jat or launch within ide, gradle wrapper provided
## apis with examples

##### user apis
* register
  ```json
  curl --location --request POST '127.0.0.1:8080/users' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "email":"maya@gmail.com",
  "firstName": "Maya",
  "lastName":"Lightbringer",
  "password":"123"
  }'
  ```
* update names
```json
curl --location --request PUT '127.0.0.1:8080/users/me' \
--header 'Authorization: Basic bWF5YUBnbWFpbC5jb206MTIz' \
--header 'Content-Type: application/json' \
--data-raw '{
    
    "firstName": "Maya",
    "lastName":"Lightbringer2"
    
}'
```
* change password
```json
curl --location --request PUT '127.0.0.1:8080/users/me/changePassword' \
--header 'Authorization: Basic bWF5YUBnbWFpbC5jb206MTIzNDU=' \
--header 'Content-Type: application/json' \
--data-raw '{
    
    "newPassword":"123"
    
}'
```
* delete user
```json
curl --location --request DELETE '127.0.0.1:8080/users/me' \
--header 'Authorization: Basic bWF5YUBnbWFpbC5jb206MTIz' \
--header 'Content-Type: application/json' \
--data-raw '{}'
```
##### manager apis (not secured)
* actuator
  ```
  http://127.0.0.1:8080/actuator/mappings
  ```
* get all users
```json
curl --location --request GET '127.0.0.1:8080/managers/users'
```
* get by email
```json
curl --location --request GET '127.0.0.1:8080/managers/users/maya@gmail.com'
```