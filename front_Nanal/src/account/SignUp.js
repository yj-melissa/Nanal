import React, { useState } from "react";

function SignUp() {
  // 기본 형식 설명
  // [실질적인 내용부분]
  // [오류발생시 내용부분]
  // [형식 Boolean 값 나타내는 부분]
  // 형식 올바름 판별하는 함수()

  // 이메일 E-mail
  const [email, setEmail] = useState("");
  const [emailMessage, setEmailMessage] = useState("");
  const [isEmail, setIsEmail] = useState(false);
  const onChangeEmail = (e) => {
    const currentEmail = e.target.value;
    setEmail(currentEmail);
    const emailRegExp =
      /^[A-Za-z0-9_]+[A-Za-z0-9]*[@]{1}[A-Za-z0-9]+[A-Za-z0-9]*[.]{1}[A-Za-z]{1,3}$/;

    if (!emailRegExp.test(currentEmail)) {
      setEmailMessage("이메일의 형식이 올바르지 않습니다!");
      setIsEmail(false);
    } else {
      setEmailMessage("이메일의 형식이 올바릅니다.");
      setIsEmail(true);
    }
  };

  // 아이디 userId
  const [id, setId] = useState("");
  const [idMessage, setIdMessage] = useState("");
  const [isId, setIsId] = useState(false);
  const onChangeId = (e) => {
    const currentId = e.target.value;
    setId(currentId);
    const idRegExp = /^[a-zA-z0-9]{4,12}$/;

    if (!idRegExp.test(currentId)) {
      setIdMessage("4-12사이 대소문자 또는 숫자만 입력해 주세요!");
      setIsId(false);
    } else {
      setIdMessage("사용가능한 아이디 입니다.");
      setIsId(true);
    }
  };

  // 비밀번호
  const [password, setPassword] = useState("");
  const [passwordMessage, setPasswordMessage] = useState("");
  const [isPassword, setIsPassword] = useState(false);
  const onChangePassword = (e) => {
    const currentPassword = e.target.value;
    setPassword(currentPassword);
    const passwordRegExp =
      /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;
    if (!passwordRegExp.test(currentPassword)) {
      setPasswordMessage(
        "숫자+영문자+특수문자 조합으로 8자리 이상 입력해주세요!"
      );
      setIsPassword(false);
    } else {
      setPasswordMessage("안전한 비밀번호 입니다.");
      setIsPassword(true);
    }
  };

  // 비밀번호 확인
  const [passwordConfirm, setPasswordConfirm] = useState("");
  const [passwordConfirmMessage, setPasswordConfirmMessage] = useState("");
  const [isPasswordConfirm, setIsPasswordConfirm] = useState(false);
  const onChangePasswordConfirm = (e) => {
    const currentPasswordConfirm = e.target.value;
    setPasswordConfirm(currentPasswordConfirm);
    if (password !== currentPasswordConfirm) {
      setPasswordConfirmMessage("비밀번호가 틀렸습니다.");
      setIsPasswordConfirm(false);
    } else {
      setPasswordConfirmMessage("똑같은 비밀번호를 입력했습니다.");
      setIsPasswordConfirm(true);
    }
  };

  // 닉네임
  const [nickName, setNickName] = useState("");
  const [nickNameMessage, setNickNameMessage] = useState("");
  const [isNickName, setIsNickName] = useState(false);
  const onChangeNickName = (e) => {
    const currentName = e.target.value;
    setNickName(currentName);

    if (currentName.length < 2 || currentName.length > 8) {
      setNickNameMessage("닉네임은 2글자 이상 8글자 이하로 입력해주세요!");
      setIsNickName(false);
    } else {
      setNickNameMessage("사용가능한 닉네임 입니다.");
      setIsNickName(true);
    }
  };
  return (
    <div className="flex justify-center">
      <div className="box-border p-4 w-80 border-2 border-black">
        <h1 className="p-3">SignUp</h1>
        <form action="">
          {/* 이메일 email */}
          <div id="form-el">
            <label htmlFor="email">Email</label>
            <br />
            <div>
              <input
                type="text"
                id="email"
                value={email}
                onChange={onChangeEmail}
              />
              {/* onClick={this.sendEmail} */}
              <button>인증요청</button>
            </div>
            <p className="message">{emailMessage}</p>
          </div>
          {/* 유저아이디 id */}
          <div className="form-el">
            <label htmlFor="id">UserId</label> <br />
            <input id="id" name="id" value={id} onChange={onChangeId} />
            <p className="message"> {idMessage} </p>
          </div>
          {/* 비밀번호 password */}
          <div className="form-el">
            <label htmlFor="password">Password</label> <br />
            <input
              id="password"
              name="password"
              value={password}
              onChange={onChangePassword}
            />
            <p className="message">{passwordMessage}</p>
          </div>
          {/* 비밀번호 확인 passwordConfirm */}
          <div className="form-el">
            <label htmlFor="passwordConfirm">Password Confirm</label> <br />
            <input
              id="passwordConfirm"
              name="passwordConfirm"
              value={passwordConfirm}
              onChange={onChangePasswordConfirm}
            />
            <p className="message">{passwordConfirmMessage}</p>
          </div>
          {/* 닉네임 nickName */}
          <div className="form-el">
            <label htmlFor="user-nick-name">Nick Name</label> <br />
            <input
              id="user-nick-name"
              name="user-nick-name"
              value={nickName}
              onChange={onChangeNickName}
            />
            <p className="message">{nickNameMessage}</p>
          </div>
          <button>SignUp</button>
        </form>
      </div>
    </div>
  );
}

export default SignUp;
