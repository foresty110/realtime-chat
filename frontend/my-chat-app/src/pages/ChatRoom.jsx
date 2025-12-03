import { useNavigate, useParams } from "react-router-dom";

export default function ChatRoom() {
  const navigate = useNavigate();
  const { roomId } = useParams();


  // 로그인한 사용자 ID를 가져오기 
  // 1) 스토리지에서 꺼내오기
  const userId = localStorage.getItem("userId");
  
  // 2) userId가 없는 경우 로그인 페이지로 이동한다.
  if(!userId) {
    navigate("/")
  }

  return (
    <div className="min-h-screen bg-[#e5e9ef] flex justify-center pt-20">
      <div className="w-[600px] h-[470px] bg-white rounded-xl shadow-xl flex flex-col">

        <div className="bg-[#1f3c6b] text-white px-4 py-3 rounded-t-xl flex items-center justify-between">
          <button onClick={() => navigate("/rooms")} className="text-xl mr-3">
            ←
          </button>

          <div className="flex items-center gap-2 flex-1">
            <span className="font-semibold">💬 ChatOn</span>
          </div>

          <div className="text-sm flex items-center gap-6">
            <span>프로트 개발</span>
            <span>나가기</span>
          </div>
        </div>

        <div className="flex-1 flex flex-col items-center justify-center text-center px-4">
          <p className="text-gray-500">2024년 7월 31일</p>
          <p className="text-gray-600 mt-2">
            ★ {roomId}번 방 / {userId}번님 입장하였습니다.
          </p>
        </div>

        <div className="border-t p-4 flex items-center">
         

          <input
            className="flex-1 border rounded-lg px-4 py-2 text-sm shadow-sm focus:outline-blue-400"
            placeholder="메시지 입력"
          />

          <button className="ml-3 bg-blue-500 text-white w-10 h-10 rounded-full flex items-center justify-center hover:bg-blue-600">
            전송
          </button>
        </div>
      </div>
    </div>
  );
}
