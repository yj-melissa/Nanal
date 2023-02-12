import * as React from 'react';
import { useState } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import bell from '../src_assets/img/bell.svg';
import logo from '../src_assets/img/home-alt.svg';
import profile from '../src_assets/img/person_FILL0.png';
import close from '../src_assets/img/close.svg';
import downarrow from '../src_assets/img/arrow_drop_down.png';
import uparrow from '../src_assets/img/arrow_drop_up.png';

function Nav({ changeIsBookCase }) {
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
  const changeBookCase = (e) => {
    changeIsBookCase(e);
  };
  return (
    <div>
      <nav className='flex justify-between m-auto space-x-4 w-80'>
        <div className='flex items-center'>
          <Link
            to='/home'
            className='w-8 h-8 py-1 my-1 font-medium rounded-lg text-slate-700 hover:bg-slate-100 hover:text-slate-900'
          >
            <img src={logo} />
          </Link>
          {location.pathname === '/home' ? (
            <div>
              {isToggle ? (
                <div className='w-3 h-3 mt-2'>
                  <img
                    src={downarrow}
                    onClick={() => toggleMenu()}
                    className='w-full h-full'
                  />
                </div>
              ) : (
                <div>
                  <div className='w-3 h-3 mt-2'>
                    <img
                      src={uparrow}
                      onClick={() => toggleMenu()}
                      className='w-full h-full'
                    />
                  </div>
                  <div className='absolute z-0 grid content-center w-24 h-20 grid-cols-1'>
                    <button
                      className='p-1 text-white bg-slate-500 '
                      onClick={() => {
                        toggleMenu();
                        changeBookCase(false);
                      }}
                    >
                      캘린더
                    </button>
                    <button
                      className='p-1 text-white bg-sky-700 '
                      onClick={() => {
                        toggleMenu();
                        changeBookCase(true);
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
          {location.pathname === '/home' ? (
            <Link to='/MyPage' className='ml-5 w-[36px]'>
              <img src={profile} className='w-[30px] h-[30px]' />
            </Link>
          ) : (
            <img
              src={close}
              className='ml-5 w-9 h-9'
              onClick={() => {
                navigate(-1);
              }}
            />
          )}
        </div>
      </nav>
    </div>
  );
}

export default Nav;
