import './App.css';
import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Nav from './another/Nav.js'
import Calendar from './main/Calendar.js'
import SignIn from './account/SignIn.js'
import NotFound from './NotFound.js'

function App() {
  const isLogin = true

  return (
    <div className="App">
      <Nav/>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Calendar/>}></Route>
          <Route path="/youshallnotpass" element={<SignIn/>}></Route>
          <Route path="*" element={<NotFound />}></Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
