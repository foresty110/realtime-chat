import { Link, useLocation } from "react-router-dom";


export default function Header() {
  //const location = useLocation();
  //const token = getToken();

  // 로그인 · 회원가입 화면에서는 헤더 숨김
  if (location.pathname === "/" || location.pathname === "/signup") {
    return null;
  }

  const handleLogout = () => {
    removeToken();
    window.location.href = "/";
  };

  return (
    <header className="w-full bg-blue-600 text-white px-6 py-3 flex justify-between items-center shadow">
      {/* 왼쪽 로고 */}
      <Link to="/rooms" className="text-xl font-bold flex items-center gap-2">
        <span className="text-white">💬</span>
        <span>ChatOn</span>
      </Link>

      {/* 오른쪽 메뉴 */}
      <nav className="flex items-center gap-6 text-sm font-medium">
        <Link to="/rooms" className="hover:underline">
          참가자 목록
        </Link>

        <Link to="/mypage" className="hover:underline">
          내 정보
        </Link>

        {/* 로그인 상태에 따라 표시 */}
        {token ? (
          <button
            onClick={handleLogout}
            className="border border-white px-3 py-1 rounded hover:bg-white hover:text-blue-600 transition"
          >
            로그아웃
          </button>
        ) : (
          <Link
            to="/"
            className="border border-white px-3 py-1 rounded hover:bg-white hover:text-blue-600 transition"
          >
            로그인
          </Link>
        )}
      </nav>
    </header>
  );
}
