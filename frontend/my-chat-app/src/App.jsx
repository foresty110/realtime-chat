import { Route, Routes } from "react-router-dom"
import LoginPages from "./pages/LoginPages"
import SignupPages from "./pages/SignupPages"
import Rooms from "./pages/Rooms"
import ChatRoom from "./pages/ChatRoom"
import Header from "./components/Header"

function App() {

  return (
    <>

    {/*맨 위에 공통적으로 헤더를 붙인다.*/}
    <Header />

      
      {/* url 경로에 따라서 페이지를 보여주겠습니다. */}
      <Routes>
        {/* 로그인 / 회원가입 페이지 경로설정 */}
        <Route path="/" element={<LoginPages />}></Route>
        <Route path="/signup" element={<SignupPages />}></Route>
        
        {/* 채팅방 목록  */}
        <Route path="/rooms" element={<Rooms />}></Route>
        
        {/* 특정 채팅방 */}
        <Route path="/chat/:roomId" element={<ChatRoom />}></Route>
      
      </Routes>
    </>
  )
}

export default App
