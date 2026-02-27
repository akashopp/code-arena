import React, { useState } from 'react';
import Editor from '@monaco-editor/react';

// Default code templates so the user doesn't start empty
const BOILERPLATE = {
  java: `class Solution {\n    public int[] twoSum(int[] nums, int target) {\n        // Write your code here\n        return new int[]{};\n    }\n}`,
  python: `class Solution:\n    def twoSum(self, nums: List[int], target: int) -> List[int]:\n        # Write your code here\n        pass`,
  cpp: `class Solution {\npublic:\n    vector<int> twoSum(vector<int>& nums, int target) {\n        // Write your code here\n        return {};\n    }\n};`
};

const CodeEditor = ({ onSubmit }) => {
  const [language, setLanguage] = useState("java");
  const [code, setCode] = useState(BOILERPLATE["java"]);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleLanguageChange = (e) => {
    const lang = e.target.value;
    setLanguage(lang);
    setCode(BOILERPLATE[lang]);
  };

  const handleSubmit = async () => {
    setIsSubmitting(true);
    await onSubmit(code, language);
    setIsSubmitting(false);
  };

  return (
    <div className="flex flex-col h-full border border-slate-200 rounded-xl overflow-hidden shadow-lg bg-white">
      
      {/* Toolbar */}
      <div className="bg-slate-100 p-3 flex justify-between items-center border-b border-slate-200">
        <div className="flex items-center space-x-4">
          <span className="text-sm font-bold text-slate-600">Language:</span>
          <select 
            value={language} 
            onChange={handleLanguageChange}
            className="p-1 border rounded text-sm bg-white focus:ring-2 focus:ring-blue-500 outline-none"
          >
            <option value="java">Java</option>
            <option value="python">Python</option>
            <option value="cpp">C++</option>
          </select>
        </div>
      </div>

      {/* Monaco Editor */}
      <div className="flex-grow min-h-[400px]">
        <Editor
          height="100%"
          language={language === "cpp" ? "cpp" : language} // Monaco uses "cpp" for C++
          theme="vs-dark" // or "vs-dark"
          value={code}
          onChange={(value) => setCode(value)}
          options={{
            minimap: { enabled: false },
            fontSize: 14,
            scrollBeyondLastLine: false,
            automaticLayout: true,
          }}
        />
      </div>

      {/* Footer / Actions */}
      <div className="bg-slate-50 p-4 border-t border-slate-200 flex justify-end">
        <button 
          onClick={handleSubmit}
          disabled={isSubmitting}
          className={`px-6 py-2 rounded-lg font-bold text-white transition ${
            isSubmitting ? 'bg-gray-400 cursor-not-allowed' : 'bg-green-600 hover:bg-green-700'
          }`}
        >
          {isSubmitting ? "Submitting..." : "Submit Code"}
        </button>
      </div>
    </div>
  );
};

export default CodeEditor;