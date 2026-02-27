import React from 'react';
import { Link } from 'react-router-dom';
import { Code, Terminal, Cpu } from 'lucide-react';

const Home = () => {
  return (
    <div className="min-h-screen bg-slate-50 flex flex-col">
      {/* Hero Section */}
      <div className="bg-slate-900 text-white py-20 px-4">
        <div className="max-w-4xl mx-auto text-center">
          <h1 className="text-5xl md:text-6xl font-extrabold mb-6 tracking-tight">
            Master Data Structures <span className="text-blue-500">& Algorithms</span>
          </h1>
          <p className="text-xl text-gray-300 mb-10 max-w-2xl mx-auto">
            Join thousands of developers practicing coding problems daily on CodeArena. 
            Level up your coding skills for your next technical interview.
          </p>
          <div className="flex justify-center gap-4">
            <Link 
              to="/problemset" 
              className="bg-blue-600 hover:bg-blue-700 text-white px-8 py-3 rounded-lg font-bold text-lg transition shadow-lg hover:shadow-blue-500/30"
            >
              Start Solving
            </Link>
            <Link 
              to="/register" 
              className="bg-transparent border border-gray-600 hover:bg-gray-800 text-white px-8 py-3 rounded-lg font-bold text-lg transition"
            >
              Create Account
            </Link>
          </div>
        </div>
      </div>

      {/* Features Section */}
      <div className="py-16 px-4 max-w-6xl mx-auto grid md:grid-cols-3 gap-8">
        <div className="bg-white p-6 rounded-xl shadow-sm border border-slate-100 hover:shadow-md transition">
          <div className="bg-blue-100 w-12 h-12 rounded-lg flex items-center justify-center mb-4">
            <Code className="text-blue-600 w-6 h-6" />
          </div>
          <h3 className="text-xl font-bold text-slate-800 mb-2">Topic-wise Practice</h3>
          <p className="text-gray-600">
            Solve problems categorized by topics like Arrays, Linked Lists, Trees, and Graphs to build a strong foundation.
          </p>
        </div>

        <div className="bg-white p-6 rounded-xl shadow-sm border border-slate-100 hover:shadow-md transition">
          <div className="bg-purple-100 w-12 h-12 rounded-lg flex items-center justify-center mb-4">
            <Terminal className="text-purple-600 w-6 h-6" />
          </div>
          <h3 className="text-xl font-bold text-slate-800 mb-2">Online Compiler</h3>
          <p className="text-gray-600">
            Write and run your code instantly in our integrated development environment without any setup.
          </p>
        </div>

        <div className="bg-white p-6 rounded-xl shadow-sm border border-slate-100 hover:shadow-md transition">
          <div className="bg-green-100 w-12 h-12 rounded-lg flex items-center justify-center mb-4">
            <Cpu className="text-green-600 w-6 h-6" />
          </div>
          <h3 className="text-xl font-bold text-slate-800 mb-2">Performance Analysis</h3>
          <p className="text-gray-600">
            Get detailed insights into your code's time and space complexity to optimize your solutions.
          </p>
        </div>
      </div>
    </div>
  );
};

export default Home;