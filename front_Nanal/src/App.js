import "./App.css";
import React, { useState } from "react";
import Nav from "./main/Nav.js";
import AppMain from "./main/AppMain.js";

function App() {
  const [isCalendaar, setIsCalendaar] = useState(true);
  
  return (

    <div className="App max-w-sm justify-center">
      <Nav setIsCalendaar={setIsCalendaar}/>
      <AppMain isCalendaar={isCalendaar}/>
    </div>

  );
}

export default App;
