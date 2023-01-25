import axios, { AxiosInstance } from "axios";
import cookies from "js-cookie";

const axios_api = axios.create({
  baseURL: process.env.REACT_APP_API_BASE_URL,
  headers: {
    access_token: cookies.get("access_token"),
  },
});

export default axios_api;
