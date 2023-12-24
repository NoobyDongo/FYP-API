# FYP-API
Glassfish server
https://drive.google.com/drive/folders/1WsSCSxg0JgNPBShnqrM1SaIgmwu2u5zD?usp=drive_link

## Description
A system that processes CRUD requests from the client side.

<a name="top"/>

## Services: [GET](#get) | [PUT](#put) | [POST](#post) | [DELETE](#delete)

<a name="get"/>

### GET

Simple searching for a single record.
> **Format:**
> 
> [http://localhost:8080/api-server/{table name}?id={record id}]

example:

> [http://localhost:8080/api-server/product?id=1]

expected return:
```json
{
        "id": "1",
        "price": 9.4324,
        "name": "Apple",
        "desc": "A red apple, very sweet, weights around 7kg.",
        "image": "{...}",
        "producttype": {
            "id": "1",
            "name": "Human"
        },
        "origin": {
            "id": "1",
            "name": "China"
        }
}
```

[Return to top](#top)
-----

<a name="put"/>

### PUT

Advance searching for zero to many records
> **Format:**
> 
> [http://localhost:8080/api-server/{table name}]
> 
> **Body[^PutNote1]:**
> 
> ```json
> [
>   {
>         "requirement": "{and, or}",
>         "column" : "{column of the table}",
>         "operator" : "{=, like, !=, >, …}",
>         "data" : "{data to compare to}"
>   },
>   {
>         "...":"..."
>   }
> ]
> ```

[^PutNote1]: When PUT request is sent with an empty array, it returns every record in the specified table.

example:

> [http://localhost:8080/api-server/product]
> 
> **Body:**
> 
> ```json
> [
>   {
>         "requirement": "and",
>         "column" : "price",
>         "operator" : ">",
>         "data" : "10"
>   },
>   {
>         "requirement": "and",
>         "column" : "name",
>         "operator" : "like",
>         "data" : "A%"
>   }
> ]
> ```
expected return:
```json
[
  {
          "id": "1",
          "price": 12,
          "name": "Apple",
          "...": "..."
  },
  {
          "id": "43",
          "price": 98,
          "name": "Alarm Clock",
          "...": "..."
  },
]
```

[Return to top](#top)
-----

<a name="post"/>

### POST

Insert/update one to many records.
> **Format:**
> 
> [http://localhost:8080/api-server/{table name}]
> 
> **Body[^PostNote1]:**
> 
> ```json
> [
>   {
>         "id": "{record id}",
>         "..." : "...",
>   },
>   {
>         "...":"..."
>   },
>   {
>         "id": "{record id}",
>         "..." : "...",
>   },
> ]
> ```

[^PostNote1]: In POST requests, records with id will ovewrite the exixiting ones. Meanwhile records without id will be inserted.

example:

> [http://localhost:8080/api-server/product]
> 
> **Body:**
> 
> ```json
> [
>   {
>           "id": "1",
>           "price": 9.4324,
>           "name": "Apple",
>           "desc": "A red apple, very sweet, weights around 7kg.",
>           "image": "{...}",
>           "producttype": "1",
>           "origin": "1"
>   },
>   {
>           "price": 199.99,
>           "name": "Local Banana",
>           "desc": "A banana, very sweet, weights around 1kg.",
>           "image": "{...}",
>           "producttype": "1",
>           "origin": "1"
>   }
> ]
> ```

expected return:
```json
[
  {
          "id": "1",
          "price": 9.4324,
          "name": "Apple",
          "desc": "A red apple, very sweet, weights around 7kg.",
          "image": "{...}",
          "producttype": {
              "id": "1",
              "name": "Food"
          },
          "origin": {
              "id": "1",
              "name": "China"
          }
  },
  {
          "price": 199.99,
          "name": "Local Banana",
          "desc": "A banana, very sweet, weights around 1kg.",
          "image": "{...}",
          "producttype": {
              "id": "1",
              "name": "Food"
          },
          "origin": {
              "id" : "2",
              "name": "Hong Kong"
          }
  }
]
```

[Return to top](#top)
-----

<a name="delete"/>

### Delete

_Not Implemented Yet_

[Return to top](#top)
-----
