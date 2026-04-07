const DB_KEY = "QUE_DATA";

function doAjax(url, data, callback) {
  return fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json;charset=utf-8'
      },
      body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(json => callback && callback(json))
    .catch(err => console.log('Request Failed', err));
}
