import "./App.css";
import React, { useState, useRef } from "react";
import { Routes, Route } from "react-router-dom";
import Nav from "./another/Nav.js";
import Calendar from "./main/Calendar.js";
import BookCase from "./main/BookCase.js";
import SignIn from "./account/SignIn.js";
import SignUp from "./account/SignUp.js";
import NotFound from "./NotFound.js";
import DiaryCreate from "./diary/DiaryCreate";
import DiaryList from "./diary/DiaryList";
import DiaryEditor from "./components/DiaryEditor";

// 더미 데이터
// const dummyList = [
//   {
//     id: 1,
//     date: "",
//     content: "일기 1",
//     group: "private",
//     // 날짜는 숫자로 변환해서 받기
//     created_at: new Date().getTime(),
//   },
//   {
//     id: 2,
//     date: "",
//     content: "일기 2",
//     group: "group",
//     // 날짜는 숫자로 변환해서 받기
//     created_at: new Date().getTime(),
//   },
//   {
//     id: 3,
//     date: "",
//     content: "일기 3",
//     group: "private",
//     // 날짜는 숫자로 변환해서 받기
//     created_at: new Date().getTime(),
//   },
//   {
//     id: 4,
//     date: "",
//     content: "일기 4",
//     group: "group",
//     // 날짜는 숫자로 변환해서 받기
//     created_at: new Date().getTime(),
//   },
// ];

function App() {
  const isCalendar = true;
  // 일기 데이터는 부모 컴포넌트에서 관리 (데이터는 단방향으로 흐르기 떄문)
  const [data, setData] = useState([]);
  // id는 useRef로 생성
  const dataId = useRef(0);
  const onCreate = (content, group) => {
    const created_at = new Date().getTime();
    const newItem = {
      id: dataId.current,
      content,
      group,
      created_at,
    };
    dataId.current += 1;
    // 새로운 일기를 가장 위로
    setData([newItem, ...data]);
  };

  // 일기 삭제 함수 - id 다른 것들은 삭제에서 제외하여 새로운 배열로 표현
  const onRemove = (targetId) => {
    const newDiaryList = data.filter((it) => it.id !== targetId);
    setData(newDiaryList);
  };

  // 일기 수정 함수 - id 같은 것을 수정
  const onEdit = (targetId, newContent) => {
    setData(
      data.map((it) =>
        it.id === targetId ? { ...it, content: newContent } : it
      )
    );
  };

  return (
    <div className="App">
      <Nav />
      <Routes>
        {isCalendar ? (
          <Route path="/" element={<Calendar />}></Route>
        ) : (
          <Route path="/" element={<BookCase />}></Route>
        )}
        <Route path="/SignUp" element={<SignUp />}></Route>
        <Route path="/SignIn" element={<SignIn />}></Route>
        <Route path="/New" element={<DiaryCreate />}></Route>
        <Route path="*" element={<NotFound />}></Route>
      </Routes>
      <DiaryEditor onCreate={onCreate} />
      <DiaryList diaryList={data} onRemove={onRemove} onEdit={onEdit} />
    </div>
  );
}

export default App;
