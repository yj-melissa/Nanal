import { useState, useRef, useEffect } from 'react';
import bell from '../src_assets/img/bell.svg';
import logo from '../src_assets/img/home-alt.svg';
import Tuning from '../webComponents/setting/Tuning'
import AlarmList from '../webComponents/another/AlarmList'

function Nav() {
  const userProfile = {
    'days' : window.localStorage.getItem('profileDays'),
    'img': window.localStorage.getItem('profileImg'),
    'introduction': window.localStorage.getItem('profileIntroduction'),
    'nickname': window.localStorage.getItem('profileNickname')
  }
  let menuRef = useRef(null)
  const [isToggle, setIsToggle] = useState(false)
  const toggleProfileMenu = () => {
    if (isAlarmToggle === true) {
      setIsAlarmToggle(false)
    }
    setIsToggle(isToggle => !isToggle)
  }
  useEffect(() => {
    function handleOutside(e) {
      // current.contains(e.target) : 컴포넌트 특정 영역 외 클릭 감지를 위해 사용
      if (menuRef.current && !menuRef.current.contains(e.target)) {
        setIsToggle(false);
        console.log("profile===ref");
      }
    }
    document.addEventListener("mousedown", handleOutside);
    return () => {
      document.removeEventListener("mousedown", handleOutside);
    };
  },[menuRef])

  let alarmRef = useRef(null)
  const [isAlarmToggle, setIsAlarmToggle] = useState(false)
  const toggleAlarmMenu = () => {
    if (isToggle === true) {
      setIsToggle(false)
    }
    setIsAlarmToggle(isAlarmToggle => !isAlarmToggle)
  }
  useEffect(() => {
    function handleOutside(e) {
      // current.contains(e.target) : 컴포넌트 특정 영역 외 클릭 감지를 위해 사용
      if (alarmRef.current && !alarmRef.current.contains(e.target)) {
        setIsAlarmToggle(false);
        console.log("alarm===ref");
      }
    }
    document.addEventListener("mousedown", handleOutside);
    return () => {
      document.removeEventListener("mousedown", handleOutside);
    };
  },[alarmRef])
  // console.log(isAlarmToggle) 

  return (
    <div>
      <nav className='flex justify-between space-x-4 m-auto'>
        <div
          className='rounded-lg w-8 h-8 pr-1 py-1 my-1 text-slate-700 font-medium hover:bg-slate-100 hover:text-slate-900'
        >
          <img src={logo} alt='logo'/>
        </div>
        <div className='m-auto'>
          <p>나날과 함께한지 {userProfile.days}일 째 되는 날입니다.</p>
        </div>
        <div className='flex items-center m-auto'>
          {/* Alarm */}
          <div ref={alarmRef} onClick={toggleAlarmMenu}>
            <img src={bell} className='w-6 h-6 my-3' alt='bell'/>
          </div>
          {isAlarmToggle && <div ref={alarmRef} className='absolute right-40 inset-y-[48px] rounded-md box-border border border-black w-72 h-[500px] z-40 bg-slate-100 grid grid-cols-1 justify-items-center overflow-auto'>
            <AlarmList />
          </div>}
          
          {/* ProfileMenu */}
          <div ref={menuRef} className="rounded-full w-8 h-8 ml-5 overflow-hidden" onClick={toggleProfileMenu}>
            {userProfile.img !== null && <img src={userProfile.img} alt='usere-profile-img'/>}
          </div>
          {isToggle && <div ref={menuRef} className='absolute right-40 inset-y-[48px] rounded-md box-border border border-black w-72 h-[500px] z-40 bg-slate-100 grid grid-cols-1 justify-items-center'>
            <div className='rounded-full w-40 h-40 overflow-hidden my-5'>
              <img src={userProfile.img} alt='user-profile-img'/>
            </div>
            <p className='justify-self-start indent-[28px]'>닉네임 : {userProfile.nickname}</p>
            {userProfile.introduction === null ? <div className='truncate justify-self-start indent-[28px] w-60'>아직 작성된 소개글이 없습니다.</div>
            :<p className='truncate justify-self-start indent-[28px] w-60'>{userProfile.introduction}</p>}
            <Tuning />
          </div>
          }
        </div>
      </nav>
    </div>
  );
}

export default Nav;
