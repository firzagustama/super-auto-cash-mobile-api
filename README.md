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
| `2001` | Param illegal | Wrong request data from client side |
| `2002` | Unauthorized | This user is not authorized |
| `2003` | This user is forbidded to access this api | User has no access to access this API | 
| `3002` | User not found | User not found when login |
| `3003` | Password invalid | Wrong password when login |
| `3004` | Username or Email already exists | Failed to register because user already exists |
| `4002` | Menu not found | Searched menu id not found |

## User Login
POST `/user/login`

User login

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

## User Register
POST `/user/register`

User Register

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

## Merchant Login
POST `/merchant/login`

Request :

| Parameter | Type | Description |
| --- | --- | --- |
| username | String | `Conditional` not either blank with `email` |
| email | String | `Conditional` not either blank with `username` |
| password | String | `Required` md5 hashed |

Response :
```json
{
  "token": "auth_token"
}
```

## Merchant Register
POST `/merchant/register`

Request :

| Parameter | Type | Description |
| --- | --- | --- |
| username | String | `Required` |
| email | String | `Required` |
| password | String | `Required` md5 hashed |
| phoneNumber | String | `Required` starts with `+62` |
| fullName | String | `Required` |
| address | String | `Optional` merchant address |

Response :
```json
{
  "token": "auth_token"
}
```

## Auth Check
POST `/auth/check`

Check for Authorization token is valid or not

Request : `token User or Merchant required`
 
| Parameter | Type | Description |
| --- | --- | --- |

Response :
```json
{
}
```

## Menu Get Detail
GET `/menu/detail/{menu_id}`

Get menu detail

Request :
 
| Parameter | Type | Description |
| --- | --- | --- |
| menuId | Int | `Required` as path variable in `menu_id` |

Response :
```json
{
    "menu": {
      "id": 1202,
      "merchantId": 1200,
      "name": "Ayam Goreng",
      "imageUrl": "https://awsimages.detik.net.id/community/media/visual/2020/12/08/food-cour-jakarta-utara-1.jpeg?w=539",
      "price": 15000,
      "description": "",
      "createdDate": "2022-01-24T15:04:27.770+00:00",
      "updatedDate": "2022-01-24T15:04:27.770+00:00"
    }
}
```

## Menu Get Pagination
GET `/menu/{merchant_id}`

Get menu detail

Request :
 
| Parameter | Type | Description |
| --- | --- | --- |
| merchantId | Int | `Required` as path variable in `merchant_id`

Response :
```json
{
  "info": {
    "count": 10,
    "next": "/menu/1200?page=1&size=3",
    "pages": "/menu/1200?page=0&size=3",
    "prev": null
  },
  "menus": [
    {
      "id": 1202,
      "merchantId": 1200,
      "name": "Ayam goreng update",
      "imageUrl": "https://awsimages.detik.net.id/community/media/visual/2020/12/08/food-cour-jakarta-utara-1.jpeg?w=539",
      "price": 15000,
      "description": "",
      "createdDate": "2022-01-24T15:04:27.770+00:00",
      "updatedDate": "2022-01-24T15:04:28.044+00:00"
    },
    {
      "id": 1203,
      "merchantId": 1200,
      "name": "Ayam Suir",
      "imageUrl": "https://awsimages.detik.net.id/community/media/visual/2020/12/08/food-cour-jakarta-utara-1.jpeg?w=539",
      "price": 16000,
      "description": "",
      "createdDate": "2022-01-24T15:04:27.770+00:00",
      "updatedDate": "2022-01-24T15:04:27.770+00:00"
    },
    {
      "id": 1204,
      "merchantId": 1200,
      "name": "Ayam ayam",
      "imageUrl": "https://awsimages.detik.net.id/community/media/visual/2020/12/08/food-cour-jakarta-utara-1.jpeg?w=539",
      "price": 20000,
      "description": "",
      "createdDate": "2022-01-24T15:04:27.770+00:00",
      "updatedDate": "2022-01-24T15:04:27.770+00:00"
    }
  ]
}
```

## Menu Create
POST `/menu/create`

Create menu

Request : `token merchant required`
 
| Parameter | Type | Description |
| --- | --- | --- |
| name | String | `Required` menu name |
| imageUrl | String | `Required` |
| price | Int | `Required` must be greater than or equals 0 |
| description | String | `Optional` menu description |

Response :
```json
{
  "id": 1208
}
```

## Menu Update
POST `/menu/update`

Update menu

Request : `token merchant required`
 
| Parameter | Type | Description |
| --- | --- | --- |
| id | String | `Required` menu id that will be updated |
| name | String | `Required` menu name |
| imageUrl | Int | `Required` |
| price | String | `Required` must be greater than or equals 0 |
| description | String | `Optional` menu description |

Response :
```json
{
  "menu": {
   "id": 1202,
   "merchantId": 1200,
   "name": "Ayam goreng update",
   "imageUrl": "https://awsimages.detik.net.id/community/media/visual/2020/12/08/food-cour-jakarta-utara-1.jpeg?w=539",
   "price": 15000,
   "description": "",
   "createdDate": "2022-01-24T15:04:27.770+00:00",
   "updatedDate": "2022-01-24T15:04:28.044+00:00"
  }
}
```

## Menu Delete
POST `/menu/delete/{menu_id}`

Update menu

Request : `token merchant required`
 
| Parameter | Type | Description |
| --- | --- | --- |
| menuId | Int | `Required` menu id that will be deleted as path variable |

Response :
```json
{
}
```