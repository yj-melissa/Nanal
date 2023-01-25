import * as React from "react";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import logo from "../src_assets/img/home.png";
import profile from "../src_assets/img/profile.png";
import close from "../src_assets/img/close.png";
import downarrow from "../src_assets/img/down-arrow.png";
import uparrow from "../src_assets/img/up-arrow.png";

function Nav() {
  // useNavigate == 뒤로가기나 앞으로가기를 위한 react 내장 객체
  const navigate = useNavigate();

  const [isToggle, setToggle] = useState(true);
  const toggleMenu = () => {
    setToggle((isToggle) => !isToggle);
  };
  const [isToggle2, setToggle2] = useState(true);
  const toggle2Menu = () => {
    setToggle2((isToggle2) => !isToggle2);
  };

  return (
    <nav className="flex max-w-sm justify-between space-x-4">
      <div className="flex items-center">
        {/* {[[<img src={logo} />, "/"]].map(([title, url]) => (
          <Link
            to={url}
            className="rounded-lg px-3 py-2 text-slate-700 font-medium hover:bg-slate-100 hover:text-slate-900"
          >
            {title}
          </Link>
        ))} */}
        <Link
          to="/"
          className="rounded-lg px-3 py-2 text-slate-700 font-medium hover:bg-slate-100 hover:text-slate-900"
        >
          <img src={logo} />
        </Link>
        {isToggle ? (
          <div className="w-4 h-4">
            <img src={downarrow} onClick={() => toggleMenu()} />
          </div>
        ) : (
          <div className="w-4 h-4">
            <img src={uparrow} onClick={() => toggleMenu()} />
          </div>
        )}
      </div>
      {isToggle2 ? (
        <Link to="/MyPage" className="w-6 h-6 my-3">
          <img src={profile} onClick={() => toggle2Menu()} />
        </Link>
      ) : (
        <div className="w-6 h-6 my-3">
          <img
            src={close}
            onClick={() => {
              navigate(-1);
              toggle2Menu();
            }}
          />
        </div>
      )}
    </nav>
  );
}

export default Nav;
