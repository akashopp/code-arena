import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import Navbar from './components/Navbar';

// Import Pages
import Home from './pages/Home'; // <--- Import the new file here
import Register from './pages/Register';
import Login from './pages/Login';
import ProblemSet from './pages/ProblemSet';
import TopicProblems from './pages/TopicProblems';
import ProblemDetail from './pages/ProblemDetail';

function App() {
  return (
    <AuthProvider>
      <Router>
        <div className="min-h-screen bg-slate-50">
          <Navbar />
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/register" element={<Register />} />
            <Route path="/login" element={<Login />} />
            <Route path="/problemset" element={<ProblemSet />} />
            <Route path="/problems/:topicSlug" element={<TopicProblems />} />
            <Route path="/problem/:problemSlug" element={<ProblemDetail />} />
          </Routes>
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;