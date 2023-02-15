import React, { useEffect } from 'react';
// import { Link, useNavigate } from 'react-router-dom';
import { setCookie } from '../config/Cookie';

function KaKaoLogin() {
  // const navigate = useNavigate();

  const getUrlParameter = (name) => {
    // 쿼리 파라미터에서 값을 추출해주는 함수
    let search = window.location.search;
    let params = new URLSearchParams(search);
    return params.get(name);
  };

  useEffect(() => {
    const token = getUrlParameter('accessToken');
    const { accessToken } = token;

    setCookie('accessToken', accessToken, {
      path: '/',
      secure: true,
      // httpOnly: true,
      sameSite: 'none',
    });

    // console.log('토큰 파싱' + token);

    if (token) {
      // console.log('로컬 스토리지에 토큰 저장 ' + token);
      // navigate(`/home`, {
      //   replace: true,
      // });
      window.location.replace('/home');
    } else {
      // return <Link to={'/'}></Link>;
      // navigate(`/`, {
      //   replace: true,
      // });
      window.location.replace('/');
    }
  }, []);
}

export default KaKaoLogin;
