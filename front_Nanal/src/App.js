import './App.css';
import React, { useState } from 'react';
import { getCookie } from './config/Cookie';
import Nav from './main/Nav';
import AppMain from './main/AppMain.js';

function App() {
  const accessToken = getCookie('accessToken');

  const [isCalendaar, setIsCalendaar] = useState(true);
  const changeCalendaar = (e) => {
    setIsCalendaar(e);
  };
  return (
    <div className='App max-w-sm justify-center'>
      {/*만약 주소가 sign in, sign up이라면 nav가 안 보이게*/}
      { accessToken !== undefined ? <Nav changeCalendaar={changeCalendaar} /> : null}
      <AppMain isCalendaar={isCalendaar} />
    </div>
  );
}

export default App;
