const express = require('express');
const fs = require('fs');

let app = express();
app.get('/files/:filename', (req, res) => {
    let img = req.params.filename;
    fs.access(`./files/${img}`, fs.constants.F_OK, (err) => {
        if(!err){
            let readStream = fs.createReadStream(`./files/${img}`);
            readStream.pipe(res);
        }else{
        res.send("Try to find your friends at files/friends.jpg  :)  ");
        }
    });
});

app.get('*', (req, res) => {
    res.send("cannot process this request. Try to get a file under the relevant path!");
});

app.listen(8080, () => {
console.log('Listening on port 8080!');
});
