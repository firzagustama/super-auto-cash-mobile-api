# API Spec

## Login
Request :
- Method : POST
- URL : `/login`
- Header :
    - Content-Type : application/json
    - Accept : application/json
- Body : 
```json
{
  "token": "",
  "data": {
    "username": "firzagustama",
    "password": "md5password"
  }
}
```

Response :
```json
{
  "success": true,
  "errorCode": null,
  "errorMessage": null,
  "data": {
    "id": "32",
    "roleId": "1",
    "username": "firzagustama",
    "email": "firzagustama@gmail.com",
    "emailVerified": false,
    "phoneNumber": "081932874979",
    "fullName": "Muhammad Firza Gustama"
  }
}
```

## Register
Request :
- Method : POST
- URL : `/register`
- Header :
    - Content-Type : application/json
    - Accept : application/json
- Body : 
```json
{
  "token": "",
  "data": {
    "password": "md5password",
    "username": "firzagustama",
    "email": "firzagustama@gmail.com",
    "phoneNumber": "081932874979",
    "fullName": "Muhammad Firza Gustama"
  }
}
```
Response :
```json
{
  "success": true,
  "errorCode": null,
  "errorMessage": null,
  "data": {
    "id": "generated id",
    "roleId": "1",
    "username": "firzagustama",
    "email": "firzagustama@gmail.com",
    "emailVerified": false,
    "phoneNumber": "081932874979",
    "fullName": "Muhammad Firza Gustama"
  }
}
```