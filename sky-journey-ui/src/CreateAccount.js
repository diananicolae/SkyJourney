import React, { useState } from "react";
import axios from "axios";
import "./CreateAccount.css";

function CreateAccount({ onLoginSuccess }) {
  const [accountCreationError, setAccountCreationError] = useState('');

  const [credentials, setCredentials] = useState({
    name: "",
    username: "",
    password: "",
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setCredentials({ ...credentials, [name]: value });
  };

  const handleCreateAccountSubmit = async (e) => {
    e.preventDefault();

    try {
      await axios.post(
        `${process.env.REACT_APP_AUTH_URL}/users/create-account`,
        credentials
      );

      const response = await axios.post(
        `${process.env.REACT_APP_AUTH_URL}/login`,
        credentials
      );
      localStorage.setItem("token", response.data.token);
      localStorage.setItem("userId", response.data.userId);
      localStorage.setItem("userName", response.data.name);
      window.location.href = "/";
      onLoginSuccess();
    } catch (error) {
      console.error("Account creation failed:", error);
      setAccountCreationError("Username already exists.");
    }
  };

  return (
    <div className="login-form-container">
      <form onSubmit={handleCreateAccountSubmit} className="login-form">
        <h2>Create Account</h2>
        <div className="form-group">
          <label htmlFor="name">Full Name</label>
          <input
            type="name"
            id="name"
            name="name"
            value={credentials.name}
            onChange={handleInputChange}
          />
        </div>

        <div className="form-group">
          <label htmlFor="username">Username</label>
          <input
            type="text"
            id="username"
            name="username"
            value={credentials.username}
            onChange={handleInputChange}
          />
        </div>

        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input
            type="password"
            id="password"
            name="password"
            value={credentials.password}
            onChange={handleInputChange}
          />
        </div>

        {accountCreationError && <div className="error-message">{accountCreationError}</div>}

        <button type="submit" className="login-button">
          Submit
        </button>
      </form>
    </div>
  );
}

export default CreateAccount;
