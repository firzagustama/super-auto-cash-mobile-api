# API Spec
This is the api spec of super auto cash mobile api. 

All the `Request` must be wrapped with : 
- Method : POST
- Header :
    - Content-Type : application/json
    - Accept : application/json
    - Authorization : Bearer TOKEN
- Body

| Parameter | Type | Description |
| --------- | ---- | ----------- |
| data | Object | `Required` request data that will be detailed in each endpoint |
| extParams | Map | `Optional` additional data needed by client 

All the `Response` will be wrapped with :

Success :
```json
{
  "success" : true,
  "errorCode": null,
  "errorMessage": null,
  "data": {},
  "extParams": {}
}
```

Failed :
```json
{
  "success" : false,
  "errorCode": 3004,
  "errorMessage": "Username or Email already exists",
  "data" : null,
  "extParams": {}
}
```

- Api call status will be presented in `success`
- Error Codes mapping will be presented in `errorCode` and `errorMessage` 
- Response object will be presented in `data`

## Error Codes

| Code | Message | Description |
| --- | --- | --- |
| `1001` | Server error, please check api logs | There are error in server side |
| `2001` | Param illegal | Wrong request data from client side
| `2002` | Unauthorized | This user is not authorized
| `3002` | User not found | User not found when login |
| `3003` | Password invalid | Wrong password when login |
| `3004` | Username or Email already exists | Failed to register because user already exists |

## Login
POST `/login`

Request :

| Parameter | Type | Description |
| --- | --- | --- |
| username | String | `Conditional` not either blank with `email` |
| email | String | `Conditional` not either blank with `username` |
| password | String | `Required` md5 hashed |

Response :
```json
{
    "id": "32",
    "roleId": "1",
    "username": "firzagustama",
    "email": "firzagustama@gmail.com",
    "emailVerified": false,
    "phoneNumber": "081932874979",
    "fullName": "Muhammad Firza Gustama",
    "token" : "authorization_token"
}
```

## Register
POST `/register`

Request :

| Parameter | Type | Description |
| --- | --- | --- |
| username | String | `Required` |
| email | String | `Required` |
| phoneNumber | String | `Required` must be started with `+62` |
| fullName | String | `Required` |
| password | String | `Required` md5 hashed |

Response :
```json
{
    "id": "generated id",
    "roleId": "1",
    "username": "firzagustama",
    "email": "firzagustama@gmail.com",
    "emailVerified": false,
    "phoneNumber": "081932874979",
    "fullName": "Muhammad Firza Gustama",
    "token": "authorization_token"
}
```

## User Check 
POST `/user/check`

Request :
 
| Parameter | Type | Description |
| --- | --- | --- |
| username | String | `Conditional` not either blank with `email` |
| email | String | `Conditional` not either blank with `username` |

Response :
```json
{
  "exists": true
}
```

## Auth Check
POST `/auth/check`

Check for Authorization token is valid or not

Request :
 
| Parameter | Type | Description |
| --- | --- | --- |

Response :
```json
{
}
```