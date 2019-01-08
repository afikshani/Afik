
// Setup basic express server and socketIO
const express = require('express'),
    app = express(),
    server = require('http').createServer(app),
    io = require('socket.io')(server);
// Setup alphaVantage module within availabe key
const KEY = 'FGDHCV4HAC31PJ99',
    alpha = require('alphavantage')({ key: KEY });
// Setup of port to be listen at
const PORT = 8081 || process.env.PORT;


server.listen(PORT, () => {
    console.log(`Server is now online on port ${PORT}`);
});


io.on('connection', function (socket) {
    console.log("hey the client just logged in");
    socket.on('stocks updates', function (stockName) {
        setInterval(socket.broadcast.emit('stock info', {
            stock_value: getStockPrice(stockName),
        }),1000);
    });
});


function getStockPrice(stockName){
    alpha.data.batch([`${stockName}`]).then(data => {
        console.log(data);
        return data;
    }). catch( (err) => {
        console.log(err);
    });
}
