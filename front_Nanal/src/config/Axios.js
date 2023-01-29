import axios from "axios";

const axios_api = axios.create({
  baseURL: process.env.REACT_APP_API_BASE_URL,
  headers: {},
});

export default axios_api;
