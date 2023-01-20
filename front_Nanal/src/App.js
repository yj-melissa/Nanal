import "./App.css";
import React, { Component } from "react";
import { Routes, Route } from "react-router-dom";
import Nav from "./another/Nav.js";
import Calendar from "./main/Calendar.js";
import BookCase from "./main/BookCase.js";
import SignIn from "./account/SignIn.js";
import SignUp from "./account/SignUp.js";
import NotFound from "./NotFound.js";

function App() {
  const isCalendar = true;

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
        <Route path="/youshallnotpass" element={<SignIn />}></Route>
        <Route path="*" element={<NotFound />}></Route>
      </Routes>
    </div>
  );
}

export default App;
