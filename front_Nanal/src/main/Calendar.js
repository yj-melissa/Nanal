import React from "react";
import { Link } from "react-router-dom";

function Calendar() {
  return (
    <div>
      캘린더 페이지 입니다.
      <Link to="/SignIn"> 로그인 </Link>
      {/* 일기쓰기 테스트용 */}
      <Link to="/New"> 일기쓰기 </Link>
    </div>
  );
}

export default Calendar;
