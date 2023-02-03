import React, { useState } from 'react';
import axios_api from '../../config/Axios';

function SignUp() {
  // 기본 형식 설명
  // [실질적인 내용부분]
  // [오류발생시 내용부분]
  // [형식 Boolean 값 나타내는 부분]
  // 형식 올바름 판별하는 함수()

  // 이메일 E-mail
  const [email, setEmail] = useState('');
  const [emailMessage, setEmailMessage] = useState('');
  const [isEmail, setIsEmail] = useState(false);
  const [emailToggle, setEmailToggle] = useState(false);
  const [emailcheckToggle, setEmailCheckToggle] = useState(false);
  const [emailV1, setEmailV1] = useState('');
  const [emailV2, setEmailV2] = useState('');

  const onChangeEmail = (e) => {
    const currentEmail = e.target.value;
    setEmail(currentEmail);
    // const emailRegExp =
    //   /^[A-Za-z0-9_]+[A-Za-z0-9]*[@]{1}[A-Za-z0-9]+[A-Za-z0-9]*[.]{1}[A-Za-z]{1,3}$/;
    // if (!emailRegExp.test(currentEmail)) {
    //   setEmailMessage("이메일의 형식이 올바르지 않습니다!");
    //   setIsEmail(false);
    // } else {
    //   setEmailMessage("이메일의 형식이 올바릅니다.");
    //   setIsEmail(true);
    // }
    // setIsEmail(true);
  };

  const sendEmail = (emailstring) => {
    axios_api
      .get(`user/validate/${emailstring}`)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === '이메일 발송 성공') {
            setEmailV1(data.data.code);
            setEmailToggle(true);
          }
        } else {
          if (data.data.responseMessage === '사용 불가') {
            alert('이미 가입한 이메일 입니다.');
            window.location.replace('/SignIn');
          } else {
            alert('이메일을 확인하고 다시 입력해주세요.');
          }
        }
      })
      .catch((error) => {
        console.log('이메일 인증 과정 오류: ' + error);
      });
  };

  const onChangeEmailValidation = (e) => {
    setEmailV2(e.target.value);
  };

  const checkEmail = () => {
    if (emailV2 !== '') {
      if (emailV1 === emailV2) {
        setIsEmail(true);
        setEmailToggle(false);
        setEmailCheckToggle(true);
      } else {
        alert('이메일 인증 코드를 다시 확인해주세요!');
      }
    }
  };

  // 아이디 userId
  const [id, setId] = useState('');
  const [idMessage, setIdMessage] = useState('');
  const [isId, setIsId] = useState(false);
  const onChangeId = (e) => {
    const currentId = e.target.value;
    setId(currentId);
    const idRegExp = /^[a-zA-z0-9]{4,12}$/;

    if (!idRegExp.test(currentId)) {
      setIdMessage('4-12사이 대소문자 또는 숫자만 입력해 주세요!');
      setIsId(false);
    } else {
      axios_api
        .get(`user/check/id/${currentId}`)
        .then(({ data }) => {
          if (data.statusCode === 200) {
            if (data.data.responseMessage === '사용 가능') {
              setIdMessage('사용가능한 아이디 입니다.');
              setIsId(true);
            }
          } else {
            setIdMessage('중복된 아이디 입니다.');
            setIsId(false);
          }
        })
        .catch((error) => {
          console.log('회원 가입 오류: ' + error);
        });
    }
  };

  // 비밀번호
  const [password, setPassword] = useState('');
  const [passwordMessage, setPasswordMessage] = useState('');
  const [isPassword, setIsPassword] = useState(false);
  const onChangePassword = (e) => {
    const currentPassword = e.target.value;
    setPassword(currentPassword);
    const passwordRegExp =
      /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;
    if (!passwordRegExp.test(currentPassword)) {
      setPasswordMessage(
        '숫자+영문자+특수문자 조합으로 8자리 이상 입력해주세요!'
      );
      setIsPassword(false);
    } else {
      setPasswordMessage('안전한 비밀번호 입니다.');
      setIsPassword(true);
    }
  };

  // 비밀번호 확인
  const [passwordConfirm, setPasswordConfirm] = useState('');
  const [passwordConfirmMessage, setPasswordConfirmMessage] = useState('');
  const [isPasswordConfirm, setIsPasswordConfirm] = useState(false);
  const onChangePasswordConfirm = (e) => {
    const currentPasswordConfirm = e.target.value;
    setPasswordConfirm(currentPasswordConfirm);
    if (password !== currentPasswordConfirm) {
      setPasswordConfirmMessage('비밀번호가 틀렸습니다.');
      setIsPasswordConfirm(false);
    } else {
      setPasswordConfirmMessage('똑같은 비밀번호를 입력했습니다.');
      setIsPasswordConfirm(true);
    }
  };

  // 닉네임
  const [nickName, setNickName] = useState('');
  const [nickNameMessage, setNickNameMessage] = useState('');
  const [isNickName, setIsNickName] = useState(false);
  const onChangeNickName = (e) => {
    const currentName = e.target.value;
    setNickName(currentName);

    if (currentName.length < 2 || currentName.length > 8) {
      setNickNameMessage('닉네임은 2글자 이상 8글자 이하로 입력해주세요!');
      setIsNickName(false);
    } else {
      axios_api
        .get(`user/check/nickname/${currentName}`)
        .then(({ data }) => {
          if (data.statusCode === 200) {
            if (data.data.responseMessage === '사용 가능') {
              setNickNameMessage('사용가능한 닉네임 입니다.');
              setIsNickName(true);
            }
          } else {
            setNickNameMessage('중복된 닉네임 입니다.');
            setIsNickName(false);
          }
        })
        .catch((error) => {
          console.log('회원 가입 오류: ' + error);
        });
    }
  };

  // 회원가입
  const SignUp = (e) => {
    e.preventDefault();

    if (isEmail !== true) {
      alert('이메일 인증을 확인해주세요.');
    } else if (isId !== true) {
      alert('아이디를 확인해주세요.');
    } else if (isPassword !== true) {
      alert('비밀번호를 확인해주세요.');
    } else if (isPasswordConfirm !== true) {
      alert('비밀번호를 맞게 입력했는지 확인해주세요.');
    } else if (isNickName !== true) {
      alert('닉네임을 확인해주세요.');
    } else {
      axios_api
        .post('user/signup', {
          email: email,
          userId: id,
          password: password,
          nickname: nickName,
        })
        .then(({ data }) => {
          // console.log(data);
          // console.log(data.data);
          // console.log(data.statusCode);
          // console.log(data.data.ResponseMessage);
          if (data.statusCode === 200) {
            if (data.data.responseMessage === '회원 가입 성공') {
              alert('회원 가입 성공!!!');
              window.location.replace('/SignIn');
            }
          } else {
            console.log(data.data.responseMessage);
            alert('이미 가입된 이메일입니다!');
            setEmail('');
            setId('');
            setPassword('');
            setPasswordConfirm('');
            setNickName('');
          }
        })
        .catch((error) => {
          console.log('회원 가입 오류: ' + error);
        });
    }
  };

  return (
    <div className='flex justify-center'>
      <div className='box-border p-4 w-80 border-[1px] border-gray-500 border-solid'>
        <h1 className='p-3'>SignUp</h1>
        <form action='' onSubmit={SignUp}>
          {/* 이메일 email */}
          <div className='m-1'>
            <label htmlFor='email'>Email</label>
            <br />
            <div>
              <input
                type='email'
                id='email'
                className='mr-5 mb-2'
                value={email}
                onChange={onChangeEmail}
              />
              <button
                type='button'
                onClick={() => {
                  sendEmail(email);
                }}
              >
                인증요청
              </button>
              {emailToggle === true ? (
                <div>
                  <input
                    type='text'
                    className='mr-5'
                    onChange={onChangeEmailValidation}
                  ></input>
                  <button
                    type='button'
                    onClick={() => {
                      checkEmail();
                    }}
                  >
                    확인
                  </button>
                </div>
              ) : emailcheckToggle === true ? (
                <p>이메일 인증이 완료 되었습니다.</p>
              ) : (
                <></>
              )}
            </div>
            <p className='message'>{emailMessage}</p>
          </div>
          {/* 유저아이디 id */}
          <div className='m-1'>
            <label htmlFor='id'>UserId</label> <br />
            <input
              type='text'
              id='id'
              name='id'
              value={id}
              onChange={onChangeId}
            />
            <p className='message'> {idMessage} </p>
          </div>
          {/* 비밀번호 password */}
          <div className='m-1'>
            <label htmlFor='password'>Password</label> <br />
            <input
              type='password'
              id='password'
              name='password'
              value={password}
              onChange={onChangePassword}
            />
            <p className='message'>{passwordMessage}</p>
          </div>
          {/* 비밀번호 확인 passwordConfirm */}
          <div className='m-1'>
            <label htmlFor='passwordConfirm'>Password Confirm</label> <br />
            <input
              type='password'
              id='passwordConfirm'
              name='passwordConfirm'
              value={passwordConfirm}
              onChange={onChangePasswordConfirm}
            />
            <p className='message'>{passwordConfirmMessage}</p>
          </div>
          {/* 닉네임 nickName */}
          <div className='m-1'>
            <label htmlFor='user-nick-name'>Nick Name</label> <br />
            <input
              type='text'
              id='user-nick-name'
              name='user-nick-name'
              value={nickName}
              onChange={onChangeNickName}
            />
            <p className='message'>{nickNameMessage}</p>
          </div>
          <div className='mx-1 mt-2'>
            <button type='submit'>SignUp</button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default SignUp;
