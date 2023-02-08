import React, { useEffect, useRef, useState } from 'react';
import { useLocation, useNavigate } from 'react-router';
import axios_api from '../../config/Axios';
import { onLogin } from '../../config/Login';
import emo_joy from '../../src_assets/img/emo_joy.png';

function UserUpdate() {
  const { state } = useLocation();
  const navigate = useNavigate();

  const [profile, setProfile] = useState({});
  let currentName = useRef('');

  // 사용자 이미지 upload
  let inputRef = useRef();
  const formData = new FormData();
  const [isImgChecked, setIsImgChecked] = useState(false);

  // 사용자 이미지 기본으로 되돌리기
  const onUploadBaseImage = (e) => {
    e.preventDefault();
    setIsImgChecked(true);
  };

  const onUploadImage = (e) => {
    // e.preventDefault();
    setIsImgChecked(true);

    if (!e.target.files) {
      return;
    }

    // console.log(e.target.files[0]);
    formData.append('multipartFile', e.target.files[0]);
  };

  useEffect(() => {
    onLogin();
    axios_api
      .get('user/profile')
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === '회원 정보 조회 성공') {
            setProfile(data.data.profile);
          } else {
            console.log('회원 정보 조회 오류: ');
            console.log(data.statusCode);
            console.log(data.data.responseMessage);
          }
        }
      })
      .catch((error) => {
        console.log('회원 정보 조회 오류: ' + error);
      });
  }, []);

  return (
    <div id='user-Update'>
      <h2 className='my-1.5 text-lg font-bold text-center'>
        ✨ 프로필 수정 ✨
      </h2>
      <div>
        <form onSubmit={UserUpdate}>
          {/* <p className='my-2 text-center'>✨ 프로필 ✨</p> */}
          {/* <div id='user-name-div'>
            <label htmlFor='user-name'>💙 아이디 : </label>
            <span className='font-bold m-0.5'>사용자 아이디</span>
          </div> */}
          <div id='user-nickname-div'>
            <label htmlFor='user-nickname'>💙 닉네임 : </label>
            <input
              type='text'
              id='user-nickname'
              className='font-medium m-0.5 px-1 p-0.5 rounded-lg'
              defaultValue={profile.nickname || ''}
              onChange={(e) => (currentName.current = e.target.value)}
            ></input>
          </div>
          {/* <div id='user-password-div'>
            <label htmlFor='user-password'>💙 비밀번호 : </label>
            <input
              type='password'
              id='user-password'
              className='font-medium m-0.5'
              defaultValue={currentName.current || ''}
              onChange={(e) => (currentName.current = e.target.value)}
            ></input>
            <br />
            <label htmlFor='user-password-check'>💙 비밀번호 확인란 : </label>
            <input
              type='password'
              id='user-password-check'
              className='font-medium m-0.5'
              defaultValue={currentName.current || ''}
              onChange={(e) => (currentName.current = e.target.value)}
            ></input>
          </div> */}
          <div id='user-image' className='my-2'>
            <p>💙 프로필 이미지 </p>
            <div className='flex'>
              <img
                src={profile.img}
                className='inline-block w-24 h-24 p-1 mr-3 rounded-md'
              ></img>
              <div className='flex'>
                <p className='my-2'>
                  <input
                    type='file'
                    accept='image/*'
                    ref={inputRef}
                    onChange={onUploadImage}
                    className='inline-block w-full text-base text-slate-500 file:mr-4 file:py-2 file:px-4 file:rounded-full file:border-0 file:text-sm file:font-semibold file:bg-violet-100 file:text-violet-700 hover:file:bg-violet-200'
                  />
                  <button
                    type='button'
                    className='inline-block px-4 py-2 my-2 text-xs font-semibold border-0 rounded-full bg-violet-100 text-violet-500 hover:bg-violet-200'
                    onClick={onUploadBaseImage}
                  >
                    기본 이미지로 선택하기
                  </button>
                </p>
              </div>
            </div>
          </div>
          <div id='user-introduction-div'>
            <label htmlFor='user-name'>💙 프로필 소개글 : </label>
            <br />
            <textarea
              type='text'
              id='user-name'
              className='font-medium m-0.5 w-full h-28 rounded-md'
              defaultValue={currentName.current || ''}
              onChange={(e) => (currentName.current = e.target.value)}
            ></textarea>
          </div>

          <button
            type='submit'
            className='hover:bg-sky-700 bg-cyan-600 text-white px-2.5 py-1 rounded-3xl m-auto block'
          >
            수정하기
          </button>
        </form>
      </div>
    </div>
  );
}

export default UserUpdate;
