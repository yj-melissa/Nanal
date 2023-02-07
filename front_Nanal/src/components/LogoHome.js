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
    <div className='grid content-center'>
      <img src={nanal} className='w-60 h-60 place-self-center' />
      <Link
        to='/SignIn'
        className='px-4 py-2 mx-4 text-base font-semibold border-0 rounded-full place-self-center bg-violet-100 text-violet-700 hover:bg-violet-200'
      >
        로그인하러 가기
      </Link>
      <Link
        to='/SignUp'
        className='px-4 py-2 my-2 text-base font-semibold border-0 rounded-full place-self-center bg-violet-100 text-violet-500 hover:bg-violet-200'
      >
        회원가입하러 가기
      </Link>
    </div>
  );
};

export default LogoHome;
