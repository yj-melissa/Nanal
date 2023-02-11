import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import axios_api from '../config/Axios';
import bell from '../src_assets/img/bell.svg';
import logo from '../src_assets/img/home-alt.svg';

function Nav() {
  const [userProfile, setUserProfile] = useState({
    days: 1,
    img: null,
    introduction: null,
    nickname: '',
  });

  useEffect(() => {
    axios_api
      .get('user/profile')
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === '회원 정보 조회 성공') {
            // console.log(data.data.profile);
            setUserProfile(data.data.profile);
          }
        } else {
          console.log('회원 정보 오류: ');
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('회원 정보 조회: ' + error);
      });
  }, [])

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
          <div className="rounded-full w-8 h-8 ml-5 overflow-auto">
            <img src={userProfile.img}/>
          </div>
        </div>
      </nav>
    </div>
  );
}

export default Nav;
