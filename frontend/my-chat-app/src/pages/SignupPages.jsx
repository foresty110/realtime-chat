import { useNavigate } from "react-router-dom";
import { useState } from "react";
//회원가입 api 가져오기
import { signupApi } from "../api/authApi";
export default function SignupPages() {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    email: "",
    password: "",
    password2: "",
  });

  async function handleSubmit(e) {
    //새로고침 방지 
    e.preventDefault();

    try{
        //기다려 singapi가 백엔드로 넘어갈거야 이때 form을 가지고
        await signupApi(form); //await-async api호출 후 기다리는 동안 시간이 걸릴건데 그동안 다른걸 하고 있다가 호출해달라고 요청
    }catch{
        alert("회원가입 실패");
    }
    alert("회원가입이 정상적으로 되었습니다.");

    //메인 페이지로 이동
    navigate("/");
  }

  return (
    <div className="flex justify-center items-center min-h-screen bg-gray-100">
      <form
        onSubmit={handleSubmit}
        className="bg-white shadow-lg p-8 rounded-2xl w-80 space-y-4"
      >
        <h1 className="text-center text-2xl font-bold">회원가입</h1>

        <input
          className="border px-3 py-2 w-full rounded"
          placeholder="이메일"
          onChange={(e) => setForm({ ...form, email: e.target.value })}
        />

        <input
          className="border px-3 py-2 w-full rounded"
          placeholder="비밀번호"
          type="password"
          onChange={(e) => setForm({ ...form, password: e.target.value })}
        />

        <input
          className="border px-3 py-2 w-full rounded"
          placeholder="비밀번호 확인"
          type="password"
          onChange={(e) => setForm({ ...form, password2: e.target.value })}
        />

        <button className="w-full bg-blue-600 text-white py-2 rounded-xl">
          가입하기
        </button>
      </form>
    </div>
  );
}

