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
      <Nav changeCalendaar={changeCalendaar} />
      <AppMain isCalendaar={isCalendaar} />
    </div>
  );
}

export default App;
