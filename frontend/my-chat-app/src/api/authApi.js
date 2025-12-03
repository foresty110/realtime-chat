
// 회원가입시 호출할 api를 작성!
import axiosInstance from "./axiosInstance";

export const signupApi = async (data) => {
  const response = await axiosInstance.post("/api/auth/signup", data);
  return response.data;
};

export const loginApi = async (data) => {
  const response = await axiosInstance.post("/api/auth/login", data);
  return response.data;  // tokenResponse
};
