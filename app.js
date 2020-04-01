const toast = require('powertoast');
var myArgs = process.argv.slice(2);
var titulo = myArgs[0];
var msg = myArgs[1];
var icono = myArgs[2];
var juego = myArgs[3];
var img = myArgs[4];

toast({
  appID: "Microsoft.XboxApp_8wekyb3d8bbwe!Microsoft.XboxApp",
  title: titulo,
  message: msg,
  attribution: "Game_Tracker - AMS2",
  icon: icono,
  headerImg: img
}).catch((err) => { 
  console.error(err);
});