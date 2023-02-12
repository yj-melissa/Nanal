import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import axios_api from '../config/Axios';
import bell from '../src_assets/img/bell.svg';
import logo from '../src_assets/img/home-alt.svg';
import Tuning from '../webComponents/setting/Tuning'

function Nav() {
  const userProfile = {
    'days' : window.localStorage.getItem('profileDays'),
    'img': window.localStorage.getItem('profileImg'),
    'introduction': window.localStorage.getItem('profileIntroduction'),
    'nickname': window.localStorage.getItem('profileNickname')
  }
  const [isToggle, setIsToggle] = useState(false)
  const toggleProfileMenu = () => {
    setIsToggle(isToggle => !isToggle)
  }
  // console.log(isToggle) 

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
          <Link to='/Alarm'>
            <img src={bell} className='w-6 h-6 my-3' alt='bell'/>
          </Link>
          <div className="rounded-full w-8 h-8 ml-5 overflow-hidden" onClick={toggleProfileMenu}>
            {userProfile.img !== null && <img src={userProfile.img} alt='usere-profile-img'/>}
          </div>
          {isToggle && <div className='absolute right-40 inset-y-[48px] rounded-md box-border border border-black w-72 h-[500px] z-40 bg-slate-100 grid grid-cols-1 justify-items-center'>
            <div className='rounded-full w-40 h-40 overflow-hidden my-5'>
              <img src={userProfile.img} alt='user-profile-img'/>
            </div>
            <p className='justify-self-start indent-[28px]'>닉네임 : {userProfile.nickname}</p>
            {userProfile.introduction === null ? <div className='truncate justify-self-start indent-[28px] w-60'>아직 작성된 소개글이 없습니다.</div>
            :<p className='truncate justify-self-start indent-[28px] w-60'>{userProfile.introduction}</p>}
            <Tuning />
          </div>}
        </div>
      </nav>
    </div>
  );
}

export default Nav;
