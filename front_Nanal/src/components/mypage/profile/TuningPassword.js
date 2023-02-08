import { useState } from "react"
import axios_api from "../../../config/Axios"
import downArrow from '../../../src_assets/img/arrow_drop_down.png'
import upArrow from '../../../src_assets/img/arrow_drop_up.png'
import Swal from 'sweetalert2';

const TuningPassword = () => { 
  const [isPassConfirm, setIsPassConfirm] = useState(false)
  const [isClick, setIsClick] = useState(false)
  const [prevPassword, setPrevPassword] = useState('')
  const [password, setPassword] = useState('')
  const [passwordMessage, setPasswordMessage] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('')
  const [passwordConfirmMessage, setPasswordConfirmMessage] = useState('');

  const onChangePrevPassword = (e) => {
    const currentPrevPassword = e.target.value;
    setPrevPassword(currentPrevPassword);
    axios_api
      .post('user/password', {
        password: currentPrevPassword
      })
      .then(({ data }) => {
        if (data.statusCode === 200) {
          Swal.fire({
            icon: 'success', // Alert 타입
            text: '비밀번호 확인완료!', // Alert 내용
            width: '60%',
          })
          setPassword('')
          setIsPassConfirm(true)
        }
      })
      .catch(({ error }) => {
        console.log('비밀번호 확인 오류: ' + error);
      });
    
  }
  const onChangePassword = (e) => {
    const currentPassword = e.target.value;
    setPassword(currentPassword);
    const passwordRegExp =
      /^(?=.*[a-zA-Z])(?=.*[`~!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;
    if (!passwordRegExp.test(currentPassword)) {
      setPasswordMessage(
        '숫자+영문자+특수문자 조합으로 8자리 이상 입력해주세요!'
      );
    } else {
      setPasswordMessage('안전한 비밀번호 입니다.');
    }
  };
  
  const onChangePasswordConfirm = (e) => {
    const currentPasswordConfirm = e.target.value;
    setPasswordConfirm(currentPasswordConfirm);
    if (password !== currentPasswordConfirm) {
      setPasswordConfirmMessage('비밀번호가 틀렸습니다.');
    } else {
      setPasswordConfirmMessage('똑같은 비밀번호를 입력했습니다.');
    }
  };

  const changePassword = () => {
    if (passwordConfirmMessage === '똑같은 비밀번호를 입력했습니다.' && passwordMessage === '안전한 비밀번호 입니다.') {
      axios_api
        .put('user/password', {
          password: password
        })
        .then(({ data }) => {
          if (data.statusCode === 200) {
            if (data.data.responseMessage === '비밀번호 변경 성공') {
              Swal.fire({
                icon: 'success',
                text: '비밀번호가 변경되었습니다!',
                width: '60%',
              }).then(() => {
                setPassword('')
                setPasswordConfirm('')
                setIsPassConfirm(false)
                changeClickFalse()
              });
            }
          }
        })
        .catch(({ error }) => {
          console.log('비밀번호 확인 오류: ' + error);
        });
    } else {
      Swal.fire({
        icon: 'warning',
        text: '비밀번호를 다시 확인해주세요!',
        width: '60%',
      }).then(function () {});
    }
  }

  const changeClickTrue = () => {
    setIsClick(true)
  }
  const changeClickFalse = () => {
    setIsClick(false)
  }
  if (isClick === false) {
    return <div
      className='flex justify-between box-border border rounded-lg h-12 border-black indent-4 bg-emerald-200/75'
      onClick={changeClickTrue}
    >
      <div className="self-center">비밀번호 수정</div>
      <img src={downArrow} className='self-center' />
    </div>
  } else {
    if (isPassConfirm === false) {
      return <div>
      <div
        className='flex justify-between box-border border rounded-lg h-12 border-black indent-4 bg-emerald-200/75'
        onClick={changeClickFalse}
      >
        <div className="self-center">비밀번호 수정</div>
        <img src={upArrow} className='self-center' />
      </div>
      <div>
        <p>현재 비밀번호를 입력해주세요.</p>
        <input
          type="password"
          value={prevPassword}
          onChange={onChangePrevPassword}
          />
      </div>
    </div>
    } else {
      return <div>
      <div
        className='flex justify-between box-border border rounded-lg h-12 border-black indent-4 bg-emerald-200/75'
          onClick={() => { changeClickFalse(); setIsPassConfirm(false); }}
      >
        <div className="self-center">비밀번호 수정</div>
        <img src={upArrow} className='self-center' />
      </div>
      <div> 
        <p>변경할 비밀번호를 입력해주세요.</p>
        <input type="password" value={password} onChange={onChangePassword} />
        <p className='text-sm'>{passwordMessage}</p>
        <p>변경할 비밀번호를 한 번 더 입력해주세요.</p>
        <input type="password" value={passwordConfirm} onChange={onChangePasswordConfirm}/>  
        <p className='text-sm'>{passwordConfirmMessage}</p>
      </div>
      <button onClick={changePassword}>비밀번호 변경</button>
    </div>
    }
    
  }
}

export default TuningPassword