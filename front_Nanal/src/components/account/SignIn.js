import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import axios_api from '../../config/Axios';

function SignIn() {
  const [userId, setUserId] = useState('');
  const [userPw, setPw] = useState('');

  const onChangeId = (e) => {
    setUserId(e.target.value);
  };

  const onChangePw = (e) => {
    setPw(e.target.value);
  };

  const JWT_EXPIRY_TIME = 24 * 3600 * 1000; // 만료 시간 (24시간 밀리 초로 표현)

  const onLoginSuccess = (response) => {
    const { accessToken } = response.data;

    // accessToken 설정
    axios_api.defaults.headers.common[
      'Authorization'
    ] = `Bearer ${accessToken}`;

    // accessToken 만료하기 1분 전에 로그인 연장
    setTimeout(onSilentRefresh, JWT_EXPIRY_TIME - 60000);
  };

  const onSilentRefresh = () => {
    axios_api
      .post('/silent-refresh')
      .then(onLoginSuccess)
      .catch((error) => {
        // ... 로그인 실패 처리
      });
  };

  const SignIn = (e) => {
    e.preventDefault();
    axios_api
      .post('/user/login', {
        userId: userId,
        password: userPw,
      })
      .then(({ data }) => {
        console.log(data.statusCode);
        if (data.statusCode === 200) {
          if (data.data.ResponseMessage === '로그인 성공') {
            console.log(data.data.User);
            // window.location.replace("/");

            onLoginSuccess(data);
          }
        } else {
          console.log(data.statusCode);
          console.log(data.data.ResponseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('회원 가입 오류: ' + error);
      });
  };

  return (
    <div className='flex justify-center'>
      <div className='box-border p-4 w-80 border-[1px] border-gray-500 border-solid'>
        <h1 className='m-3'> SignIn to 나날</h1>
        <div id='sign-in-form'>
          <form action='' onSubmit={SignIn}>
            <div className='m-1'>
              <label htmlFor='user-id'>ID &nbsp;&nbsp;: </label>
              <input
                type='text'
                id='user-id'
                placeholder='아이디'
                onChange={onChangeId}
                value={userId}
                className='max-w-full'
              />
              <br />
            </div>
            <div className='m-1'>
              <label htmlFor='user-password'>Pw : </label>
              <input
                type='password'
                id='user-password'
                placeholder='비밀번호'
                onChange={onChangePw}
                value={userPw}
                className='max-w-full'
              />
            </div>
            <div className='m-1'>
              <button type='submit'>Sign In</button>{' '}
              <Link to='/SignUp'>SignUp</Link>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}

export default SignIn;
