import * as React from 'react';
import { useState } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import bell from '../src_assets/img/bell.svg';
import logo from '../src_assets/img/home-alt.svg';
// import downarrow from '../src_assets/img/arrow_drop_down.png';
// import uparrow from '../src_assets/img/arrow_drop_up.png';

function Nav({ changeCalendaar }) {
  // useNavigate == 뒤로가기나 앞으로가기를 위한 react 내장 객체
  // const navigate = useNavigate();
  // Home 의 정보를 알려줄 로케이션 변수 정의.
  // location.pathname === '/' 이 경우 현재 위치가 홈이다.
  // const location = useLocation();
  // Home 옆의 화살표 토글
  const [isToggle, setToggle] = useState(true);
  const toggleMenu = () => {
    setToggle((isToggle) => !isToggle);
  };
  const changeCalendar = (e) => {
    changeCalendaar(e);
  };
  return (
    <div>
      <nav className='flex justify-between space-x-4 m-auto'>
        <div
          className='rounded-lg w-8 h-8 pr-1 py-1 my-1 text-slate-700 font-medium hover:bg-slate-100 hover:text-slate-900'
        >
          <img src={logo} />
        </div>
        <div>
          <p>search bar 들어올 자리</p>
        </div>
        <div className='flex items-center m-auto'>
          <Link to='/Alarm'>
            <img src={bell} className='w-6 h-6 my-3' />
          </Link>
          <div className="rounded-full w-6 h-6 ml-5">
            <img src={bell} />
          </div>
        </div>
      </nav>
    </div>
  );
}

export default Nav;
