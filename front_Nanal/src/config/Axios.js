import axios, { AxiosInstance } from "axios";
import cookies from "js-cookie";

const axios_api = axios.create({
  baseURL: "http://192.168.100.93:8080/nanal/",
  headers: {
    access_token: cookies.get("access_token"),
  },
});

export default axios_api;
