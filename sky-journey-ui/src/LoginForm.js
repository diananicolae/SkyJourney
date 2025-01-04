import React, { useState } from 'react';
import axios from 'axios';
import './LoginForm.css';

function LoginForm({ onLoginSuccess }) {
  const [credentials, setCredentials] = useState({  username: '', password: '' });

  const [loginError, setLoginError] = useState('');

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setCredentials({ ...credentials, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post(`${process.env.REACT_APP_AUTH_URL}/login`, credentials);
      localStorage.setItem('token', response.data.token);
      localStorage.setItem('userId', response.data.userId);
      localStorage.setItem('userName', response.data.name);
      window.location.href = '/';
      onLoginSuccess();
    } catch (error) {
      console.error('Login failed:', error);
      setLoginError('Incorrect username or password.');
    }
  };

  return (
    <div className="login-form-container">
      <form onSubmit={handleSubmit} className="login-form">
        <h2>Log In</h2>
        <div className="form-group">
          <label htmlFor="username">Username</label>
          <input type="text" id="username" name="username" value={credentials.username} onChange={handleInputChange} />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input type="password" id="password" name="password" value={credentials.password} onChange={handleInputChange} />
        </div>
        {loginError && <div className="login-error">{loginError}</div>}
        <button type="submit" className="login-button">Log In</button>
      </form>
    </div>
  );
}

export default LoginForm;
