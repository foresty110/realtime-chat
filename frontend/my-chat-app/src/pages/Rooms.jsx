import { Link } from "react-router-dom";

export default function Rooms() {
  const rooms = [
    { id: 1, name: "프론트엔드 개열" },
    { id: 2, name: "백엔드 개열" },
    { id: 3, name: "공지사항" },
  ];

  return (
    <div className="min-h-screen bg-gray-100 flex justify-center pt-10">
      <div className="bg-white w-[330px] h-[500px] rounded-xl shadow-lg p-6 flex flex-col gap-2">
        <h2 className="text-xl font-bold">채팅방</h2>

        {rooms.map((room) => (
          <Link
            key={room.id}
            to={`/chat/${room.id}`}
            className="block border p-3 rounded hover:bg-gray-50"
          >
            {room.name}
          </Link>
        ))}
      </div>
    </div>
  );
}
