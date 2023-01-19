import * as React from "react";
import { useState } from "react";
import { Link } from "react-router-dom";
import logo from "../src_assets/home.png";
import downarrow from "../src_assets/down-arrow.png";
import uparrow from "../src_assets/up-arrow.png";

function Nav(props) {
  const [isToggle, setToggle] = useState(true);
  const toggleMenu = () => {
    setToggle((isToggle) => !isToggle);
  };

  return (
    <nav className="flex sm:justify-center space-x-4">
      {[[<img src={logo} />, "/"]].map(([title, url]) => (
        <Link
          to={url}
          className="rounded-lg px-3 py-2 text-slate-700 font-medium hover:bg-slate-100 hover:text-slate-900"
        >
          {title}
        </Link>
      ))}
      {isToggle ? (
        <img src={downarrow} onClick={() => toggleMenu()} />
      ) : (
        <img src={uparrow} onClick={() => toggleMenu()} />
      )}
      {[
        ["Team", "/team"],
        ["Projects", "/projects"],
        ["Reports", "/reports"],
      ].map(([title, url]) => (
        <Link
          to={url}
          className="rounded-lg px-3 py-2 text-slate-700 font-medium hover:bg-slate-100 hover:text-slate-900"
        >
          {title}
        </Link>
      ))}
    </nav>
  );
}

export default Nav;
