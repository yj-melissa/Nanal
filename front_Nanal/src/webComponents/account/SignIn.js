import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import Swal from 'sweetalert2';
import axios_api from '../../config/Axios';
import { setCookie } from '../../config/Cookie';
import nanal from '../../src_assets/img/나날1.jpeg';

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
            console.log('로그인 완료, 토큰 출력');
            console.log(data.data.token);
            onLoginSuccess(data.data.token);
            window.location.replace('/home');
            // navigate(`/home`, {
            //   replace: true,
            // });
          }
        } else if (data.statusCode === 500) {
          if (data.data.responseMessage === '로그인 실패') {
            // alert('아이디 또는 비밀번호를 다시 확인해주세요.');
            Swal.fire({
              icon: 'warning',
              text: '아이디 또는 비밀번호를 다시 확인해주세요.',
              width: '30%',
            }).then(function () {
              setUserId('');
              setPw('');
            });
          } else if (data.data.responseMessage === '회원을 찾을 수 없음') {
            // alert('회원이 아닙니다. 회원 가입을 해주세요.');
            Swal.fire({
              icon: 'warning',
              text: '회원이 아닙니다. 회원 가입을 해주세요.',
              width: '30%',
            }).then(function () {
              setUserId('');
              setPw('');
            });
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
    <div className='grid mt-20 place-items-center h-96'>
      <div className='box-border p-4 w-auto border-[1px] border-gray-500 border-solid flex items-center justify-center'>
        <div className='mr-2 w-80'>
          <img src={nanal} alt='main_logo' className='h-50' />
        </div>
        <div>
          <h1 className='m-3 font-bold tracking-wider text-center'>
            SignIn to 나날🤗
          </h1>
          <div
            id='sign-in-form'
            className='flex items-center justify-center ml-2'
          >
            <form action='' onSubmit={SignIn}>
              <div className='m-1'>
                <label htmlFor='user-id' className='font-bold cursor-pointer'>
                  ID &nbsp;&nbsp;{' '}
                </label>
                <input
                  type='text'
                  id='user-id'
                  placeholder='아이디'
                  onChange={onChangeId}
                  value={userId}
                  className='w-full px-2 py-1 mb-2 text-sm border border-black border-solid rounded-lg'
                />
                <br />
              </div>
              <div className='m-1'>
                <label
                  htmlFor='user-password'
                  className='font-bold cursor-pointer'
                >
                  PW{' '}
                </label>
                <input
                  type='password'
                  id='user-password'
                  placeholder='비밀번호'
                  onChange={onChangePw}
                  value={userPw}
                  className='w-full px-2 py-1 mb-2 text-sm border border-black border-solid rounded-lg'
                />
              </div>
              <div className='mr-2 text-right'>
                <Link>
                  <span className='text-sm'>ID 찾기 | </span>
                </Link>
                <Link>
                  <span className='text-sm'>PW 찾기 | </span>
                </Link>
                <Link to='/signup'>
                  <span className='text-sm'>회원가입</span>
                </Link>
              </div>
              <div className='grid grid-cols-1 mt-2 just'>
                <button
                  type='submit'
                  className='inline-block px-20 py-1.5 mx-auto my-2 text-sm font-bold text-white whitespace-normal bg-rose-300 hover:bg-rose-400 rounded-lg'
                >
                  Sign In
                </button>

                <Link>
                  <button className='className=inline-block px-16 py-1.5 mx-auto my-2 text-sm font-bold text-white whitespace-normal bg-yellow-300 hover:bg-yellow-400 rounded-lg'>
                    카카오 로그인
                  </button>
                </Link>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default SignIn;
