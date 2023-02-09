import React, { useEffect, useRef, useState } from 'react';
import { useLocation, useNavigate } from 'react-router';
import Swal from 'sweetalert2';
import axios_api from '../../../config/Axios';
import { onLogin } from '../../../config/Login';

function UserUpdate() {
  // const { state } = useLocation();
  const navigate = useNavigate();

  const [profile, setProfile] = useState({});
  let currentName = useRef('');
  let currentInfo = useRef('');

  // 사용자 닉네임
  const [nicknameMessage, setNicknameMessage] = useState('');
  const [isNickname, setIsNickname] = useState(true);

  const onChangeNickname = (e) => {
    currentName.current = e.target.value;

    if (currentName.current.length < 2 || currentName.current.length > 8) {
      setNicknameMessage('닉네임은 2글자 이상 8글자 이하로 입력해주세요!');
      setIsNickname(false);
    } else {
      setNicknameMessage('');
      setIsNickname(true);
    }
  };

  // 사용자 이미지 upload
  const formData = new FormData();
  const [imageFile, setImageFile] = useState(null);
  const [isImgChecked, setIsImgChecked] = useState(false);

  // 사용자 이미지 기본으로 되돌리기
  const onUploadBaseImage = (e) => {
    e.preventDefault();
    setIsImgChecked(true);
    setImageFile(null);
  };

  const onUploadImage = (e) => {
    // e.preventDefault();

    if (!e.target.files) {
      return;
    }

    setIsImgChecked(true);
    setImageFile(e.target.files[0]);
    // console.log(e.target.files[0]);
  };

  // 사용자 프로필 소개글
  const [infoMessage, setInfoMessage] = useState('');
  const [isInfo, setIsInfo] = useState(true);

  const onChangeInfo = (e) => {
    currentInfo.current = e.target.value;

    const maxByte = 150; //최대 100바이트
    const text_val = currentInfo.current; //입력한 문자
    const text_len = text_val.length; //입력한 문자수

    let totalByte = 0;
    for (let i = 0; i < text_len; i++) {
      const each_char = text_val.charAt(i);
      const uni_char = escape(each_char); //유니코드 형식으로 변환
      if (uni_char.length > 4) {
        // 한글 : 2Byte
        totalByte += 2;
      } else {
        // 영문,숫자,특수문자 : 1Byte
        totalByte += 1;
      }
    }

    if (totalByte > maxByte) {
      setInfoMessage('75자까지 입력 가능해요!');
      setIsInfo(false);
    } else {
      setInfoMessage('');
      setIsInfo(true);
    }
  };

  const userUpdate = (e) => {
    e.preventDefault();

    if (isNickname === true && isInfo === true) {
      axios_api
        .put('user/profile', {
          nickname: currentName.current,
          introduction: currentInfo.current,
        })
        .then(({ data }) => {
          if (data.statusCode === 200) {
            if (data.data.responseMessage === '회원 정보 수정 성공') {
              // 이미지를 변경하는 경우
              if (isImgChecked === true) {
                formData.append(
                  'value',
                  new Blob([JSON.stringify({})], {
                    type: 'application/json',
                  })
                );

                if (imageFile === null) {
                  formData.append('multipartFile', null);
                } else {
                  formData.append('multipartFile', imageFile);
                }

                // 이미지 업로드
                axios_api
                  .put('file/s3', formData, {
                    headers: {
                      'Content-Type': 'multipart/form-data',
                    },
                  })
                  .then(({ data }) => {
                    if (data.statusCode === 200) {
                      if (data.data.responseMessage === '그림 저장 성공') {
                        navigate(`/MyPage`, {
                          replace: true,
                        });
                      }
                    } else {
                      console.log('그림 저장 오류 : ');
                      console.log(data.statusCode);
                      console.log(data.data.responseMessage);
                    }
                  })
                  .catch(({ error }) => {
                    console.log('그림 저장 오류 : ' + error);
                  });
              } else {
                // 이미지를 변경하지 않는 경우
                navigate(`/MyPage`, {
                  replace: true,
                });
              }
            }
          } else {
            console.log('회원 정보 수정 오류: ');
            console.log(data.statusCode);
            console.log(data.data.responseMessage);
          }
        })
        .catch((error) => {
          console.log('회원 정보 수정 오류: ' + error);
        });
    } else {
      Swal.fire({
        icon: 'warning',
        text: '닉네임을 다시 확인해주세요.',
        width: '60%',
      }).then(function () {});
    }
  };

  useEffect(() => {
    onLogin();
    axios_api
      .get('user/profile')
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === '회원 정보 조회 성공') {
            setProfile(data.data.profile);
            currentName.current = data.data.profile.nickname;
            currentInfo.current = data.data.profile.introduction;
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
        <form>
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
              defaultValue={currentName.current || ''}
              onChange={onChangeNickname}
            ></input>
            <p className='text-sm'>{nicknameMessage}</p>
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
              defaultValue={currentInfo.current || ''}
              onChange={onChangeInfo}
            ></textarea>
            <p className='text-sm'>{infoMessage}</p>
          </div>

          <button
            className='hover:bg-sky-700 bg-cyan-600 text-white px-2.5 py-1 rounded-3xl m-auto block'
            onClick={userUpdate}
          >
            수정하기
          </button>
        </form>
      </div>
    </div>
  );
}

export default UserUpdate;

//textarea 바이트 수 체크하는 함수
function fn_checkByte(obj) {
  const maxByte = 200; //최대 100바이트
  const text_val = obj.value; //입력한 문자
  const text_len = text_val.length; //입력한 문자수

  let totalByte = 0;
  for (let i = 0; i < text_len; i++) {
    const each_char = text_val.charAt(i);
    const uni_char = escape(each_char); //유니코드 형식으로 변환
    if (uni_char.length > 4) {
      // 한글 : 2Byte
      totalByte += 2;
    } else {
      // 영문,숫자,특수문자 : 1Byte
      totalByte += 1;
    }
  }

  if (totalByte > maxByte) {
    alert('최대 100Byte까지만 입력가능합니다.');
    document.getElementById('nowByte').innerText = totalByte;
    document.getElementById('nowByte').style.color = 'red';
  } else {
    document.getElementById('nowByte').innerText = totalByte;
    document.getElementById('nowByte').style.color = 'green';
  }
}
