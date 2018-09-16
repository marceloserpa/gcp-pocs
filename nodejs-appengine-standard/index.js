const express = require('express');
const app = express();

app.get('/api/books', (req, res) => {
  let books = [{
    title: "The shining",
    author: "Stephen King"
  }];

  res.send(books);
});

app.get('/api/books222', (req, res) => {
  let books = [{
    title: "The shining",
    author: "Stephen King"
  }];

  res.send(books);
});

const server = app.listen(8080, () => {
  const host = server.address().address;
  const port = server.address().port;

  console.log(`bookstore app listening at http://${host}:${port}`);
});
