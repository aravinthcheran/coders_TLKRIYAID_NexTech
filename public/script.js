// After successful authentication
function onSuccess(googleUser) {
    var profile = googleUser.getBasicProfile();
    console.log('Logged in as: ' + profile.getName());
    
    // Redirect to the ordering platform page
    window.location.href = 'rest_index.html'; // Redirect to the ordering platform page
}

function onFailure(error) {
    console.log('Error logging in: ' + error);
    alert("Authentication failed. Invalid Domain!");
}

function renderButton() {
    gapi.signin2.render('google-signin-button', {
        'scope': 'profile email',
        'width': 240,
        'height': 50,
        'longtitle': true,
        'theme': 'dark',
        'onsuccess': onSuccess,
        'onfailure': onFailure,
        'hd': '@example.com' // Restrict sign-in to users from this domain
    });
}

// Custom login form submission
document.getElementById('custom-login-form').addEventListener('submit', function(event) {
    event.preventDefault();
    // Get input values
    var email = document.getElementById('email').value;
    var password = document.getElementById('password').value;

    // Perform authentication with email and password (you would typically send this data to your backend)
    console.log('Custom login with email:', email, 'and password:', password);
});

// Load Google API platform
function handleLoad() {
    gapi.load('auth2', function() {
        gapi.auth2.init();
    });
}
