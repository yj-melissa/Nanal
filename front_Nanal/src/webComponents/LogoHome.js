import { useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { getCookie } from '../config/Cookie';
import nanal from '../src_assets/img/나날1.jpeg';

const LogoHome = () => {
  const navigate = useNavigate();

  useEffect(() => {
    // 토큰이 있는 상태에서 '/' 이 경로로
    const accessToken = getCookie('accessToken');
    if (accessToken !== undefined) {
      // window.location.replace('/home');
      navigate(`/home`, {
        replace: true,
      });
    }
  }, []);

  return (
    <div className='grid grid-cols-1 mt-20 place-items-center'>
      <img src={nanal} alt='logo' className='w-96 h-96' />
      <div className='my-4'>
        <Link to='/SignIn'>
          <button className='px-4 py-2 mx-10 text-base font-semibold border-0 rounded-full bg-violet-100 text-violet-500 hover:bg-violet-200'>
            로그인
          </button>
        </Link>
        <Link to='/SignUp'>
          <button className='px-4 py-2 mx-10 text-base font-semibold border-0 rounded-full bg-violet-100 text-violet-500 hover:bg-violet-200'>
            회원가입
          </button>
        </Link>
      </div>
    </div>
  );
};

export default LogoHome;
