module.exports = {
  devServer: {
    onBeforeSetupMiddleware(devServer) {
      devServer.app.use((req, res, next) => {
        if (req.headers['accept'] && req.headers['accept'].indexOf('text/html') < 0) {
          req.headers['accept'] = "text/html, " + req.headers['accept'];
        }
          
        next()
      });
    }
  }
};
