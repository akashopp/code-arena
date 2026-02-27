import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { ArrowLeft, AlertCircle } from 'lucide-react';
// 1. Import your custom API instance
import api from '../services/api'; 

const TopicProblems = () => {
  const { topicSlug } = useParams(); 
  const [problems, setProblems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchProblems = async () => {
      try {
        setLoading(true);
        setError(null);

        // 2. Use api.get instead of fetch
        // The Base URL (http://localhost:8080/api/v1) is already in api.js
        const response = await api.get(`/problems/topic/${topicSlug}`);
        
        // 3. Handle Data (Axios returns the server response in response.data)
        const serverData = response.data;
        
        // Safety check: Backend might return [ ... ] or { data: [ ... ] }
        // We check both cases to be safe
        const problemsList = Array.isArray(serverData) 
          ? serverData 
          : (serverData.data || []);

        setProblems(problemsList);
        
      } catch (err) {
        console.error("Error fetching problems:", err);
        // 4. Improved Error Handling for Axios
        setError(
          err.response?.data?.message || 
          "Failed to load problems. Please check your connection."
        );
      } finally {
        setLoading(false);
      }
    };

    fetchProblems();
  }, [topicSlug]);

  const getDifficultyColor = (diff) => {
    const d = diff ? diff.toLowerCase() : 'easy';
    if (d === 'easy') return 'text-green-700 bg-green-100 border border-green-200';
    if (d === 'medium') return 'text-yellow-700 bg-yellow-100 border border-yellow-200';
    return 'text-red-700 bg-red-100 border border-red-200';
  };

  if (loading) return (
    <div className="min-h-screen flex items-center justify-center text-slate-500">
        <div className="animate-pulse">Loading problems...</div>
    </div>
  );

  if (error) return (
    <div className="min-h-screen flex flex-col items-center justify-center text-slate-600 bg-slate-50">
        <AlertCircle className="w-12 h-12 text-red-500 mb-4" />
        <h3 className="text-xl font-semibold text-slate-800">Oops!</h3>
        <p className="mb-6">{error}</p>
        <Link 
          to="/problemset" 
          className="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition"
        >
          Go Back to Topics
        </Link>
    </div>
  );

  return (
    <div className="min-h-screen bg-slate-50 p-6 md:p-10">
      <div className="max-w-5xl mx-auto bg-white rounded-xl shadow-sm border border-slate-200 p-8">
        
        {/* Header Section */}
        <div className="mb-8 border-b border-slate-100 pb-6">
          <Link to="/problemset" className="flex items-center text-slate-400 hover:text-blue-500 text-sm font-medium mb-4 transition w-fit">
            <ArrowLeft className="w-4 h-4 mr-1" /> Back to Topics
          </Link>
          <div className="flex items-center justify-between">
            <h1 className="text-3xl font-extrabold capitalize text-slate-800">
              {topicSlug.replace(/-/g, ' ')} <span className="text-blue-600">Problems</span>
            </h1>
            <span className="text-sm font-medium bg-slate-100 text-slate-600 px-3 py-1 rounded-full">
              {problems.length} Problems
            </span>
          </div>
        </div>

        {/* Problems List */}
        <div className="space-y-4">
            {problems.length === 0 ? (
                <div className="text-center py-12 bg-slate-50 rounded-lg border border-dashed border-slate-200">
                    <p className="text-slate-500 text-lg">No problems found for this topic yet.</p>
                    <p className="text-sm text-slate-400 mt-2">Check back later!</p>
                </div>
            ) : (
                problems.map((prob, index) => (
                    <div key={prob.id} className="group flex justify-between items-center p-4 border border-slate-100 rounded-lg hover:bg-blue-50 hover:border-blue-200 transition duration-200 bg-white shadow-sm">
                    
                        <Link to={`/problem/${prob.slug}`} className="flex-1 flex items-center text-lg font-medium text-slate-700 group-hover:text-blue-700">
                            <span className="w-10 text-slate-400 font-mono text-base">#{index + 1}</span> 
                            {prob.title} 
                        </Link>
                        
                        <span className={`px-3 py-1 rounded-full text-xs font-bold uppercase tracking-wider ${getDifficultyColor(prob.difficulty)}`}>
                            {prob.difficulty}
                        </span>
                    </div>
                ))
            )}
        </div>

      </div>
    </div>
  );
};

export default TopicProblems;