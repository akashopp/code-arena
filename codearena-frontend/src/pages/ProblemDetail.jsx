import React, { useEffect, useState, useRef } from 'react';
import { useParams, Link } from 'react-router-dom';
import CodeEditor from '../components/CodeEditor'; 
import { useAuth } from '../context/AuthContext';
// 1. Import your custom API instance
import api from '../services/api';

const ProblemDetail = () => {
  const { problemSlug } = useParams();
  const { user } = useAuth();
  
  const [problem, setProblem] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  
  // State to store status AND output
  const [submissionResult, setSubmissionResult] = useState(null);

  // Ref to store the polling interval ID so we can clear it on unmount
  const pollingIntervalRef = useRef(null);

  // Cleanup polling on unmount
  useEffect(() => {
    return () => {
      if (pollingIntervalRef.current) clearInterval(pollingIntervalRef.current);
    };
  }, []);

  useEffect(() => {
    const fetchProblemDetails = async () => {
      try {
        setLoading(true);
        // 2. Use api.get instead of fetch
        const response = await api.get(`/problems/${problemSlug}`);
        
        // Axios returns the parsed JSON in response.data
        setProblem(response.data.data); 
      } catch (err) {
        console.error("Error fetching problem details:", err);
        setError(err.response?.data?.message || "Failed to load problem");
      } finally {
        setLoading(false);
      }
    };
    fetchProblemDetails();
  }, [problemSlug]);

  const handleCodeSubmit = async (code, language) => {
    // Optional: Block submission if not logged in
    // if (!user) { alert("Please login to submit"); return; }

    try {
      setSubmissionResult({ status: 'PENDING', message: 'Submitting...', output: 'Waiting for server...' });
      
      const payload = {
          code: code,
          language: language,
          problemId: problem.id,
          userId: user?.id || 1 // Fallback or handle auth check
      };

      // 3. Use api.post for submission
      const response = await api.post("/submissions/submit-code", payload);

      // Axios throws error automatically on 4xx/5xx, so we assume success here
      const submissionId = response.data.data.id; 
      pollSubmissionStatus(submissionId);

    } catch (err) {
        console.error("Error submitting code:", err);
        setSubmissionResult({ 
            status: 'ERROR', 
            message: 'Failed to submit', 
            output: err.response?.data?.message || err.message 
        });
    }
  };

  const pollSubmissionStatus = (id) => {
    // Clear any existing interval
    if (pollingIntervalRef.current) clearInterval(pollingIntervalRef.current);

    pollingIntervalRef.current = setInterval(async () => {
      try {
        // 4. Use api.get for polling
        const response = await api.get(`/submissions/${id}`);
        const submission = response.data.data;

        console.log("Polling status:", submission.submissionStatus);

        if (submission.submissionStatus !== "PENDING") {
          // STOP Polling
          clearInterval(pollingIntervalRef.current);
          
          let statusMessage = "";
          let statusType = "ERROR";

          // Update UI with final result
          if (submission.submissionStatus === "ACCEPTED") {
            statusMessage = 'Accepted! 🎉';
            statusType = 'SUCCESS';
          } else if (submission.submissionStatus === "WRONG_ANSWER") {
            statusMessage = 'Wrong Answer ❌';
            statusType = 'ERROR';
          } else if (submission.submissionStatus === "COMPILE_ERROR") {
             statusMessage = 'Compilation Error ⚠️';
             statusType = 'ERROR';
          } else {
             statusMessage = `Error: ${submission.submissionStatus}`;
          }

          setSubmissionResult({ 
              status: statusType, 
              message: statusMessage, 
              output: submission.output 
          });
        }
      } catch (err) {
        clearInterval(pollingIntervalRef.current);
        console.error("Polling error", err);
        setSubmissionResult({ 
            status: 'ERROR', 
            message: 'Polling Failed', 
            output: "Lost connection to server." 
        });
      }
    }, 2000); 
  };

  const getDifficultyColor = (diff) => {
    const d = diff ? diff.toLowerCase() : 'easy';
    if (d === 'easy') return 'bg-green-100 text-green-800 border-green-200';
    if (d === 'medium') return 'bg-yellow-100 text-yellow-800 border-yellow-200';
    return 'bg-red-100 text-red-800 border-red-200';
  };

  if (loading) return <div className="min-h-screen flex items-center justify-center text-slate-500">Loading Problem...</div>;
  if (error || !problem) return <div className="min-h-screen flex items-center justify-center text-red-500">Error: {error}</div>;

  return (
    <div className="min-h-screen bg-slate-50 flex flex-col font-sans">
      
      <div className="flex-grow p-4 md:p-6 lg:p-8">
        <div className="max-w-[1920px] mx-auto grid grid-cols-1 lg:grid-cols-2 gap-6 h-[85vh]">
          
          {/* LEFT COLUMN: Problem Details */}
          <div className="bg-white rounded-xl shadow-sm border border-slate-200 flex flex-col h-full overflow-hidden">
            
            {/* Header */}
            <div className="p-6 border-b border-slate-100 bg-white z-10">
              <Link to={`/problems/${problem.topicSlug}`} className="text-slate-400 hover:text-blue-500 text-sm font-medium mb-3 inline-flex items-center gap-1 transition-colors">
                &larr; Back to Problems
              </Link>
              <div className="flex justify-between items-center mt-1">
                <h1 className="text-2xl font-bold text-slate-900 tracking-tight">{problem.title}</h1>
                <span className={`px-3 py-1 rounded-full text-xs font-bold uppercase tracking-wider border ${getDifficultyColor(problem.difficulty)}`}>
                  {problem.difficulty}
                </span>
              </div>
            </div>

            {/* Scrollable Content Area */}
            <div className="overflow-y-auto p-6 flex-grow custom-scrollbar">
                {/* Description */}
                <div className="prose prose-slate max-w-none mb-8">
                   <p className="text-slate-700 whitespace-pre-line leading-relaxed">{problem.description}</p>
                </div>

                {/* Examples / IO */}
                <div className="space-y-8">
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                        <div>
                            <h3 className="font-semibold text-slate-900 mb-2 text-sm uppercase tracking-wide">Input Format</h3>
                            <div className="bg-slate-50 p-4 rounded-lg border border-slate-200 text-sm text-slate-600 font-mono whitespace-pre-wrap shadow-sm">
                            {problem.inputFormat}
                            </div>
                        </div>
                        <div>
                            <h3 className="font-semibold text-slate-900 mb-2 text-sm uppercase tracking-wide">Output Format</h3>
                            <div className="bg-slate-50 p-4 rounded-lg border border-slate-200 text-sm text-slate-600 font-mono whitespace-pre-wrap shadow-sm">
                            {problem.outputFormat}
                            </div>
                        </div>
                    </div>
                
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                        <div>
                            <h3 className="font-semibold text-slate-900 mb-2 text-sm uppercase tracking-wide">Sample Input</h3>
                            <pre className="bg-slate-900 text-green-400 p-4 rounded-lg overflow-x-auto text-sm font-mono shadow-md border border-slate-800">
                                {problem.sampleInput}
                            </pre>
                        </div>
                        <div>
                            <h3 className="font-semibold text-slate-900 mb-2 text-sm uppercase tracking-wide">Sample Output</h3>
                            <pre className="bg-slate-900 text-green-400 p-4 rounded-lg overflow-x-auto text-sm font-mono shadow-md border border-slate-800">
                                {problem.sampleOutput}
                            </pre>
                        </div>
                    </div>

                    {problem.constraints && (
                        <div>
                            <h3 className="font-semibold text-slate-900 mb-2 text-sm uppercase tracking-wide">Constraints</h3>
                            <div className="bg-red-50 p-4 rounded-lg border border-red-100 text-sm text-red-700 font-mono whitespace-pre-wrap">
                            {problem.constraints}
                            </div>
                        </div>
                    )}
                </div>
            </div>
          </div>

          {/* RIGHT COLUMN: Code Editor + Console */}
          <div className="flex flex-col h-full gap-4">
            
            {/* Editor Area */}
            <div className="bg-white rounded-xl shadow-sm border border-slate-200 overflow-hidden flex-grow flex flex-col min-h-0">
               <CodeEditor onSubmit={handleCodeSubmit} />
            </div>

            {/* Console / Output Section */}
            <div className="bg-slate-900 text-white p-4 rounded-xl font-mono text-sm h-48 flex flex-col shadow-lg border border-slate-800">
              <div className="flex justify-between items-center border-b border-slate-700 pb-2 mb-2">
                <span className="font-bold text-slate-400 tracking-wider text-xs uppercase">Console Output</span>
                {submissionResult && (
                   <div className="flex items-center gap-2">
                       {submissionResult.status === 'PENDING' && <span className="animate-pulse text-yellow-400">●</span>}
                       <span className={`font-bold ${submissionResult.status === 'SUCCESS' ? 'text-green-400' : submissionResult.status === 'PENDING' ? 'text-yellow-400' : 'text-red-400'}`}>
                         {submissionResult.message || submissionResult.status}
                       </span>
                   </div>
                )}
              </div>
              
              <div className="overflow-auto flex-grow custom-scrollbar">
                <pre className="whitespace-pre-wrap text-slate-300 font-mono text-xs leading-relaxed">
                  {submissionResult?.output || "Run your code to see the output here..."}
                </pre>
              </div>
            </div>

          </div>

        </div>
      </div>
    </div>
  );
};

export default ProblemDetail;