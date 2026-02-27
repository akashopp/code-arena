import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import api from '../services/api'; 

const Login = () => {
  const { login } = useAuth();
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    try {
      const response = await api.post('/auth/login', { email, password });
      console.log("Full Backend Response:", response.data);
      const token = response.data.data?.token;

      if (token) {
        localStorage.setItem('user_token', token);
        console.log("Token saved to storage:", token);
        login({ email: email }, token);
        navigate('/');
      } else {
        setError("Login succeeded but no token was received.");
      }

    } catch (err) {
      console.error("Login request failed:", err);
      setError(err.response?.data?.message || "Login Failed");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-slate-100">
      <div className="bg-white p-8 rounded-xl shadow-lg w-full max-w-sm">
        <h2 className="text-2xl font-bold text-center mb-6">Welcome Back</h2>
        
        {/* Show Error Message if exists */}
        {error && <div className="p-3 mb-4 text-sm text-red-700 bg-red-100 rounded">{error}</div>}

        <form onSubmit={handleSubmit} className="space-y-4">
          <input 
            type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} 
            className="p-3 border rounded w-full" required 
          />
          <input 
            type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} 
            className="p-3 border rounded w-full" required 
          />
          <button type="submit" className="w-full bg-slate-900 text-white py-3 rounded hover:bg-slate-800">Login</button>
        </form>
        <p className="mt-4 text-center">
          New here? <Link to="/register" className="text-blue-600 font-bold">Register</Link>
        </p>
      </div>
    </div>
  );
};

export default Login;