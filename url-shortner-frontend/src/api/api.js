// import axios from "axios";

// export default axios.create({
//     baseURL: import.meta.env.VITE_BACKEND_URL,
// });
import axios from "axios";

const api = axios.create({
  baseURL: import.meta.env.VITE_BACKEND_URL,
  headers: {
    "Content-Type": "application/json"
  }
});

// OPTIONAL: attach JWT ONLY for protected APIs
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");

  // attach token only for secured endpoints
  if (token && config.url.startsWith("/api/urls")) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

export default api;
