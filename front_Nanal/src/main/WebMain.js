import { useState } from 'react';
import { Routes, Route } from 'react-router-dom';
import { getCookie } from '../config/Cookie';
import LogoHome from '../webComponents/LogoHome';
import MyDiary from '../webComponents/MyDiary';
import GroupDiary from '../webComponents/GroupDiary';
import FriendList from '../webComponents/FriendList';
import RecycleBin from '../webComponents/RecycleBin';
import SettingsNanal from '../webComponents/SettingsNanal';
import SignUp from '../webComponents/account/SignUp';
import SignIn from '../webComponents/account/SignIn';
import DiaryCreate from '../webComponents/diary/DiaryCreate';
import DiaryDetail from '../webComponents/diary/DiaryDetail';
import NotFound from '../webComponents/another/NotFound';
import bmkRR from '../src_assets/img/bookmark/bookmark-red-red.svg';
import bmkRW from '../src_assets/img/bookmark/bookmark-red-white.svg';
import bmkOO from '../src_assets/img/bookmark/bookmark-orange-orange.svg';
import bmkOW from '../src_assets/img/bookmark/bookmark-orange-white.svg';
import bmkYY from '../src_assets/img/bookmark/bookmark-yellow-yellow.svg';
import bmkYW from '../src_assets/img/bookmark/bookmark-yellow-white.svg';
import bmkGG from '../src_assets/img/bookmark/bookmark-green-green.svg';
import bmkGW from '../src_assets/img/bookmark/bookmark-green-white.svg';
import bmkBB from '../src_assets/img/bookmark/bookmark-blue-blue.svg';
import bmkBW from '../src_assets/img/bookmark/bookmark-blue-white.svg';

const AppMain = () => {
  const accessToken = getCookie('accessToken');
  const [homeState, setHomeState] = useState([
    true,
    false,
    false,
    false,
    false,
  ]);
  const changeHomeStateZero = () => {
    setHomeState([true, false, false, false, false]);
  };
  const changeHomeStateOne = () => {
    setHomeState([false, true, false, false, false]);
  };
  const changeHomeStateTwo = () => {
    setHomeState([false, false, true, false, false]);
  };
  const changeHomeStateThree = () => {
    setHomeState([false, false, false, true, false]);
  };
  const changeHomeStateFour = () => {
    setHomeState([false, false, false, false, true]);
  };

  return (
    <div>
      <Routes>
        <Route path='/' element={<LogoHome />}></Route>
        {homeState[0] === true ? (
          <Route path='/home' element={<MyDiary />}></Route>
        ) : homeState[1] === true ? (
          <Route path='/home' element={<GroupDiary />}></Route>
        ) : homeState[2] === true ? (
          <Route path='/home' element={<FriendList />}></Route>
        ) : homeState[3] === true ? (
          <Route path='/home' element={<SettingsNanal />}></Route>
        ) : homeState[4] === true ? (
          <Route path='/home' element={<RecycleBin />}></Route>
        ) : null}
        <Route path='/SignIn' element={<SignIn />}></Route>
        <Route path='/SignUp' element={<SignUp />}></Route>
        <Route path='/Diary/Create' element={<DiaryCreate />}></Route>
        <Route path='/Diary/Detail' element={<DiaryDetail />}></Route>
        <Route path='*' element={<NotFound />}></Route>
      </Routes>
      {accessToken !== undefined ? (
        <div className='w-[1440px]'>
          {homeState[0] === true ? (
            <img src={bmkRR} className='absolute z-0 right-[158px] top-20' />
          ) : (
            <div onClick={changeHomeStateZero}>
              <img src={bmkRW} className='absolute z-0 right-[158px] top-20' />
            </div>
          )}
          {homeState[1] === true ? (
            <img src={bmkOO} className='absolute z-0 right-[158px] top-40' />
          ) : (
            <div onClick={changeHomeStateOne}>
              <img src={bmkOW} className='absolute z-0 right-[158px] top-40' />
            </div>
          )}
          {homeState[2] === true ? (
            <img src={bmkYY} className='absolute z-0 right-[158px] top-60' />
          ) : (
            <div onClick={changeHomeStateTwo}>
              <img src={bmkYW} className='absolute z-0 right-[158px] top-60' />
            </div>
          )}
          {homeState[3] === true ? (
            <img src={bmkGG} className='absolute z-0 right-[158px] top-80' />
          ) : (
            <div onClick={changeHomeStateThree}>
              <img src={bmkGW} className='absolute z-0 right-[158px] top-80' />
            </div>
          )}
          {homeState[4] === true ? (
            <img
              src={bmkBB}
              className='absolute z-0 right-[158px] top-[400px]'
            />
          ) : (
            <div onClick={changeHomeStateFour}>
              <img
                src={bmkBW}
                className='absolute z-0 right-[158px] top-[400px]'
              />
            </div>
          )}
        </div>
      ) : null}
    </div>
  );
};

export default AppMain;
