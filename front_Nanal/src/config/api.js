import axios, { AxiosInstance } from "axios";
import cookies from "js-cookie";

const axios_api = axios.create({
  baseURL: "https://loaclhost:8080/nanal/",
  headers: {
    access_token: cookies.get("access_token"),
  },
});

export default axios_api;
