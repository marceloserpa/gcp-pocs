const Datastore = require('@google-cloud/datastore');

const projectId = 'YOUR_PROJECT_ID';
const datastore = new Datastore({
  projectId: projectId,
});

const query = datastore.createQuery('entity').select(['a', 'b', 'c', 'd']);
datastore
  .runQuery(query)
  .then(results => {
    console.log(results[0]);
  })
  .catch(err => {
    console.error('ERROR:', err);
  });
