import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
// 1. IMPORT YOUR CUSTOM API INSTANCE
import api from '../services/api'; 
import { Layers, Code, Database, Hash, Cpu, TreePine, ListEnd, Layers2} from 'lucide-react';

const iconMap = {
  "arrays": <Layers className="w-6 h-6"/>,
  "linked-list": <Code className="w-6 h-6"/>,
  "stack": <Layers2 className="w-6 h-6"/>,
  "queue": <ListEnd className="w-6 h-6"/>,
  "dp": <Hash className="w-6 h-6"/>,
  "trees": <TreePine className="w-6 h-6"/>,
  "default": <Cpu className="w-6 h-6"/>
};

const ProblemSet = () => {
  const [topics, setTopics] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchTopics = async () => {
      try {
        const response = await api.get('/topics/');
        console.log("Topics fetched:", response.data);
        setTopics(response.data.data); // Assuming your backend returns { data: [...] }
      } catch (error) {
        console.error("Error fetching topics:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchTopics();
  }, []);

  if (loading) return <div className="p-8 text-center">Loading topics...</div>;

  return (
    <div className="min-h-screen bg-slate-50 p-8">
      <div className="max-w-6xl mx-auto">
        <h1 className="text-4xl font-bold text-slate-800 mb-8">DSA Topics</h1>
        
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {topics.map((topic) => (
            <Link to={`/problems/${topic.slug}`} key={topic.id} className="group">
              <div className="bg-white p-6 rounded-xl shadow-sm hover:shadow-md transition border border-slate-200 hover:border-blue-400">
                <div className="flex items-center space-x-4 mb-4 text-blue-500 group-hover:text-blue-600">
                  {/* Handle icon mapping safely */}
                  {iconMap[topic.slug] || iconMap["default"]}
                  <h2 className="text-xl font-bold text-slate-700 group-hover:text-blue-600">
                    {topic.name}
                  </h2>
                </div>
                <p className="text-slate-500 text-sm">{topic.problemCount || 0} Problems</p>
              </div>
            </Link>
          ))}
        </div>
      </div>
    </div>
  );
};

export default ProblemSet;