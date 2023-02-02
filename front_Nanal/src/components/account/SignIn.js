import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import axios_api from '../../config/Axios';
import { setCookie } from '../../config/Cookie';

function SignIn() {
  const [userId, setUserId] = useState('');
  const [userPw, setPw] = useState('');

  const onChangeId = (e) => {
    setUserId(e.target.value);
  };

  const onChangePw = (e) => {
    setPw(e.target.value);
  };

  function onLoginSuccess(data) {
    const { accessToken } = data;

    setCookie('accessToken', accessToken, {
      path: '/',
      secure: true,
      // httpOnly: true,
      sameSite: 'none',
    });
  }

  const SignIn = (e) => {
    e.preventDefault();
    axios_api
      .post('/user/login', {
        userId: userId,
        password: userPw,
      })
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === '로그인 성공') {
            // console.log(data.data.token);
            onLoginSuccess(data.data.token);
            window.location.replace('/');
          }
        } else if (data.statusCode === 500) {
          if (data.data.responseMessage === '로그인 실패') {
            alert('아이디랑 비밀번호를 다시 확인해주세요.');
            setUserId('');
            setPw('');
          } else if (data.data.responseMessage === '회원을 찾을 수 없음') {
            alert('회원이 아닙니다. 회원 가입을 해주세요.');
            setUserId('');
            setPw('');
          }
        } else {
          console.log('로그인 오류: ');
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('로그인 오류: ' + error);
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
