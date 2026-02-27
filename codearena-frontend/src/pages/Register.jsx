import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

const Register = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    firstName: '', lastName: '', email: '', phoneNumber: '', password: ''
  });

  const handleChange = (e) => setFormData({ ...formData, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // Ensure your Spring Boot app is running on port 8080
      const response = await fetch('http://localhost:8080/api/v1/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        alert("Registration Successful!");
        navigate('/login');
      } else {
        alert("Registration failed.");
      }
    } catch (error) {
      console.error("Error:", error);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-slate-100">
      <div className="bg-white p-8 rounded-xl shadow-lg w-full max-w-md">
        <h2 className="text-2xl font-bold text-center mb-6">Create Account</h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="grid grid-cols-2 gap-2">
            <input name="firstName" placeholder="First Name" onChange={handleChange} className="p-3 border rounded w-full" required />
            <input name="lastName" placeholder="Last Name" onChange={handleChange} className="p-3 border rounded w-full" required />
          </div>
          <input name="email" type="email" placeholder="Email" onChange={handleChange} className="p-3 border rounded w-full" required />
          <input name="phoneNumber" placeholder="Phone" onChange={handleChange} className="p-3 border rounded w-full" />
          <input name="password" type="password" placeholder="Password" onChange={handleChange} className="p-3 border rounded w-full" required />
          <button type="submit" className="w-full bg-blue-600 text-white py-3 rounded hover:bg-blue-700">Register</button>
        </form>
        <p className="mt-4 text-center">
          Already have an account? <Link to="/login" className="text-blue-600 font-bold">Login</Link>
        </p>
      </div>
    </div>
  );
};

export default Register;