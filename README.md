# URL Shortener
> Server for shorting and resolving URL-s.

## Description
Server application written in Java which purpose is to provide URL shortening and resolving it back into the original URL
when shorted URL is visited. Also, statistics about each shorted URL visit is registered.

## Installation
Build deployable WAR using maven:
```sh
mvn package
```

After building is finished, deploy WAR located in `target/url-shortener.war` using Tomcat or any other web container.

## API documentation

| URI                    | Description          | HTTP Method  | Request Body                                           | Response Body                                                 |
|:-----------------------|:---------------------|:-------------|:-------------------------------------------------------|:--------------------------------------------------------------|
| /account               | Create new account   | POST         | {"accountId": "myAccId"}                               | {success: true, password: "zW573xjK"}                         |
| /register              | Register new URL     | POST         | {"url": "https://verylongurl.com", "redirectType: 301} | {shortUrl: "http://short.com/zwQpuM}                          |
| /statistic/{AccountId} | Get statistics       | GET          | -                                                      | {"http://verylong1.com": 5, "http://verylong2.com": 18, ...}  |
| /help                  | Display help page    | GET          | -                                                      | HTML help page content                                        |

LICENSE
---
MIT