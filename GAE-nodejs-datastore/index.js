const Datastore = require('@google-cloud/datastore');
const express = require('express');
var app = express();

app.use(express.bodyParser());

const projectId = 'YOUR_PROJECT_ID';
const datastore = new Datastore({
  projectId: projectId,
});

app.post('/api/books', (req, res) => {
  let entity = {
    key: datastore.key('book'),
    data: {
      title: request.body.title,
      author: request.body.author
    }
  };

  datastore
    .save(entity)
    .then(() => {
      console.log(`Saved ${task.key.name}: ${task.data.description}`);
    })
    .catch(err => {
      console.error('ERROR:', err);
    });

  res.send(book);
});

app.get('/api/books', (req, res) => {
  res.send([]);
});

const server = app.listen(8080, () => {
  const host = server.address().address;
  const port = server.address().port;

  console.log(`bookstore app listening at http://${host}:${port}`);
});
