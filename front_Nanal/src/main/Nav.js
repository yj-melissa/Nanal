import * as React from 'react';
import { useState } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import bell from '../src_assets/img/bell.svg';
import logo from '../src_assets/img/home-alt.svg';
import profile from '../src_assets/img/person_FILL0.png';
import close from '../src_assets/img/close.svg';
import downarrow from '../src_assets/img/arrow_drop_down.png';
import uparrow from '../src_assets/img/arrow_drop_up.png';

function Nav({ changeCalendaar }) {
  // useNavigate == 뒤로가기나 앞으로가기를 위한 react 내장 객체
  const navigate = useNavigate();
  // Home 의 정보를 알려줄 로케이션 변수 정의.
  // location.pathname === '/' 이 경우 현재 위치가 홈이다.
  const location = useLocation();
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
      <nav className='flex w-80 justify-between space-x-4 m-auto'>
        <div className='flex items-center'>
          <Link
            to='/'
            className='rounded-lg w-8 h-8 pr-1 py-1 my-1 text-slate-700 font-medium hover:bg-slate-100 hover:text-slate-900'
          >
            <img src={logo} />
          </Link>
          {location.pathname === '/' ? (
            <div>
              {isToggle ? (
                <div className='w-6 h-6 pt-[5px]'>
                  <img src={downarrow} onClick={() => toggleMenu()} />
                </div>
              ) : (
                <div>
                  <div className='w-6 h-6 pt-[5px]'>
                    <img src={uparrow} onClick={() => toggleMenu()} />
                  </div>
                  <div className='grid grid-cols-1 h-20 w-24 content-center absolute'>
                    <button
                      className='bg-slate-500 text-white p-1'
                      onClick={() => {
                        toggleMenu();
                        changeCalendar(true);
                      }}
                    >
                      캘린더
                    </button>
                    <button
                      className='bg-sky-700 text-white p-1'
                      onClick={() => {
                        toggleMenu();
                        changeCalendar(false);
                      }}
                    >
                      책장
                    </button>
                  </div>
                </div>
              )}
            </div>
          ) : null}
        </div>
        <div className='flex items-center m-auto'>
          <Link to='/Alarm'>
            <img src={bell} className='w-6 h-6 my-3' />
          </Link>
          {location.pathname === '/' ? (
            <Link to='/MyPage' className='ml-5 w-[36px]'>
              <img src={profile} className='w-[30px] h-[30px]' />
            </Link>
          ) : (
            <img
              src={close}
              className='w-9 h-9 ml-5'
              onClick={() => {
                navigate(-1);
              }}
            />
          )}
        </div>
      </nav>
      <hr className='mb-3 border-slate-500/75' />
    </div>
  );
}

export default Nav;
