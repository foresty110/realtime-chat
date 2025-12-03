import { useEffect, useId, useRef, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

export default function ChatRoom() {
  const navigate = useNavigate();
  const { roomId } = useParams();

  // 로그인한 사용자 ID를 가져오기 
  // 1) 스토리지에서 꺼내오기
  const userId = localStorage.getItem("userId");

  // 2) userId가 없는 경우 로그인 페이지로 이동한다.
  if (!userId) {
    navigate("/");
  }

  // 웹 소켓에 관한 변수들을 사용하기 
  // 3) 웹 소켓 저장
  const socketRef = useRef(null);

  // 4) 메시지 목록 
  const [messages, setMessages] = useState([]);

  // 5) 입력 상태 
  const [input, setInput] = useState("");

  // 6) 채팅방에 들어가면 처음 한번만 웹 소켓을 연결하겠습니다!
  useEffect(() => {

    // 7) 웹소켓 객체를 만든다. 
    const socket = new WebSocket("ws://localhost:8080/ws/chat");

    // 8) 연결된 객체 유지하겠다.
    socketRef.current = socket;

    // 9) 연결 시도 
    socket.onopen = () => {
      console.log("웹소켓 연결 성공!");

      //10) 메시지 보내기
      socket.send(
        JSON.stringify({
          type: "ENTER",  // 입장 이벤트 타입!
          roomId,
          userId,
          content: `${userId}번 유저가 입장했습니다.`,
        })
      );
    };

    // 16) 채팅방에있는 모든 사람에게 다시 메시지를 전송
    // 서버에서 메시지를 받을 때 
    socket.onmessage = (event) => {
      console.log(event);

      // 17) 문자 → 객체 변경
      const msg = JSON.parse(event.data);
      console.log("서버에서 메세지 받음:"+msg);
      // 18) 화면에 추가한다.
      setMessages((prev) => [...prev, msg]);
    };

    // 소켓 종료
    console.log("소켓 종료");
    return () => socket.close();

  },[roomId, userId]);

  //11) 연결이 되면 이제는 실제 메시지를 전송하겠다.
  const handleSend = () => {
   
    // 12) 입력값이 없으면 종료 
    if (!input.trim()) return;

    // 13) 입력값이 있으면 JSON으로 만든다.
    const msg = {
      type: "CHAT",
      roomId,
      userId,
      content: input,
    };
    console.log("userId: "+userId);
    // 14) 서버로 실제 전송 
    if (socketRef.current.readyState === WebSocket.OPEN) {
      socketRef.current.send(JSON.stringify(msg));
    } else {
      console.warn("소켓이 아직 열리지 않았거나 이미 닫혔습니다.");
    }  
    // 15) input태그 초기화
    setInput("");
  };

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
        </div>

        <div className="flex-1 overflow-y-auto px-4 py-3 space-y-3">
          <p className="text-gray-500 text-center mb-2">
            {/* 3) 몇번님 입장하였습니다! */}
            ★ {roomId}번 방 / {userId}번님 입장하였습니다.
          </p>

          {/* 19) 메시지를 화면에 출력하는 코드 */}
          {messages.map((m, i) => (
            <div key={i}>
              <b>{m.userId}번:</b> {m.content}
            </div>
          ))}
        </div>

        <div className="border-t p-4 flex items-center">

          <input
            className="flex-1 border rounded-lg px-4 py-2 text-sm shadow-sm focus:outline-blue-400"
            placeholder="메시지 입력"
            // 20) 키보드 이벤트 : 입력값 상태 저장
            value={input}
            onChange={(e) => setInput(e.target.value)}
          />

          {/* 21) 버튼 이벤트 추가  버튼을 클릭했을 때 실행 */}
          <button
            onClick={handleSend}
            className="ml-3 bg-blue-500 text-white w-10 h-10 rounded-full flex items-center justify-center hover:bg-blue-600"
          >
            전송
          </button>
        </div>
      </div>
    </div>
  );
}
