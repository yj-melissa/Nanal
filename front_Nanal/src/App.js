import './App.css';
import React, { useState } from 'react';
import Nav from './main/Nav';
import AppMain from './main/AppMain.js';

function App() {
  const [isCalendaar, setIsCalendaar] = useState(true);
  const changeCalendaar = (e) => {
    setIsCalendaar(e);
  };
  return (
    <div className='App max-w-sm justify-center'>
      {/*만약 주소가 sign in, sign up이라면 nav가 안 보이게*/}
      <Nav changeCalendaar={changeCalendaar} />
      <AppMain isCalendaar={isCalendaar} />
    </div>
  );
}

export default App;
