import "./App.css";
import React from "react";
import { Routes, Route } from "react-router-dom";
import Nav from "./another/Nav.js";
import Calendar from "./main/Calendar.js";
import BookCase from "./main/BookCase.js";
import SignIn from "./account/SignIn.js";
import SignUp from "./account/SignUp.js";
import NotFound from "./another/NotFound.js";
import DiaryCreate from "./diary/DiaryCreate";

function App() {
  const isCalendar = true;

  return (
    <div className="App max-w-sm justify-center">
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
    </div>
  );
}

export default App;
