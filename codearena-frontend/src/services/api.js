import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080/api/v1/'
});

api.interceptors.request.use((config) => {
    const token = localStorage.getItem('user_token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});

api.interceptors.response.use(
    (response) => response,
    (error) => {
        // If the backend says 401 (Unauthorized/Expired Token)
        if (error.response && error.response.status === 401) {
            console.warn("Token expired or invalid. Logging out...");
            
            // Clear the "Passport"
            localStorage.removeItem('user_token');
            
            // Redirect to Login (Hard refresh ensures app state is cleared)
            window.location.href = '/login'; 
        }
        return Promise.reject(error);
    }
);

export default api;