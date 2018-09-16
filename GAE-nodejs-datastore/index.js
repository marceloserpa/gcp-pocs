const Datastore = require('@google-cloud/datastore');
const express = require('express');
const bodyParser = require('body-parser')

var app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

const projectId = 'YOUR_PROJECT_ID';
const datastore = new Datastore({
  projectId: projectId,
});

app.post('/api/books', (req, res) => {
  let entity = {
    key: datastore.key('book'),
    data: {
      title: req.body.title,
      author: req.body.author
    }
  };
  datastore
    .save(entity)
    .then(() => {
      console.log(`Saved ${entity.key.name}: ${entity.data.title}`);
    })
    .catch(err => {
      console.error('ERROR:', err);
    });

  res.send(entity);
});

app.get('/api/books', (req, res) => {
  const query = datastore.createQuery('book');
  datastore
    .runQuery(query)
    .then(results => {
      const books = results[0]
      res.send(books);
    })
    .catch(err => {
      console.error('ERROR:', err);
      res.send([]);
    });
});

const server = app.listen(8080, () => {
  const host = server.address().address;
  const port = server.address().port;

  console.log(`bookstore app listening at http://${host}:${port}`);
});
