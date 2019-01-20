
// Setup basic express server and socketIO
const express = require('express'),
    app = express(),
    server = require('http').createServer(app),
    io = require('socket.io')(server),
    bodyParser = require('body-parser');

// Setup google FireBase module and config it
const FCM = require('fcm-push');
const FCM_SERVER_KEY = 'AIzaSyClqCCeHow5ttaHzUOsZIsmJKWq3etWH5c';
let fcm = new FCM(FCM_SERVER_KEY);

// Setup alphaVantage module within availabe key
const KEY = 'FGDHCV4HAC31PJ99',
    alpha = require('alphavantage')({ key: KEY });


// Setup of port to be listen at
const PORT = 8080 || process.env.PORT;

// Using middleware
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

server.listen(PORT, () => {
    console.log(`Server is now online on port ${PORT}`);
});

// Holding the relevant tokens
let tokens = {};

// Get from client when user register with new token at the first run of the app
app.post('/:user/token', (req, res, next) => {
    let token = req.body.myToken;
    // Monitor that we got the token fro the user
    console.log(`Received save token request from ${req.params.user} for token=${token}`);
    if (!token) return res.status(400).json({err: "missing token"});
    tokens[req.params.user] = token;
    res.status(200).json({msg: "saved ok"});
});

let convertedName = 'aapl';

io.on('connection', function (socket) {

    // Check for conection between user and server
    console.log("Hey! The client just logged in into the system :)");
    
    // Listener for stock update request
    socket.on('stocks updates', function (stock_name) {
        // Indication for the request from the user
        console.log(`User just asked for ${stock_name}'s stock updates!!!`);
        // Converting the display stock name into alphaVantage vaild name for request
        convertedName = getStockName(stock_name);

        // Interval for emitting to user answer every 15 sec'
        setInterval( () => {

            alpha.data.batch([`${convertedName}`]).then (stockData => {
                let currentValueOfStock = stockData['Stock Quotes'][0]['2. price'];
                // Send to user the stock value using the socket.IO
                socket.emit('stock info', {
                    stockValue: currentValueOfStock
                });

            }).catch(err =>{
                console.log(err);
            });

        }, 15000);

    });

    // If socket is close use FireBase to send the stock value
    socket.on('disconnect', function () { // if user socket is being closed
        console.log("User socket was just closed.");

        setInterval( () => {

            alpha.data.batch([`${convertedName}`]).then (stockData => {
                currentValueOfStock = stockData['Stock Quotes'][0]['2. price'];
                console.log("FireBase is sending information to client");

                // Send to user stock value using firebase     
                fcm.send({
                        to: tokens['afik'],
                        data: {
                            someValue: 'stock information'
                        },
                        notification: {
                            title: `${convertedName} stock value`,
                            body: 'the current price is:' + currentValueOfStock
                        }
                }, (err, response) => {});

            }).catch(err =>{
                console.log("alphaVantage error " + err);
            });
        
        }, 15000);
        
    });
});   

// Helper function. Converts the company's stock name for a vaild alphaVantage name 
function getStockName(stock_name) {
    let realStockName;
    switch(stock_name) {
        case "Tesla":
            realStockName = "tsla";
            break;
        case "Ferrari":
            realStockName = "race";
            break;
        case "Ford":
            realStockName = "f";
            break;
        case "Microsoft":
            realStockName = "msft";
            break;
        case "Apple":
            realStockName = "aapl";
            break;
      } 
    return realStockName;
 } 