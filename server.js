const express = require('express');
const path = require('path');
const bodyParser = require('body-parser');

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware to parse JSON request body
app.use(bodyParser.json());

// Serve static files from the public directory
app.use(express.static(path.join(__dirname, 'public')));

// Endpoint to handle login requests
app.post('/login', (req, res) => {
    const { email, password } = req.body;
    // Here you can perform authentication logic with email and password
    console.log('Received login request with email:', email, 'and password:', password);
    // Dummy response for demonstration purposes
    res.json({ message: 'Received login request' });
});

// Start the server
app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});
