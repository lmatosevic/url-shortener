<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>URL Shortener</title>
</head>
<body>
<h1>HTTP URL Shortener API</h1>
<h2>Usage</h2>
<ol>
  <li>Start <a href="https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop">Postman</a>
  or any similar API testing tools.</li>
  <li>Send POST request to /url-shortener/account with following body content: {"AccountId":"SomeName"}</li>
  <li>Read response and save returned password, it will be used for further requests.</li>
  <li>Send POST request to /url-shortener/register with following body content: {"url":"SomeLongUrl"}</li>
  <li>Read response and save returned shortUrl link, something like /url-shortener/short/xCqWElzY</li>
  <li>Go to returned short url and you will be redirected to the long url you provided in step 3</li>
  <li>To get statistics, send GET request to /url-shortener/statistic , response will be json array with registered urls
    and numbers of visits</li>
</ol>
</body>
</html>
