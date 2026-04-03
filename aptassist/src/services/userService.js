import axios from "axios";

const API = "http://localhost:7001/admin";

export const getUser = (userId) => {
  return axios.get(`${API}/getUser?userId=${userId}`);
};

export const addUser = (user) => {
  return axios.post(`${API}/addUser`, user);
};