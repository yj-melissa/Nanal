import "./App.css";
import React, { useReducer, useState } from "react";
import { Routes, Route } from "react-router-dom";
import Nav from "./another/Nav.js";
import Calendar from "./main/Calendaar.js";
import BookCase from "./main/BookCase.js";
import SignIn from "./account/SignIn.js";
import MyPage from "./mypage/MyPage.js";
import SignUp from "./account/SignUp.js";
import NotFound from "./another/NotFound.js";
import DiaryCreate from "./diary/DiaryCreate";
import DiaryUpdate from "./diary/DiaryUpdate";

const reducer = (state, action) => {
  let newState = [];
  switch (action.type) {
    case "INIT": {
      return action.data;
    }
    case "CREATE": {
      const newItem = {
        ...action.data,
      };
      newState = [newItem, ...state];
      break;
    }
    case "REMOVE": {
      newState = state.filter((it) => it.id !== action.targetId);
      break;
    }
    case "EDIT": {
      newState = state.map((it) =>
        it.id === action.data.id ? { ...action.data } : it
      );
      break;
    }
    default:
      return state;
  }

  return newState;
};

// 데이터와 함수 전달
export const DiaryStateContext = React.createContext();
export const DiaryDispatchContext = React.createContext();

const dummyData = [
  {
    id: 1,
    group: "private",
    content: "테스트용",
    date: 1674711350632,
  },
  {
    id: 2,
    group: "group",
    content: "테스트용22 글자잘리는지 확인해보자. 과연 정말로 될 것인가?",
    date: 1674711350635,
  },
  {
    id: 3,
    group: "private",
    content: "테스트용333",
    date: 1674711350732,
  },
];

function App() {
  const isCalendar = true;
  console.log(new Date().getTime());
  const [data, dispatch] = useReducer(reducer, dummyData);
  const dataId = useState(0);
  //CREAT
  const onCreate = (date, content, group) => {
    dispatch({
      type: "CREATE",
      data: {
        id: dataId.current,
        date: new Date(date).getTime(),
        content,
        group,
      },
    });
  };
  //REMOVE
  const onRemove = (targetId) => {
    dispatch({ type: "REMOVE", targetId });
  };
  //EDIT
  const onEdit = (targetId, date, content, group) => {
    dispatch({
      type: "EDIT",
      data: { id: targetId, date: new Date(date).getTime(), content, group },
    });
  };

  return (
    <DiaryStateContext.Provider value={data}>
      <DiaryDispatchContext.Provider value={{ onCreate, onRemove, onEdit }}>
        <div className="App max-w-sm justify-center">
          <Nav />
          <Routes>
            {isCalendar ? (
              <Route path="/" element={<Calendar />}></Route>
            ) : (
              <Route path="/" element={<BookCase />}></Route>
            )}
            <Route path="/MyPage" element={<MyPage />}></Route>
            <Route path="/SignUp" element={<SignUp />}></Route>
            <Route path="/SignIn" element={<SignIn />}></Route>
            <Route path="/New" element={<DiaryCreate />}></Route>
            <Route path="/Edit/:id" element={<DiaryUpdate />}></Route>
            <Route path="*" element={<NotFound />}></Route>
          </Routes>
        </div>
      </DiaryDispatchContext.Provider>
    </DiaryStateContext.Provider>
  );
}

export default App;
