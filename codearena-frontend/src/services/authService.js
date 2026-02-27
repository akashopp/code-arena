import axios from 'axios';

const API_URL = 'http://localhost:8080/api/v1/';

export const login = async (username, password) => {
    const response = await axios.post(API_URL + '/auth/login', { username, password });
    if (response.data.token) {
        localStorage.setItem('user_token', response.data.token);
    }
    return response.data;
};

export const logout = () => {
    localStorage.removeItem('user_token');
};