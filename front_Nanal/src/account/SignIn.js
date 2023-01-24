import React, { useState } from "react";
import { useNavigate } from "react-router";
import { useDispatch } from "react-redux";
import { useForm } from "react-hook-form";
import { Link } from "react-router-dom";

import { loginUser } from "../config/Users";
import { setRefreshToken } from "../config/Cookie";
import { SET_TOKEN } from "../config/Auth";

function SignIn() {
  const [userId, setUserId] = useState("");
  const [pw, setPw] = useState("");

  const onChangeId = (e) => {
    setUserId(e.target.value);
  };

  const onChangePw = (e) => {
    setPw(e.target.value);
  };

  const onSubmit = (e) => {
    e.preventDefault();
  };

  const navigate = useNavigate();
  const dispatch = useDispatch();

  // useForm 사용을 위한 선언
  const {
    register,
    setValue,
    formState: { errors },
    handleSubmit,
  } = useForm();

  // submit 이후 동작할 코드
  // 백으로 유저 정보 전달
  const onValid = async ({ userid, password }) => {
    // input 태그 값 비워주는 코드
    setValue("password", "");

    // 백으로부터 받은 응답
    const response = await loginUser({ userid, password });

    if (response.status) {
      // 쿠키에 Refresh Token, store에 Access Token 저장
      setRefreshToken(response.json.refresh_token);
      dispatch(SET_TOKEN(response.json.access_token));

      return navigate("/");
    } else {
      console.log(response.json);
    }
  };

  return (
    <div>
      <div className="DiaryEditor">
        <h1> SignIn to 나날</h1>
        <div id="sign-in-form">
          <form action="" onSubmit={onSubmit}>
            <label htmlFor="user-id">ID: </label>
            <input
              type="text"
              id="user-id"
              placeholder="아이디"
              onChange={onChangeId}
              value={userId}
            />
            <br />
            <label htmlFor="user-password">PW: </label>
            <input
              type="text"
              id="user-password"
              placeholder="비밀번호"
              onChange={onChangePw}
              value={pw}
            />
            <div>
              <button>Sign In</button>
              <br />
              <Link to="/SignUp">SignUp</Link>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}

export default SignIn;
